package controllers;

import api.MovieService;
import api.RetrofitClient;
import data_holders.DateHolder;
import data_holders.MovieHolder;
import data_holders.UserHolder;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static other.Constants.RENT_DETAILS_DIALOG;

public class RentController {

    @FXML
    public ComboBox<String> filterMovie;
    @FXML
    public String allFilter;
    @FXML
    public String actionFilter;
    @FXML
    public String comedyFilter;
    @FXML
    public String dramaFilter;
    @FXML
    public String horrorFilter;
    @FXML
    public String romanceFilter;

    @FXML
    public TableView<MovieData> moviesTableView;
    @FXML
    public TableColumn<MovieData, Integer> tableId;
    @FXML
    public TableColumn<MovieData, String> tableName;
    @FXML
    public TableColumn<MovieData, String> tableGenre;
    @FXML
    public TableColumn<MovieData, String> tableYear;
    @FXML
    public TableColumn<MovieData, Double> tableRating;
    @FXML
    public TableColumn<MovieData, Double> tableFee;

    public TextField searchLabel;
    public Label errorLabel;

    private final ObservableList<MovieData> observableMovies = FXCollections.observableArrayList();
    private final RetrofitClient retrofitClient = new RetrofitClient();
    private MovieService movieService;
    private List<MovieData> listOfMovieData;
    private Integer userId;

    @FXML
    public void initialize() {
        initMovieService();
        initTableColumns();
        fetchUserIdFromUserHolder();
        fetchAllMoviesFromDb();
    }

    private void initMovieService() {
        movieService = retrofitClient.getRetrofitClient().create(MovieService.class);
    }

    private void initTableColumns() {
        tableId.setCellValueFactory(new PropertyValueFactory<>("TableId"));
        tableName.setCellValueFactory(new PropertyValueFactory<>("TableName"));
        tableGenre.setCellValueFactory(new PropertyValueFactory<>("TableGenre"));
        tableYear.setCellValueFactory(new PropertyValueFactory<>("TableYear"));
        tableRating.setCellValueFactory(new PropertyValueFactory<>("TableRating"));
        tableFee.setCellValueFactory(new PropertyValueFactory<>("TableFee"));
    }

    private void fetchUserIdFromUserHolder() {
        UserHolder userHolder = UserHolder.getINSTANCE();
        userId = userHolder.getUserId();
    }

    private void fetchAllMoviesFromDb() {
        var moviesCall = movieService.getAllMovies();
        moviesCall.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.isSuccessful()) {
                    var allMovies = response.body();

                    if (allMovies != null) {
                        listOfMovieData = transformToDataFormat(allMovies);
                        updateObservableMovies(listOfMovieData);
                        Platform.runLater(
                                () -> moviesTableView.setItems(observableMovies)
                        );
                    } else {
                        System.out.println("Allmovies = null");
                        Platform.runLater(
                                () -> errorLabel.setText("Response is not successful.")
                        );
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable throwable) {
                System.out.println("Error has occurred" + throwable.getMessage());
                Platform.runLater(() -> errorLabel.setText("Server is not available."));
            }
        });
    }

    private List<MovieData> transformToDataFormat(List<Movie> movies) {
        return movies.stream()
                .map(movie -> new MovieData(movie.getMovieId(), movie.getName(), movie.getGenre(), movie.getYear(), movie.getRating(), movie.getFeePerDay())).collect(Collectors.toList());
    }

    private void updateObservableMovies(List<MovieData> listOfMovieData) {
        observableMovies.clear();
        observableMovies.addAll(listOfMovieData);
    }

    public void onSearchClicked() {
        var searchMovie = searchLabel.getText();
        var genre = filterMovie.getValue();

        if (searchMovie.isEmpty() && genre.equals("all")) {
            updateObservableMovies(listOfMovieData);
            return;
        }

        var moviesQueryCall = movieService.getAllMoviesByQuery(searchMovie, genre);
        moviesQueryCall.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.isSuccessful()) {
                    var queryMovies = response.body();

                    if (queryMovies != null) {
                        updateObservableMovies(transformToDataFormat(queryMovies));
                        Platform.runLater(
                                () -> moviesTableView.setItems(observableMovies)
                        );
                    }
                } else {
                    System.out.println("Received query == null");
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable throwable) {
                System.out.println("Error has occurred" + throwable.getMessage());
            }
        });

    }


    public void onRentMovieClicked() {
        var selectedMovie = moviesTableView.getSelectionModel().getSelectedItem();

        if (selectedMovie != null) {
            System.out.println(selectedMovie);
            showWindowWithRentalDetails(selectedMovie);
        } else {
            System.out.println("Selected movie = null");
        }
    }

    private void showWindowWithRentalDetails(MovieData selectedMovie) {
        Pane root;
        String fullPath = "layouts/" + RENT_DETAILS_DIALOG;

        try {
            Stage newStage = new Stage();
            newStage.initStyle(StageStyle.UNDECORATED);
            newStage.initModality(Modality.APPLICATION_MODAL);

            MovieHolder movieHolder = MovieHolder.getInstance();
            Movie movie = transformToPlainMovie(selectedMovie);
            movieHolder.setMovie(movie);

            FXMLLoader loader = new FXMLLoader(this.getClass().getClassLoader().getResource(fullPath));
            root = loader.load();
            newStage.setScene(new Scene(root));
            newStage.showAndWait();

            fetchChosenDates(movie);
/*            RentDetailsController rentDetailsController = loader.getController();
            rentDetailsController.setSelectedMovie(selectedMovie);
            rentDetailsController.setCurrentStage(newStage);*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fetchChosenDates(Movie movie) {
        DateHolder dateHolder = DateHolder.getINSTANCE();

        var rentDate = dateHolder.getRentDate();
        var returnDate = dateHolder.getReturnDate();

        if (rentDate != null && returnDate != null) {
            postRentMovie(movie, rentDate, returnDate);
            clearFieldsInDateHolder(dateHolder);
        }
    }

    private void postRentMovie(Movie movie, LocalDate rentDate, LocalDate returnDate) {
        // This method doesn't include last day, so we add 1 day to result.
        long daysBetween = ChronoUnit.DAYS.between(rentDate, returnDate) + 1;
        double totalFee = daysBetween * movie.getFeePerDay();

        RentedMovie rentedMovie = new RentedMovie(userId, movie.getMovieId(), rentDate.format(DateTimeFormatter.ISO_LOCAL_DATE), returnDate.format(DateTimeFormatter.ISO_LOCAL_DATE), totalFee);

        try {
            var response = movieService.rentMovie(rentedMovie).execute();
            if (response.isSuccessful()) {
                System.out.println("Response OK");
            } else {
                System.out.println("Response Error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFieldsInDateHolder(DateHolder dateHolder) {
        dateHolder.setRentDate(null);
        dateHolder.setReturnDate(null);
    }

    private Movie transformToPlainMovie(MovieData movieData) {
        return new Movie(movieData.getTableId(), movieData.getTableName(), movieData.getTableGenre(), movieData.getTableYear(), movieData.getTableRating(), movieData.getTableFee());
    }
}
