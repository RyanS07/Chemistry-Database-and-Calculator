package classes;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.text.DecimalFormat;

public class SigFig {
	/*
	 * Description:
	 * - This class performs simple arithmetic in accordance with the rules of significant figures (sig fig).
	 * - This is a static class, meant to be used only for its arithmetic operations.
	 * - All public methods take Strings as parameteres.
	 *   - There are two reasons for this:
	 *     - Input will be obtained through textfields, therefore input will be Strings.
	 *     - If input is converted into a double, trailing zeros are not kept. Sig fig requires all significant
	 *    	 digits be kept, therefore input has to be kept in String form to retain trailing zeros. 
	 */
	



	/*
	 * Description:
	 * - a{n} hereby denotes the numerical value of a
	 * - Returns a{n} + b{n} in accordance with sig fig rules
	 * 
	 * Precondition:
	 * - a{n}, b{n} > 0
	 * - a and b must both be able to be parsed by Double.parseDouble()
	 */
	public static String add(String a, String b) {
		return "";
	}
	
	/*
	 * Description:
	 * - Returns a{n} - b{n} in accordance with sig fig rules
	 * 
	 * Precondition: 
	 * - a{n} > b{n} > 0
	 * - a and b must both be able to be parsed by Double.parseDouble()
	 */
	public static String subtract(String a, String b) {
		return "";
	}
	
	/*
	 * Description:
	 * - Returns a{n} * b{n} in accordance with sig fig rules
	 * 
	 * Precondition: 
	 * - a{n}, b{n} > 0
	 */
	public static String multiply(String a, String b) {
		return "";
	}
	
	/*
	 * Description:
	 * - Returns a{n} / b{n} in accordance with sig fig rules
	 * 
	 * Precondition:
	 * - a{n}, b{n} > 0
	 */
	public static String divide(String a, String b) {
			return "";
	}
}
