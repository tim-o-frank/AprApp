package exercises;

/**
 * Created with IntelliJ IDEA.
 * User: tim.frank
 * Date: 3/9/14
 * Time: 2:09 PM
 * To change this template use File | Settings | File Templates.
 */

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;


public class AmortizationScheduleNewTests {

	//verify that sending zero as the loan, apr, and term fail
	@Test
	public void testConstructAmortizationScheduleIllegalArgZeros() {
		System.out.println("Test if invalid values for constructor throw illegal arg all 0's ex...") ;
		try {
			AmortizationScheduleNew1 asn = new AmortizationScheduleNew1(BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
		} catch (IllegalArgumentException e) {
			assert true;
			return;
		}
		assert false;
	}

	//verify that sending zero as the loan, apr, and term fail
	@Test
	public void testConstructAmortizationScheduleIllegalArgLowPrinciple() {
		System.out.println("Test if invalid values for constructor throw illegal arg ex...") ;
		try {
			AmortizationScheduleNew1 asn = new AmortizationScheduleNew1(BigDecimal.ZERO,new BigDecimal("5"),new BigDecimal("5"));
		} catch (IllegalArgumentException e) {
			assert true;
			return;
		}
		assert false;
	}

	//verify the result of calculate monthly payment
	@Test
	public void testCalculateMonthlyPayment() {
		System.out.println("Test if calculate monthly payment returns valid value...") ;

		AmortizationScheduleNew1 asn = new AmortizationScheduleNew1(new BigDecimal("1000"), new BigDecimal("5"), new BigDecimal("1"));
		assertEquals(asn.calculateMonthlyPayment().setScale(2, RoundingMode.HALF_UP), new BigDecimal("85.61"));
	}
}

