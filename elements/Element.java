package elements;

import java.lang.reflect.Field;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

/* The Element abstract class is the superclass to the:
 *  - Metal class
 *  - Nonmetal class
 *  - Metalloid class
 *  - Filler class (Lanthanoids and Actinoids)
 * This class defines state and behavior all types of elements have. 
 * 
 * The only difference between each subclass is the color of their icon in the
 * periodic table, however, in their true definition each group of elements have
 * more differences. With our current chemistry knowledge, we can't fully implement
 * these classes (especially lanthanoids and actinoids), but in a fully fledged app
 * these groups would have their own classes (and subclasses). 
 */
public abstract class Element {
    /* The periodic table will be displayed in a grid of buttons. When pressed, each
     * button/icon will tell the client program which elements are desired to be
     * displayed in more detail.
     */
    private Button icon;
    // All buttons are squares, so only one dimension size
    private double iconSize = 45;
    // The style Strings are the CSS properties for the icon in the three states
    private String defaultStyle;
    // Hover Style is the color displayed when a mouse hovers over the icon
    private String hoverStyle;
    // See setIcon() for the purpose of displayStyle
    private String displayStyle;
    // Stores if the button has been selected
    private boolean clicked;

    /* The SimpleStringProperty attributes of the Element class are intentionally
     * named to match the headers of the periodic table csv. This way, the 
     * constructor can use the propertyList alongisde the details[] parameter to
     * automatically assign all attributes (instead of manually inputing).
     */
    private static String[] propertyList;
    public static void setPropertyList(String[] properties) {
        // propertyList is only called once when the periodic table csv is parsed.
        // The first line of the csv (the headers) are then assigned here. 
        // See TablePage.java (Lines ___).
        Element.propertyList = properties;
    }
    /*
     * All state below is are of type SimpleStringProperty to display in a TableView
     * object.
     * 
     * For context, see TablePage.java (Lines ___).
     * The reason the Element class does not just store a String[] and use the custom 
     * CallBack like the rest of the tables is because each value may need to be called 
     * on its own in later added methods (see setIcon()). To create a more "editting 
     * friendly" class, all are stored in their own variable.
     */
    private SimpleStringProperty atomicNumber;
    private SimpleStringProperty symbol;
    private SimpleStringProperty name;
    private SimpleStringProperty atomicMass;
    private SimpleStringProperty electronConfiguration;
    private SimpleStringProperty period;
    private SimpleStringProperty group;
    private SimpleStringProperty electronegativity;
    private SimpleStringProperty vanDelWaalsRadius;
    private SimpleStringProperty ionRadius;
    private SimpleStringProperty ionizationEnergy;
    private SimpleStringProperty electronAffinity;
    private SimpleStringProperty standardState;
    private SimpleStringProperty meltingPoint;
    private SimpleStringProperty boilingPoint;
    private SimpleStringProperty density;
    private SimpleStringProperty groupBlock;
    // See the constructor for an explanation
    private SimpleStringProperty ionCharge = new SimpleStringProperty("N/A");

    /* Description:
     *  - 
     */
    public Element(String[] details) {
        // Loops through all the details of the element
        for (int i = 0; i < details.length; i++) {
            /* A Field object represents a data field of a class
             * Class attributes can then be indexed using Strings.
             * Since all SimpleStringProperty attributes were named
             * after the corresponding headers in the csv, (excluding
             * the added ionCharge), the code uses propertyList[] to
             * loop and assign values to their corresponding header.
             * Source/Research: https://stackoverflow.com/questions/744226/java-reflection-how-to-get-the-name-of-a-variable
             * 
             * This is to avoid having a constructor with 17 
             * parameters and to avoid hardcoding. Also makes code 
             * easier to debug. 
             * 
             * Ex:
             * this.atomicMass = new SimpleStringProperty(details[3]);
             */
            Field state;
            String value = details[i];
            String property = propertyList[i];
            try {
                state = Element.class.getDeclaredField(property);
                /* The csv we found online kept an element's ionCharge as 
                 * part of the ionRadius property in brackets. To avoid 
                 * manually editing 100+ elements, the code separates the 
                 * two properties here. 
                 * 
                 * Noble gases have a value of "N/A" and some other 
                 * elements have "No Data". As such, if the value for
                 * that element's ionRadius is 1 of the 2, it does
                 * not have an ionCharge. The reason ionCharge is set 
                 * to "N/A" by default is to avoid splitting the if 
                 * clause below into 2 and adding another else. 
                 */
                if(property.equals("ionRadius") && !value.equals("N/A") && !value.equals("No Data")) {
                    int start = value.indexOf('(');
                    int end = value.indexOf(')');
                    String ionRadius = value.substring(0, start).trim();
                    String ionCharge = value.substring(start + 1, end);
                    /* Parameter 1 is the object the field belongs too
                     * Parameter 2 is the new value of the field
                     */
                    state.set(this, new SimpleStringProperty(ionRadius));
                    this.ionCharge = new SimpleStringProperty(ionCharge);
                } else {
                    // Source/Research: https://stackoverflow.com/questions/24094871/set-field-value-with-reflection
                    state.set(this, new SimpleStringProperty(value));
                }
            } catch (NoSuchFieldException | SecurityException | 
                    IllegalArgumentException | IllegalAccessException e) {
                /* getDeclaredField(String name) can throw a 
                 * NoSuchFieldException if no field called name exists or
                 * a SecurityException if a security manager is present.
                 * Source/Research: https://www.tutorialspoint.com/java/lang/class_getdeclaredfield.htm
                 * 
                 * set(Object obj, Object value) can throw an
                 * IllegalArgumentException if obj is not an instances of
                 * the class the field is retrieved from or an
                 * IllegalAccessException if the field is inaccessable. 
                 * Source/Research: https://www.geeksforgeeks.org/field-set-method-in-java-with-examples/
                 */
                e.printStackTrace();
            } 
        }
    }

    /* Initializes the element's icon.
     * This method is called at the end of every subclasses' constructor.
     * The reason for this is because the icon depends on some state unique
     * to each subclass (hoverStyle and defaultStyle), but the behavior is 
     * the same across all subclasses. 
     * 
     * Only meant to be used in the constructors of subclass methods (hence 
     * protected).
     *   - Not private, since the subclass can not access it.
     *   - Not public, since only the subclass need access.
     */
    protected void setIcon() {
        this.icon = new Button(getSymbol());
        this.icon.setPrefWidth(iconSize);
        this.icon.setPrefHeight(iconSize);
        int elementPeriod = Integer.parseInt(getPeriod());
        // In other words, if this element is not a lanthanoid or actinoid.
        if(!getGroup().equals("N/A")) {
            /* Since an element's period and group are the rows and columns
             * of the periodic table, they can be used to position each
             * element. 
             */
            int elementGroup = Integer.parseInt(getGroup());
            this.icon.setLayoutX(iconSize * (elementGroup-1));
            this.icon.setLayoutY(iconSize * (elementPeriod-1));   
        } else {
            /* Since lanthanoids and actinoids are positioned outside the 
             * main table, they require different logic to determine their
             * position. 
             * 
             * 
             */
            int atomicNumber = Integer.parseInt(getAtomicNumber());
            // atomicNumber 57-71 are the lanthinoids
            if(atomicNumber >= 57 && atomicNumber <= 71) {
                // Puts the element icon 2 spots in from the left edge of the
                // table. 
                // Uses atomicNumber instead of period to determine subsequent
                // position.  
                this.icon.setLayoutX(iconSize * (2 + (atomicNumber - 57)));
            } else {
                // The rest are actinoids (atomicNumber 90-103)
                this.icon.setLayoutX(2 * iconSize + (iconSize * (atomicNumber - 89)));
            }
            // Positions the element 3 rows below its period
            // (elementPeriod - 1) + 3 = elementPeriod + 2
            this.icon.setLayoutY(iconSize * (elementPeriod + 2));
        }
        // This following block is why setIcon() is called at the end of each constructor
        this.displayStyle = this.defaultStyle;
        this.icon.setStyle(this.displayStyle);
        this.icon.setOnMouseEntered(event -> this.icon.setStyle(this.hoverStyle));
        this.icon.setOnMouseExited(event -> this.icon.setStyle(this.displayStyle));
        // When an icon is clicked, to signify that it has been selected, it turns into its
        // hoverStyle
        this.icon.setOnAction(event -> {
            this.clicked = !this.clicked;
            this.displayStyle = this.clicked ? this.hoverStyle : this.defaultStyle;
        });
    }

    // Used to reset icon status
    // See TablePage.java (Line ___)
    public void resetClicked() {
        this.clicked = false;
        this.displayStyle = this.clicked ? this.hoverStyle : this.defaultStyle;
        this.icon.setStyle(this.displayStyle);
    }

    /* this.clicked accessor
     * Called gotClicked() instead of getClicked() because it improves code 
     * readability.
     * Ex: if(currentElement.gotClicked()) {...}
     */
    public boolean gotClicked() {
        return this.clicked;
    }

    /* icon accessor
     * Used to put the Element subclass instance in the Periodic Table display
     */
    public Button getIcon() {
        return this.icon;
    } 
    
    /* Below are all the accessor methods for the SimpleStringProperty variables
     * Primary purpose is for the PropertyValueFactory in the TableView object
     * that displays an Element's attributes.
     */
    public String getAtomicNumber() {
        return this.atomicNumber.get();
    }

    public String getSymbol() {
        return this.symbol.get();
    }

    public String getName() {
        return this.name.get();
    }

    public String getAtomicMass() {
        return this.atomicMass.get();
    }

    public String getElectronConfiguration() {
        return this.electronConfiguration.get();
    }

    public String getPeriod() {
        return this.period.get();
    }

    public String getGroup() {
        return this.group.get();
    }

    public String getElectronegativity() {
        return this.electronegativity.get();
    }

    public String getVanDelWaalsRadius() {
        return this.vanDelWaalsRadius.get();
    }

    public String getIonRadius() {
        return this.ionRadius.get();
    }

    public String getIonizationEnergy() {
        return this.ionizationEnergy.get();
    }

    public String getElectronAffinity() {
        return this.electronAffinity.get();
    }

    public String getStandardState() {
        return this.standardState.get();
    }

    public String getMeltingPoint() {
        return this.meltingPoint.get();
    }

    public String getBoilingPoint() {
        return this.boilingPoint.get();
    }

    public String getDensity() {
        return this.density.get();
    }

    public String getGroupBlock() {
        return this.groupBlock.get();
    }
    
    public String getIonCharge() {
        return this.ionCharge.get();
    }

    /* These two mutator methods are only meant to be used in
     * the constructors of subclass methods (hence protected).
     *   - Not private, since the subclass can not access it.
     *   - Not public, since only the subclass need access.
     */
    protected void setDefaultStyle(String style) {
        this.defaultStyle = style;
    }

    protected void setHoverStyle(String style) {
        this.hoverStyle = style;
    }
}
