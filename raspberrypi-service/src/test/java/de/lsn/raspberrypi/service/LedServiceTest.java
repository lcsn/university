package de.lsn.raspberrypi.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.openejb.jee.WebApp;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.testing.Classes;
import org.apache.openejb.testing.EnableServices;
import org.apache.openejb.testing.Module;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.lsn.raspberrypi.service.LedService;

@EnableServices(value = "jaxrs")
@RunWith(ApplicationComposer.class)
public class LedServiceTest {

    @Module
    @Classes(LedService.class)
    public WebApp app() {
        return new WebApp().contextRoot("raspberrypi-service");
    }

    @Test
    public void put0() throws IOException {
    	ObjectMapper m = new ObjectMapper(new JsonFactory());
    	String json = m.writeValueAsString(new Boolean(true));
        final Response response = WebClient.create("http://localhost:4204").path("/raspberrypi-service/led/on").put(json);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void put1() throws IOException {
    	ObjectMapper m = new ObjectMapper(new JsonFactory());
    	String json = m.writeValueAsString(new Boolean(false));
        final Response response = WebClient.create("http://localhost:4204").path("/raspberrypi-service/led/off").put(json);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
    
    @Test
    public void put2() throws IOException {
    	final Response response = WebClient.create("http://localhost:4204").path("/raspberrypi-service/led/on").put(true);
    	assertEquals(Status.OK.getStatusCode(), response.getStatus());
    	final Response response1 = WebClient.create("http://localhost:4204").path("/raspberrypi-service/led/on").put(false);
    	assertEquals(Status.BAD_REQUEST.getStatusCode(), response1.getStatus());
    }
    
    @Test
    public void put3() throws IOException {
    	final Response response = WebClient.create("http://localhost:4204").path("/raspberrypi-service/led/off").put(true);
    	assertEquals(Status.OK.getStatusCode(), response.getStatus());
    	final Response response1 = WebClient.create("http://localhost:4204").path("/raspberrypi-service/led/off").put(false);
    	assertEquals(Status.BAD_REQUEST.getStatusCode(), response1.getStatus());
    }
    
    @Test
    public void put4() throws IOException {
    	final Response response = WebClient.create("http://localhost:4204").path("/raspberrypi-service/led/ok").put(null);
    	assertEquals(Status.OK.getStatusCode(), response.getStatus());
    	final Response response1 = WebClient.create("http://localhost:4204").path("/raspberrypi-service/led/fail").put(null);
    	assertEquals(Status.BAD_REQUEST.getStatusCode(), response1.getStatus());
    }
    
}
