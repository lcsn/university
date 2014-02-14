package de.lsn.playground.zgame.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import de.lsn.playground.MyConfigurator;

@ServerEndpoint(value="/ws/player", configurator=MyConfigurator.class)
public class PlayerSocket {
    
	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());

	
    @OnMessage
    public void sayHello(String message, Session session) throws IOException {
    	synchronized (clients) {
			// Iterate over the connected sessions
			// and broadcast the received message
			for (Session client : clients) {
				if (!client.equals(session)) {
					client.getBasicRemote().sendText(message);
				}
			}
		}
    }
    
    @OnOpen
	public void onOpen(Session session) {
		// Add session to the connected sessions set
		clients.add(session);
	}

	@OnClose
	public void onClose(Session session) {
		// Remove session from the connected sessions set
		clients.remove(session);
	}
    
}