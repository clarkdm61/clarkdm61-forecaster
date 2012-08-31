package dmc.forecaster.domain;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import dmc.forecaster.server.FinancialEventDAO;
import dmc.forecaster.shared.FinancialEvent;
import dmc.forecaster.shared.FinancialEventType;
import dmc.forecaster.shared.Reoccurrence;

public class FinancialEventDAOTest {
	private static FinancialEventDAO dao = new FinancialEventDAO();
	//private static Long mID;
    private static final LocalServiceTestHelper helper =
        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		helper.setUp();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		helper.tearDown();
	}

	@Test
	public void testCreate() {
		FinancialEventType type = FinancialEventType.valueOf("Income");
		FinancialEvent newInstance = new FinancialEvent("name", "desc", new java.util.Date(), new java.util.Date(), 500.23d, type, Reoccurrence.Monthly);
		dao.createUpdate(newInstance);
		//mID = newInstance.getId();
		System.out.println("create: success. "+newInstance);
	}

	@Test
	public void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}
	
	@Test
	public void clean() {
		List<FinancialEvent> fes = dao.findAll();
		for (FinancialEvent fe: fes) {
			System.out.println("deleting " + fe);
			dao.delete(fe.getId());
		}
		System.out.println("cleaned up");
	}

}
