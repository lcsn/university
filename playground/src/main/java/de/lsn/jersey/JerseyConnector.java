package de.lsn.jersey;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.JerseyClient;


public class JerseyConnector {

	public static void main(String[] args) {
//		ClientConfig clientConfig = new DefaultClientConfig();
//		clientConfig.getClasses().add(JacksonJsonProvider.class);
//		Client client = Client.create(clientConfig);
		
//		JerseyClient client = ClientBuilder.newClient();

		/*
		Client client = ClientBuilder.newClient(new ClientConfig()
        .register(MyClientResponseFilter.class)
        .register(new AnotherClientFilter()));

String entity = client.target("http://example.com/rest")
        .register(FilterForExampleCom.class)
        .path("resource/helloworld")
        .queryParam("greeting", "Hi World!")
        .request(MediaType.TEXT_PLAIN_TYPE)
        .header("some-header", "true")
        .get(String.class);
        
        GrizzlyConnector???????
        
        
        */
		
	}
	
}
