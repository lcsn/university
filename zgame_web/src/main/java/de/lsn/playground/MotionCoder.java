package de.lsn.playground;

import java.io.IOException;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.lsn.playground.zgame.websocket.MotionBean;

public class MotionCoder implements Encoder.Text<MotionBean>, Decoder.Text<MotionBean> {

	private ThreadLocal<ObjectMapper> _mapper = new ThreadLocal<ObjectMapper>() {
		@Override
		protected ObjectMapper initialValue() {
			return new ObjectMapper();
		}
	};
	
	@Override
	public void init(EndpointConfig endpointConfig) {
	}

	@Override
	public void destroy() {
	}
	
	@Override
	public String encode(MotionBean arg0) throws EncodeException {
		String res = null;
		try {
			res = _mapper.get().writeValueAsString(arg0);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	@Override
	public MotionBean decode(String arg0) throws DecodeException {
		MotionBean mb = null;
		try {
			mb = _mapper.get().readValue(arg0, MotionBean.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mb;
	}

	@Override
	public boolean willDecode(String arg0) {
		return true;
	}

}
