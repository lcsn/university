package de.lsn.playground.ws1;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.Decoder;
import javax.websocket.DeploymentException;
import javax.websocket.Encoder;
import javax.websocket.Session;

import org.glassfish.tyrus.client.ClientManager;

public class TyrusClient {

	public static void main(String[] args) throws InterruptedException {
		ClientManager client = ClientManager.createClient();
		EchoBeanClient beanClient = new EchoBeanClient();
		 
		Session session = null;
		try {
			session = client.connectToServer(
			        beanClient, 
			        ClientEndpointConfig.Builder.create()
			         .encoders(Arrays.<Class<? extends Encoder>>asList(EchoBean.EchoBeanCode.class))
			         .decoders(Arrays.<Class<? extends Decoder>>asList(EchoBean.EchoBeanCode.class))
			         .build(),
			        URI.create("ws://localhost:8025/echo"));
		} catch (DeploymentException | IOException e) {
			e.printStackTrace();
		}
		 
		// Wait until things are closed down
		 
		while (session.isOpen()) {
		    System.out.println("Waiting");
		    TimeUnit.MILLISECONDS.sleep(10);
		}
	}
	
}
