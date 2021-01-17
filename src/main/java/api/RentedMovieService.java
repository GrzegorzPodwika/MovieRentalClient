package api;

import model.Movie;
import model.RentedMovie;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;
import java.util.Optional;

public interface RentedMovieService {

    @POST("/saveRentedMovie")
    Call<Integer> save(@Body RentedMovie rentedMovie);

    @POST("/updateRentedMovie")
    Call<Movie> update(@Body RentedMovie rentedMovie);

    @POST("/deleteRentedMovie")
    Call<Void> delete(@Body RentedMovie rentedMovie);

    @POST("/getRentedMovie")
    Call<Optional<Movie>> get(@Body Integer id);

    @GET("/getAllRentedMovies")
    Call<List<Movie>> getAll();

    @POST("/getAllRentedMoviesByUser")
    Call<List<RentedMovie>> getAllRentedMovies(@Body Integer userId);
}
