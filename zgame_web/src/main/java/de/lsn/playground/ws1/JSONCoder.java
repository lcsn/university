package de.lsn.playground.ws1;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JSONCoder<T> implements Encoder.TextStream<T>, Decoder.TextStream<T> {

	private Class<T> _type;

	// When configured my read in that ObjectMapper is not thread safe
	//
	private ThreadLocal<ObjectMapper> _mapper = new ThreadLocal<ObjectMapper>() {

		@Override
		protected ObjectMapper initialValue() {
			return new ObjectMapper();
		}
	};

	@Override
	public void init(EndpointConfig endpointConfig) {

		ParameterizedType $thisClass = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type $T = $thisClass.getActualTypeArguments()[0];
		if ($T instanceof Class) {
			_type = (Class<T>) $T;
		} else if ($T instanceof ParameterizedType) {
			_type = (Class<T>) ((ParameterizedType) $T).getRawType();
		}
	}

	@Override
	public void encode(T object, Writer writer) throws EncodeException, IOException {
		_mapper.get().writeValue(writer, object);
	}

	@Override
	public T decode(Reader reader) throws DecodeException, IOException {
		return _mapper.get().readValue(reader, _type);
	}

	@Override
	public void destroy() {

	}

}
