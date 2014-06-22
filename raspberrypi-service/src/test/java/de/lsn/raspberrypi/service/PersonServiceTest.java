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

import de.lsn.raspberrypi.model.Person;
import de.lsn.raspberrypi.service.GreetingService;
import de.lsn.raspberrypi.util.MockDB;

@EnableServices(value = "jaxrs")
@RunWith(ApplicationComposer.class)
public class PersonServiceTest {

    @Module
    @Classes(PersonService.class)
    public WebApp app() {
        return new WebApp().contextRoot("raspberrypi-service");
    }

    @Test
    public void get0() throws IOException {
//    	Person person = MockDB.persons.get(1);
//    	final String message = WebClient.create("http://localhost:4204").path("/raspberrypi-service/person/1/xml").get(String.class);
//        assertEquals("Hi, lars!", message);
    }
    
}
