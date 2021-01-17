package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import data_holders.DateHolder;
import model.Movie;
import data_holders.MovieHolder;

public class RentDetailsController {
    @FXML public Label movieNameLabel;
    @FXML public Label movieRatingLabel;
    @FXML public Label movieFeeLabel;
    @FXML public DatePicker rentDatePicker;
    @FXML public DatePicker returnDatePicker;
    public Button confirmButton;
    public Button cancelButton;
    public Label errorEmptyDateLabel;

    @FXML
    public void initialize() {
        MovieHolder movieHolder = MovieHolder.getInstance();

        if (movieHolder.getMovie() != null) {
            setMovieDetailsIntoLabels(movieHolder.getMovie());
        } else
            System.out.println("MovieHolder getMovie = null");
    }

    private void setMovieDetailsIntoLabels(Movie movie) {
        movieNameLabel.setText(movie.getName());
        movieRatingLabel.setText(String.valueOf(movie.getRating()));
        movieFeeLabel.setText(String.valueOf(movie.getFeePerDay()));
    }

    @FXML
    public void OnConfirmClicked() {
        if (rentDatePicker.getValue() == null || returnDatePicker.getValue() == null
                || rentDatePicker.getValue().toString().isEmpty() || returnDatePicker.getValue().toString().isEmpty()) {
            errorEmptyDateLabel.setText("Wypełnij daty!");
            return;
        }

        if (rentDatePicker.getValue().isAfter(returnDatePicker.getValue())) {
            errorEmptyDateLabel.setText("Zła data zwrotu!");
            return;
        }

        errorEmptyDateLabel.setText("");
        var rentDate = rentDatePicker.getValue();
        var returnDate = returnDatePicker.getValue();

        DateHolder dateHolder = DateHolder.getINSTANCE();
        dateHolder.setRentDate(rentDate);
        dateHolder.setReturnDate(returnDate);

        closeWindow();
    }

    private void closeWindow() {
        Stage currentStage = (Stage) confirmButton.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void onCancelClicked() {
        Stage currentStage = (Stage) cancelButton.getScene().getWindow();
        currentStage.close();
    }
}
