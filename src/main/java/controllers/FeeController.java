package controllers;

import api.MovieService;
import api.RetrofitClient;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Fee;
import model.FeeData;
import data_holders.UserHolder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
import java.util.stream.Collectors;

public class FeeController {
    @FXML
    public Label amountToBePaidLabel;
    @FXML
    public TableView<FeeData> paymentHistoryTable;
    @FXML
    public TableColumn<FeeData, Integer> tableRentId;
    @FXML
    public TableColumn<FeeData, Integer> tableMovieId;
    @FXML
    public TableColumn<FeeData, String> tableMovieName;
    @FXML
    public TableColumn<FeeData, String> tableRentDate;
    @FXML
    public TableColumn<FeeData, String> tableReturnDate;
    @FXML
    public TableColumn<FeeData, Double> tableFee;

    private final RetrofitClient retrofitClient = new RetrofitClient();
    private MovieService movieService;

    private final ObservableList<FeeData> observableFees = FXCollections.observableArrayList();
    private List<Fee> allFees;
    private Integer userId;
    private double totalAmountToPay = 0.0;

    @FXML
    public void initialize() {
        initMovieService();
        initTableColumns();
        fetchUserIdFromUserHolder();
        fetchAllUserFeesFromDb();
    }

    private void initMovieService() {
        movieService = retrofitClient.getRetrofitClient().create(MovieService.class);
    }

    private void initTableColumns() {
        tableRentId.setCellValueFactory(new PropertyValueFactory<>("TableRentId"));
        tableMovieId.setCellValueFactory(new PropertyValueFactory<>("TableMovieId"));
        tableMovieName.setCellValueFactory(new PropertyValueFactory<>("TableMovieName"));
        tableRentDate.setCellValueFactory(new PropertyValueFactory<>("TableRentDate"));
        tableReturnDate.setCellValueFactory(new PropertyValueFactory<>("TableReturnDate"));
        tableFee.setCellValueFactory(new PropertyValueFactory<>("TableFee"));
    }

    private void fetchUserIdFromUserHolder() {
        UserHolder userHolder = UserHolder.getINSTANCE();
        userId = userHolder.getUserId();
    }

    private void fetchAllUserFeesFromDb() {
        var feesCall = movieService.getAllRentedMovies(userId);
        feesCall.enqueue(new Callback<List<Fee>>() {
            @Override
            public void onResponse(Call<List<Fee>> call, Response<List<Fee>> response) {
                if (response.isSuccessful()) {
                    allFees = response.body();

                    if (allFees != null) {
                        observableFees.addAll(transformToDataFormat(allFees));
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
            public void onFailure(Call<List<Fee>> call, Throwable throwable) {
                System.out.println("Error has occurred" + throwable.getMessage());

            }
        });
    }

    private List<FeeData> transformToDataFormat(List<Fee> fees) {
        return fees.stream()
                .map(fee -> new FeeData(fee.getRentId(), fee.getMovieId(), fee.getMovieName(), fee.getRentDate(), fee.getReturnDate(), fee.getRentFee()))
                .collect(Collectors.toList());
    }

    private void setTotalAmountToMustBePaid() {
        for (Fee fee : allFees) {
            totalAmountToPay += fee.getRentFee();
        }

        amountToBePaidLabel.setText(String.valueOf(totalAmountToPay));
    }

}
