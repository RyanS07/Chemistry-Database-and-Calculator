// Made by Ryan Seto

package classes.calculators;

// Instances of the Data class represent a value with a unit, in accordance with significant figure rules (sig fig)
public class Data {
    // In chemistry, all values have 3 attributes: a numerical value, and a number of sig figs
    private double val;
    private int sf;

    // Only accepted units
    public static String[] validUnits = {"kPa", "mmHg", "atm", "psi", "L", "ml", "mol", "K", "mol/L"};

    /* Description:
     *  - Constructor for Data instances
     *  - There is no default constructor because there is no default unit or value or sig fig for 
     *    any piece of data
     * 
     * Precondition:
     *  - If Data instance is meant to represent temperature, unit must be K
     *  - input must have one of the accepted units
     *  - Validity must be checked before instantiating
     * 
     * Postcondition:
     *  - N/A
     */
    protected Data(String input) {
        this.val = Double.parseDouble(input);
        this.sf = SigFig.determine(input);

        /* All calculators work with:
         *  - kPa for pressure
         *  - L for Volume
         *  - K for temperature        
         */
    }

    /* Description:
     *  - Determines if input is a character of the Data instance's unit
     *  - See Constructor for usage
     *  
     * Precondition:
     *  - Should only be used in constructor
     * 
     * Postcondition:
     *  - N/A
     */
    protected static boolean isUnitChar(char input) {
        // returns true if the character is outside the 10 digits, and is not '.' or 'E'
        return !(input >= '0' && input <= '9' || input == '.' || input == 'E');
    }

    public static boolean hasValidUnit(String data) {
        boolean unitFound = false;
        for(int i = 0; i < data.length(); i++) {
            if(isUnitChar(data.charAt(i))) {
                String unit = data.substring(i);
                for(int j = 0; j < validUnits.length; j++) {
                    if(unit.equals(validUnits[j])) {
                        unitFound = unit.equals(validUnits[j]);
                        break;
                    }
                }
                break;
            }
        }
        return unitFound;
    }

    /* Descrption:
     *  - this.sf accessor
     */
    protected int getSF() {
        return this.sf;
    }

    /* Descrption:
     *  - this.val accessor
     */
    protected double getVal() {
        return this.val;
    }
}