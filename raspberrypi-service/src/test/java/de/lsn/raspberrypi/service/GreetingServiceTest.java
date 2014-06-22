package de.lsn.raspberrypi.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.openejb.jee.WebApp;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.testing.Classes;
import org.apache.openejb.testing.EnableServices;
import org.apache.openejb.testing.Module;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.lsn.raspberrypi.service.GreetingService;

@EnableServices(value = "jaxrs")
@RunWith(ApplicationComposer.class)
public class GreetingServiceTest {

    @Module
    @Classes(GreetingService.class)
    public WebApp app() {
        return new WebApp().contextRoot("raspberrypi-service");
    }

    @Test
    public void get0() throws IOException {
        final String message = WebClient.create("http://localhost:4204").path("/raspberrypi-service/greeting/lars").get(String.class);
        assertEquals("Hi, lars!", message);
    }
    
    @Test
    public void get1() throws IOException {
        final String message = WebClient.create("http://localhost:4204").path("/raspberrypi-service/greeting/").get(String.class);
        assertEquals("Hi REST!", message);
    }

    @Test
    public void post() throws IOException {
        final String message = WebClient.create("http://localhost:4204").path("/raspberrypi-service/greeting/").post("Hi REST!", String.class);
        assertEquals("hi rest!", message);
    }
    
}
