package classes.pages;

import classes.pages.rows.SolubilityRow;
import classes.pages.rows.ActivityRow;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TablePage extends Page {
    private Button back;
    private Button toCalculators;
    private static String[] solubilityProperties = { "Header", "Acetate", "Bromide", "Carbonate", "Chlorite",
            "Chloride", "Hydroxide", "Iodite", "Nitrate", "Oxide", "Perchlorate", "Phosphate", "Sulphate", "Sulphide" };
    // https://stackoverflow.com/questions/11145681/how-to-convert-a-string-with-unicode-encoding-to-a-string-of-letters
    // https://en.wikipedia.org/wiki/Unicode_subscripts_and_superscripts
    // Learning about Unicode:
    // https://docs.oracle.com/javase/tutorial/i18n/text/unicode.html
    private static String[] solubilityHeaders = { "", "C\u2082H\u2083O\u2082\u207B", "Br\u00B2\u207B",
            "CO\u2083\u00B2\u207B", "ClO\u2082\u207B", "Cl\u207B", "OH\u207B", "I\u207B", "NO\u2083\u207B",
            "O\u00B2\u207B", "ClO\u2084\u207B", "PO\u2084\u00B3\u207B", "SO\u2084\u00B2\u207B",
            "SO\u2083\u00B2\u207B" };
    private TableView<SolubilityRow> solubilityTable;
    private String solubilityFileName = "SolubilityTable.csv";
    private TableView<ActivityRow> activitySeries;
    private String activityFileName = "ActivitySeries.csv";

    private MenuBar tableMenu;
    private Text tableTitle;
    private VBox tableBox;
    private String tableType;
    private String[] tableOptions = {"Periodic Table", "Solubility Table", "Activity Series"};

    public TablePage() {
        this.pane = new Pane();
        this.scene = new Scene(this.pane, Page.width, Page.height);

        this.back = setBackButton();
        this.pane.getChildren().add(this.back);

        this.toCalculators = setButton("Calculators");
        this.toCalculators.setLayoutX(Page.width - this.toCalculators.getPrefWidth() - 10);
        this.toCalculators.setLayoutY(Page.height - this.toCalculators.getPrefHeight() - 10);
        this.pane.getChildren().add(this.toCalculators);

        setTableMenu();
        setTable("Periodic Table");
        

        setRedirects();
    }

    protected void setRedirects() {
        this.back.setOnAction(event -> {
            goTo("Home");
        });
        this.toCalculators.setOnAction(event -> {
            goTo("Calculators");
        });
    }

    private void setTableMenu() {
        Menu tableMenuOptions = new Menu("Options");
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent> () {
            public void handle(ActionEvent e) { 
                String selection = ((MenuItem) e.getSource()).getText();
                setTable(selection);
            }
        };
        for(int i = 0; i < tableOptions.length; i++) {
            MenuItem mi = new MenuItem(tableOptions[i]);
            mi.setOnAction(event);
            tableMenuOptions.getItems().add(mi);
        }
        this.tableMenu = new MenuBar();
        this.tableMenu.setLayoutX(20);
        this.tableMenu.setLayoutY(100);

        // Add Stoic Calc Menu Here
        this.tableMenu.getMenus().add(tableMenuOptions);
        this.pane.getChildren().add(tableMenu);
    }

    private void setTable(String type) {
        this.pane.getChildren().remove(this.tableBox);

        this.tableType = type;
        this.tableTitle = new Text(this.tableType);
        this.tableBox = new VBox(20);
        this.tableBox.setPadding(new Insets(20, 20, 20, 20));
        this.tableBox.setLayoutY(this.tableMenu.getLayoutY() + 100);
        this.tableBox.setLayoutX(20);
        this.tableBox.getChildren().add(this.tableTitle);

        if(this.tableType.equals("Periodic Table")) {
            setPeriodicTable();
        } else if(this.tableType.equals("Solubility Table")) {
            setSolubilityTable();
        } else if(this.tableType.equals("Activity Series")) {
            setActivitySeries();
        }

        this.pane.getChildren().addAll(this.tableBox);
    }

    private void setPeriodicTable() {

    }

    private void setSolubilityTable() {
        // Initialized to emptp list to avoid "Not initialized" warning
        ObservableList<SolubilityRow> solubilityValues = FXCollections.observableArrayList();
        try {
            solubilityValues = getSolubilityValues();
        } catch (IOException e) {
            System.out.println("Solubilty.csv not found");
        }
        this.solubilityTable = new TableView<SolubilityRow>();
        for(int i = 0; i < solubilityValues.get(0).length(); i++) {
            TableColumn<SolubilityRow, String> column;
            // Assigned on separate line to improve readability
            column = new TableColumn<SolubilityRow, String>(solubilityHeaders[i]);
            // Source: https://stackoverflow.com/questions/13455326/javafx-tableview-text-alignment
            column.setStyle("-fx-alignment: CENTER;");
            column.setPrefWidth(75);
            column.setCellValueFactory(new PropertyValueFactory<SolubilityRow, String>(solubilityProperties[i]));
            column.setSortable(false);
            this.solubilityTable.getColumns().add(column);
        }
        this.solubilityTable.setItems(solubilityValues);
        this.solubilityTable.setPrefHeight(440);
        this.solubilityTable.setSelectionModel(null);

        this.tableBox.getChildren().add(this.solubilityTable);
    }

    private ObservableList<SolubilityRow> getSolubilityValues() throws IOException {
        String filePath = System.getProperty("user.dir") + "\\classes\\pages\\CSV\\" + this.solubilityFileName;
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        ObservableList<SolubilityRow> fileValues = FXCollections.observableArrayList();
		String line;
		while ((line = br.readLine()) != null) {
            String[] row = line.split(",");
            for(int i = 0; i < row.length; i++) {
                row[i] = parseSubstance(row[i]);
            }
			fileValues.add(new SolubilityRow(row));
        }
        br.close();
        return fileValues;
    }
    
    // https://stackoverflow.com/questions/11145681/how-to-convert-a-string-with-unicode-encoding-to-a-string-of-letters
    // Strings can only turn unicode into chars in initialization
    // If stored in a text file, they need to be parsed explicitly
    private String parseSubstance(String code) {
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

    private void setActivitySeries() {
        ObservableList<ActivityRow> activityValues = FXCollections.observableArrayList();
        try {
            activityValues = getActivityValues();
        } catch (IOException e) {
            System.out.println("ActivitySeries.csv not found");
        }

        this.activitySeries = new TableView<ActivityRow>();

        TableColumn<ActivityRow, String> elementColumn = new TableColumn<ActivityRow, String>("Element");
        elementColumn.setStyle("-fx-alignment: CENTER;");
        elementColumn.setPrefWidth(150);
        elementColumn.setCellValueFactory(new PropertyValueFactory<ActivityRow, String>("element"));
        elementColumn.setSortable(false);
        this.activitySeries.getColumns().add(elementColumn);

        TableColumn<ActivityRow, String> statusColumn = new TableColumn<ActivityRow, String>("Displaces H\u207A in");
        statusColumn.setStyle("-fx-alignment: CENTER;");
        statusColumn.setPrefWidth(250);
        statusColumn.setCellValueFactory(new PropertyValueFactory<ActivityRow, String>("status"));
        statusColumn.setSortable(false);
        this.activitySeries.getColumns().add(statusColumn);

        this.activitySeries.setItems(activityValues);
        this.activitySeries.setPrefHeight(525);
        this.activitySeries.setSelectionModel(null);

        this.tableBox.getChildren().add(this.activitySeries);
    }

    private ObservableList<ActivityRow> getActivityValues() throws IOException {
        String filePath = System.getProperty("user.dir") + "\\classes\\pages\\CSV\\" + this.activityFileName;
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        ObservableList<ActivityRow> fileValues = FXCollections.observableArrayList();
		String line;
		while ((line = br.readLine()) != null) {
            String[] element = line.split(",");
            for(int i = 0; i < element.length; i++) {
                element[i] = parseSubstance(element[i]);
            }
            fileValues.add(new ActivityRow(element[0], element[1]));
        }
        br.close();
        return fileValues;
    }
}