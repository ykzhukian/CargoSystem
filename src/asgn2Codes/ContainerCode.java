package asgn2Codes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import asgn2Exceptions.InvalidCodeException;

/* Note to self:
 * Use option "-noqualifier java.lang" when generating Javadoc from this
 * file so that, for instance, we get "String" instead of
 * "java.lang.String".
 */

/**
 * This class provides cargo container codes in a format similar to that
 * prescribed by international standard ISO 6346.  (The only difference
 * is that we use a simpler algorithm for calculating the check digit.)
 * <p>
 * A container code is an 11-character string consisting of the following
 * fields:
 * <ul>
 * <li>
 * An <em>Owner Code</em> which uniquely identifies the company that owns
 * the container.  The owner code consists of three upper-case letters.
 * (To ensure uniqueness in practice, owner codes must be registered at
 * the <em>Bureau International des Containers</em> in Paris.)
 * </li>
 * <li>
 * A <em>Category Identifier</em> which identifies the type of container.
 * The identifier is one of three letters, 'U', 'J' or 'Z'.  For our
 * purposes the category identifier is <em>always</em> 'U', which denotes a
 * freight container.
 * </li>
 * <li>
 * A <em>Serial Number</em> used by the owner to uniquely identify the
 * container.  The number consists of six digits.
 * </li>
 * <li>
 * A <em>Check Digit</em> used to ensure that the number has not been
 * transcribed incorrectly.  It consists of a single digit derived from the
 * other 10 characters according to a specific algorithm, described below.
 * </li>
 * </ul>
 * <p>
 * <strong>Calculating the check digit:</strong> ISO 6346 specifies a
 * particular algorithm for deriving the check digit from the
 * other 10 characters in the code.  However, because this algorithm is
 * rather complicated, we use a simpler version for this assignment.
 * Our algorithm works as follows:
 * <ol>
 * <li>
 * Each of the 10 characters in the container code (excluding the check
 * digit) is treated as a number.  The digits '0' to '9' each have
 * their numerical value.  The upper case alphabetic letters 'A' to
 * 'Z' are treated as the numbers '0' to '25', respectively.
 * </li>
 * <li>
 * These 10 numbers are added up.
 * </li>
 * <li>
 * The check digit is the least-significant digit in the sum produced
 * by the previous step.
 * </li>
 * </ol>
 * For example, consider container code <code>MSCU6639871</code> which
 * has owner code <code>MSC</code>, category identifier <code>U</code>,
 * serial number <code>663987</code> and check digit <code>1</code>.  We can 
 * confirm that this code is valid by treating the letters as numbers (e.g.,
 * '<code>M</code>' is 12, '<code>S</code>' is 18, etc) and calculating the
 * following sum.
 * <p>
 * <center>
 * 12 + 18 + 2 + 20 + 6 + 6 + 3 + 9 + 8 + 7 = 91
 * </center>
 * <p>
 * The check digit is then the least-sigificant digit in the number 91,
 * i.e., '<code>1</code>', thus confirming that container code 
 * <code>MSCU6639871</code> is valid.
 * 
 * @author CAB302 Yunkai(Kian) Zhu n9253921
 * @version 1.0
 */ 
public class ContainerCode {

	String code;

	/**
	 * Constructs a new container code.
	 * 
	 * @param code the container code as a string
	 * @throws InvalidCodeException if the container code is not eleven characters long; if the
	 * Owner Code does not consist of three upper-case letters; if the
	 * Category Identifier is not 'U'; if the Serial Number does not consist
	 * of six digits; or if the Check Digit is incorrect.
	 */
	public ContainerCode(String code) throws InvalidCodeException {
		if (code == null || code.isEmpty()) {
			throw new InvalidCodeException("Missing code");
		}
		// get the length of code
		int length = code.length();
		if (length != 11) {
			throw new InvalidCodeException("the code is not eleven characters long");
		}
		// pattern to check the three upper-case letter
		String pattern1 = "[A-Z]{3}[A-Z0-9]*";
		Pattern p1 = Pattern.compile(pattern1);
		Matcher m1 = p1.matcher(code);
		if (!m1.find()) {
			throw new InvalidCodeException("the code does not have three upper-case letter");
		}
		// pattern to check the Category Identifier 'U'
		String pattern2 = "[A-Z]{3}U[A-Z0-9]*";
		Pattern p2 = Pattern.compile(pattern2);
		Matcher m2 = p2.matcher(code);
		if (!m2.find()) {
			throw new InvalidCodeException("the code does not have Indentifier 'U'");
		} 
		// pattern to check if the Serial Number consists of six digits
		String pattern3 = "[A-Z]{3}U[0-9]{6}[0-9]*";
		Pattern p3 = Pattern.compile(pattern3);
		Matcher m3 = p3.matcher(code);
		if (!m3.find()) {
			throw new InvalidCodeException("the code does not consist of six digits");
		}
		// check the check digit
		char[] codeArray = code.toCharArray();
		char[] letterArray = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		int checkDigit = Integer.parseInt(code.substring(code.length() - 1));
		int sumOfThreeUpperCase = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 26; j++) {
				if (codeArray[i] == letterArray[j]) {
					sumOfThreeUpperCase += j;
				}
			}
		}
		int sumOfDigits = 0;
		for (int i = 0; i < 6; i++) {
			sumOfDigits += Character.getNumericValue(codeArray[4+i]);
		}
		int sum = sumOfThreeUpperCase + 20 + sumOfDigits;
		
		if (checkDigit != sum % 10) {
			throw new InvalidCodeException("the Check Digit is incorrect");
		}
		
		this.code = code;
	}
	



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return code;
	}

	
	/**
	 * Returns true iff the given object is a container code and has an
	 * identical value to this code.
	 * 
	 * @param obj an object
	 * @return true if the given object is a container code with the
	 * same string value as this code, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		//Implementation Here
		if (obj.toString() == code) {
			return true;
		} else {
			return false;
		}
	}
}

