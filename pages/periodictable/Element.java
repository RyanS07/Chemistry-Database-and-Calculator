package pages.periodictable;

import java.lang.reflect.Field;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public abstract class Element {
    /*
     * The periodic table will be displayed in a grid of buttons. When pressed, each
     * button/icon will tell the client program which elements are desired to be
     * displayed in more detail.
     */
    private Button icon;
    // All buttons are squares, so only one dimension size
    private double iconSize = 45;
    // The style Strings are the CSS properties for the icon in the three states
    // Two are set as protected so they can be access
    private String defaultStyle;
    // Hover Style is the color displayed when a mouse hovers over the icon
    private String hoverStyle;
    // See setIcon() for an explanation
    private String displayStyle;
    // Stores if the button has been selected
    private boolean clicked;


    private static String[] propertyList;

    public static void setPropertyList(String[] properties) {
        Element.propertyList = properties;
    }
    /*
     * All state below is are of type SimpleStringProperty to display in a TableView
     * object.
     * 
     * For context, see TablePage.java (Lines ___) The reason the Element class does
     * not just simply store a String[] and use the custom CallBack like the rest of
     * the tables is because each value may need to be called on its own in later
     * added methods. To create a more "editting friendly" class, all are stored in
     * their own variable.
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
    private SimpleStringProperty ionCharge;

    public Element(String[] details) {
        for (int i = 0; i < details.length; i++) {
            Field state;
            String value = details[i];
            String property = propertyList[i];
            try {
                state = Element.class.getDeclaredField(property);
                if(property.equals("ionRadius")) {
                    int key = value.indexOf('(');
                    if(key != -1) {
                        String ionRadius = value.substring(0, key).trim();
                        String ionCharge = value.substring(key + 1, key + 3);
                        state.set(this, new SimpleStringProperty(ionRadius));
                        this.ionCharge = new SimpleStringProperty(ionCharge);
                    } else {
                        state.set(this, new SimpleStringProperty(value));
                        this.ionCharge = new SimpleStringProperty("N/A");
                    }
                } else {
                    state.set(this, new SimpleStringProperty(value));
                }
            } catch (NoSuchFieldException e) {
                System.out.println("NoSuchFieldException");
            } catch (SecurityException e) {
                System.out.println("SecurityException");
            } catch (IllegalArgumentException e) {
                System.out.println("IllegalArgumentException");
            } catch (IllegalAccessException e) {
                System.out.println("IllegalAccessException");
            }
        }
    }

    protected void setIcon() {
        this.icon = new Button(getSymbol());
        this.icon.setPrefWidth(iconSize);
        this.icon.setPrefHeight(iconSize);
        int elementPeriod = Integer.parseInt(getPeriod());
        if(!getGroup().equals("N/A") && !getGroup().equals("No Data")) {
            int elementGroup = Integer.parseInt(getGroup());
            this.icon.setLayoutX(iconSize * (elementGroup-1));
            this.icon.setLayoutY(iconSize * (elementPeriod-1));   
        } else {
            int atomicNumber = Integer.parseInt(getAtomicNumber());
            if(atomicNumber >= 57 && atomicNumber <= 71) {
                this.icon.setLayoutX(3 * iconSize + (iconSize * (atomicNumber - 58)));
            } else {
                this.icon.setLayoutX(3 * iconSize + (iconSize * (atomicNumber - 90)));
            }
            this.icon.setLayoutY(iconSize * (elementPeriod + 2));
        }
        this.displayStyle = this.defaultStyle;
        this.icon.setStyle(this.displayStyle);
        this.icon.setOnMouseEntered(event -> this.icon.setStyle(this.hoverStyle));
        this.icon.setOnMouseExited(event -> this.icon.setStyle(this.displayStyle));
        this.icon.setOnAction(event -> {
            this.clicked = !this.clicked;
            this.displayStyle = this.clicked ? this.hoverStyle : this.defaultStyle;
        });
        // Make deselect all
    }

    public Button getIcon() {
        return this.icon;
    } 
    
    public boolean gotClicked() {
        return this.clicked;
    }

    public void setClicked(boolean status) {
        this.clicked = status;
        this.displayStyle = this.clicked ? this.hoverStyle : this.defaultStyle;
        this.icon.setStyle(this.displayStyle);
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
