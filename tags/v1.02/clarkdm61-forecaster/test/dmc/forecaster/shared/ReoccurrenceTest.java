package dmc.forecaster.shared;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class ReoccurrenceTest {

	@Test
	public void testGetNextWeekSimple() {
		int year = 110;
		int month = 0;
		int date = 1;
		
		Date priorDate = new Date(year, month, date);
		Date nextDate =Reoccurrence.Weekly.getNext(priorDate);
		Date expected = new Date(year, month, date + 7);
		
		System.out.println("testGetNextWeekSimple");
		System.out.println("priorDate="+priorDate);
		System.out.println("nextDate="+nextDate);
		
		assertEquals(expected, nextDate);
	}
	@Test
	public void testGetNextWeekYearEnd() {
		int year = 110;
		int month = 11;
		int date = 28;
		
		Date priorDate = new Date(year, month, date);
		Date nextDate =Reoccurrence.Weekly.getNext(priorDate);
		Date expected = new Date(year+1, 0, 4);
		
		System.out.println("testGetNextWeekYearEnd");
		System.out.println("priorDate="+priorDate);
		System.out.println("nextDate="+nextDate);
		
		assertEquals(expected, nextDate);
	}
	@Test
	public void testGetNextBiWeeklySimple() {
		int year = 110;
		int month = 0;
		int date = 1;
		
		Date priorDate = new Date(year, month, date);
		Date nextDate =Reoccurrence.BiWeekly.getNext(priorDate);
		Date expected = new Date(year, month, date + 14);
		
		System.out.println("testGetNextMonthBiWeeklySimple");
		System.out.println("priorDate="+priorDate);
		System.out.println("nextDate="+nextDate);
		
		assertEquals(expected, nextDate);
	}
	@Test
	public void testGetNextBiWeeklyYearEnd() {
		int year = 110;
		int month = 11;
		int date = 28;
		
		Date priorDate = new Date(year, month, date);
		Date nextDate =Reoccurrence.BiWeekly.getNext(priorDate);
		Date expected = new Date(year+1, 0, 11);
		
		System.out.println("testGetNextMonthBiWeeklySimple");
		System.out.println("priorDate="+priorDate);
		System.out.println("nextDate="+nextDate);
		
		assertEquals(expected, nextDate);
	}
	@Test
	public void testGetNextMonthSimple() {
		int year = 110;
		int month = 0;
		int date = 1;
		
		Date priorDate = new Date(year, month, date);
		Date nextDate =Reoccurrence.Monthly.getNext(priorDate);
		Date expected = new Date(year, 1, date);
		
		System.out.println("testGetNextMonthBiWeeklySimple");
		System.out.println("priorDate="+priorDate);
		System.out.println("nextDate="+nextDate);
		
		assertEquals(expected, nextDate);
	}
	@Test
	public void testGetNextMonthYearEnd() {
		int year = 110;
		int month = 11;
		int date = 28;
		
		Date priorDate = new Date(year, month, date);
		Date nextDate =Reoccurrence.Monthly.getNext(priorDate);
		Date expected = new Date(year+1, 0, date);
		
		System.out.println("testGetNextMonthBiWeeklySimple");
		System.out.println("priorDate="+priorDate);
		System.out.println("nextDate="+nextDate);
		
		assertEquals(expected, nextDate);

	}
	@Test
	public void testGetNextTwiceYearly() {
		// twice-yearly dates are only approximations, so this will fail
		int year = 110;
		int month = 0;
		int date = 3;
		
		Date priorDate = new Date(year, month, date);
		Date nextDate =Reoccurrence.TwiceYearly.getNext(priorDate);
		Date expected = new Date(year, 6, 5);
		
		System.out.println("testGetNextMonthBiWeeklySimple");
		System.out.println("priorDate="+priorDate);
		System.out.println("nextDate="+nextDate);
		
		assertEquals(expected, nextDate);

	}

}
