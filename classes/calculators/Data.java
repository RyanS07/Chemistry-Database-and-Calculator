package classes.calculators;

import classes.calculators.SigFig;


// Instances of the Data class represent a value with a unit, in accordance with significant figure rules (sig fig)
public class Data {
    // Below are the conversion constants from each corresponding unit
    private final double mlToL = 1.0/1000;
    private final double mmHgTokPa = 101.3/760;
    private final double atmTokPa = 101.3/1.00;
    private final double psiTokPa = 101.3/14.7;

    // In chemistry, all values have 3 attributes: a numerical value, a unit, and a number of sig figs
    private double val;
    private String unit;
    private int sf;

    /* Description:
     *  - Constructor for Data instances
     *  - There is no default constructor because there is no default unit or value or sig fig for 
     *    any piece of data
     * 
     * Precondition:
     *  - If Data instance is meant to represent temperature, unit must be K
     *  - input must have a unit
     * 
     * Postcondition:
     *  - N/A
     */
    public Data(String input) {
        for(int i = 0; i < input.length(); i++) {
            // Loop iterates until it has found the first char of the units
            if(isUnitChar(input.charAt(i))) {
                // input.substring(0, i) --> numerical value
                this.val = Double.parseDouble(input.substring(0, i));
                this.sf = SigFig.determine(input.substring(0, i));
                // input.substring(i) --> units
                this.unit = input.substring(i);
                break;
            }
        }
        /* All calculators work with:
         *  - kPa for pressure
         *  - L for Volume
         *  - K for temperature        
         */
        convertTokPa();
        convertToL();
        // System.out.println("Val: " + this.val);
        // System.out.println("SF: " + this.sf);
        // System.out.println("Unit: " + this.unit);
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
    private boolean isUnitChar(char input) {
        // returns true if the character is outside the 10 digits, and is not '.' or 'E'
        return !(input >= '0' && input <= '9' || input == '.' || input == 'E');
    }

    /* Descrption:
     *  - Converts a Data instance's val attribute and unit to be in L instead of ml
     * 
     * Precondition:
     *  - this.unit is not an empty String ("")
     * 
     * Postcondition:
     *  - N/A
     */
    private void convertToL() {
        if(this.unit.equals("ml")) {
            this.val *= mlToL;
            this.unit = "L";
        }
    }

    /* Descrption:
     *  - Converts a Data instance's val attribute and unit to be in kPa
     *  
     * Precondition:
     *  - this.unit is not an empty String ("")
     * 
     * Postcondition:
     *  - N/A
     */
    private void convertTokPa() {
        switch(this.unit) {
            case "mmHg":
                this.val *= mmHgTokPa;
                this.unit = "kPa";
                break;
            case "atm":
                this.val *= atmTokPa;
                this.unit = "kPa";
                break;
            case "psi":
                this.val *= psiTokPa;
                this.unit = "kPa";
                break;
        }
    }

    /* Descrption:
     *  - this.sf accessor
     */
    public int getSF() {
        return this.sf;
    }

    /* Descrption:
     *  - this.val accessor
     */
    public double getVal() {
        return this.val;
    }

    /* Descrption:
     *  - this.unit accessor
     */
    public String getUnit() {
        return this.unit;
    }

    @Override
    public String toString() {
        return this.val + this.unit;
    }
}