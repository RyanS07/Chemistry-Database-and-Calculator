package classes;

import java.util.Arrays;
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
	
//	 Next Steps:
//	 - Implement support for sci not'n input
//	 - Implement sig fig when sum/difference/product/quotient returned in sci not'n
//	 - Use rng to test methods once done (for extreme edge cases)
//   - Check if value passed into constructor is valid 
//   - Change setSigFig to use DecimalFormat
	
	private int sf;
	private int sfLoc;
	private String val;
	private double doubleVal;

	public SigFig(String value) {
		if(value.indexOf('E') > -1) { 
			int exp = Integer.parseInt(value.substring(value.indexOf('E') + 1));
			value = value.substring(0, value.indexOf('E'));
			this.val = shift(value, exp);
			this.sfLoc = value.indexOf('E') - 1;
		} else {
			this.val = value;
			this.sfLoc = value.length()-1;
		}
		this.sf = determineSigFig(value);
		this.doubleVal = Double.parseDouble(this.val);
	}


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
		// Finds the number of digits the integer of the sum will have.
		int integerLength = Math.max(getIntLength(a), getIntLength(b));
		int aDecLength = getDecLength(a);
		int bDecLength = getDecLength(b);
		int decimalLength = Math.max(aDecLength, bDecLength);
		// Finds the total length of the array.
		// +1 to add an extra zero in front of integer in case sum overloads
		// Ex: 90 + 10 = 100
		int offset = 1;
		// +1 to add decimal point
		int totalLength = offset + integerLength + 1 + decimalLength;
		int[][] operand = new int[2][0];
		// Creates int array to store full value of String a ('.' is represented with -1)
		operand[0] = align(a, integerLength, decimalLength, totalLength, offset);
		operand[1] = align(b, integerLength, decimalLength, totalLength, offset);

		int[] sum = new int[totalLength];
		int carry = 0;
		int decPoint = -1;
		// Begin adding digits from right to left
		for(int i = totalLength-1; i >= 0; i--) {
			if(operand[0][i] != decPoint && operand[1][i] != decPoint) {
				sum[i] = (operand[0][i] + operand[1][i] + carry)%10;
				carry = (operand[0][i] + operand[1][i] + carry)/10;
			} else {
				sum[i] = decPoint;
			}
		}
		String answer = "";
		if(aDecLength-bDecLength != 0) {
			int finalLength = sum.length - Math.abs(aDecLength-bDecLength);
			int extra = sum[finalLength];
			if(extra >= 5) {
				carry = 1;
			}
			
			// Rounding to the correct sig fig, and turning into a string
			for(int i = finalLength - 1; i >= 0; i--) {
				if(sum[i] != decPoint) {
					sum[i] = (sum[i] + carry);
					carry = sum[i]/10;
					sum[i] %= 10;
					answer = sum[i] + answer;
				} else {
					answer = "." + answer;
				}
			}
		} else {
			for(int i = 0; i < sum.length; i++) {
				if(sum[i] != decPoint) {
					answer += sum[i];
				} else {
					answer += ".";
				}
			}
		}
		
		//Removes leading zeros
		if(answer.charAt(0) == '0') {
			answer = answer.substring(1);
		}
		
		// Removes trailing '.' if any
		if(aDecLength == 0 || bDecLength == 0) {
			answer = answer.substring(0, answer.length()-1);
		}
		return answer;
	}

	private static int getIntLength(String x) {
		if(x.indexOf('.') > -1) {
			return x.substring(0, x.indexOf('.')).length();
		}
		return x.length();
	}

	private static int getDecLength(String x) {
		if(x.indexOf('.') > -1) {
			return x.substring(x.indexOf('.') + 1).length();
		}
		return 0;
	}

	private static int[] align(String x, int intLength, int decLength, int totalLength, int offset) { 
		int start = intLength - getIntLength(x);
		int end = decLength - getDecLength(x);
		int decPoint = -1;
		int[] operand = new int[offset + totalLength];
		int i = start;
		int j = 0;
		while(i < operand.length - end && j < x.length()) {
			if(x.charAt(j) == '.') {
				operand[i + offset] = decPoint;
			} else {
				operand[i + offset] = (int) x.charAt(j) - 48;
			}
			i++;
			j++;
		}
		return operand;
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
		// Finds the number of digits the integer of the sum will have.
		int integerLength = Math.max(getIntLength(a), getIntLength(b));
		int aDecLength = getDecLength(a);
		int bDecLength = getDecLength(b);
		int decimalLength = Math.max(aDecLength, bDecLength);
		// Finds the total length of the array.
		// +1 to add decimal point
		int offset = 0;
		int totalLength = integerLength + 1 + decimalLength;
		int[][] operand = new int[2][0];
		// Creates int array to store full value of String a ('.' is represented with -1)
		operand[0] = align(a, integerLength, decimalLength, totalLength, offset);
		operand[1] = align(b, integerLength, decimalLength, totalLength, offset);

		int[] difference = new int[totalLength];
		int decPoint = -1;
		// Begin subtracting digits from right to left
		for(int i = totalLength-1; i >= 0; i--) {
			if(operand[0][i] != decPoint && operand[1][i] != decPoint) { 
				if(operand[0][i] - operand[1][i] >= 0) {
					difference[i] = operand[0][i] - operand[1][i];
				} else {
					int index = i-1;
					// Finds the next digit (works around if next value is decPoint)
					while(index > 0 && operand[0][index] == decPoint) {
						index--;
					}
					operand[0][index]--;
					difference[i] = (10+operand[0][i]) - operand[1][i];
				}
			} else {
				difference[i] = decPoint;
			}
		}

		String answer = "";
		if(aDecLength-bDecLength != 0) {
			int finalLength = difference.length - Math.abs(aDecLength-bDecLength);
		int extra = difference[finalLength];
		int carry = 0;
		if(extra >= 5) {
			carry = 1;
		}
		
		// Rounding to the correct sig fig, and turning into a string
		for(int i = finalLength - 1; i >= 0; i--) {
			if(difference[i] != decPoint) {
				difference[i] = (difference[i] + carry);
				carry = difference[i]/10;
				difference[i] %= 10;
				answer = difference[i] + answer;
			} else {
				answer = "." + answer;
			}
		}
		} else {
			for(int i = 0; i < difference.length; i++) {
				if(difference[i] != decPoint) {
					answer += difference[i];
				} else {
					answer += ".";
				}
			}
		}
		

		//Removes leading zeros 
		// Account for more than 1 zero
		if(answer.charAt(0) == '0') {
			answer = answer.substring(1);
		}
		
		// If no decimal should be in the answer, remove the trailing '.'
		// Ex: "2." --> "2"
		if(aDecLength == 0 || bDecLength == 0) {
			answer = answer.substring(0, answer.length()-1);
		}
		return answer;
	}
	
	private static int getLeadingZeros(String x) {
		// Removes the '.' from the string
		if(x.indexOf('.') > -1) {
			x = x.substring(0, x.indexOf('.')) + x.substring(x.indexOf('.') + 1);
		}
		
		// Counts the number of leading zeros
		int leadingZeros = 0;
		while(leadingZeros < x.length() && x.charAt(leadingZeros) == '0') {
			leadingZeros++;
		}
		return leadingZeros;
	}
	
	/*
	 * Description:
	 * - Returns a{n} * b{n} in accordance with sig fig rules
	 * 
	 * Precondition: 
	 * - a{n}, b{n} > 0
	 */
	public static String multiply(String a, String b) {
		SigFig firstOperand = new SigFig(a);
		SigFig secondOperand = new SigFig(b);

		if(firstOperand.sf == -1 || secondOperand.sf == -1) {
			return "Invalid entry.";
		} else {
			// Returns the product, set to the lower of the sig figs of the two operands
			// See setSigFig() at the end of the class
			int productSF = firstOperand.sf < secondOperand.sf ? firstOperand.sf : secondOperand.sf;
			return setSigFig(firstOperand.doubleVal * secondOperand.doubleVal, productSF);
		}
	}
	
	/*
	 * Description:
	 * - Returns a{n} / b{n} in accordance with sig fig rules
	 * 
	 * Precondition:
	 * - a{n}, b{n} > 0
	 */
	public static String divide(String a, String b) {
		SigFig firstOperand = new SigFig(a);
		SigFig secondOperand = new SigFig(b);
		if(firstOperand.sf == -1 || secondOperand.sf == -1) {
			return "Invalid entry.";
		} else {
			// Returns the quotient, set to the lower of the sig figs of the two operands
			// See setSigFig() at the end of the class
			int quotientSF = firstOperand.sf < secondOperand.sf ? firstOperand.sf : secondOperand.sf;
			return setSigFig(firstOperand.doubleVal / secondOperand.doubleVal, quotientSF);
		}
	}
	
	
	private static int determineSigFig(String x) {
		// Checks if x is a valid double
		try {
			Double.parseDouble(x);
		} catch (NumberFormatException e) {
			return -1;
		} finally {
			if(Double.parseDouble(x) < 0) {
				return -1;
			}
		}
		if(x.indexOf('.') != -1) {
			x = x.substring(0, x.indexOf('.')) + x.substring(x.indexOf('.') + 1);
		}
		return x.length()-getLeadingZeros(x);
	}
	
	/*
	 * Description:
	 * - Returns x in the correct number of sig figs sf
	 * 
	 * Precondition:
	 * - sf > 0
	 */
	private static String setSigFig(double x, int sf) {
		return String.format("%." + sf + "g", x);
	}

	public static String parse(String x) {
		if(x.indexOf('E') > -1) {
			String val = x.substring(0, x.indexOf('E'));
			int exp = Integer.parseInt(x.substring(x.indexOf('E') + 1));
			return shift(val, exp);
		} else {
			return x;
		}
	}

	private static String shift(String x, int exp) {
		if(exp < 0) {
			exp *= -1;
			exp--;
			int dotIndex = x.indexOf('.');
			x = x.substring(0, dotIndex) + x.substring(dotIndex + 1);
			for(int i = 0; i < exp; i++) {
				x = "0" + x;
			}
			x = "0." + x;
			return x;
		} else {
			int dotIndex = x.indexOf('.');
			if(dotIndex > -1) {
				int decLength = x.substring(dotIndex + 1).length();
				System.out.println("EXP:" + exp + ", DecLength: " + decLength);
				exp -= decLength;
			}
			for(int i = 0; i < exp; i++) {
				x += "0";
			}
			return x;
		}
	}
}
