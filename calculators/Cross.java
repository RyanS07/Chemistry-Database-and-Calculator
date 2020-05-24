package calculators;

/* Cross is a collection of static methods use to solve calcluations that either cross 
 * multiply or "cross divide".
 *  - Example of Cross Multiply
 *     - A1/B1 = A2/B2 --> (A1/B1)*B2 = A2
 *  - Example of Cross Divide
 *     - A1*B1 = A2*B2 --> (A1*B1)/B2 = A2
 * 
 * All methods use this notation (A1, B1, A2, B2) to denote the respective terms. The 
 * notation uses A1 and A2 instead of A and C because these terms have the same units,
 * in their respective calculators that use them. 
 * 
 * Note: All comments from here until the class header explain how each law that
 * would use the Cross methods works. This can be skipped if deemde necessary. 
 *  
 * Calculators below use Cross Multiplication:
 *   - Charles' Law (V1/T1 = V2/T2)
 *     - Volume and temperature of an ideal gas are proportional if the moles and 
 *       pressure of the gas are held constant. The formula (V/T = k), where k 
 *       is some constant unique to each substance, expresses this property. From this 
 *       constant, the formula V1/T1 = V2/T2 is derived, where V2 and T2 are the volume 
 *       and temperature of the same substance (moles and pressure held constant) at 
 *       different quantities. If 3/4 values are known, the 4th can be solved for. 
 *   - Gay-Lussac's Law (P1/T1 = P2/V2)
 *     - Like Charles' Law, the pressure and temperature of an ideal gas are 
 *       proportional if volume and moles are held constant. The formula (P/T = k) where 
 *       k is some constant unique to each substance, expresses this property. The same
 *       derivation and usage applies here.
 *   - Avogadro's Hypothesis (V1/n1 = V2/n2)
 *     - The same logic as the two laws above applies here, except V/n = k if pressure
 *       and temperature are held constant. Same derivation and usages as well. 
 * 
 * Calculators below use Cross Division:
 *   - Concentration (C1*V1 = C2*V2) 
 *     - Like all the laws above, the concentration (mol/L) and volume (L) of a substance
 *       equals a constant, in this case the moles of the solution. Same derivation and 
 *       usage.
 *   - Boyle's Law (P1*V1 = P2*V2)
 *     - Same logic as the laws above, where pressure and volume are inversely proportional
 *       if moles and temperature are held constant (P/V = k). Same derivation and usage.
 */
public class Cross {
    /* Description:
     *  - Calculators that use Cross Multiplication:
     *    - Charle's Law          (V1/T1 = V2/T2)
     *    - Gay-Lussac's Law      (P1/T1 = P2/V2)
     *    - Avogadro's Hypothesis (V1/n1 = V2/n2)
     * 
     *  - Cross.multiply() is designed with the UI Textfields in mind
     *    - The UI will have 4 textfields where users input their 3/4 values
     *      - Input validation is handled by the client program
     *    - Determining which String is empty is handled in Cross.multiply()
     * 
     *  - Accepts Strings because values are receive from TextFields
     *    - Trailing zeros are lost when parsed from String to int/double
     *    - Trailing zeros count towards sig figs
     *  - Returns a String to maintain any trailing zeros
     * 
     * Precondition:
     *  - Only 1/4 String can be an empty String ("")
     *  - All values must be an int or double contained within a String
     *    Ex: 1.23 is valid, 1.23a is not valid
     */
    public static String multiply(String[] inputs) {
        // Values are trimmed to properly determine the number of sig figs in
        // a number.
        for(String value : inputs) {
            value.trim();
        }
        // Reassigned for readability
        String a1 = inputs[0];
        String b1 = inputs[1];
        String a2 = inputs[2];
        String b2 = inputs[3];
        /* Determines which String is empty, so the correct method is called.
         * Contains 4 if/else clauses instead of 2 to allow the user to leave 
         * the A1 or B1 empty (more user flexibility and a lower chance of the
         * app breaking).
         */
        if(a1.equals("")) {
            return multiplyForA(a2, b2, b1);
        } else if(b1.equals("")) {
            return multiplyForB(a2, b2, a1);
        } else if(a2.equals("")) {
            return multiplyForA(a1, b1, b2);
        } else {
            return multiplyForB(a1, b1, a2);
        }
    }

    /* Description:
     *  - (A1/B1) * B2 = A2
     *  - Only meant to be used internally (hence private)
     *  - Accepts Strings because values are receive from TextFields
     *    - Trailing zeros are lost when parsed from String to int/double
     *    - Trailing zeros count towards sig figs
     *  - Returns a String to maintain any trailing zeros
     *  
     * Precondition: 
     *  - All parameters must satisfy the Data constructor preconditions
     *    - Must be an int or double contained within a String
     */
    private static String multiplyForA(String a1, String b1, String b2) {
        Data A1 = new Data(a1);
        Data B1 = new Data(b1);
        Data B2 = new Data(b2);
        // Determines lowest sig fig amongst the three Data instnace
        int lowestSigFig = SigFig.min(A1, B1, B2);

        double answer = (A1.getVal() / B1.getVal()) * B2.getVal();

        return SigFig.set(answer, lowestSigFig);
    }

    /* Description:
     *  - (B1/A1) * A2 = B2
     *  - Only meant to be used internally (hence private)
     *  - Accepts Strings because values are receive from TextFields
     *    - Trailing zeros are lost when parsed from String to int/double
     *    - Trailing zeros count towards sig figs
     *  - Returns a String to maintain any trailing zeros
     *  
     * Precondition: 
     *  - All parameters must satisfy the Data constructor preconditions
     *    - Must be an int or double contained within a String
     */
    private static String multiplyForB(String a1, String b1, String a2) {
        Data A1 = new Data(a1);
        Data B1 = new Data(b1);
        Data A2 = new Data(a2);
        // Determines lowest sig fig amongst the three Data instnace
        int lowestSigFig = SigFig.min(A1, B1, A2);

        double answer = (B1.getVal() / A1.getVal()) * A2.getVal();

        return SigFig.set(answer, lowestSigFig);
    }

    /* Description:
     *  - Calculators that use Cross Division:
     *    - Concentration (C1*V1 = C2*V2)
     *    - Boyle's Law   (P1*V1 = P2*V2)
     * 
     *  - Cross.divide() is designed with the UI Textfields in mind
     *    - The UI will have 4 textfields where users input their 3/4 values
     *      - Input validation is handled by the client program
     *    - Determining which String is empty is handled in Cross.divide()
     * 
     *  - Accepts Strings because values are receive from TextFields
     *    - Trailing zeros are lost when parsed from String to int/double
     *    - Trailing zeros count towards sig figs
     *  - Returns a String to maintain any trailing zeros
     * 
     * Precondition:
     *  - Only 1/4 String can be an empty String ("")
     *  - All values must be an int or double contained within a String
     *    Ex: 1.23 is valid, 1.23a is not valid
     */
    public static String divide(String[] inputs) {
        // Values are trimmed to properly determine the number of sig figs in
        // a number.
        for(String values : inputs) {
            values.trim();
        }
        // Reassigned for readability
        String a1 = inputs[0];
        String b1 = inputs[1];
        String a2 = inputs[2];
        String b2 = inputs[3];
        /* Determines which String is empty, so the correct method is called.
         * Contains 4 if/else clauses instead of 2 to allow the user to leave 
         * the A1 or B1 empty (more user flexibility and a lower chance of the
         * app breaking).
         */
        if(a1.equals("")) {
            return divideForA(a2, b2, b1);
        } else if(b1.equals("")) {
            return divideForB(a2, b2, a1);
        } else if(a2.equals("")) {
            return divideForA(a1, b1, b2);
        } else {
            return divideForB(a1, b1, a2);
        }
    }

    /* Description:
     *  - (A1*A1) * B2 = A2
     *  - Only meant to be used internally (hence private)
     *  - Accepts Strings because values are receive from TextFields
     *    - Trailing zeros are lost when parsed from String to int/double
     *    - Trailing zeros count towards sig figs
     *  - Returns a String to maintain any trailing zeros
     *  
     * Precondition: 
     *  - All parameters must satisfy the Data constructor preconditions
     *    - Must be an int or double contained within a String
     */
    private static String divideForA(String a1, String b1, String b2) {
        Data A1 = new Data(a1);
        Data B1 = new Data(b1);
        Data B2 = new Data(b2);
        // Determines lowest sig fig amongst the three Data instance
        int lowestSigFig = SigFig.min(A1, B1, B2);

        double answer = (A1.getVal() * B1.getVal()) / B2.getVal();

        return SigFig.set(answer, lowestSigFig);
    }

    /* Description:
     *  - (A1*B1) / A2 = B2
     *  - Only meant to be used internally (hence private)
     *  - Accepts Strings because values are receive from TextFields
     *    - Trailing zeros are lost when parsed from String to int/double
     *    - Trailing zeros count towards sig figs
     *  - Returns a String to maintain any trailing zeros
     *  
     * Precondition: 
     *  - All parameters must satisfy the Data constructor preconditions
     *    - Must be an int or double contained within a String
     */
    private static String divideForB(String a1, String b1, String a2) {
        Data A1 = new Data(a1);
        Data B1 = new Data(b1);
        Data A2 = new Data(a2);
        // Determines lowest sig fig amongst the three Data instance
        int lowestSigFig = SigFig.min(A1, B1, A2);

        double answer = (A1.getVal() * B1.getVal()) / A2.getVal();

        return SigFig.set(answer, lowestSigFig);
    }
}