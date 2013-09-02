package de.lsn.playground.ws1;

import javax.websocket.DeploymentException;

import org.glassfish.tyrus.server.Server;

public class TyrusServer {

	public static void main(String[] args) {
		Server server = new Server("localhost", 8025, "/", EchoBeanService.class);
		try {
			server.start();
		} catch (DeploymentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
