package pages;

import elements.Element;
import elements.Filler;
import elements.Metal;
import elements.Metalloid;
import elements.Nonmetal;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

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
        // other 3 tables.
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
            if(type.equals(tableOptions[1])) {
                columnWidth = 75;
                tableHeight = 440;
            } else if(type.equals(tableOptions[2])) {
                columnWidth = 150;
                tableHeight = 525;
            } else if(type.equals(tableOptions[3])) {
                columnWidth = 200;
                tableHeight = 550;
            }
        }
        instructions.setText(guide);
        this.tableBox.getChildren().add(instructions);
        headers = csv.get(0);
        csv.remove(0);

        for(int i = 0; i < headers.length; i++) {
            TableColumn<String[], String> column = new TableColumn<String[], String>(headers[i]);
            // Source: https://stackoverflow.com/questions/20769723/populate-tableview-with-two-dimensional-array
            int colNo = i;
            // Research CallBack
            column.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            // Source: https://stackoverflow.com/questions/13455326/javafx-tableview-text-alignment\
            column.setStyle("-fx-alignment: CENTER;");
            column.setPrefWidth(columnWidth);
            column.setSortable(false);
            table.getColumns().add(column);
        }
        table.setItems(csv);
        table.setPrefHeight(tableHeight);
        table.setSelectionModel(null);
        this.tableBox.getChildren().add(table);
        this.pane.getChildren().addAll(this.tableBox);
    }

    private void createPeriodicTable() {
        this.periodicTableValues = new ArrayList<Element>();
        this.instructions = new Text();
        try {
            this.periodicTableValues = getPeriodicTableValues(tableOptions[0] + ".csv");
            this.instructions.setText(getText("Periodic Table"));
        } catch(IOException e) {
            System.out.println( tableOptions[0]+ ".csv could not be parsed.");
        }

        Pane periodicTable = new Pane();
        for(int i = 0; i < this.periodicTableValues.size(); i++) {
            Element currentElement = this.periodicTableValues.get(i);
            Button icon = currentElement.getIcon();
            periodicTable.getChildren().add(icon);
        }

        HBox elementRedirectBox = new HBox(10);
        Button seeDetails = new Button("See Details");
        seeDetails.setOnAction(event -> {
            createElementDisplay();            
        });
        Text errorMessage = new Text("");
        elementRedirectBox.getChildren().addAll(seeDetails, errorMessage);

        Button clearSelection = new Button("Clear Selection");
        clearSelection.setOnAction(event -> {
            clearSelection();
        });

        this.tableBox.getChildren().addAll(this.instructions, periodicTable, elementRedirectBox, clearSelection);

        this.pane.getChildren().addAll(this.tableBox);
    }

    private void createElementDisplay() {
        TableView<Element> elementTable = new TableView<Element>();
        ObservableList<Element> clickedElements = FXCollections.observableArrayList();
        for(int i = 0; i < this.periodicTableValues.size(); i++) {
            Element currentElement = this.periodicTableValues.get(i);
            if(currentElement.gotClicked()) {
                clickedElements.add(currentElement);
            }
        } 
        Text errorMessage = (Text) ((HBox) this.tableBox.getChildren().get(3)).getChildren().get(1);
        if(clickedElements.size() == 0) {
            errorMessage.setText("Please select at least 1 element.");
            return;
        } else {
            this.tableBox.getChildren().clear();
        }
        try { 
            this.elementTableHeaders = getText("Element Table Headers").split(",");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < this.elementTableProperties.length; i++) {
            String currentHeader = this.elementTableHeaders[i];
            String currentProperty = this.elementTableProperties[i];
            TableColumn<Element, String> column = new TableColumn<Element, String>(currentHeader);
            if(currentHeader.equals("Name")) {
                column.setPrefWidth(150);
            } else {
                column.setPrefWidth(30 + this.elementTableHeaders[i].length() * 10);
            }            
            column.setCellValueFactory(new PropertyValueFactory<Element, String>(currentProperty));
            column.setStyle("-fx-alignment: CENTER;");
            elementTable.getColumns().add(column);
        }
        elementTable.setItems(clickedElements);
        elementTable.setPrefWidth(1150);

        Button returnButton = new Button("Return to Periodic Table");
        returnButton.setOnAction(event -> {
            setTable(tableOptions[0]);
        });
        this.tableBox.getChildren().addAll(elementTable, returnButton);
    }

    private void clearSelection() {
        for(int i = 0; i < this.periodicTableValues.size(); i++) {
            this.periodicTableValues.get(i).resetClicked();
        }
    }

    private ArrayList<Element> getPeriodicTableValues(String fileName) throws IOException {
        String filePath = System.getProperty("user.dir") + "\\CSV\\" + fileName;
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        ArrayList<Element> periodicTableValues = new ArrayList<Element>();
        String line;
        String[] metalSet = {"alkali metal", "alkaline earth metal", "transition metal", "post-transition metal", "metal"};
        String[] nonmetalSet = {"nonmetal", "noble gas", "halogen"};
        String[] metalloidSet = {"metalloid"};
        int counter = 0;
        while((line = br.readLine()) != null) {
            String[] values = line.split(",");
            for(int i = 0; i < values.length; i++) {
                values[i] = values[i].trim();
                if(values[i].equals("")) {
                    values[i] = "No Data";
                }
            }
            if(counter == 0) {
                this.elementTableProperties = new String[values.length + 1];
                for(int i = 0; i < values.length; i++) {
                    this.elementTableProperties[i] = values[i];
                }
                this.elementTableProperties[values.length] = "ionCharge";
                Element.setPropertyList(this.elementTableProperties);
            } else {
                String type = values[16];
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
            counter++;
        }
        br.close();
        return periodicTableValues;
    }

    private boolean isPartOfSet(String key, String[] set) {
        for(int i = 0; i < set.length; i++) {
            if(key.equals(set[i])) {
                return true;
            }
        }
        return false;
    }

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
                    row[i] = parse(row[i]);
                }
            }
            fileValues.add(row);
        }
        br.close();
        return fileValues;
    }

    // https://stackoverflow.com/questions/11145681/how-to-convert-a-string-with-unicode-encoding-to-a-string-of-letters
    // Strings can only turn unicode into chars in initialization
    // If stored in a text file, they need to be parsed explicitly
    // code cannot be ""
    // https://stackoverflow.com/questions/11145681/how-to-convert-a-string-with-unicode-encoding-to-a-string-of-letters
    // https://en.wikipedia.org/wiki/Unicode_subscripts_and_superscripts
    // Learning about Unicode:
    // https://docs.oracle.com/javase/tutorial/i18n/text/unicode.html
    private String parse(String code) {
        String[] parts = code.split("/");
        String decoded = "";
        for(int i = 0; i < parts.length; i++) {
            if(parts[i].charAt(0) == 'u') {
                parts[i] = parts[i].substring(1);
                // Decodes String containing hexadecimal value to an int
                decoded += (char) Integer.parseInt(parts[i], 16);
            } else {
                decoded += parts[i];
            }
        }
        return decoded;
    }
}