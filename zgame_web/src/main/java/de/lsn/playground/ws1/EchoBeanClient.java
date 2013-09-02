package de.lsn.playground.ws1;

import static java.lang.System.out;

import java.io.IOException;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

@ClientEndpoint(encoders = { EchoBean.EchoBeanCode.class }, decoders = { EchoBean.EchoBeanCode.class })
public class EchoBeanClient extends Endpoint {
	public void onOpen(final Session session, EndpointConfig endpointConfig) {

		out.println("Client Connection open " + session + " " + endpointConfig);

		// Add a listener to capture the returning event
		//

		session.addMessageHandler(new MessageHandler.Whole() {

			@Override
			public void onMessage(Object bean) {
				out.println("Message from server : " + ((EchoBean)bean).getReply());

				out.println("Closing connection");
				try {
					session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "All fine"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		// Once we are connected we can now safely send out initial message to
		// the server
		//

		out.println("Sending message to server");
		try {
			EchoBean bean = new EchoBean("Hello");
			session.getBasicRemote().sendObject(bean);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EncodeException e) {
			e.printStackTrace();
		}

	}
}
