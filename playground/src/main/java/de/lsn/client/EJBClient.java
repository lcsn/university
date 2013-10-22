package de.lsn.client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.naming.Context;

import org.apache.commons.io.FileUtils;

import de.lsn.playground.entity.attribute.Coord;
import de.lsn.playground.entity.attribute.Name;
import de.lsn.playground.entity.map.Desert;
import de.lsn.playground.entity.map.FieldDefinition;
import de.lsn.playground.entity.map.Hill;
import de.lsn.playground.entity.map.Map;
import de.lsn.playground.entity.map.MapDefinition;
import de.lsn.playground.entity.map.None;
import de.lsn.playground.entity.map.Plain;
import de.lsn.playground.entity.map.Sea;
import de.lsn.playground.entity.map.Swamp;
import de.lsn.playground.entity.map.Terrain;
import de.lsn.playground.entity.map.Wood;
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
		
//		Plain simplePlain = new Plain("simple_plain");
//		simplePlain = (Plain) createSampleTerrain(simplePlain);
//		Wood simpleWood = new Wood("simple_wood");
//		simpleWood = (Wood) createSampleTerrain(simpleWood);
//		Sea simpleSea = new Sea("simple_sea");
//		simpleSea = (Sea) createSampleTerrain(simpleSea);
//		Hill simpleHill = new Hill("simple_hill");
//		simpleHill = (Hill) createSampleTerrain(simpleHill);
//		Swamp simpleSwamp = new Swamp("simple_swamp");
//		simpleSwamp = (Swamp) createSampleTerrain(simpleSwamp);
//		Desert simpleDesert = new Desert("simple_desert");
//		simpleDesert = (Desert) createSampleTerrain(simpleDesert);
//		None emptyTerrain = new None("empty_terrain");
//		emptyTerrain = (None) createSampleTerrain(emptyTerrain);
		
//		createSampleMapDefinition(3, 3);
		
		Map newMap = createSampleMapFromMapDefinition(98l);
		
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

	public static Terrain createSampleTerrain(Terrain terrain) {
		Terrain t = null;
		try {
			t = fieldServiceDAORemote.createTerrain(terrain);
		} catch (ZgameException e) {
			e.printStackTrace();
		}
		return t;
	}

	public static Terrain[][] getRandomTerrainMatrix(int height, int width) {
		Terrain[][] m = new Terrain[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int rInt = new Random().nextInt(10);
				try {
					m[i][j] = getRandomTerrain(rInt);
				} catch (ZgameException e) {
					e.printStackTrace();
				}
			}
		}
		return m;
	}
	
	private static UnitDefinition getRandomUnitDefinition(int rInt) throws ZgameException {
		switch (rInt) {
		case 0:
			return unitDefinitionDAORemote.findUnitDefintionById(1l); 
		case 1:
			return unitDefinitionDAORemote.findUnitDefintionById(2l);
		case 2:
			return unitDefinitionDAORemote.findUnitDefintionById(3l);
		case 3:
			return unitDefinitionDAORemote.findUnitDefintionById(4l);
		case 4:
			return unitDefinitionDAORemote.findUnitDefintionById(5l);
		case 5:
			return unitDefinitionDAORemote.findUnitDefintionById(6l);
		case 6:
			return null;
		default:
			return null;
		}
	}
	
	private static Terrain getRandomTerrain(int rInt) throws ZgameException {
		switch (rInt) {
		case 0:
			return fieldServiceDAORemote.findTerrainByName("simple_plain"); 
		case 1:
			return fieldServiceDAORemote.findTerrainByName("simple_wood");
		case 2:
			return fieldServiceDAORemote.findTerrainByName("simple_sea");
		case 3:
			return fieldServiceDAORemote.findTerrainByName("simple_hill");
		case 4:
			return fieldServiceDAORemote.findTerrainByName("simple_swamp");
		case 5:
			return fieldServiceDAORemote.findTerrainByName("simple_desert");
		case 6:
			return fieldServiceDAORemote.findTerrainByName("empty_terrain");
		default:
			return fieldServiceDAORemote.findTerrainByName("empty_terrain");
		}
	}

	public static void createSampleMapDefinition(int height, int width) {
		Terrain[][] terrain = getRandomTerrainMatrix(height, width);
		List<FieldDefinition> fieldDefintions = new ArrayList<FieldDefinition>();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				UnitDefinition unitDefinition = null;
				try {
					unitDefinition = getRandomUnitDefinition(new Random().nextInt(7));
				} catch (ZgameException e) {
					e.printStackTrace();
				}
				fieldDefintions.add(new FieldDefinition(terrain[i][j].getTerrainName().toString()+"_"+i+"_"+j, new Coord(i, j), terrain[i][j], unitDefinition, false, false));
			}
		}
		MapDefinition newMapDefinition = new MapDefinition(2, height, width, new Name("first_map_"+height+"x"+width), fieldDefintions);
		try {
			mapServiceDAORemote.createMapDefinition(newMapDefinition);
		} catch (ZgameException e) {
			e.printStackTrace();
		}
	}
	
	public static Map createSampleMapFromMapDefinition(Long mapDefinitionId) {
		Map m = null;
		try {
			m = mapServiceDAORemote.createMapByMapDefinitionId(mapDefinitionId);
		} catch (ZgameException e) {
			e.printStackTrace();
		}
		return m;
	}
	
}
