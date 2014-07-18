package de.lsn.raspberrypi;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriBuilderException;
import javax.ws.rs.core.Response.Status;

public class JerseyMotorConnector {

	private URI uri;
	private String uriAsString;
	private Client client;
	private boolean started;
	
	private static JerseyMotorConnector instance;
	
	public static JerseyMotorConnector getInstance() {
		if (null == instance) {
			instance = new JerseyMotorConnector();
		}
		return instance;
	}
	
	public void start(String uriAsString) {
		if (!started) {
			this.uriAsString = uriAsString;
			try {
				this.uri = UriBuilder.fromUri(new URI(uriAsString)).build();
			} catch (IllegalArgumentException | UriBuilderException | URISyntaxException e) {
				e.printStackTrace();
			}
			this.client = ClientBuilder.newClient();
			started = true;
		}
	}
	
	public String get(String path) throws Exception {
		validateUri();
		WebTarget webTarget = client.target(uri).path(path);
		Response response = webTarget.request(MediaType.TEXT_PLAIN).get();
		validateResponse(response);
		return asString(response);
	}
	
	
	public String put(String path) throws Exception {
		validateUri();
		WebTarget webTarget = client.target(uri).path(path);
		Response response = webTarget.request(MediaType.TEXT_PLAIN).put(Entity.entity("", MediaType.TEXT_PLAIN));
		validateResponse(response);
		return asString(response);
	}
	
	public String post(String path) throws Exception {
		validateUri();
		WebTarget webTarget = client.target(uri).path(path);
		Response response = webTarget.request(MediaType.TEXT_PLAIN).post(Entity.entity("", MediaType.TEXT_PLAIN));
		validateResponse(response);
		return asString(response);
	}
	
	private void validateResponse(Response response) throws Exception {
		if (response.getStatus() != Status.OK.getStatusCode()) {
			throw new Exception("Response status not ok: " + response.getStatus()+", Message: "+asString(response));
		}
	}
	
	private void validateUri() throws Exception {
		if (null == uri) {
			throw new Exception("Connector not started - usage: call start(..) first");
		}
	}
	
	public String asString(Response response) {
		return response.readEntity(String.class);
	}

	public URI getUri() {
		return uri;
	}
	
	public String getUriAsString() {
		return uriAsString;
	}
	
	public static void main(String[] args) {
		
		JerseyMotorConnector.getInstance().start("http://localhost:8080/raspberrypi-service");
		try {
			JerseyMotorConnector.getInstance().get("/greeting");
			String s = JerseyMotorConnector.getInstance().get("/greeting/lars");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
}
