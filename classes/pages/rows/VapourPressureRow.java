package classes.pages.rows;

import javafx.beans.property.SimpleStringProperty;

public class VapourPressureRow {
    private SimpleStringProperty temperature;
    private SimpleStringProperty pressure;

    public VapourPressureRow(String temp, String pres) {
        this.temperature = new SimpleStringProperty(temp);
        this.pressure = new SimpleStringProperty(pres);
    }

    public String getTemperature() {
        return this.temperature.get();
    }

    public String getPressure() {
        return this.pressure.get();
    }
}