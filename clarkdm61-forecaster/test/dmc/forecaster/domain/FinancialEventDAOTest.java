package dmc.forecaster.domain;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class FinancialEventDAOTest {
	private static FinancialEventDAO dao = new FinancialEventDAO();
	private static Long mID;
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
		FinancialEvent newInstance = new FinancialEvent("name", "desc", new java.util.Date(), new java.util.Date(), 500.23d);
		dao.create(newInstance);
		mID = newInstance.getId();
		System.out.println("create: success ");
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
			dao.delete(fe.getId());
		}
		System.out.println("cleaned up");
	}

}
