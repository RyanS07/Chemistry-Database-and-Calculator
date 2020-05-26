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

    // The MenuBar that displays the different calculator options.
    private MenuBar calcMenuBar;

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
        this.back = setBackButton("<-");
        this.pane.getChildren().add(this.back);

        setCalcMenu();
        // this.calcList[0], "Ideal Gas Law", is the default calculator. 
        this.calcType = this.calcList[0];
        setCalc(this.calcType);
        setRedirects();
    }

    /* This method establishes how each page redirects to each other. 
     * The Calculator Page is an "endpoint" page, so it only needs to 
     * redirect back to the Table Page (hence why there is only a back
     * button, and one redirect).
     */
    protected void setRedirects() {
        this.back.setOnAction(event -> {
            goTo("Table");
        });
    }

    /* Creates the MenuBar that allows the user to flip between the different
     * types of calculators in calcList. 
     */
    private void setCalcMenu() {
        /* When a MenuItem is selected, an ActionEvent is triggered. As such,
         * an EventHandler for said ActionEvent is required. 
         */
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent> () {
            public void handle(ActionEvent e) { 
                // The children of most JavaFX classes are stored as Node 
                // instances. As such, a downcast is required to cast from
                // Node to MenuItem. 
                String selection = ((MenuItem) e.getSource()).getText();
                // See below for an explanation
                setCalc(selection);
            }
        };
        /* When creating a MenuBar, there are three components: the MenuBar (1) 
         * with some number of Menu (2) instances each having some number of 
         * MenuItem (3) instances. 
         * 
         * The Menu and MenuItem instances of the calcMenu do not need to be 
         * accessed by any other method, so they are kept locally here.
         * calcMenu (MenuBar) needs to be accessed to position the calcBox 
         * relative to the MenuBar. 
         */
        Menu calcMenu = new Menu("Calculators");
        for(int i = 0; i < this.calcList.length; i++) {
            MenuItem mi = new MenuItem(this.calcList[i]);
            mi.setOnAction(event);
            // Adding each MenuItem to the Menu
            calcMenu.getItems().add(mi);
        }
        // Initializing and positioning calcMenuBar
        this.calcMenuBar = new MenuBar();
        this.calcMenuBar.setLayoutX(Page.margin);
        this.calcMenuBar.setLayoutY(100);
        this.calcMenuBar.getMenus().add(calcMenu);

        this.pane.getChildren().add(this.calcMenuBar);
    }

    /* CalculatorPage.java overrides getText() because there are 
     * some additional steps to parse the instruction text files 
     * for the calculator page. 
     * 
     * The instruction text files also store the headers for their
     * respective input fields. For CalculatorPage.java, they need
     * to be separated from super.getText().
     */
    @Override
    protected String getText(String type) throws IOException {
        String fullText = super.getText(type);
        String conjoinedHeader = "";
        int i = 0;
        // Retrieves the first line of the text file
        while(fullText.charAt(i) != '\n') {
            conjoinedHeader += fullText.charAt(i);
            i++;
        }
        // In the text file, the headers are comma separate.
        // All instructions text are below the headers. 
        this.calcMap.put(type, conjoinedHeader.split(","));
        return fullText.substring(i+1);
    }

    // Creates the calculator specified by type. 
    private void setCalc(String type) {
        /* Each time setCalc is called, the method creates a new VBox 
         * instance to display on this.pane. As such, the old VBox
         * needs to be removed from the pane. 
         */
        this.pane.getChildren().remove(this.calcBox);

        // Assigns type to this.calcType so that other methods know
        // Which calculator is current displayed. 
        this.calcType = type;
        // Create the calculator's title
        this.calcTitle = new Text(this.calcType);

        // inputHBox stores the 4 TextFields (and corresponding headers)
        // that the user inputs their values into. 
        this.inputHBox = new HBox(20);
        this.inputHBox.setPadding(Page.boxPadding);

        /* guideBox contains the two instruction/guide blocks of text
         * for each calculator: the calculator's instructions and an
         * explanation on significant figures.
         * 
         * Note: the constructor parameter is the spacing between each
         * children of the HBox (or VBox).
         */
        HBox guideBox = new HBox(30);
        guideBox.setPadding(boxPadding);
        Text instructions = new Text();
        Text sigFigGuide = new Text();
        try {
            // Note: Most methods of the entire project use Strings to
            // determine what methods to call. Methods are intentionally
            // after these Strings so that method calls are more intuitive. 
            instructions.setText(getText(this.calcType));
            sigFigGuide.setText(super.getText("Sig Fig"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        guideBox.getChildren().addAll(instructions, sigFigGuide);
        
        // Creating and assigning the input fields to inputHBox
        for(int i = 0; i < numInputs; i++) {
            VBox inputField = new VBox(5);
            // Each inputField has a header signifying what value to input,
            // and a TextField to input that value into. 
            // Note, since getText() is always called before this loop
            // calcMap will always have a value for calcType
            inputField.getChildren().add(new Text(calcMap.get(this.calcType)[i]));
            
            inputField.getChildren().add(new TextField());
            this.inputHBox.getChildren().add(inputField);
        }

        // calcResponse is where the answer from the calculator is displayed.
        // Two cases: the calculated answer or "Invalid Entry". See 
        // calculate() for an explanation on "Invalid Entry".
        this.calcResponse = new Text();

        // Creating the new calcBox
        this.calcBox = new VBox(20);
        this.calcBox.setPadding(Page.boxPadding);
        this.calcBox.setLayoutY(this.calcMenuBar.getLayoutY() + 50);
        this.calcBox.setLayoutX(Page.margin);
        this.calcBox.getChildren().addAll(this.calcTitle, guideBox, this.inputHBox, this.calcResponse);

        /* Creating an EventHandler for when the ENTER key is pressed.
         * When ENTER is pressed, the answer to the calculation is 
         * displayed.
         */
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
            // As mentioned above, children are stored as Node 
            // instances, so they need to be downcast. 
            VBox vb = (VBox) this.inputHBox.getChildren().get(i);
            TextField tf = (TextField) vb.getChildren().get(1);
            inputs[i] = tf.getCharacters().toString().trim();
            tf.clear();
        }

        /* Whenever a method takes in a parameter called type,
         * the value passed in is always from calcList (since 
         * the value is always from calcMenuBar). As such,
         * using calcList[n] ensures that no typos in the String
         * can break the code (ensures the Strings are from the
         * same source).
         * 
         * Note:
         * calcList[0] = "Ideal Gas Law"
         * calcList[1] = "Boyle's Law"
         * calcList[5] = "Concentration"
         */
        if(this.calcType.equals(this.calcList[0])) {
            return IdealGasLaw.solve(inputs);
        } else if(this.calcType.equals(this.calcList[1]) || this.calcType.equals(this.calcList[5])) {
            // See Cross.java for an explanation
            return Cross.divide(inputs);
        } else {
            // See Cross.java for an explanation
            return Cross.multiply(inputs);
        }
    }

    /* A valid input for the calculator is defined as:
     *  - Only one TextField is empty
     *  - All values are doubles (scientific notation is okay)
     */
    private boolean isValidInput() {
        int numValid = 0;
        // Loops through all inputBox instances in inputHBox
        for(int i = 0; i < this.numInputs; i++) {
            VBox vb = (VBox) this.inputHBox.getChildren().get(i);
            TextField tf = (TextField) vb.getChildren().get(1);
            String fieldValue = tf.getCharacters().toString().trim();
            boolean isValid = true;
            for(char digit : fieldValue.toCharArray()) {
                // If fieldValues is not a double or is not in sci not'n
                if(!(digit >= '0' && digit <= '9' || digit == '.' || digit == 'E')) {
                    isValid = false;
                    break;
                }
            }
            if(!isValid || fieldValue.equals("")) {
                numValid++;
            } 
        }
        return numValid == 1;
    }

    /* A separate method to display the calculator response needed
     * to be made because the EventHandler for the ENTER KeyEvent
     * "cannot resolve" the attributes of CalculatorPage.
     * 
     * Also helps shorten the already long setCalc() method. 
     */
    private void displayResponse() {
        if(isValidInput()) {
            String answer = calculate();
            // Loops through all the TextFields to find which
            // field is empty.
            for(int i = 0; i < this.numInputs; i++) {
                VBox vb = (VBox) this.inputHBox.getChildren().get(i);
                Text header = (Text) vb.getChildren().get(0);
                TextField tf = (TextField) vb.getChildren().get(1);
                if(tf.getCharacters().toString().trim().equals("")) {
                    // Once the empty field has been found, the unit of
                    // the answer is stored in brackets in the header.
                    String headerText = header.getText();
                    int start = headerText.indexOf('(') + 1;
                    int end = headerText.indexOf(')');
                    // Retrieves the unit
                    String unit = headerText.substring(start, end);
                    // Will never get IndexOutOfBounds because headers are predefined
                    this.calcResponse.setText(" = " + answer + unit);                    
                    break;
                } 
            }  
        } else {
            this.calcResponse.setText("Invalid Entry");
        }
    }
}