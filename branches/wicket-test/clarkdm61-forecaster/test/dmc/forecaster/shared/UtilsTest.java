package dmc.forecaster.shared;

import org.junit.Test;
import static org.junit.Assert.*;

public class UtilsTest {
	@Test
	public void testCurrencyFormat() {
		Double amt = 50d;
		String samt = Utils.currencyFormat(amt);
		System.out.println(samt);
		assertEquals("$50.00",samt);
	}
}
