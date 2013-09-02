package de.lsn.playground.ws;

import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

import de.lsn.playground.MyConfigurator;

@ServerEndpoint(value="/ws", configurator=MyConfigurator.class)
public class EchoBean {
    
    @OnMessage
    public String sayHello(String name) {
        return "Hello " + name + "!";
    }
    
}