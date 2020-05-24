package calculators;

/* TODO:
 *  - Write comment to explain determine() return 0
 */

/* The SigFig class is a collection of static methods used to perform arithmatic with
 * the rules of significant digits (referred to as sig fig or sf)
 */
public class SigFig {	
	/* Description:
	 *  - Determines the amount of sig figs in a number
	 *    - The sig figs of a number is defined as all digits of a number, exluding leading zeros
	 *      - Ex: 1.234 --> 4sf, 0.034 --> 2sf, 1.23E4 --> 3sf
	 *  - Only supports multiplication and division in accordance with significant figures
	 * 	  - Addition and subtraction have separate rules
	 *  - Made protected since only the calculator classes need to use SigFig
	 * 
	 * Algorithm:
	 *  1. Determines the sig figs of the number by taking the length of the String
	 *     a. First removes the potential '.' and 'E', as they do not count as a sig fig
	 *     b. Removes any leading zeros, as they do not count as sig figs (Ex: 0.0010 --> 2 sf)
	 *  - An example is provided alongside the code
	 * 
	 * Precondition:
	 *  - val must be a valid int or double contained within a String
	 */
	protected static int determine(String val) {
		// Ex val: 0.034E3
		// Removes the '.' and 'E' characters from val, if any
		for(int i = 0; i < val.length(); i++) {
			if(val.charAt(i) == '.') {
				// Removes the '.'
				val = val.substring(0, i) + val.substring(i+1);
			}
			if(val.charAt(i) == 'E') {
				// Removes the 'E'
				// Any part of val beyond the 'E' is not included in a number's sig fig, so it 
				// can be removed from val.
				val = val.substring(0, i);
				break;
			}
		}
		// Ex val: 0034
		
		// Counts the number of leading zeros
		int counter = 0;
		/* Once a non-zero number has been found, there are no more 
		 * leading zeros.
		 * The boolean (i < val.length()) is placed ahead first to:
		 *   a. Avoid an IndexOutOfBoundsException
		 *   b. Utilize lazy evaluation
		 * 
		 * Note: the index of the first non-zero digit is also the number of 
		 * leading zeros. 
		 */
		while(counter < val.length() && val.charAt(counter) == 0) {
			counter++;
		}

		// Ex val: "0034".length() - 2;
		return val.length()-counter;
	}
	
	/* Description:
	 *  - Returns val in the correct number of sig figs sf
	 *  - Made protected since only the calculator classes need to use SigFig
	 * 
	 * Algorithm:
	 *  1. Formats val to sf amount of sig figs
	 *  2. Converts String.format() to proper scientific notation
	 * 
	 * Precondition:
	 *  - sf > 0
	 */
	protected static String set(double val, int sf) {
		/* String.format() returns scientific notation in this format:
		 * - Ex: "1.2e+02" or "1.2e-02"
		 * 
		 * The block below converts from String.format()'s formating to the ideal
		 * scientific notation 
		 *  - Ex: "1.2e+02" --> "1.2E2"
		 *  - Ex: "1.2e-02" --> "1.2E-2"
		 */ 
		String stringFormat = String.format("%." + sf + "g", val);
		// stringFormat is assumed the correct value
		// Ex: String.format(...) --> 1.23
		String corrected = stringFormat; 
		for(int i = 0; i < stringFormat.length(); i++) {
			// Replaces 'e' with 'E'
			if(stringFormat.charAt(i) == 'e') {
				corrected = stringFormat.substring(0, i);
				corrected += "E";
				/* Integer.parseInt("+02") returns 2
				 * Integer.parseInt("-02") returns -2
				 * 
				 * So this method can be used to convert the remainder
				 * of the String.format() to the correct exponent
				 *  - Correct exponent has the correct sign and no 
				 *    leading zeros
				 */
				corrected += Integer.parseInt(stringFormat.substring(i+1));
				break;
			}
		}
		return corrected;
	}

	/* Description:
	 *  - Finds the mininum sig fig of 3 Data objects
	 *     - All calculators (except for stoichiometry) use 3 values to solve for 
	 *       1 unknown
	 *     - All calculators then need to find the lowest sig fig of all 3 input 
	 *       values to determine the answer's sig fig
	 *  - Made protected since only the calculator classes need to use SigFig
	 */ 
	protected static int min(Data a, Data b, Data c) {
        int sf = Math.min(a.getSF(), b.getSF());
        sf = Math.min(sf, c.getSF());
        return sf;
    }
}
