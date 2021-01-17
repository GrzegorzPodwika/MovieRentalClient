package controllers;

import api.RentedMovieService;
import api.ServiceGenerator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.RentedMovie;
import model.RentedMovieData;
import data_holders.UserHolder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static other.MovieUtils.*;

public class FeeController {
    @FXML public Label amountToBePaidLabel;
    @FXML public TableView<RentedMovieData> paymentHistoryTable;
    @FXML public TableColumn<RentedMovieData, Integer> tableRentId;
    @FXML public TableColumn<RentedMovieData, Integer> tableMovieId;
    @FXML public TableColumn<RentedMovieData, String> tableMovieName;
    @FXML public TableColumn<RentedMovieData, String> tableRentDate;
    @FXML public TableColumn<RentedMovieData, String> tableReturnDate;
    @FXML public TableColumn<RentedMovieData, Double> tableFee;
    @FXML public TableColumn<RentedMovieData, Boolean> tableIsPaid;
    @FXML public Button buttonPayReservation;

    private final RentedMovieService rentedMovieService = ServiceGenerator.createService(RentedMovieService.class);
    private final ObservableList<RentedMovieData> observableFees = FXCollections.observableArrayList();
    private List<RentedMovie> allRentedMovies;
    private Integer userId;
    private final List<String> paymentTypes = Arrays.asList("BLIK", "CREDIT CARD", "DEBIT CARD");

    @FXML
    public void initialize() {
        initTableColumns();
        fetchUserIdFromUserHolder();
        fetchAllUserFeesFromDb();
    }

    private void initTableColumns() {
        tableRentId.setCellValueFactory(new PropertyValueFactory<>("TableRentId"));
        tableMovieId.setCellValueFactory(new PropertyValueFactory<>("TableMovieId"));
        tableMovieName.setCellValueFactory(new PropertyValueFactory<>("TableMovieName"));
        tableRentDate.setCellValueFactory(new PropertyValueFactory<>("TableRentDate"));
        tableReturnDate.setCellValueFactory(new PropertyValueFactory<>("TableReturnDate"));
        tableFee.setCellValueFactory(new PropertyValueFactory<>("TableFee"));
        tableIsPaid.setCellValueFactory(new PropertyValueFactory<>("TableIsPaid"));
    }

    private void fetchUserIdFromUserHolder() {
        UserHolder userHolder = UserHolder.getINSTANCE();
        userId = userHolder.getUser().getUserId();
    }

    private void fetchAllUserFeesFromDb() {
        var allRentedMoviesCall = rentedMovieService.getAllRentedMovies(userId);
        allRentedMoviesCall.enqueue(new Callback<List<RentedMovie>>() {
            @Override
            public void onResponse(Call<List<RentedMovie>> call, Response<List<RentedMovie>> response) {
                if (response.isSuccessful()) {
                    allRentedMovies = response.body();

                    if (allRentedMovies != null) {
                        observableFees.clear();
                        observableFees.addAll(transformToDataFormat(allRentedMovies));
                        Platform.runLater(() -> {
                            paymentHistoryTable.setItems(observableFees);
                            setTotalAmountToMustBePaid();
                        });
                    } else {
                        System.out.println("Fees = null");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RentedMovie>> call, Throwable throwable) {
                System.out.println("Error has occurred" + throwable.getMessage());
            }
        });
    }

    private List<RentedMovieData> transformToDataFormat(List<RentedMovie> rentedMovies) {
        return rentedMovies.stream()
                .map(mov -> new RentedMovieData(mov.getRentId(), mov.getMovie().getMovieId(), mov.getMovie().getName(),
                        mov.getRentDate().toString(), mov.getReturnDate().toString(), mov.getRentFee(), mov.isPaid()))
                .collect(Collectors.toList());
    }

    private void setTotalAmountToMustBePaid() {
        double totalAmountToPay = allRentedMovies.stream().filter(mov -> !mov.isPaid()).mapToDouble(RentedMovie::getRentFee).sum();

        amountToBePaidLabel.setText(roundOffToStr(totalAmountToPay));
    }

    @FXML
    public void onPayReservationClick() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("BLIK", paymentTypes);
        dialog.setTitle("Opłać rezerwację");
        dialog.setHeaderText(null);
        dialog.setContentText("Wybierz metodę płatności:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            updateFeeInDb();
        }
    }

    private void updateFeeInDb() {
        RentedMovieData selectedRentedMovieData = paymentHistoryTable.getSelectionModel().getSelectedItem();
        if (selectedRentedMovieData == null) {
            showChooseAtLeastOneDialog();
            return;
        }


        if (selectedRentedMovieData.isTableIsPaid()) {
            showIsAlreadyPaidDialog();
        } else {
            try {
                RentedMovie selectedRentedMovie = allRentedMovies.stream().filter(mov -> mov.getRentId() == selectedRentedMovieData.getTableRentId()).findFirst().get();
                selectedRentedMovie.setPaid(true);

                rentedMovieService.update(selectedRentedMovie).execute();
                fetchAllUserFeesFromDb();
                setTotalAmountToMustBePaid();
                showConfirmationDialog();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showChooseAtLeastOneDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText("Zaznacz wypożyczenie!");

        alert.showAndWait();
    }

    private void showConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText("Wypożyczenie zostało opłacone!");

        alert.showAndWait();
    }

    private void showIsAlreadyPaidDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText("Wypożyczenie jest już opłacone!");

        alert.showAndWait();
    }
}
