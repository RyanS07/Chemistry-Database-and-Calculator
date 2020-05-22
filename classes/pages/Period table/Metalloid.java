public class Metalloid extends Element {

    // Non-empty constructor
    public Metalloid(String[] details) {
        super(details);
    }

    // Implement abstract method from Element class for Metalloids
    public String determineIon() {
        String elecConfig = getElectronicConfiguration().get();
        return "+" + elecConfig.charAt(elecConfig.length()-1);
    }
}
