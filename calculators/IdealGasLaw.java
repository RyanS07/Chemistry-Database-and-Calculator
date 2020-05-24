package calculators;

/* IdealGasLaw is a collection of static methods used to perform calculations using the 
 * ideal gas law (PV = nRT).
 *   - P: the pressure of the ideal gas
 *   - V: the volume of the ideal gas
 *   - n: the moles of the ideal gas
 *   - T: the temperature of the ideal gas
 *   - R: a constant true for all ideal gases
 * 
 * If 3/4 variables (P, V, n, T) are known, the 4th can be solved for
 */
public class IdealGasLaw {
    /* Note: the units of R is (L * kPa)/(mol * K)
     *  - L: litres
     *  - kPa: kilopascals
     *  - mol: moles
     *  - K: kelvin
     * 
     * All client programs that use this class must pass in values in the corresponding
     * units:
     *  - Volume in L
     *  - Pressure in kPa
     *  - Moles in mol
     *  - Temperature in K
     */
    private static final double R = 8.314;

    /* Description:
     *  - P = nRT/V
     *  - This formula is a rearranged version of the ideal gas law above
     *    - Data instances are named to represent each variable
     *  - Only meant to be used internally (hence private)
     *  - Accepts Strings because values are receive from TextFields
     *    - Trailing zeros are lost when parsed from String to int/double
     *    - Trailing zeros count towards sig figs
     *  - Returns a String to maintain any trailing zeros
     *
     * Precondition: 
     *  - vol must be in (L) unit
     *  - mol must be in (mol) unit
     *  - temp must be in (K) unit 
     *  - All parameters must satisfy the Data constructor preconditions
     *    - Must be an int or double contained within a String
     */
    private static String solveForPressure(String vol, String mol, String temp) {
        Data V = new Data(vol);
        Data n = new Data(mol);
        Data T = new Data(temp);
        // Determines lowest sig fig amongst the three Data instnace
        int lowestSigFig = SigFig.min(V, n, T);

        double answer = (n.getVal() * R * T.getVal()) / V.getVal();

        return SigFig.set(answer, lowestSigFig);
    }

    /* 
     * Description:
     *  - V = nRT/P
     *  - This formula is a rearranged version of the ideal gas law above
     *    - Variables are named to represent each variable
     *  - Only meant to be used internally (hence private)
     *  - Accepts Strings because values are receive from TextFields
     *    - Trailing zeros are lost when parsed from String to int/double
     *    - Trailing zeros count towards sig figs
     *  - Returns a String to maintain any trailing zeros
     *
     * Precondition: 
     *  - pressure must be in (kPa) unit
     *  - mol must be in (mol) unit
     *  - temp must be in (K) unit 
     *  - All parameters must satisfy the Data constructor preconditions
     *    - Must be an int or double contained within a String
     */
    private static String solveForVolume(String pressure, String mol, String temp) {
        Data P = new Data(pressure);
        Data n = new Data(mol);
        Data T = new Data(temp);
        // Determines lowest sig fig amongst the three Data instnace
        int lowestSigFig = SigFig.min(P, n, T);

        double answer = (n.getVal() * R * T.getVal()) / P.getVal();

        return SigFig.set(answer, lowestSigFig);
    }

    /* 
     * Description:
     *  - n = PV/RT
     *  - This formula is a rearranged version of the ideal gas law above
     *    - Variables are named to represent each variable
     *  - Only meant to be used internally (hence private)
     *  - Accepts Strings because values are receive from TextFields
     *    - Trailing zeros are lost when parsed from String to int/double
     *    - Trailing zeros count towards sig figs
     *  - Returns a String to maintain any trailing zeros
     *
     * Precondition: 
     *  - pressure must be in (kPa) unit
     *  - vol must be in (L) unit
     *  - temp must be in (K) unit 
     *  - All parameters must satisfy the Data constructor preconditions
     *    - Must be an int or double contained within a String
     */
    private static String solveForMoles(String pressure, String vol, String temp) {
        Data P = new Data(pressure);
        Data V = new Data(vol);
        Data T = new Data(temp);
        // Determines lowest sig fig amongst the three Data instnace
        int lowestSigFig = SigFig.min(P, V, T);
        
        double answer = (P.getVal() * V.getVal()) / (T.getVal() * R);

        return SigFig.set(answer, lowestSigFig);
    }

    /* 
     * Description:
     *  - T = PV/nR 
     *  - This formula is a rearranged version of the ideal gas law above
     *    - Variables are named to represent each variable
     *  - Only meant to be used internally (hence private)
     *  - Accepts Strings because values are receive from TextFields
     *    - Trailing zeros are also lost when parsed from String to int/double
     *    - Trailing zeros count towards sig figs
     *  - Returns a String to maintain any trailing zeros
     *
     * Precondition: 
     *  - pressure must be in (kPa) unit
     *  - vol must be in (L) unit
     *  - mol must be in (mol) unit 
     *  - All parameters must satisfy the Data constructor preconditions
     *    - Must be an int or double contained within a String
     */
    private static String solveForTemp(String pressure, String vol, String mol) {
        Data P = new Data(pressure);
        Data V = new Data(vol);
        Data n = new Data(mol);
        // Determines lowest sig fig amongst the three Data instnace
        int lowestSigFig = SigFig.min(P, V, n);

        double answer = (P.getVal() * V.getVal()) / (n.getVal() * R);
 
        return SigFig.set(answer, lowestSigFig);
    }

    /* Description:
     *  - The method client programs call
     *  - Determines which of the above methods to call
     *  - Method is designed with the UI Textfields in mind
     *    - The UI will have 4 textfields where users input their 3/4 values
     *    - Instead of handling which method to call to solve the equation in 
     *      the Main.java, the handling is done here
     *  - Returns a String to maintain trailing zeros
     * 
     * Precondition:
     *  - Textfields must call the trim() method on all their String values
     *  - Onnly 1/4 String can be an empty String ("")
     */
    public static String solve(String[] inputs) {
        // Values are trimmed to properly determine the number of sig figs in
        // a number.
        for(String value : inputs) {
            value.trim();
        }
        // Reassigned for readability
        String pressure = inputs[0];
        String vol = inputs[1];
        String mol = inputs[2];
        String temp = inputs[3];
        // Determines which String is empty, so the correct method is called.
        if(pressure.equals("")) {
            return solveForPressure(vol, mol, temp);
        } else if(vol.equals("")) {
            return solveForVolume(pressure, mol, temp);
        } else if(mol.equals("")) {
            return solveForMoles(pressure, vol, temp);
        } else {
            return solveForTemp(pressure, vol, mol);
        }
    }
}