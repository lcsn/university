package de.lsn.jersey;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.SslConfigurator;


public class JerseyConnector {

	public static void main(String[] args) {
//		ClientConfig clientConfig = new DefaultClientConfig();
//		clientConfig.getClasses().add(JacksonJsonProvider.class);
//		Client client = Client.create(clientConfig);
		
//		JerseyClient client = ClientBuilder.newClient();

		SslConfigurator sslConfig = SslConfigurator.newInstance()
		        .trustStoreFile("./truststore_client")
		        .trustStorePassword("secret-password-for-truststore")
		        .keyStoreFile("./keystore_client")
		        .keyPassword("secret-password-for-keystore");
		 
		SSLContext sslContext = sslConfig.createSSLContext();
		Client client = ClientBuilder.newBuilder().sslContext(sslContext).build();
		
		String entity = client.target("https://example.com/rest")
        .path("resource/helloworld")
        .queryParam("greeting", "Hi World!")
        .request(MediaType.TEXT_PLAIN_TYPE)
        .header("some-header", "true")
        .get(String.class);
        
		/*
        GrizzlyConnector???????
        
        
        */
		
	}
	
}
