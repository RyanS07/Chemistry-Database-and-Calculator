// Made by Ryan Seto and Matton Xia

import javafx.application.Application;
import javafx.stage.Stage;

import pages.Page;
import pages.HomePage;
import pages.TablePage;
import pages.CalculatorPage;

import java.util.HashMap;

public class Main extends Application{
    // HashMap containing all Page instances of the app and how to get to them
    public HashMap<String, Page> pageMap = new HashMap<String, Page>();
    // Array containing each page key for pageMap
    public String pageList[] = {"Home", "Table", "Calculator"};
 
    @Override
    public void start(Stage stage) {
        stage.setTitle("Balkulator");
        initializePages(stage);
        stage.setScene(this.pageMap.get("Home").getScene());
        stage.show();
    }

    // Initializes all Page instances and puts them in pageMap
    public void initializePages(Stage stage) {
        /* Page.setStage() and Page.setPageMap() must be called before any methods of each Page 
         * subclass is used. All methods rely on having static variables Page.stage and Page.pageMap.
         */
        Page.setStage(stage);
        Page.setPageMap(pageMap);
        // All Page instances are stored in a HashMap so that pages can be accessed through a 
        // String name. See setRedirects() in each Page subclass for an example.
        this.pageMap.put(pageList[0], new HomePage());
        this.pageMap.put(pageList[1], new TablePage());
        this.pageMap.put(pageList[2], new CalculatorPage());      
    }

    public static void main(String[] args) {
        launch(args);
    }
}