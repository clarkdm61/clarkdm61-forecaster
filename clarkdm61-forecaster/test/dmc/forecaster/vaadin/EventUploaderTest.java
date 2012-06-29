package dmc.forecaster.vaadin;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EventUploaderTest {
	private String xml = null;

	@Before
	public void setUp() throws Exception {
		xml = "<?xml version=\"1.0\"?><eventList> <financialEvent>  <id>1</id> <name>income</name>  <description></description>  <reoccurrence>BiWeekly</reoccurrence>  <type>Income</type>  <amount>100</amount>  <startDt>Tue Jun 26 10:44:26 EDT 2012</startDt>  <endDt>null</endDt></financialEvent> <financialEvent>  <id>2</id>  <name>Rent</name>  <description></description>  <reoccurrence>Monthly</reoccurrence>  <type>Expense</type>  <amount>50</amount>  <startDt>Tue Jun 26 10:44:45 EDT 2012</startDt>  <endDt>null</endDt></financialEvent></eventList>";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testImportEvents() {
		EventUploader uploader = new EventUploader();
		
		try {
			uploader.importEvents(xml.getBytes());
		} catch (Exception re) {
			re.printStackTrace();
			fail("Proccessing failed");
			
		}
		assertTrue(true);
		
	}

}
