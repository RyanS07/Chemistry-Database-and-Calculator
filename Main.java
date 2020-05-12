import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import classes.pages.Page;
import classes.pages.HomePage;
import classes.pages.TablePage;
import classes.pages.InstructionsPage;
import classes.pages.ElementPage;
import classes.pages.CalculatorPage;

import java.util.HashMap;

public class Main extends Application{
    public String pageList[] = {"Home", "Instructions", "Tables", "ElementPage", "Calculators"};
    public HashMap<String, Page> pageMap = new HashMap<String, Page>();
    public String appTitle = "Balkulator";

    @Override
    public void start(Stage stage) {
        stage.setTitle(appTitle);
        setPages(stage);
        setPagePaths();
        stage.setScene(pageMap.get("Home").getScene());
        stage.show();
    }

    public void setPages(Stage stage) {
        Page.setStage(stage);
        Page.setPageMap(pageMap);
        pageMap.put("Home", new HomePage());
        pageMap.put("Instructions", new InstructionsPage());
        pageMap.put("Tables", new TablePage());
        pageMap.put("ElementPage", new ElementPage());
        pageMap.put("Calculators", new CalculatorPage());
    }

    public void setPagePaths() {
        for(int i = 0; i < pageList.length; i++) {
            pageMap.get(pageList[i]).setRedirects();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}