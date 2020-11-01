package api;

import model.Fee;
import model.Movie;
import model.RentedMovie;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface MovieService {

    @GET("/getAllMovies")
    Call<List<Movie>> getAllMovies();

    @GET("/movies")
    Call<List<Movie>> getAllMoviesByQuery(@Query("searchMovie") String searchMovie, @Query("genre") String genre );

    @POST("/rentMovie")
    Call<Integer> rentMovie(@Body RentedMovie rentedMovie);

    @GET("/rentedMovies/{userId}")
    Call<List<Fee>> getAllRentedMovies(@Path("userId") Integer userId);

}
