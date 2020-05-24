package pages;

import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;

import calculators.IdealGasLaw;
import calculators.Cross;


public class CalculatorPage extends Page {
    private Button back;
    private String[] gasLawTitles = {"Ideal Gas Law", "Boyle's Law", "Charles' Law", 
        "Gay-Lussac's Law", "Avogadro's Hypothesis"};
    private String[][] gasLawHeaders = { 
        { "Pressure (kPa)", "Volume (L)", "Moles (mol)", "Temperature (K)" },
        { "Pressure 1 (kPa)", "Volume 1 (L)", "Pressure 2 (kPa)", "Volume 2 (L)" }, 
        { "Volume 1 (L)", "Temperature 1 (K)", "Volume 2 (L)", "Temperature 2 (K)" },
        { "Pressure 1 (kPa)", "Temperature 1 (K)", "Pressure 2 (kPa)", "Temperature 2 (K)" }, 
        { "Volume 1 (L)", "Moles 1 (mol)", "Volume 2 (L)", "Moles 2 (mol)" } 
    };
    private HashMap<String, String[]> gasLawHeaderMap = new HashMap<String, String[]>();

    private MenuBar calcMenu;

    private Text calcTitle;
    private int numInputs = 4;
    private VBox gasLawCalcBox;
    private String calcType;
    private Text calcResponse;
    private HBox gasLawBox;

    public CalculatorPage() {
        this.pane = new Pane();
        this.scene = new Scene(this.pane, Page.width, Page.height);

        this.back = setBackButton();
        this.pane.getChildren().add(this.back);

        setGasLawHeaderMap();
        setCalcMenu();
        setGasLawCalc(gasLawTitles[0]);

        this.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.ENTER) {
                    displayResponse();
                }
            }
        });

        setRedirects();
    }

    // Called once
    protected void setRedirects() {
        this.back.setOnAction(event -> {
            goTo("Tables");
        });
    }

    // Called once
    private void setGasLawHeaderMap() {
        for (int i = 0; i < gasLawTitles.length; i++) {
            this.gasLawHeaderMap.put(this.gasLawTitles[i], this.gasLawHeaders[i]);
        }
    }

    private void setGasLawCalc(String type) {
        // Clears the old setup, if there is one
        this.pane.getChildren().remove(this.gasLawCalcBox);

        this.gasLawBox = new HBox(20);
        this.gasLawBox.setPadding(new Insets(20, 20, 20, 20));
        
        this.calcType = type;
        this.calcTitle = new Text(this.calcType);
        
        for(int i = 0; i < numInputs; i++) {
            VBox inputBox = new VBox(5);
            inputBox.getChildren().add(new Text(gasLawHeaderMap.get(calcType)[i]));
            inputBox.getChildren().add(new TextField());
            this.gasLawBox.getChildren().add(inputBox);
        }
        this.gasLawBox.setLayoutY(this.calcTitle.getLayoutY() + 10);

        this.calcResponse = new Text();
        this.calcResponse.setLayoutY((this.gasLawBox.getLayoutY() + 10));
        this.calcResponse.setLayoutX(20);

        this.gasLawCalcBox = new VBox(20);
        this.gasLawCalcBox.setPadding(new Insets(20,20,20,20));
        this.gasLawCalcBox.setLayoutY(this.calcMenu.getLayoutY() + 100);
        this.gasLawCalcBox.setLayoutX(20);
        this.gasLawCalcBox.getChildren().addAll(this.calcTitle, this.gasLawBox, this.calcResponse);

        this.pane.getChildren().add(this.gasLawCalcBox);
    }

    private String calculate() {
        // Pull values from TextFields
        String[] inputs = new String[this.numInputs];
        for(int i = 0; i < inputs.length; i++) {
            VBox vb = (VBox) this.gasLawBox.getChildren().get(i);
            TextField tf = (TextField) vb.getChildren().get(1);
            inputs[i] = tf.getCharacters().toString().trim();
            tf.clear();
        }

        // Uses headerList to assure same string
        // calcType will only ever be assigned a value from title[]
        if(this.calcType.equals(gasLawTitles[0])) {
            return IdealGasLaw.solve(inputs);
        } else if(this.calcType.equals(gasLawTitles[1])) {
            return Cross.divide(inputs);
        } else {
            return Cross.multiply(inputs);
        }
    }

    private boolean gasLawValidInput() {
        int numEmpty = 0;
        for(int i = 0; i < this.numInputs; i++) {
            VBox vb = (VBox) this.gasLawBox.getChildren().get(i);
            TextField tf = (TextField) vb.getChildren().get(1);
            String fieldValue = tf.getCharacters().toString().trim();
            boolean isValid = true;
            for(char digit : fieldValue.toCharArray()) {
                if(digit < '0' || digit > '9') {
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
        if(gasLawValidInput()) {
            for(int i = 0; i < this.numInputs; i++) {
                VBox vb = (VBox) this.gasLawBox.getChildren().get(i);
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
                setGasLawCalc(selection);
            }
        };
        Menu gasLawCalcMenu = new Menu("Gas Law");
        for(int i = 0; i < gasLawTitles.length; i++) {
            MenuItem mi = new MenuItem(gasLawTitles[i]);
            mi.setOnAction(event);
            gasLawCalcMenu.getItems().add(mi);
        }
        this.calcMenu = new MenuBar();
        this.calcMenu.setLayoutX(20);
        this.calcMenu.setLayoutY(100);

        // Add Stoic Calc Menu Here
        // Add Concentration Menu
        this.calcMenu.getMenus().add(gasLawCalcMenu);
        this.pane.getChildren().add(calcMenu);
    }
}