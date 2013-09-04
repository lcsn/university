package enitity.dao;

//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
//import org.jboss.arquillian.transaction.api.annotation.Transactional;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.lsn.playground.entity.unit.Unit;
import de.lsn.playground.logic.unit.UnitDAO;

/*
@RunWith(Arquillian.class)
@Transactional(TransactionMode.ROLLBACK)
public class UnitDAOTest {

	@Deployment
	public static WebArchive createArchive() {
		return ShrinkWrap.create(WebArchive.class).
				addClass(UnitDAO.class).
				addPackage(Unit.class.getPackage()).
				addAsResource("META-INF/persistence.xml");
	}

	@Before
	public void insertTestData() {
		
	}
	
	@Test
	public void testFindUnitById() {

	}

}
*/