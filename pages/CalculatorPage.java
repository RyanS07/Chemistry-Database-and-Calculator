package pages;

import java.io.IOException;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;

import calculators.IdealGasLaw;
import calculators.Cross;


public class CalculatorPage extends Page {
    // The back button that returns to the table page.
    private Button back;
    /* A list of all the different types of calculators. Usage ranges from creating
     * a Menu object to locating .txt files. 
     */
    private String[] calcList = {"Ideal Gas Law", "Boyle's Law", "Charles' Law", 
        "Gay-Lussac's Law", "Avogadro's Hypothesis", "Concentration"};
    // A HashMap containing each calcType's input headers. Used to create input fields.
    private HashMap<String, String[]> calcMap = new HashMap<String, String[]>();

    // The Menu that displays the different calculator options.
    private MenuBar calcMenu;

    private Text calcTitle;
    private int numInputs = 4;
    private HBox inputHBox;
    private VBox calcBox;
    private String calcType;
    private Text calcResponse;

    /* The constructor is the only public method of any Page subclass. This lowers
     * the number of method calls in the client program, improving readability and
     * centralizing all of the code pertaining to a specific Page subclass to that 
     * file. 
     */
    public CalculatorPage() {
        // All Page subclasses build on Page, so they call super() and add some
        // additional behavior unique to each subclass. 
        super();

        // Creating the back button to return to the previous page.
        // Note, this only initializes the button. It does not tell the
        // button where to redirect to when clicked (see setRedirects()). 
        this.back = setBackButton();
        this.pane.getChildren().add(this.back);

        setCalcMenu();
        // this.calcList[0], "Ideal Gas Law", is the default calculator. 
        this.calcType = this.calcList[0];
        setCalcMap();
        setCalc(this.calcType);
        setRedirects();
    }

    /* This method establishes how each page redirects to each other. 
     * 
     */
    protected void setRedirects() {
        this.back.setOnAction(event -> {
            goTo("Table");
        });
    }

    // Called once
    private void setCalcMap() {
        String[][] calcHeaders = { 
            { "Pressure (kPa)", "Volume (L)", "Moles (mol)", "Temperature (K)" },
            { "Pressure 1 (kPa)", "Volume 1 (L)", "Pressure 2 (kPa)", "Volume 2 (L)" }, 
            { "Volume 1 (L)", "Temperature 1 (K)", "Volume 2 (L)", "Temperature 2 (K)" },
            { "Pressure 1 (kPa)", "Temperature 1 (K)", "Pressure 2 (kPa)", "Temperature 2 (K)" }, 
            { "Volume 1 (L)", "Moles 1 (mol)", "Volume 2 (L)", "Moles 2 (mol)" },
            { "Concentration 1 (mol/L)", "Volume 1 (L)", "Concentration 2 (mol/L)", "Volume 2 (L)"} 
        };
        for (int i = 0; i < this.calcList.length; i++) {
            this.calcMap.put(this.calcList[i], calcHeaders[i]);
        }
    }

    private void setCalc(String type) {
        // Clears the old setup, if there is one
        this.pane.getChildren().remove(this.calcBox);

        this.inputHBox = new HBox(20);
        this.inputHBox.setPadding(Page.boxPadding);
        
        this.calcType = type;
        this.calcTitle = new Text(this.calcType);

        HBox guideBox = new HBox();
        guideBox.setPadding(boxPadding);
        Text instructions = new Text();
        Text sigFigGuide = new Text();
        try {
            instructions.setText(getGuide(this.calcType));
            sigFigGuide.setText(getGuide("Sig Fig"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        guideBox.getChildren().addAll(instructions, sigFigGuide);
        
        for(int i = 0; i < numInputs; i++) {
            VBox inputField = new VBox(5);
            inputField.getChildren().add(new Text(calcMap.get(calcType)[i]));
            inputField.getChildren().add(new TextField());
            this.inputHBox.getChildren().add(inputField);
        }
        this.inputHBox.setLayoutY(this.calcTitle.getLayoutY() + 10);

        this.calcResponse = new Text();
        this.calcResponse.setLayoutY((this.inputHBox.getLayoutY() + 10));
        this.calcResponse.setLayoutX(20);

        this.calcBox = new VBox(20);
        this.calcBox.setPadding(Page.boxPadding);
        this.calcBox.setLayoutY(this.calcMenu.getLayoutY() + 50);
        this.calcBox.setLayoutX(20);
        this.calcBox.getChildren().addAll(this.calcTitle, guideBox, this.inputHBox, this.calcResponse);

        this.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.ENTER) {
                    displayResponse();
                }
            }
        });

        this.pane.getChildren().add(this.calcBox);
    }

    private String calculate() {
        // Pull values from TextFields
        String[] inputs = new String[this.numInputs];
        for(int i = 0; i < inputs.length; i++) {
            VBox vb = (VBox) this.inputHBox.getChildren().get(i);
            TextField tf = (TextField) vb.getChildren().get(1);
            inputs[i] = tf.getCharacters().toString().trim();
            tf.clear();
        }

        // Uses headerList to assure same string
        // calcType will only ever be assigned a value from title[]
        if(this.calcType.equals(this.calcList[0])) {
            return IdealGasLaw.solve(inputs);
        } else if(this.calcType.equals(this.calcList[1]) || this.calcType.equals(this.calcList[5])) {
            return Cross.divide(inputs);
        } else {
            return Cross.multiply(inputs);
        }
    }

    private boolean isValidInput() {
        int numEmpty = 0;
        for(int i = 0; i < this.numInputs; i++) {
            VBox vb = (VBox) this.inputHBox.getChildren().get(i);
            TextField tf = (TextField) vb.getChildren().get(1);
            String fieldValue = tf.getCharacters().toString().trim();
            boolean isValid = true;
            for(char digit : fieldValue.toCharArray()) {
                if(!(digit >= '0' && digit <= '9' || digit == '.' || digit == 'E')) {
                    isValid = false;
                    break;
                }
            }
            if(!isValid || fieldValue.equals("")) {
                numEmpty++;
            } 
        }
        return numEmpty == 1;
    }

    private void displayResponse() {
        if(isValidInput()) {
            for(int i = 0; i < this.numInputs; i++) {
                VBox vb = (VBox) this.inputHBox.getChildren().get(i);
                Text header = (Text) vb.getChildren().get(0);
                TextField tf = (TextField) vb.getChildren().get(1);
                if(tf.getCharacters().toString().trim().equals("")) {
                    String headerText = header.getText();
                    int start = headerText.indexOf('(') + 1;
                    int end = headerText.indexOf(')');
                    String unit = headerText.substring(start, end);
                    // Will never get IndexOutOfBounds because headers are predefined
                    String answer = calculate();
                    this.calcResponse.setText(" = " + answer + unit);                    
                    break;
                } 
            }  
        } else {
            this.calcResponse.setText(" Invalid Entry");
        }
        
    }
    
    // Called once
    private void setCalcMenu() {
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent> () {
            public void handle(ActionEvent e) { 
                String selection = ((MenuItem) e.getSource()).getText();
                setCalc(selection);
            }
        };
        Menu gasLawCalcMenu = new Menu("Calculators");
        for(int i = 0; i < this.calcList.length; i++) {
            MenuItem mi = new MenuItem(this.calcList[i]);
            mi.setOnAction(event);
            gasLawCalcMenu.getItems().add(mi);
        }
        this.calcMenu = new MenuBar();
        this.calcMenu.setLayoutX(20);
        this.calcMenu.setLayoutY(100);

        this.calcMenu.getMenus().add(gasLawCalcMenu);
        this.pane.getChildren().add(calcMenu);
    }
}