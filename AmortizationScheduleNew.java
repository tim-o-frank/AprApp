package exercises;

import java.io.Console;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.IllegalFormatException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AmortizationScheduleNew {

	//The amount being borrowed
	private BigDecimal amountBorrowed = new BigDecimal("0");
	//The annualPercentageRate
	private BigDecimal annualPercentageRate = new BigDecimal("0");
	//The term of the loan in years
	private BigDecimal termOfLoanInYears = new BigDecimal("0");

	//enum containing low and high allowed values for apr
	public static enum AprRange {
		LOW(new BigDecimal("0.000001")), HIGH(new BigDecimal("100"));

		private BigDecimal rangeValue;

		private AprRange(BigDecimal s) {
			rangeValue = s;
		}

		public BigDecimal getRangeValue() {
			return rangeValue;
		}
	}

	//enum containing low and high allowed values for principle
	public static enum PrincipleRange {
		LOW(new BigDecimal(".01")), HIGH(new BigDecimal("1000000000000"));

		private BigDecimal rangeValue;

		private PrincipleRange(BigDecimal s) {
			rangeValue = s;
		}

		public BigDecimal getRangeValue() {
			return rangeValue;
		}
	}

	//enum containing low and high allowed values for term in years
	public static enum TermRange {
		LOW(new BigDecimal("1")), HIGH(new BigDecimal("1000000"));

		private BigDecimal rangeValue;

		private TermRange(BigDecimal s) {
			rangeValue = s;
		}

		public BigDecimal getRangeValue() {
			return rangeValue;
		}
	}

	//prefix for valid range string
	public static final String VALID_RANGES = "valid ranges are: ";
	//error response for invalid apr
	public static final String VALID_APR = "APR " + VALID_RANGES + AprRange.LOW.getRangeValue() + " and " + AprRange.HIGH.getRangeValue();
	//error response for invalid Principle
	public static final String VALID_PRINCIPLE = "Principle " + VALID_RANGES + PrincipleRange.LOW.getRangeValue() + " and " + PrincipleRange.HIGH.getRangeValue();
	//error response for invalid Term
	public static final String VALID_TERM = "Term " + VALID_RANGES + TermRange.LOW.getRangeValue() + " and " + TermRange.HIGH.getRangeValue();
	//prefix for borrow prompt
	public static final String ENTER_BORROW_AMOUNT = "Enter the amount you would like to borrow. " + VALID_PRINCIPLE;
	//prefix for apr prompt
	public static final String ENTER_APR_AMOUNT = "Enter the interest rate for the loan. " + VALID_APR;
	//prefix for term prompt
	public static final String ENTER_TERM_AMOUNT = "Enter the term in years for the loan. " + VALID_TERM;


	/**
	 * Constructor
	 *
	 * param {BigDecimal} amountBorrowed - Amount being borrowed.
	 *
	 * param {BigDecimal} annualPercentageRate - Annual percentage rate.
	 *
	 * param {BigDecimal} termOfLoanInYears - Term of loan in years
	 *
	 */
	public AmortizationScheduleNew(BigDecimal amountBorrowed, BigDecimal annualPercentageRate, BigDecimal termOfLoanInYears){
		//validate input
		String invalidInputs = null;
		if (!isValidApr(annualPercentageRate)){
			invalidInputs = VALID_APR;
		}

		if (!isValidPrinciple(amountBorrowed)){
			invalidInputs = (invalidInputs != null)?invalidInputs + " " + VALID_PRINCIPLE : VALID_PRINCIPLE;
		}

		if (!isValidTerm(termOfLoanInYears)){
			invalidInputs = (invalidInputs != null)?invalidInputs + " " + VALID_TERM : VALID_TERM;
		}

		if (invalidInputs != null){
			throw new IllegalArgumentException(invalidInputs);
		}
		//set the precision scale to 2 and use the half up rounding mode
		this.amountBorrowed = amountBorrowed.setScale(2, RoundingMode.HALF_UP);
		this.annualPercentageRate = annualPercentageRate.setScale(6, RoundingMode.HALF_UP);
		this.termOfLoanInYears = termOfLoanInYears.setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 * Utility method to read in values from command line
	 */
	public static AmortizationScheduleNew getInputFromCommandLine(){
		Scanner sc = new Scanner(System.in);
		AmortizationScheduleNew asn = null;

		try{
			//get and validate principle
			System.out.println(ENTER_BORROW_AMOUNT);
			BigDecimal amountBorrowed = sc.nextBigDecimal();
			while (!isValidPrinciple(amountBorrowed)){
				System.out.println(VALID_PRINCIPLE);
				amountBorrowed = sc.nextBigDecimal();
			}

			//get and validate apr
			System.out.println(ENTER_APR_AMOUNT);
			BigDecimal apr = sc.nextBigDecimal();
			while (!isValidApr(apr)){
				System.out.println(VALID_APR);
				apr = sc.nextBigDecimal();
			}

			//get and validate term
			System.out.println(ENTER_TERM_AMOUNT);
			BigDecimal termInYears = sc.nextBigDecimal();
			while (!isValidTerm(termInYears)){
				System.out.println(VALID_TERM);
				amountBorrowed = sc.nextBigDecimal();
			}

			asn = new AmortizationScheduleNew(amountBorrowed, apr, termInYears);
		} catch (InputMismatchException e){
			System.out.println("You failed to qualify for the loan.");
		}
		return  asn;
	}

	/**
	 * Utility method to validate the principle of the loan
	 *
	 * param BigDecimal principle - Principle amount of the loan entered by user
	 *
	 * return Boolean - true if principle is valid
	 */
	public static Boolean isValidPrinciple(BigDecimal principle){
		//is the principle less than .01
		if (principle.compareTo(PrincipleRange.LOW.getRangeValue()) < 0){
			return false;
		}
		//is the principle greater than 1000000000000
		if (principle.compareTo(PrincipleRange.HIGH.getRangeValue()) > 0){
			return false;
		}
		return true;
	}

	/**
	 * Utility method to validate the apr of the loan
	 *
	 * param BigDecimal apr - Annual percentage rate entered by user
	 *
	 * return Boolean - true if apr is valid
	 */
	public static Boolean isValidApr(BigDecimal apr){
		//is the apr less than .000001
		if (apr.compareTo(AprRange.LOW.getRangeValue()) < 0){
			return false;
		}
		//is the apr greater than 100
		if (apr.compareTo(AprRange.HIGH.getRangeValue()) > 0){
			return false;
		}
		return true;
	}

	/**
	 * Utility method to validate the term of the loan
	 *
	 * param BigDecimal term - Term of the loan entered by user
	 *
	 * return Boolean - true if term is valid
	 */
	public static Boolean isValidTerm(BigDecimal term){
		//is the term less than .01
		if (term.compareTo(TermRange.LOW.getRangeValue()) < 0){
			return false;
		}
		//is the term greater than 1000000000000
		if (term.compareTo(TermRange.HIGH.getRangeValue()) > 0){
			return false;
		}
		return true;
	}

	/**
	 * Utility method to validate the term of the loan
	 *
	 * return BigDecimal - the monthly payment on the loan
	 */
	public BigDecimal calculateMonthlyPayment(){
		//determine the monthly interest by dividing apr by (12 months * 100)
		BigDecimal monthlyInterest = getMonthlyInterest();
		// this is 1 / (1 + J)
		BigDecimal temp = new BigDecimal("1").add(monthlyInterest).setScale(12, RoundingMode.HALF_UP);
		temp = new BigDecimal("1").divide(temp, 12, RoundingMode.HALF_UP);
		//initialTermMonths
		BigDecimal initialTermMonths = this.termOfLoanInYears.multiply(new BigDecimal("12"));
		// this is Math.pow(1/(1 + J), N)
		temp = temp.pow(initialTermMonths.intValue());
		// this is 1 - (Math.pow(1/(1 + J), N)))
		temp = BigDecimal.ONE.subtract(temp);
		// this is 1 / (1 - (Math.pow(1/(1 + J), N))))
		temp = monthlyInterest.divide(temp, 12, RoundingMode.HALF_UP);
		// M = P * (J / (1 - (Math.pow(1/(1 + J), N))));
		BigDecimal rc = this.amountBorrowed.multiply(temp);

		return rc;
	}

	/**
	 * Utility method to print the schedule
	 */
	public void printSchedule(){
		BigDecimal balance = this.amountBorrowed; //current balance
		Integer paymentNumber = 0; //current payment
		Integer maxNumberOfPayments = this.termOfLoanInYears.intValue() * 12 + 1; //maximum number of payments
		BigDecimal totalPayments = BigDecimal.ZERO; //total payments made
		BigDecimal totalInterestPaid = BigDecimal.ZERO; //total interest paid
		//formatting helpers
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		NumberFormat percent = NumberFormat.getPercentInstance();
		percent.setMinimumFractionDigits(2);
		String formatString = "%-10s %-10s %-10s %-10s %-10s %-10s\n";

		//print first line of table
		printf(formatString, paymentNumber++,
				currency.format(BigDecimal.ZERO),
				percent.format(0),
				currency.format(balance).toString(),
				currency.format(totalPayments).toString(),
				currency.format(totalInterestPaid).toString());

		while (balance.compareTo(BigDecimal.ZERO) > 0){
			BigDecimal curMonthlyInterest = balance.multiply(getMonthlyInterest());

			// the amount required to payoff the loan
			BigDecimal curPayoffAmount = balance.add(curMonthlyInterest);

			BigDecimal monthlyPayment = calculateMonthlyPayment();

			// the amount to payoff the remaining balance may be less than the calculated monthlyPaymentAmount
			BigDecimal curMonthlyPaymentAmount = monthlyPayment.min(curPayoffAmount);

			if ((paymentNumber.equals(maxNumberOfPayments)) &&
					((curMonthlyPaymentAmount.equals(BigDecimal.ZERO)) || (curMonthlyPaymentAmount.equals(curMonthlyInterest)))) {
				curMonthlyPaymentAmount = curPayoffAmount;
			}

			// Calculate C = M - H, this is your monthly payment minus your monthly interest,
			// so it is the amount of principal you pay for that month
			BigDecimal curMonthlyPrincipalPaid = curMonthlyPaymentAmount.subtract(curMonthlyInterest);

			// Calculate Q = P - C, this is the new balance of your principal of your loan.
			BigDecimal curBalance = balance.subtract(curMonthlyPrincipalPaid);

			totalPayments = totalPayments.add(curMonthlyPaymentAmount);
			totalInterestPaid = totalInterestPaid.add(curMonthlyInterest);

			printf(formatString, paymentNumber++,
					currency.format(curMonthlyPaymentAmount),
					percent.format(curMonthlyInterest.doubleValue() / 100),
					currency.format(curBalance).toString(),
					currency.format(totalPayments).toString(),
					currency.format(totalInterestPaid).toString());

			// Set P equal to Q and go back to Step 1: You thusly loop around until the value Q (and hence P) goes to zero.
			balance = curBalance;
		}
	}

	/**
	 * Utility method to print to the console
	 *
	 * param String - format for output
	 *
	 * param Object ... - var args to be printed to console
	 *
	 */
	private static void printf(String formatString, Object... args) {
		Console console = System.console();
		try {
			if (console != null) {
				console.printf(formatString, args);
			} else {
				System.out.print(String.format(formatString, args));
			}
		} catch (IllegalFormatException e) {
			System.err.print("Error printing...\n");
		}
	}

	/**
	 * Utility method to get the monthly interest on the loan
	 *
	 * return BigDecmial - monthly interest on the loan
	 */
	public BigDecimal getMonthlyInterest(){
		return this.annualPercentageRate.divide((new BigDecimal("1200")), 6, RoundingMode.HALF_UP);
	}

	/**
	 * Main method
	 */
	public static void main(String[] args){
		//Prompt the user for loan information
		AmortizationScheduleNew asn = getInputFromCommandLine();
		asn.printSchedule();
	}

}