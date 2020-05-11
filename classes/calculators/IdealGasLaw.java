// Made by Ryan Seto

package classes.calculators;

import classes.calculators.SigFig;
import classes.calculators.Data;

/* IdealGasLaw is a collection of static methods used to perform calculations using the 
 * ideal gas law (PV = nRT).
 */
public class IdealGasLaw {
    // Note: all pressure values must be in kPa
    public static final double R = 8.314;

    /* 
     * Description:
     *  - P = nRT/V
     *
     * Precondition: 
     *  - vol must be in (L) unit
     *  - mol must be in (mol) unit
     *  - temp must be in (K) unit 
     *  - All parameters must satisfy the Data constructor preconditions
     */
    public static String solveForPressure(String vol, String mol, String temp) {
        Data V = new Data(vol);
        Data n = new Data(mol);
        Data T = new Data(temp);
        // Determines lowest sig fig amongst the three Data instnace
        int sf = SigFig.min(V, n, T);

        return SigFig.set((n.getVal() * R * T.getVal()) / V.getVal(), sf) + "kPa";
    }

    /* 
     * Description:
     *  - V = nRT/P
     *
     * Precondition: 
     *  - pressure must be in (kPa) unit
     *  - mol must be in (mol) unit
     *  - temp must be in (K) unit 
     */
    public static String solveForVolume(String pressure, String mol, String temp) {
        Data P = new Data(pressure);
        Data n = new Data(mol);
        Data T = new Data(temp);
        // Determines lowest sig fig amongst the three Data instnace
        int sf = SigFig.min(P, n, T);
        
        return SigFig.set((n.getVal() * R * T.getVal()) / P.getVal(), sf) + "L";
    }

    /* 
     * Description:
     *  - n = PV/RT
     *
     * Precondition: 
     *  - pressure must be in (kPa) unit
     *  - vol must be in (L) unit
     *  - temp must be in (K) unit 
     */
    public static String solveForMoles(String pressure, String vol, String temp) {
        Data P = new Data(pressure);
        Data V = new Data(vol);
        Data T = new Data(temp);
        // Determines lowest sig fig amongst the three Data instnace
        int sf = SigFig.min(P, V, T);
        
        return SigFig.set((P.getVal() * V.getVal()) / (T.getVal() * R), sf) + "mol";
    }

    /* 
     * Description:
     *  - T = PV/nR 
     *
     * Precondition: 
     *  - pressure must be in (kPa) unit
     *  - vol must be in (L) unit
     *  - mol must be in (mol) unit 
     */
    public static String solveForTemp(String pressure, String vol, String mol) {
        Data P = new Data(pressure);
        Data V = new Data(vol);
        Data n = new Data(mol);
        // Determines lowest sig fig amongst the three Data instnace
        int sf = SigFig.min(P, V, n);
        
        return SigFig.set((P.getVal() * V.getVal()) / (n.getVal() * R), sf) + "K";
    }
}