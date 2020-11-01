package data_holders;

import model.Movie;

public final class MovieHolder {
    private Movie movie;
    private final static MovieHolder INSTANCE = new MovieHolder();

    private MovieHolder() {}

    public static MovieHolder getInstance() {
        return INSTANCE;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }
}
