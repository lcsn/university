package de.lsn.client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.naming.Context;

import org.apache.commons.io.FileUtils;

import de.lsn.http.HttpConnector;
import de.lsn.playground.entity.attribute.Coord;
import de.lsn.playground.entity.attribute.Name;
import de.lsn.playground.entity.map.FieldDefinition;
import de.lsn.playground.entity.map.Map;
import de.lsn.playground.entity.map.MapDefinition;
import de.lsn.playground.entity.map.Terrain;
import de.lsn.playground.entity.unit.Unit;
import de.lsn.playground.entity.unit.UnitDefinition;
import de.lsn.playground.framwork.exception.ZgameException;
import de.lsn.playground.framwork.remote.RemoteInterfaceFactory;
import de.lsn.playground.json.JsonHelper;
import de.lsn.playground.logic.game.field.FieldServiceDAORemote;
import de.lsn.playground.logic.game.map.MapServiceDAORemote;
import de.lsn.playground.logic.test.TestEJBRemote;
import de.lsn.playground.logic.unit.UnitDAORemote;
import de.lsn.playground.logic.unit.definition.UnitDefinitionDAORemote;
import de.lsn.playground.logic.unit.service.UnitServiceDAORemote;

public class EJBClient {
	
	private static final int NUMBER_OF_UNITDEFINITIONS = 2;
	
	private static MapServiceDAORemote mapServiceDAORemote;
	private static FieldServiceDAORemote fieldServiceDAORemote;
	private static UnitDefinitionDAORemote unitDefinitionDAORemote;
	private static UnitServiceDAORemote unitServiceDAORemote;
	private static UnitDAORemote unitDAORemote;
	private static TestEJBRemote testEJBRemote; 

	private static Properties getInitialContextProps() {
//		System.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
		Properties properties = new Properties(); 
		properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory"); 
		properties.put(Context.URL_PKG_PREFIXES, "com.sun.enterprise.naming"); 
		properties.put(Context.STATE_FACTORIES, "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl"); 
		properties.put("org.omg.CORBA.ORBInitialHost", "localhost"); 
		properties.put("org.omg.CORBA.ORBInitialPort", "3700"); 
//		ctx.addToEnvironment("org.omg.CORBA.ORBInitialHost", "localhost"); 
//		ctx.addToEnvironment("org.omg.CORBA.ORBInitialPort", "3700");
//		TestEJBRemote remote = (TestEJBRemote) ctx.lookup("java:global/zgame_ear/zgame_ejb/TestEJB!de.lsn.playground.logic.TestEJBRemote");
		return properties;
	}

	private static void getRemoteInterfaces() {
		mapServiceDAORemote = RemoteInterfaceFactory.getInstance().getCachedRemote(MapServiceDAORemote.class);
		fieldServiceDAORemote = RemoteInterfaceFactory.getInstance().getCachedRemote(FieldServiceDAORemote.class);
		unitDefinitionDAORemote = RemoteInterfaceFactory.getInstance().getCachedRemote(UnitDefinitionDAORemote.class);
		unitServiceDAORemote = RemoteInterfaceFactory.getInstance().getCachedRemote(UnitServiceDAORemote.class);
		unitDAORemote = RemoteInterfaceFactory.getInstance().getCachedRemote(UnitDAORemote.class);
		testEJBRemote = RemoteInterfaceFactory.getInstance().getCachedRemote(TestEJBRemote.class);
	}
	
	public static void main(String[] args) {
		
		RemoteInterfaceFactory.recreateInitialContext(getInitialContextProps());
		getRemoteInterfaces();
		
//		InitialContext ctx = null;
//		try {
//			ctx = new InitialContext(getInitialContextProps());
//			MapServiceDAORemote mapServiceDAORemote = (MapServiceDAORemote) ctx.lookup("java:global/"+ZgameConstants.EAR+"/"+ZgameConstants.MODUL_I+"/"+(StringUtils.replace(MapServiceDAORemote.class.getSimpleName(), "Remote", ""))+"!"+MapServiceDAORemote.class.getCanonicalName());
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
		
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
		
		loadUnitDefintitionConvertToJsonAndWriteToFile(1000l, new File("src/main/resources/unitdefinition_light_walker.json"));
		loadUnitDefintitionConvertToJsonAndWriteToFile(1010l, new File("src/main/resources/unitdefinition_light_survivor.json"));
		*/

		
////	LOAD AND PERSIST TEST UNITS
//		loadAndPersistTestUnits();
		
////	LOAD AND PERSIST UNITDEFINITION
//		loadUnitDefinitionAndSaveToDB(new File("src/main/resources/unitdefinition_light_walker.json"));
//		loadUnitDefinitionAndSaveToDB(new File("src/main/resources/unitdefinition_light_survivor.json"));
		
////	CREATING INITIAL TERRIAN TYPES
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
		
////	CREATE A SAMPLE MAPDEFINITION
//		createSampleMapDefinition(5, 6);
	
//		CREATE A SAMPLE MAP FROM MAPDEFINTION
		Map newMap = createSampleMapFromMapDefinition(191l);
		
//		try {
//			Map map = mapServiceDAORemote.findMapById(108l);
			String mapAsJson = newMap.toJson();
			System.out.println(mapAsJson);
			HttpConnector.getInstance().put("https://boiii.firebaseio.com/maps/"+newMap.getMapInstanceId()+"/.json", newMap);
//		} catch (ZgameException e) {
//			e.printStackTrace();
//		}
		
//		ArrayList<FieldDefinition> fields = new ArrayList<FieldDefinition>();
//		fields.add(new FieldDefinition("", null, null, null, true, false));
//		MapDefinition mapDefinition = new MapDefinition(2, 3, 4, null, fields);
//		System.out.println(mapDefinition.toJson());
//		HttpConnector.getInstance().put("https://boiii.firebaseio.com/maps/mapdefinition2/.json", mapDefinition);
		
//		Test.testWriteObjectToFireBase();
		
	}
	
	private static void loadAndPersistTestUnits() {
		loadUnitAndSaveToDB(new File("src/main/resources/unit_0.json"));
		loadUnitAndSaveToDB(new File("src/main/resources/unit_1.json"));
		loadUnitAndSaveToDB(new File("src/main/resources/unit_2.json"));
		loadUnitAndSaveToDB(new File("src/main/resources/unit_3.json"));
		loadUnitAndSaveToDB(new File("src/main/resources/unit_4.json"));
		loadUnitAndSaveToDB(new File("src/main/resources/unit_5.json"));
	}
	
	private static void loadUnitAndSaveToDB(File file) {
		try {
			unitDAORemote.createUnit(JsonHelper.getInstance().getObjectFromJson(FileUtils.readFileToByteArray(file), Unit.class));
		} catch (IOException | ZgameException e) {
			e.printStackTrace();
		}
	}

	private static void loadAndPersistTestUnitDefinitions() {
		loadUnitDefinitionAndSaveToDB(new File("src/main/resources/unitdefinition_0.json"));
		loadUnitDefinitionAndSaveToDB(new File("src/main/resources/unitdefinition_1.json"));
	}
	
	private static void loadUnitDefinitionAndSaveToDB(File file) {
		try {
			unitDefinitionDAORemote.createUnitDefinition(JsonHelper.getInstance().getObjectFromJson(FileUtils.readFileToByteArray(file), UnitDefinition.class));
		} catch (IOException | ZgameException e) {
			e.printStackTrace();
		}
	}
	
	private static void loadUnitConvertToJsonAndWriteToFile(File file) {
		try {
			FileUtils.writeByteArrayToFile(file, unitDAORemote.findUnitById(81l).toJson().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private static void loadUnitDefintitionConvertToJsonAndWriteToFile(Long unitDefinitionId, File file) {
		try {
			FileUtils.writeByteArrayToFile(file, unitDefinitionDAORemote.findUnitDefintionById(unitDefinitionId).toJson().getBytes());
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
		case 1:
			return unitDefinitionDAORemote.findUnitDefintionById(139l); 
		case 2:
			return unitDefinitionDAORemote.findUnitDefintionById(140l);
//		case 3:
//			return unitDefinitionDAORemote.findUnitDefintionById(3l);
//		case 4:
//			return unitDefinitionDAORemote.findUnitDefintionById(4l);
//		case 5:
//			return unitDefinitionDAORemote.findUnitDefintionById(5l);
//		case 6:
//			return unitDefinitionDAORemote.findUnitDefintionById(6l);
		case 7:
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
					unitDefinition = getRandomUnitDefinition(new Random().nextInt(NUMBER_OF_UNITDEFINITIONS+1));
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
