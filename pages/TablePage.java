package pages;

import elements.Element;
import elements.Filler;
import elements.Metal;
import elements.Metalloid;
import elements.Nonmetal;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class TablePage extends Page {
    // Two Page redirect buttons 
    private Button home;
    private Button toCalculators;

    // Stores the periodic table in a list of Element subclass instances.
    private ArrayList<Element> periodicTableValues;
    // Contains all names of properties in the Element class. Used to 
    // PropertyValueFactory instances for a TableView<Element>. 
    private String[] elementTableProperties;
    // Contains the headers of the TableView<Element> table.
    private String[] elementTableHeaders;

    // The MenuBar that displays the different table options.
    private MenuBar tableMenu;
    private Text tableTitle;
    private Text instructions;
    private VBox tableBox;
    private String tableType;
    private String[] tableOptions = {
        "Periodic Table", "Solubility Table", 
        "Activity Series", "Vapour Pressure Table"
    };

    /* The constructor is the only public method of any Page subclass. This lowers
     * the number of method calls in the client program, improving readability and
     * centralizing all of the code pertaining to a specific Page subclass to that 
     * file. 
     */
    public TablePage() {
        // All Page subclasses build on Page, so they call super() and add some
        // additional behavior unique to each subclass. 
        super();

        // Creating the back button to return to the previous page.
        // Note, this only initializes the button. It does not tell the
        // button where to redirect to when clicked (see setRedirects()). 
        this.home = setBackButton("Home");
        this.pane.getChildren().add(this.home);

        // Creating the button that redirects to the calculator page.
        // Note, this only initializes the button. It does not tell the
        // button where to redirect to when clicked (see setRedirects()). 
        this.toCalculators = setButton("Calculators");
        this.toCalculators.setLayoutX(Page.width - this.toCalculators.getPrefWidth() - 10);
        this.toCalculators.setLayoutY(10);
        this.pane.getChildren().add(this.toCalculators);

        // Initializes the tableMenu MenuBar. 
        setTableMenu();
        // Creates/fills the tableBox VBox with whichever table specified by the parameter.
        // this.tableOptions[0] ("Periodic Table") is the default value. 
        setTable(this.tableOptions[0]);
        
        setRedirects();
    }

    /* This method establishes how each page redirects to each other. 
     * The Table Page is a "middle point" page, so it redirects back
     * to the Home Page, and up towards the Calculator Page. 
     */
    protected void setRedirects() {
        this.home.setOnAction(event -> {
            goTo("Home");
        });
        this.toCalculators.setOnAction(event -> {
            goTo("Calculator");
        });
    }

    /* Creates the MenuBar that allows the user to flip between the different
     * types of tables in tableOptions. 
     */
    private void setTableMenu() {
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
                setTable(selection);
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
        Menu tableMenuOptions = new Menu("Options");
        for(int i = 0; i < tableOptions.length; i++) {
            MenuItem mi = new MenuItem(tableOptions[i]);
            mi.setOnAction(event);
            // Adding each MenuItem to the Menu
            tableMenuOptions.getItems().add(mi);
        }
        // Initializing and positioning calcMenuBar
        this.tableMenu = new MenuBar();
        this.tableMenu.setLayoutX(Page.margin);
        this.tableMenu.setLayoutY(100);
        this.tableMenu.getMenus().add(tableMenuOptions);

        this.pane.getChildren().add(tableMenu);
    }

    // Creates the table specified by type. 
    private void setTable(String type) {
        /* Each time setTable is called, the method creates a new VBox 
         * instance to display on this.pane. As such, the old VBox
         * needs to be removed from the pane. 
         */
        this.pane.getChildren().remove(this.tableBox);

        // Assigns type to this.calcType so that other methods know
        // Which calculator is current displayed. 
        this.tableType = type;
        this.tableTitle = new Text(this.tableType);

        // Creating the new tableBox (mentioned above).
        this.tableBox = new VBox(20);
        this.tableBox.setPadding(Page.boxPadding);
        this.tableBox.setLayoutX(Page.margin);
        this.tableBox.setLayoutY(this.tableMenu.getLayoutY() + 50);
        this.tableBox.getChildren().add(this.tableTitle);

        /* For 3/4 tables (all but the Periodic Table), they share the 
         * exact same structure (TableView<String[]> with values from
         * a csv). As such, all the common variables of the 3 tables
         * are declared, then assigned proper values based on which 
         * option is desired. 
         */
        ObservableList<String[]> csv = FXCollections.observableArrayList();
        TableView<String[]> table = new TableView<String[]>();
        String[] headers;
        int columnWidth = 0;
        int tableHeight = 0;
        String guide = "";
        this.instructions = new Text();
        
        // As mentioned above, the Periodic Table is unique from the 
        // other 3 tables. The remaining 3 tables are part of the else. 
        if(type.equals(this.tableOptions[0])) {
            createPeriodicTable();
            return;
        } else {
            try {
                csv = readCSV(type);
                guide = getText(type);
            } catch(IOException e) {
                e.printStackTrace();
            }
            // The remaining 3 tables have the exact same algorithm, with
            // different values assigned below. 
            if(type.equals(this.tableOptions[1])) {
                columnWidth = 75;
                tableHeight = 440;
            } else if(type.equals(this.tableOptions[2])) {
                columnWidth = 150;
                tableHeight = 525;
            } else if(type.equals(this.tableOptions[3])) {
                columnWidth = 200;
                tableHeight = 550;
            }
        }
        this.instructions.setText(guide);
        this.tableBox.getChildren().add(this.instructions);
        // All table text files (besides the Periodic Table) store their table
        // headers in the first line. 
        headers = csv.get(0);
        csv.remove(0);

         // The block below creates each TableColumn for the respective TableView. 
        for(int i = 0; i < headers.length; i++) {
            TableColumn<String[], String> column = new TableColumn<String[], String>(headers[i]);
            /* A CallBack is a block of executable code that is passed in as an argument to another block. The 
             * other block is then expected to call back/execute the callback function at a given time. 
             * 
             * In this case, the CallBack is executed when the cellValueFactory of the TableColumn instance is
             * set. The cellValueFactory then takes this CallBack and puts a returned ObservableValue<String> 
             * in each cell of the TableColumn, allowing the table to update whenever each ObservableValue is 
             * changed. 
             * 
             * A CellDataFeatures instance is a wrapper class representing all features about a given cell. This 
             * CallBack requires a CellDataFeatures instance to return the ObservableValue. The rest is handled
             * in the background by the TableView and TableColumn instances. 
             * 
             * Source: https://stackoverflow.com/questions/20769723/populate-tableview-with-two-dimensional-array
             */
            int colNo = i;
            column.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            // Sets the CSS style of the column so the text is centered. 
            column.setStyle("-fx-alignment: CENTER;");
            column.setPrefWidth(columnWidth);
            // Stops the user from sorting the table (Table is pre-organized).
            column.setSortable(false);
            table.getColumns().add(column);
        }
        table.setItems(csv);
        table.setPrefHeight(tableHeight);
        // Makes it so the user cannot select a specified cell (the user has no need). 
        table.setSelectionModel(null);
        this.tableBox.getChildren().add(table);
        this.pane.getChildren().addAll(this.tableBox);
    }

    // Creates the periodic table to display. 
    private void createPeriodicTable() {
        this.periodicTableValues = new ArrayList<Element>();
        this.instructions = new Text();
        try {
            // See below for an explanation on getPeriodTableValues()
            this.periodicTableValues = getPeriodicTableValues(this.tableOptions[0]);
            this.instructions.setText(getText("Periodic Table"));
        } catch(IOException e) {
            System.out.println(this.tableOptions[0]+ ".csv could not be parsed.");
        }

        /* Encapsulates the each Element in this.periodicTableValues into one 
         * display Node.
         */
        Pane periodicTable = new Pane();
        for(int i = 0; i < this.periodicTableValues.size(); i++) {
            Element currentElement = this.periodicTableValues.get(i);
            // Icon positioning is done in the Element class. 
            Button icon = currentElement.getIcon();
            periodicTable.getChildren().add(icon);
        }

        // elementRedirectBox stores the the button to redirect to the Element
        // Display table and the errorMessage if the user has an invalid input. 
        HBox elementRedirectBox = new HBox(10);
        Button seeDetails = new Button("See Details");
        seeDetails.setOnAction(event -> {
            // Input validation is handled in this method
            createElementDisplay();            
        });
        Text errorMessage = new Text("");
        elementRedirectBox.getChildren().addAll(seeDetails, errorMessage);

        // clearSelection deselects all icons the user pressed on the periodic
        // table.
        Button clearSelection = new Button("Clear Selection");
        clearSelection.setOnAction(event -> {
            clearSelection();
        });

        this.tableBox.getChildren().addAll(this.instructions, periodicTable, elementRedirectBox, clearSelection);
        this.pane.getChildren().addAll(this.tableBox);
    }

    /* Creates a TableView to display all the information about the selected
     * elements. 
     */
    private void createElementDisplay() {
        TableView<Element> elementTable = new TableView<Element>();
        ObservableList<Element> clickedElements = FXCollections.observableArrayList();
        // Creates an ObservableList to store all the selected elements. 
        for(int i = 0; i < this.periodicTableValues.size(); i++) {
            Element currentElement = this.periodicTableValues.get(i);
            if(currentElement.gotClicked()) {
                clickedElements.add(currentElement);
            }
        } 
        // Handling input validation
        Text errorMessage = (Text) ((HBox) this.tableBox.getChildren().get(3)).getChildren().get(1);
        // If no elements were clicked, prompt the user to select one. 
        if(clickedElements.size() == 0) {
            errorMessage.setText("Please select at least 1 element.");
            return;
        } else {
            this.tableBox.getChildren().clear();
        }
 
        /* Obtains the Table headers for the Element display
         * The file stores the values (comma separated) on one line.
         * The reason the file is a .txt file (and not a .csv) is because 
         * getText() only looks through .txt files. Overloading getText() 
         * just to read one file did not seem necessary. 
         */
        try { 
            this.elementTableHeaders = getText("Element Table Headers").split(",");
        } catch (IOException e) {
            e.printStackTrace();
        }


        for(int i = 0; i < this.elementTableProperties.length; i++) {
            String currentHeader = this.elementTableHeaders[i];
            String currentProperty = this.elementTableProperties[i];
            TableColumn<Element, String> column = new TableColumn<Element, String>(currentHeader);
            /* The if clause below tries to make column size dynamic, based on which property it
             * has to display. 
             * 
             * The formula for size is (30px + (number of characters in the header) * 10px)
             */
            if(currentHeader.equals("Name")) {
                // Name does not use the formula because some elemnt names are really long
                // Ex: Einsteinium
                column.setPrefWidth(150);
            } else {
                column.setPrefWidth(30 + this.elementTableHeaders[i].length() * 10);
            }            
            column.setCellValueFactory(new PropertyValueFactory<Element, String>(currentProperty));
            column.setStyle("-fx-alignment: CENTER;");
            elementTable.getColumns().add(column);
        }
        elementTable.setItems(clickedElements);
        elementTable.setPrefWidth(Page.width - 100);

        Button returnButton = new Button("Return to Periodic Table");
        returnButton.setOnAction(event -> {
            // Returns back to the periodic table.
            setTable(this.tableOptions[0]);
        });
        this.tableBox.getChildren().addAll(elementTable, returnButton);
    }

    // Sets all element's to "not clicked".
    private void clearSelection() {
        for(int i = 0; i < this.periodicTableValues.size(); i++) {
            this.periodicTableValues.get(i).resetClicked();
        }
    }

    /* Reads the csv containing the periodic table values and parses 
     * it into an ArrayList<Element>. 
     */
    private ArrayList<Element> getPeriodicTableValues(String fileName) throws IOException {
        String filePath = System.getProperty("user.dir") + "\\CSV\\" + fileName + ".csv";
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        ArrayList<Element> periodicTableValues = new ArrayList<Element>();
        // The sets below contain the key words (pulled from the csv directly) 
        // that state what kind of element each line of the csv is. 
        String[] metalSet = {"alkali metal", "alkaline earth metal", "transition metal", "post-transition metal", "metal"};
        String[] nonmetalSet = {"nonmetal", "noble gas", "halogen"};
        String[] metalloidSet = {"metalloid"};

        String line = br.readLine();
        String[] values = line.split(",");
        /* The name of each element property is stored in the first line
         * of the csv. 
         * Creates an array to store the properties, with the additional
         * "ionCharge" property. 
         */
        this.elementTableProperties = new String[values.length + 1];
        for(int i = 0; i < values.length; i++) {
            this.elementTableProperties[i] = values[i].trim();
        }
        this.elementTableProperties[values.length] = "ionCharge";
        // Assigns it to the Element static variable propertyList
        // so that the constructor can properly fill out its Fields. 
        Element.setPropertyList(this.elementTableProperties);
        
        // Loop reads the file until it reaches the end
        while((line = br.readLine()) != null) {
            values = line.split(",");
            for(int i = 0; i < values.length; i++) {
                values[i] = values[i].trim();
                // A large portion of values are blank. This is presumable because no
                // research has been done on them (Ex: radioactive elements). As such,
                // if a value is blank, change it to "No Data".
                if(values[i].equals("")) {
                    values[i] = "No Data";
                }
            }
            // Index 16 pulled from manually reading the csv
            String type = values[16];
            // Creates a Metal, Nonmetal, Metalloid, or Filler instance 
            // based on what type the element is. 
            if(isPartOfSet(type, metalSet)) {
                periodicTableValues.add(new Metal(values));
            } else if(isPartOfSet(type, nonmetalSet)) {
                periodicTableValues.add(new Nonmetal(values));
            } else if(isPartOfSet(type, metalloidSet)) {
                periodicTableValues.add(new Metalloid(values));
            } else {
                periodicTableValues.add(new Filler(values));
            }
        }
        br.close();
        return periodicTableValues;
    }

    // Determines if key is part of set
    private boolean isPartOfSet(String key, String[] set) {
        for(String datum : set) {
            if(key.equals(datum)) {
                return true;
            }
        }
        return false;
    }

    // Reads a csv and parses it into an ObservableList to use in a TableView
    private ObservableList<String[]> readCSV(String fileName) throws IOException {
        String filePath = System.getProperty("user.dir") + "\\CSV\\" + fileName + ".csv";
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        ObservableList<String[]> fileValues = FXCollections.observableArrayList();
		String line;
		while ((line = br.readLine()) != null) {
            String[] row = line.split(",");
            for(int i = 0; i < row.length; i++) {
                if(!row[i].equals("")) {
                    // See below for an explanation on parse()
                    row[i] = parse(row[i]);
                }
            }
            fileValues.add(row);
        }
        br.close();
        return fileValues;
    }

    /* parse() converts any unicode characters into a String representation. 
     * Sources/Research:
     *  - https://docs.oracle.com/javase/tutorial/i18n/text/unicode.html
     *  - https://stackoverflow.com/questions/11145681/how-to-convert-a-string-with-unicode-encoding-to-a-string-of-letters
     *  - https://en.wikipedia.org/wiki/Unicode_subscripts_and_superscripts
     */
    private String parse(String code) {
        String[] parts = code.split("/");
        String decoded = "";
        for(String part : parts) {
            // See Solubility Table.csv for an example of what code would look like
            if(part.charAt(0) == 'u') {
                // Unicode is represented in hex (less digits but more combinations 
                // than decimal). Converst from hexideciaml to decimal, then casts 
                // to a char.
                decoded += (char) Integer.parseInt(part.substring(1), 16);
            } else {
                decoded += part;
            }
        }
        return decoded;
    }
}