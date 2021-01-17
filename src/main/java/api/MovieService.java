package api;

import model.Movie;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    @POST("/saveMovie")
    Call<Integer> save(@Body Movie movie);

    @POST("/updateMovie")
    Call<Movie> update(@Body Movie movie);

    @POST("/deleteMovie")
    Call<Void> delete(@Body Movie movie);

    @POST("/getMovie")
    Call<Optional<Movie>> get(@Body Integer id);

    @GET("/getAllMovies")
    Call<List<Movie>> getAll();

    @GET("/getAllMoviesByQuery")
    Call<List<Movie>> getAllMoviesByQuery(@Query("searchMovie") String searchMovie, @Query("genre") String genre );
}
