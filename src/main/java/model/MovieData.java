package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MovieData {
    private final SimpleIntegerProperty tableId;
    private final SimpleStringProperty tableName;
    private final SimpleStringProperty tableGenre;
    private final SimpleStringProperty tableYear;
    private final SimpleDoubleProperty tableRating;
    private final SimpleDoubleProperty tableFee;

    public MovieData(Integer tableId, String tableName, String tableGenre, String tableYear, Double tableRating, Double tableFee) {
        this.tableId = new SimpleIntegerProperty(tableId);
        this.tableName = new SimpleStringProperty(tableName);
        this.tableGenre = new SimpleStringProperty(tableGenre);
        this.tableYear = new SimpleStringProperty(tableYear);
        this.tableRating = new SimpleDoubleProperty(tableRating) ;
        this.tableFee = new SimpleDoubleProperty(tableFee);
    }

    public int getTableId() {
        return tableId.get();
    }

    public SimpleIntegerProperty tableIdProperty() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId.set(tableId);
    }

    public String getTableName() {
        return tableName.get();
    }

    public SimpleStringProperty tableNameProperty() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName.set(tableName);
    }

    public String getTableGenre() {
        return tableGenre.get();
    }

    public SimpleStringProperty tableGenreProperty() {
        return tableGenre;
    }

    public void setTableGenre(String tableGenre) {
        this.tableGenre.set(tableGenre);
    }

    public String getTableYear() {
        return tableYear.get();
    }

    public SimpleStringProperty tableYearProperty() {
        return tableYear;
    }

    public void setTableYear(String tableYear) {
        this.tableYear.set(tableYear);
    }

    public double getTableRating() {
        return tableRating.get();
    }

    public SimpleDoubleProperty tableRatingProperty() {
        return tableRating;
    }

    public void setTableRating(double tableRating) {
        this.tableRating.set(tableRating);
    }

    public double getTableFee() {
        return tableFee.get();
    }

    public SimpleDoubleProperty tableFeeProperty() {
        return tableFee;
    }

    public void setTableFee(double tableFee) {
        this.tableFee.set(tableFee);
    }

    @Override
    public String toString() {
        return "MovieData{" +
                "tableId=" + tableId.get() +
                ", tableName=" + tableName.get() +
                ", tableGenre=" + tableGenre.get() +
                ", tableYear=" + tableYear.get() +
                ", tableRating=" + tableRating.get() +
                ", tableFee=" + tableFee.get() +
                '}';
    }
}
