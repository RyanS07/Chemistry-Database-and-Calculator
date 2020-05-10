// Made by Ryan Seto

package classes.calculators;

/* TODO:
 *  - Write comment to explain determine() return 0
 */

/* The SigFig class is a collection of static methods used to perform arithmatic with
 * the rules of significant digits (referred to as sig fig or sf)
 */
public class SigFig {	
	/* Description:
	 *  - Determines the amount of sig figs in the number
	 *  - Only works for multiplication and division in accordance with significant figures
	 * 
	 * Algorithm:
	 *  1. Checks if val is a valid number
	 *  2. Determines the sig figs of the number by taking the length of the String
	 *     a. First removes the potential '.' and 'E', as they do not count as a sig fig
	 *     b. Removes any leading zeros, as they do not count as sig figs (Ex: 0.0010 --> 2 sf)
	 * 
	 * Precondition:
	 *  - val must be a valid number
	 *  - Method can handle the exception, but all subsequent steps will be incorrect
	 * 
	 * Postcondition:
	 *  - N/A
	 */
	public static int determine(String val) {
		/* Checks if val is a valid number (Ex: 1.234, 1.23E4, 0.01, etc.) 
		 * If x contains any non-number values besides '.' and 'E', the algorithm does not run properly
		 */
		try {
			Double.parseDouble(val);
		} catch(NumberFormatException e) {
			return 0;
		} 

		// Removes the '.' and 'E' characters from x, if any
		for(int i = 0; i < val.length(); i++) {
			if(val.charAt(i) == '.') {
				// Assigns the String value of val without a '.' to val
				val = val.substring(0, i) + val.substring(i+1);
			}
			if(val.charAt(i) == 'E') {
				// Only assigns the substring of val up to this index to val
				// The exponent plays no part in the sig fig
				val = val.substring(0, i);
				// The 'E' also denotes the end of any sig figs
				break;
			}
		}
		
		// Counts the number of leading zeros
		int leadingZeros = 0;
		int i = 0;
		// Once val.charAt(i) != 0, there are no more leading zeros
		// i < val.length() avoids an IndexOutOfBoundsException 
		while(val.charAt(i) == 0 && i < val.length()) {
			leadingZeros++;
			i++;
		}

		return val.length()-leadingZeros;
	}
	
	/*
	 * Description:
	 * - Returns val in the correct number of sig figs sf
	 * 
	 * Precondition:
	 * - sf > 0
	 */
	public static String set(double val, int sf) {
		/* If sf == 0, then determine() was given an invalid value
		 * If determine() was given an invalid value, then the sig figs are wrong
		 */
		if(sf == 0) {
			return "Invalid Sig Fig";
		}
		/* String.format() returns scientific notation in this format:
		 * - Ex: "1.2e+02" or "1.2e-02"
		 * 
		 * The block below converts from String.format()'s formating to the ideal
		 * scientific notation 
		 *  - Ex: "1.2e+02" --> "1.2E2"
		 *  - Ex: "1.2e-02" --> "1.2E-2"
		 */ 
		String firstStep = String.format("%." + sf + "g", val);
		String secondStep = firstStep;
		for(int i = 0; i < firstStep.length(); i++) {
			if(firstStep.charAt(i) == 'e') {
				secondStep = firstStep.substring(0, i);
				secondStep += "E";
				/* Integer.parseInt("+02") returns 2
				 * Integer.parseInt("-02") returns -2
				 * 
				 * So this method can be used to convert the remainder
				 * of the String.format() to the correct exponent
				 *  - Correct exponent has the correct sign and no 
				 *    leading zeros
				 */
				secondStep += Integer.parseInt(firstStep.substring(i+1));
				break;
			}
		}
		return secondStep;
	}

	/* Description:
	 * - Finds the mininum sig fig of 3 Data objects
	 * - All calculators (except for stoichiometry) use 3 values to solve for 1 unknown
	 * - All calculators then need to find the lowest sig fig of all 3 input values to determine 
	 *   the answer's sig fig
	 */ 
	public static int min(Data a, Data b, Data c) {
        int sf = Math.min(a.getSF(), b.getSF());
        sf = Math.min(sf, c.getSF());
        return sf;
    }
}
