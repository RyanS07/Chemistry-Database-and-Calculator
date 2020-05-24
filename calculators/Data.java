package calculators;

// Instances of the Data class represent a value with a unit, in accordance with significant 
// figure rules (sig fig)
// From here on, significant figures will be referred to as sig fig or sf.
public class Data {
    private double val;
    private int sf;

    /* Description:
     *  - Constructor for Data instances
     *  - There is no default constructor because there is no default sig fig for 
     *    any piece of data
     * 
     * Precondition:
     *  - The client program must check before instantiating if input is a valid 
     *    double contained in a String
     */
    protected Data(String input) {
        this.val = Double.parseDouble(input);
        this.sf = SigFig.determine(input);
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