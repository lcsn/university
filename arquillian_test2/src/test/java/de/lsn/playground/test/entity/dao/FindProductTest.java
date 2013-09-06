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

import de.lsn.playground.test.entity.Product;

@RunWith(Arquillian.class)
@Transactional(TransactionMode.ROLLBACK)
public class FindProductTest {

	@EJB
	private ProductFinderService serviceUnderTest;

	@PersistenceContext
	private EntityManager em;

	@Deployment
	public static WebArchive createArchive() {
		final File[] guavaFiles = Maven.resolver()
				.loadPomFromFile("pom.xml")
				.resolve("com.google.guava:guava")
				.withTransitivity()
				.asFile();

		return ShrinkWrap.create(WebArchive.class, "arq-test.war")
				.addClass(ProductFinderService.class)
				.addPackage(Product.class.getPackage())
				.addAsLibraries(guavaFiles)
				.addAsResource("META-INF/persistence.xml");
	}

	@Before
	public void insertTestData() {
		Product product = new Product("123456", "Testartikel");
		em.persist(product);

		em.flush(); // Aenderungen auf DB schreiben
		em.clear(); // und Neuladen erzwingen
	}

	@Test
	public void testFindByCodeSuccess() {
		Product product = serviceUnderTest.findProductByCode("123456");

		assertNotNull(product);
		assertEquals("Testartikel", product.getName());
	}

	@Test
	public void testFindByCodeNoResult() {
		try {
			serviceUnderTest.findProductByCode("000000");
			fail("EJBException erwartet!");
		} catch (EJBException e) {
			assertEquals(NoResultException.class, Throwables.getRootCause(e)
					.getClass());
		}
	}

}
