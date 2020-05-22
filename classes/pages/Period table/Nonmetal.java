public class Nonmetal extends Element {

    // Non-empty constructor
    public Nonmetal(String[] details) {
        super(details);
    }

    // Implement abstract method from Element class for Non-metals
    public String determineIon() {
        String elecConfig = getElectronicConfiguration().get();
        return "+" + elecConfig.charAt(elecConfig.length()-1);
    }
}
