// Made by Ryan Seto

package classes.calculators;

/* IdealGasLaw is a collection of static methods used to perform calculations using the 
 * ideal gas law (PV = nRT).
 */
public class IdealGasLaw {
    // Note: all pressure values must be in kPa
    private static final double R = 8.314;

    /* 
     * Description:
     *  - P = nRT/V
     *  - This formula is a rearranged version of the ideal gas law above
     *    - Variables are named to represent each variable
     *
     * Precondition: 
     *  - vol must be in (L) unit
     *  - mol must be in (mol) unit
     *  - temp must be in (K) unit 
     *  - All parameters must satisfy the Data constructor preconditions
     */
    private static String solveForPressure(String vol, String mol, String temp) {
        Data V = new Data(vol);
        Data n = new Data(mol);
        Data T = new Data(temp);
        // Determines lowest sig fig amongst the three Data instnace
        int sf = SigFig.min(V, n, T);

        return SigFig.set((n.getVal() * R * T.getVal()) / V.getVal(), sf);
    }

    /* 
     * Description:
     *  - V = nRT/P
     *  - This formula is a rearranged version of the ideal gas law above
     *    - Variables are named to represent each variable
     *
     * Precondition: 
     *  - pressure must be in (kPa) unit
     *  - mol must be in (mol) unit
     *  - temp must be in (K) unit 
     */
    private static String solveForVolume(String pressure, String mol, String temp) {
        Data P = new Data(pressure);
        Data n = new Data(mol);
        Data T = new Data(temp);
        // Determines lowest sig fig amongst the three Data instnace
        int sf = SigFig.min(P, n, T);
        
        return SigFig.set((n.getVal() * R * T.getVal()) / P.getVal(), sf)
        ;
    }

    /* 
     * Description:
     *  - n = PV/RT
     *  - This formula is a rearranged version of the ideal gas law above
     *    - Variables are named to represent each variable
     *
     * Precondition: 
     *  - pressure must be in (kPa) unit
     *  - vol must be in (L) unit
     *  - temp must be in (K) unit 
     */
    private static String solveForMoles(String pressure, String vol, String temp) {
        Data P = new Data(pressure);
        Data V = new Data(vol);
        Data T = new Data(temp);
        // Determines lowest sig fig amongst the three Data instnace
        int sf = SigFig.min(P, V, T);
        
        return SigFig.set((P.getVal() * V.getVal()) / (T.getVal() * R), sf);
    }

    /* 
     * Description:
     *  - T = PV/nR 
     *  - This formula is a rearranged version of the ideal gas law above
     *    - Variables are named to represent each variable
     *
     * Precondition: 
     *  - pressure must be in (kPa) unit
     *  - vol must be in (L) unit
     *  - mol must be in (mol) unit 
     */
    private static String solveForTemp(String pressure, String vol, String mol) {
        Data P = new Data(pressure);
        Data V = new Data(vol);
        Data n = new Data(mol);
        // Determines lowest sig fig amongst the three Data instnace
        int sf = SigFig.min(P, V, n);
        
        return SigFig.set((P.getVal() * V.getVal()) / (n.getVal() * R), sf);
    }

    /* Description:
     *  - Determines which of the above methods to call
     *  - Method is designed with the UI Textfields in mind
     *    - The UI will have 4 textfields where users input their 3/4 values
     *    - Instead of handling which method to call to solve the equation in 
     *      the Main.java, the handling is done here
     * 
     * Precondition:
     *  - Textfields must call the trim() method on all their String values
     *  - Onnly 1/4 String can be an empty String ("")
     */
    public static String solve(String[] inputs) {
        // Reassigned for readability
        String pressure = inputs[0];
        String vol = inputs[1];
        String mol = inputs[2];
        String temp = inputs[3];
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