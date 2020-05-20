package classes.pages.rows;

import javafx.beans.property.SimpleStringProperty;

public class ActivityRow {
    private SimpleStringProperty element;
    private SimpleStringProperty status;
    
    public ActivityRow(String element, String status) {
        this.element = new SimpleStringProperty(element);
        this.status = new SimpleStringProperty(status);
    }

    public String getElement() {
        return this.element.get();
    }

    public String getStatus() {
        return this.status.get();
    }
}