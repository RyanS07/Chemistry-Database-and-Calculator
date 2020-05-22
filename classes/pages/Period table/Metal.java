public class Metal extends Element {

    // Non-empty constructor
    public Metal(String[] details) {
        super(details);
    }

    // Implement abstract method from Element class for Metals
    public String determineIon() {
        String elecConfig = getElectronicConfiguration().get();
        return "-" + elecConfig.charAt(elecConfig.length()-1);
    }
}
