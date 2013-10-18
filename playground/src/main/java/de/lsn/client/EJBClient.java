package de.lsn.client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;

import org.apache.commons.io.FileUtils;

import de.lsn.playground.entity.attribute.Coord;
import de.lsn.playground.entity.attribute.Name;
import de.lsn.playground.entity.map.FieldDefinition;
import de.lsn.playground.entity.map.MapDefinition;
import de.lsn.playground.entity.map.Terrain;
import de.lsn.playground.entity.map.TerrainType;
import de.lsn.playground.entity.unit.Unit;
import de.lsn.playground.entity.unit.UnitDefinition;
import de.lsn.playground.framwork.exception.ZgameException;
import de.lsn.playground.framwork.remote.RemoteInterfaceFactory;
import de.lsn.playground.logic.game.field.FieldServiceDAORemote;
import de.lsn.playground.logic.game.map.MapServiceDAORemote;
import de.lsn.playground.logic.test.TestEJBRemote;
import de.lsn.playground.logic.unit.UnitDAORemote;
import de.lsn.playground.logic.unit.definition.UnitDefinitionDAORemote;
import de.lsn.playground.logic.unit.service.UnitServiceDAORemote;

public class EJBClient {
	
	private static MapServiceDAORemote mapServiceDAORemote;
	private static FieldServiceDAORemote fieldServiceDAORemote;
	private static UnitDefinitionDAORemote unitDefinitionDAORemote;
	private static UnitServiceDAORemote unitServiceDAORemote;
	private static UnitDAORemote unitDAORemote;
	private static TestEJBRemote testEJBRemote; 
	
	private static void getRemoteInterfaces() {
		mapServiceDAORemote = RemoteInterfaceFactory.getInstance().getCachedRemote(MapServiceDAORemote.class);
		fieldServiceDAORemote = RemoteInterfaceFactory.getInstance().getCachedRemote(FieldServiceDAORemote.class);
		unitDefinitionDAORemote = RemoteInterfaceFactory.getInstance().getCachedRemote(UnitDefinitionDAORemote.class);
		unitServiceDAORemote = RemoteInterfaceFactory.getInstance().getCachedRemote(UnitServiceDAORemote.class);
		unitDAORemote = RemoteInterfaceFactory.getInstance().getCachedRemote(UnitDAORemote.class);
		testEJBRemote = RemoteInterfaceFactory.getInstance().getCachedRemote(TestEJBRemote.class);

	}
	
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
		
		getRemoteInterfaces();
		
		/*
		testPersistence();

		testLoadUnitDefinition();
		
		Unit newUnit0 = testCreateUnitById(1l);
		Unit newUnit1 = testCreateUnitById(2l);
		Unit newUnit2 = testCreateUnitById(3l);
		Unit newUnit3 = testCreateUnitById(4l);
		Unit newUnit4 = testCreateUnitById(5l);
		Unit newUnit5 = testCreateUnitById(6l);
		testLoadUnitById(newUnit0.getId());
		testLoadUnitById(newUnit1.getId());
		testLoadUnitById(newUnit2.getId());
		testLoadUnitById(newUnit3.getId());
		testLoadUnitById(newUnit4.getId());
		testLoadUnitById(newUnit5.getId());
		
		loadUnitConvertToJsonAndWriteToFile(new File("src/main/resources/unit_0.json"));
		loadUnitConvertToJsonAndWriteToFile(new File("src/main/resources/unit_1.json"));
		loadUnitConvertToJsonAndWriteToFile(new File("src/main/resources/unit_2.json"));
		loadUnitConvertToJsonAndWriteToFile(new File("src/main/resources/unit_3.json"));
		loadUnitConvertToJsonAndWriteToFile(new File("src/main/resources/unit_4.json"));
		loadUnitConvertToJsonAndWriteToFile(new File("src/main/resources/unit_5.json"));
		*/
		
		Terrain simplePlain = new Terrain(new Name("Simple Plain"), TerrainType.PLAIN);
		createSampleTerrain(simplePlain);
		Terrain simpleWood = new Terrain(new Name("Simple Wood"), TerrainType.WOOD);
		createSampleTerrain(simpleWood);
		Terrain simpleSea = new Terrain(new Name("Simple Sea"), TerrainType.SEA);
		createSampleTerrain(simpleSea);
		Terrain simpleHill = new Terrain(new Name("Simple Hill"), TerrainType.HILL);
		createSampleTerrain(simpleHill);
		Terrain simpleSwamp = new Terrain(new Name("Simple Swamp"), TerrainType.SWAMP);
		createSampleTerrain(simpleSwamp);
		Terrain simpleDesert = new Terrain(new Name("Simple Desert"), TerrainType.DESERT);
		createSampleTerrain(simpleDesert);
		Terrain emptyTerrain = new Terrain(new Name("Empty Terrain"), TerrainType.NONE);
		createSampleTerrain(emptyTerrain);
		
		createSampleMapDefinition(3, 3, simplePlain);
		
	}
	
	private static void loadUnitConvertToJsonAndWriteToFile(File file) {
		try {
			FileUtils.writeByteArrayToFile(file, unitDAORemote.findUnitById(81l).toJson().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	private static void testLoadUnitById(Long unitId) {
		System.out.println(unitDAORemote.findUnitById(unitId));
	}

	private static Unit testCreateUnitById(Long unitDefinitionId) {
		return unitServiceDAORemote.createUnitByUnitDefinitionId(unitDefinitionId);
	}

	private static void testLoadUnitDefinition() {
		UnitDefinition unitDefinition = unitDefinitionDAORemote.findUnitDefintionById(1l);
		System.out.println(unitDefinition.getId());		
	}

	private static void testPersistence() {
		testEJBRemote.moo();
		testEJBRemote.testPersistence(10);
		testEJBRemote = null;
		testEJBRemote = RemoteInterfaceFactory.getInstance().getCachedRemote(TestEJBRemote.class);
		testEJBRemote.moo();
		testEJBRemote.testPersistence(10);
	}

	public static void createSampleTerrain(Terrain terrain) {
		try {
			fieldServiceDAORemote.createTerrain(terrain);
		} catch (ZgameException e) {
			e.printStackTrace();
		}
	}

	public static void createSampleMapDefinition(int height, int width, Terrain terrain) {
		List<FieldDefinition> fieldDefintions = new ArrayList<FieldDefinition>();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				fieldDefintions.add(new FieldDefinition("field_plain_"+i+"_"+j, new Coord(i, j), terrain, null, false, false));
			}
		}
		MapDefinition newMapDefinition = new MapDefinition(2, 3, 3, new Name("first_map_3x3"), fieldDefintions);
		try {
			mapServiceDAORemote.createMapDefinition(newMapDefinition);
		} catch (ZgameException e) {
			e.printStackTrace();
		}
	}
	
}

