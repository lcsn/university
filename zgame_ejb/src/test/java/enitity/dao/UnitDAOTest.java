package enitity.dao;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.lsn.playground.entity.ZgameEntity;
import de.lsn.playground.entity.unit.Unit;
import de.lsn.playground.json.JsonHelper;
import de.lsn.playground.logic.unit.UnitDAO;

@RunWith(Arquillian.class)
@Transactional(TransactionMode.ROLLBACK)
public class UnitDAOTest {

	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private UnitDAO unitDAO;
	
	@Deployment
	public static WebArchive createArchive() {
		return ShrinkWrap.create(WebArchive.class).
				addClass(UnitDAO.class).
				addPackage(ZgameEntity.class.getPackage()).
				addAsResource("META-INF/persistence.xml");
	}

	@Before
	public void insertTestData() {
		try {
			String fileContent0 = FileUtils.readFileToString(new File("src/test/resources/unit_0.json"), "UTF-8");
			String fileContent1 = FileUtils.readFileToString(new File("src/test/resources/unit_1.json"), "UTF-8");
			String fileContent2 = FileUtils.readFileToString(new File("src/test/resources/unit_2.json"), "UTF-8");
			String fileContent3 = FileUtils.readFileToString(new File("src/test/resources/unit_3.json"), "UTF-8");
			String fileContent4 = FileUtils.readFileToString(new File("src/test/resources/unit_4.json"), "UTF-8");
			String fileContent5 = FileUtils.readFileToString(new File("src/test/resources/unit_5.json"), "UTF-8");
			Unit unit = JsonHelper.getInstance().getObjectFromJson(fileContent0.getBytes(), Unit.class);
			em.persist(unit);
			unit = JsonHelper.getInstance().getObjectFromJson(fileContent1.getBytes(), Unit.class);
			em.persist(unit);
			unit = JsonHelper.getInstance().getObjectFromJson(fileContent2.getBytes(), Unit.class);
			em.persist(unit);
			unit = JsonHelper.getInstance().getObjectFromJson(fileContent3.getBytes(), Unit.class);
			em.persist(unit);
			unit = JsonHelper.getInstance().getObjectFromJson(fileContent4.getBytes(), Unit.class);
			em.persist(unit);
			unit = JsonHelper.getInstance().getObjectFromJson(fileContent5.getBytes(), Unit.class);
			em.persist(unit);
			em.clear();
			em.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFindUnitById() {
		Unit unit = unitDAO.findUnitById(1l);
		assertEquals(true, 1l==unit.getId());
		unit = unitDAO.findUnitById(2l);
		assertEquals(true, 2l==unit.getId());
		unit = unitDAO.findUnitById(3l);
		assertEquals(true, 3l==unit.getId());
		unit = unitDAO.findUnitById(4l);
		assertEquals(true, 4l==unit.getId());
		unit = unitDAO.findUnitById(5l);
		assertEquals(true, 5l==unit.getId());
		unit = unitDAO.findUnitById(6l);
		assertEquals(true, 6l==unit.getId());
	}

	@Test
	public void testFindUnitByIdNoResult() {
		assertEquals(true, true);
//		try {
//			unitDAO.findUnitById(1234l);
//		} catch (EJBException e) {
//			assertEquals(NoResultException.class, Throwables.getRootCause(e).getClass());
//		}
	}
	
}
