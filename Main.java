import javafx.application.Application;
import javafx.stage.Stage;

import classes.pages.Page;
import classes.pages.HomePage;
import classes.pages.TablePage;
import classes.pages.InstructionsPage;
import classes.pages.ElementPage;
import classes.pages.CalculatorPage;

import java.util.Arrays;
import java.util.HashMap;

public class Main extends Application{
    // HashMap containing all pages of the app and how to get to them
    public HashMap<String, Page> pageMap = new HashMap<String, Page>();
    // Array containing each page key for pageMap
    public String pageList[] = {"Home", "Instructions", "Tables", "ElementPage", "Calculators"};
    public String appTitle = "Balkulator";

    @Override
    public void start(Stage stage) {
        stage.setTitle(appTitle);
        initializePages(stage);
        stage.setScene(pageMap.get("Home").getScene());
        stage.show();
        
    }

    /* Description:
     *  - Initializes all page instances and puts them in pageMap
     *  
     * Precondition:
     *  - Page.setStage() and Page.setPageMap() must be called before any methods of each Page
     *    subclass is used
     *  - All methods rely on having static variables Page.stage and Page.pageMap
     */
    public void initializePages(Stage stage) {
        Page.setStage(stage);
        Page.setPageMap(pageMap);
        pageMap.put(pageList[0], new HomePage());
        pageMap.put(pageList[1], new InstructionsPage());
        pageMap.put(pageList[2], new TablePage());
        pageMap.put(pageList[3], new ElementPage());
        pageMap.put(pageList[4], new CalculatorPage());
    }

    public static void main(String[] args) {
        launch(args);
    }
}