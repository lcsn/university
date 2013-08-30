package de.lsn.client;

import java.util.Properties;

import javax.naming.Context;

import de.lsn.playground.entity.unit.Unit;
import de.lsn.playground.framwork.remote.RemoteInterfaceFactory;
import de.lsn.playground.logic.unit.UnitDAORemote;
import de.lsn.playground.logic.unit.service.UnitServiceDAORemote;

public class EJBClient {

	public static void main(String[] args) {
		
		
		System.setProperty("org.omg.CORBA.ORBInitialHost", "localhost"); 
		Properties properties = new Properties(); 
		properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory"); 
		properties.put(Context.URL_PKG_PREFIXES, "com.sun.enterprise.naming"); 
		properties.put(Context.STATE_FACTORIES, "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl"); 
		properties.put("org.omg.CORBA.ORBInitialHost", "localhost"); 
		properties.put("org.omg.CORBA.ORBInitialPort", "3700"); 
//			ctx.addToEnvironment("org.omg.CORBA.ORBInitialHost", "localhost"); 
//			ctx.addToEnvironment("org.omg.CORBA.ORBInitialPort", "3700");

//			TestEJBRemote remote = (TestEJBRemote) ctx.lookup("java:global/zgame_ear/zgame_ejb/TestEJB!de.lsn.playground.logic.TestEJBRemote");
		RemoteInterfaceFactory.recreateInitialContext(properties);
//		TestEJBRemote remote = RemoteInterfaceFactory.getInstance().getCachedRemote(TestEJBRemote.class);
//		remote.moo();
//		remote.testPersistence(10);
//		remote = null;
//		remote = RemoteInterfaceFactory.getInstance().getCachedRemote(TestEJBRemote.class);
//		remote.moo();
//		remote.testPersistence(10);
		
//		UnitDefinition unitDefinition = RemoteInterfaceFactory.getInstance().getCachedRemote(UnitDefinitionDAORemote.class).findUnitDefintionById(1l);
//		System.out.println(unitDefinition.getId());
		
		UnitServiceDAORemote unitServiceDAORemote = RemoteInterfaceFactory.getInstance().getCachedRemote(UnitServiceDAORemote.class);
		UnitDAORemote unitDAORemote = RemoteInterfaceFactory.getInstance().getCachedRemote(UnitDAORemote.class);
		
		Unit newUnit = unitServiceDAORemote.createUnit(1l);
		System.out.println(unitDAORemote.findUnitById(newUnit.getId()));
		
		newUnit = unitServiceDAORemote.createUnit(2l);
		System.out.println(unitDAORemote.findUnitById(newUnit.getId()));

		newUnit = unitServiceDAORemote.createUnit(3l);
		System.out.println(unitDAORemote.findUnitById(newUnit.getId()));
		
		newUnit = unitServiceDAORemote.createUnit(4l);
		System.out.println(unitDAORemote.findUnitById(newUnit.getId()));
		
		newUnit = unitServiceDAORemote.createUnit(5l);
		System.out.println(unitDAORemote.findUnitById(newUnit.getId()));
		
		newUnit = unitServiceDAORemote.createUnit(6l);
		System.out.println(unitDAORemote.findUnitById(newUnit.getId()));
		
		
	}
	
}

