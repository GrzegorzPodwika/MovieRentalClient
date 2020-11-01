package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FeeData {
    private final SimpleIntegerProperty tableRentId;
    private final SimpleIntegerProperty tableMovieId;
    private final SimpleStringProperty tableMovieName;
    private final SimpleStringProperty tableRentDate;
    private final SimpleStringProperty tableReturnDate;
    private final SimpleDoubleProperty tableFee;

    public FeeData(Integer tableRentId, Integer tableMovieId, String tableMovieName, String tableRentDate, String tableReturnDate, Double tableFee) {
        this.tableRentId = new SimpleIntegerProperty(tableRentId);
        this.tableMovieId = new SimpleIntegerProperty(tableMovieId);
        this.tableMovieName = new SimpleStringProperty(tableMovieName);
        this.tableRentDate = new SimpleStringProperty(tableRentDate);
        this.tableReturnDate = new SimpleStringProperty(tableReturnDate);
        this.tableFee = new SimpleDoubleProperty(tableFee);
    }

    public int getTableRentId() {
        return tableRentId.get();
    }

    public SimpleIntegerProperty tableRentIdProperty() {
        return tableRentId;
    }

    public void setTableRentId(int tableRentId) {
        this.tableRentId.set(tableRentId);
    }

    public int getTableMovieId() {
        return tableMovieId.get();
    }

    public SimpleIntegerProperty tableMovieIdProperty() {
        return tableMovieId;
    }

    public void setTableMovieId(int tableMovieId) {
        this.tableMovieId.set(tableMovieId);
    }

    public String getTableMovieName() {
        return tableMovieName.get();
    }

    public SimpleStringProperty tableMovieNameProperty() {
        return tableMovieName;
    }

    public void setTableMovieName(String tableMovieName) {
        this.tableMovieName.set(tableMovieName);
    }

    public String getTableRentDate() {
        return tableRentDate.get();
    }

    public SimpleStringProperty tableRentDateProperty() {
        return tableRentDate;
    }

    public void setTableRentDate(String tableRentDate) {
        this.tableRentDate.set(tableRentDate);
    }

    public String getTableReturnDate() {
        return tableReturnDate.get();
    }

    public SimpleStringProperty tableReturnDateProperty() {
        return tableReturnDate;
    }

    public void setTableReturnDate(String tableReturnDate) {
        this.tableReturnDate.set(tableReturnDate);
    }

    public Double getTableFee() {
        return tableFee.get();
    }

    public SimpleDoubleProperty tableFeeProperty() {
        return tableFee;
    }

    public void setTableFee(Double tableFee) {
        this.tableFee.set(tableFee);
    }
}
