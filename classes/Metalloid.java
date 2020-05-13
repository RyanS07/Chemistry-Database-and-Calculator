public class Metalloid extends Element {

    // Default constructor
    public Metalloid() {
        super();
    }

    // Non-empty constructor
    public Metalloid(int atomicNumber, String element, String symbol, double atomicMass, int numberOfNeutrons, int numberOfProtons, int numberOfElectrons, int period, int group, String phase, String isRadioactive, String isNatural, String type, double atomicRadius, double electronegativity, double firstIonization, double density, double meltingPoint, double boilingPoint, int numberOfIsotopes, String discoverer, int year, double specificHeat, int numberOfShells, int numberOfValence) {
        super(atomicNumber, element, symbol, atomicMass, numberOfNeutrons, numberOfProtons, numberOfElectrons, period, group, phase, isRadioactive, isNatural, type, atomicRadius, electronegativity, firstIonization, density, meltingPoint, boilingPoint, numberOfIsotopes, discoverer, year, specificHeat, numberOfShells, numberOfValence);
    }
}
