package classes.calculators;

import classes.calculators.Data;
import classes.calculators.SigFig;

/* Cross is a collection of static methods use to solve calcluations that either cross 
 * multiply or "cross divide"
 *  - Example of Cross Multiply
 *     - A1/B1 = A2/B2 --> (A1/B1)*B2 = A2
 *  - Example of Cross Divide
 *     - A1*B1 = A2*B2 --> (A1*B1)/B2 = A2
 * 
 * Calculators below use Cross Multiplication:
 *   - Charle's Law          (V1/T1 = V2/T2)
 *   - Gay-Lussac's Law      (P1/T1 = P2/V2)
 *   - Avogadro's Hypothesis (V1/n1 = V2/n2)
 * 
 * Calculators below use Cross Division:
 *   - Concentration         (C1*V1 = C2*V2) 
 *   - Boyle's Law           (P1*V1 = P2*V2)
 */
public class Cross {
    /* Description:
     *  - Calculators that use Cross Multiplication:
     *    - Charle's Law          (V1/T1 = V2/T2)
     *    - Gay-Lussac's Law      (P1/T1 = P2/V2)
     *    - Avogadro's Hypothesis (V1/n1 = V2/n2)
     *  - Cross.multiply() is designed with the UI Textfields in mind
     *    - The UI will have 4 textfields where users input their 3/4 values
     *    - Instead of handling which method to call to solve the equation in 
     *      the Main.java, the handling is done in Cross.multiply()
     * 
     * Precondition:
     *  - Textfields must call the trim() method on all their String values
     *  - Onnly 1/4 String can be an empty String ("")
     */
    public static String multiply(String a1, String b1, String a2, String b2) {
        // Determines which String is empty, so the correct method is called
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
     *  
     * Precondition: 
     *  - All parameters must satisfy the Data constructor preconditions
     */
    private static String multiplyForA(String a1, String b1, String b2) {
        Data A1 = new Data(a1);
        Data B1 = new Data(b1);
        Data B2 = new Data(b2);
        // Determines lowest sig fig amongst the three Data instnace
        int sf = SigFig.min(A1, B1, B2);

        return SigFig.set((A1.getVal() / B1.getVal()) * B2.getVal() , sf) + A1.getUnit();
    }

    /* Description:
     *  - (B1/A1) * A2 = B2
     *  
     * Precondition: 
     *  - All parameters must satisfy the Data constructor preconditions
     */
    private static String multiplyForB(String a1, String b1, String a2) {
        Data A1 = new Data(a1);
        Data B1 = new Data(b1);
        Data A2 = new Data(a2);
        // Determines lowest sig fig amongst the three Data instnace
        int sf = SigFig.min(A1, B1, A2);

        return SigFig.set((B1.getVal() / A1.getVal()) * A2.getVal(), sf) + B1.getUnit();
    }

    /* Description:
     *  - Calculators that use Cross Division:
     *    - Concentration (C1*V1 = C2*V2)
     *    - Boyle's Law   (P1*V1 = P2*V2)
     *  - Cross.divide() is designed with the UI Textfields in mind
     *    - The UI will have 4 textfields where users input their 3/4 values
     *    - Instead of handling which method to call to solve the equation in 
     *      the Main.java, the handling is done in Cross.divide()
     * 
     * Precondition:
     *  - Textfields must call the trim() method on all their String values
     *  - Onnly 1/4 String can be an empty String ("")
     */
    public static String divide(String a1, String b1, String a2, String b2) {
        // Determines which String is empty, so the correct method is called
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
     *  
     * Precondition: 
     *  - All parameters must satisfy the Data constructor preconditions
     */
    private static String divideForA(String a1, String b1, String b2) {
        Data A1 = new Data(a1);
        Data B1 = new Data(b1);
        Data B2 = new Data(b2);
        // Determines lowest sig fig amongst the three Data instnace
        int sf = SigFig.min(A1, B1, B2);

        return SigFig.set((A1.getVal() * B1.getVal()) / B2.getVal(), sf) + A1.getUnit();
    }

    /* Description:
     *  - (A1*B1) / A2 = B2
     *  
     * Precondition: 
     *  - All parameters must satisfy the Data constructor preconditions
     */
    private static String divideForB(String a1, String b1, String a2) {
        Data A1 = new Data(a1);
        Data B1 = new Data(b1);
        Data A2 = new Data(a2);

        int sf = SigFig.min(A1, B1, A2);

        return SigFig.set((A1.getVal() * B1.getVal()) / A2.getVal(), sf) + B1.getUnit();
    }
}