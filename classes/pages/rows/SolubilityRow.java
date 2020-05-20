package classes.pages.rows;

import javafx.beans.property.SimpleStringProperty;

public class SolubilityRow {
    // Reasoning for not creating an array of SimpleStrongProperties is that
    // Creating our own Callback to fill a table with array values requires an 
    // understanding of generics that we do not have.
    // https://stackoverflow.com/questions/52244810/how-to-fill-tableviews-column-with-a-values-from-an-array
    private String[] source;
    private SimpleStringProperty header;
    private SimpleStringProperty acetate;
    private SimpleStringProperty bromide;
    private SimpleStringProperty carbonate;
    private SimpleStringProperty chlorite;
    private SimpleStringProperty chloride;
    private SimpleStringProperty hydroxide;
    private SimpleStringProperty iodite;
    private SimpleStringProperty nitrate;
    private SimpleStringProperty oxide;
    private SimpleStringProperty perchlorate;
    private SimpleStringProperty phosphate;
    private SimpleStringProperty sulphate;
    private SimpleStringProperty sulphide;

    public SolubilityRow(String[] row) {
        this.source = row;
        this.header = new SimpleStringProperty(row[0]);
        this.acetate = new SimpleStringProperty(row[1]);
        this.bromide = new SimpleStringProperty(row[2]);
        this.carbonate = new SimpleStringProperty(row[3]);
        this.chlorite = new SimpleStringProperty(row[4]);
        this.chloride = new SimpleStringProperty(row[5]);
        this.hydroxide = new SimpleStringProperty(row[6]);
        this.iodite = new SimpleStringProperty(row[7]);
        this.nitrate = new SimpleStringProperty(row[8]);
        this.oxide = new SimpleStringProperty(row[9]);
        this.perchlorate = new SimpleStringProperty(row[10]);
        this.phosphate = new SimpleStringProperty(row[11]);
        this.sulphate = new SimpleStringProperty(row[12]);
        this.sulphide = new SimpleStringProperty(row[13]);
    }

    public int length() {
        return source.length;
    }

    // Accessor Methods for TableColumn PropertyValueFactory objects
    public String getHeader() {
        return this.header.get();
    }
    
    public String getAcetate() {
        return this.acetate.get();
    }
    
    public String getBromide() {
        return this.bromide.get();
    }
    
    public String getCarbonate() {
        return this.carbonate.get();
    }
    
    public String getChlorite() {
        return this.chlorite.get();
    }
    
    public String getChloride() {
        return this.chloride.get();
    }

    public String getHydroxide() {
        return this.hydroxide.get();
    }
    
    public String getIodite() {
        return this.iodite.get();
    }
    
    public String getNitrate() {
        return this.nitrate.get();
    }
    
    public String getOxide() {
        return this.oxide.get();
    }
    
    public String getPerchlorate() {
        return this.perchlorate.get();
    }
    
    public String getPhosphate() {
        return this.phosphate.get();
    }
   
    public String getSulphate() {
        return this.sulphate.get();
    }
    
    public String getSulphide() {
        return this.sulphide.get();
    }

}