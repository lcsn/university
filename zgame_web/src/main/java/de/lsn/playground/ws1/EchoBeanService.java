package de.lsn.playground.ws1;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/echo", encoders = { EchoBean.EchoBeanCode.class }, decoders = { EchoBean.EchoBeanCode.class })
public class EchoBeanService {

	@OnMessage
	public void echo(EchoBean bean, Session peer) throws IOException, EncodeException {
		//
		bean.setReply("Server says " + bean.getMessage());
		System.out.println("Sending message to client");
		peer.getBasicRemote().sendObject(bean);
	}

	@OnOpen
	public void onOpen(final Session session, EndpointConfig endpointConfig) {
		System.out.println("Server connected " + session + " " + endpointConfig);
	}

}
