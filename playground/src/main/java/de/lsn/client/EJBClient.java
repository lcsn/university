package de.lsn.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import de.lsn.playground.logic.TestEJBRemote;

public class EJBClient {

	public static void main(String[] args) {
		try {
			System.setProperty("org.omg.CORBA.ORBInitialHost", "localhost"); 
			Properties properties = new Properties(); 
			properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory"); 
			properties.put(Context.URL_PKG_PREFIXES, "com.sun.enterprise.naming"); 
			properties.put(Context.STATE_FACTORIES, "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl"); 
			properties.put("org.omg.CORBA.ORBInitialHost", "localhost"); 
			properties.put("org.omg.CORBA.ORBInitialPort", "3700"); 

			InitialContext ctx = new InitialContext(properties);

//			ctx.addToEnvironment("org.omg.CORBA.ORBInitialHost", "localhost"); 
//			ctx.addToEnvironment("org.omg.CORBA.ORBInitialPort", "3700");
			
			TestEJBRemote remote = (TestEJBRemote) ctx.lookup("java:global/zgame_ear/zgame_ejb/TestEJB!de.lsn.playground.logic.TestEJBRemote");
			remote.moo();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
}

