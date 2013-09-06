package de.lsn.playground.test.entity.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.commons.io.FileUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Throwables;

import de.lsn.playground.test.entity.Person;

@RunWith(Arquillian.class)
@Transactional(TransactionMode.ROLLBACK)
public class PersonDAOTest {

	private Long p0_id = 0l;
	private Long p1_id = 0l;
	
//	@PersistenceContext
//	private EntityManager em;
	
//	@EJB(mappedName="java:global/ejb-test/PersonDAO!de.lsn.playground.test.entity.dao.PersonDAORemote")
	@EJB(mappedName="java:global/ejb-test/PersonDAO!de.lsn.playground.test.entity.dao.PersonDAOLocal")
//	@EJB
	private PersonDAO personDAO;

	@PersistenceContext
	private EntityManager em;
	
	@Deployment
	public static WebArchive createArchive() {
		final File[] guavaFiles = Maven.resolver().loadPomFromFile("pom.xml").resolve("com.google.guava:guava").withTransitivity().asFile();
		
		System.out.println("GUAVA FILES:");
		for (int i = 0; i < guavaFiles.length; i++) {
			System.out.println("File["+i+"]: "+guavaFiles[i]);
		}
		
		WebArchive war = ShrinkWrap.create(WebArchive.class, "ejb-test.war");
		war.addClass(PersonDAO.class);
		war.addPackage(Person.class.getPackage());
		war.addAsLibraries(guavaFiles);
//		war.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
//		war.addAsResource(FileUtils.getFile("src/main/resources/META-INF/persistence.xml"));
		war.addAsResource("META-INF/persistence.xml");
		
//		war.addAsManifestResource(new StringAsset(  
//                Descriptors.create(PersistenceDescriptor.class)  
//                        .persistenceUnit("ctjug")  
//                        .provider("oracle.toplink.essentials.PersistenceProvider")  
//                        .transactionType(TransactionType.JTA)  
//                        .excludeUnlistedClasses()  
//                        .jtaDataSource("jdbc/ctjug")  
//                        .schemaGenerationMode(SchemaGenerationModeType.CREATE_DROP)  
//                        .exportAsString()), "persistence.xml");  
		return war;
	}

	@Before
	public void insertTestData() {

//		Properties properties = new Properties(); 
//		properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory"); 
//		properties.put(Context.URL_PKG_PREFIXES, "com.sun.enterprise.naming"); 
//		properties.put(Context.STATE_FACTORIES, "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl"); 
//		properties.put("org.omg.CORBA.ORBInitialHost", "localhost"); 
//		properties.put("org.omg.CORBA.ORBInitialPort", "3700"); 
//		try {
//			InitialContext ctx = new InitialContext(properties);
//			this.personDAO = (PersonDAORemote) ctx.lookup("java:global/ejb-test/PersonDAO!de.lsn.playground.test.entity.dao.PersonDAORemote");
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
		
		Person p0 = new Person("Lars", "Simon", 27, 'M');
//		p0 = personDAO.createPerson(p0);
		em.persist(p0);
		Person p1 = new Person("Stefan", "Schulz", 37, 'M');
//		p1 = personDAO.createPerson(p1);
		em.persist(p1);
		p0_id = p0.getId();
		p1_id = p1.getId();
		
		em.flush();
		em.clear();
	}
	
	@Test
	public void testFindUnitById() {
		assertNotNull(personDAO);
		
		Person person0 = personDAO.findPersonById(p0_id);
		assertNotNull(person0);
		assertEquals("Lars", person0.getFirstname());
		Person person1 = personDAO.findPersonById(p1_id);
		assertNotNull(person1);
		assertEquals("Stefan", person1.getFirstname());
	}

	@Test
	public void testFindUnitByIdNoResult() {
		assertNotNull(personDAO);
		try {
			personDAO.findPersonById(0l);
			fail("Person mit ID:0 kann nicht gefunden werden -> erwarte EJBException");
		} catch (EJBException e) {
			assertEquals(NoResultException.class, Throwables.getRootCause(e).getClass());
		}
	}


}
