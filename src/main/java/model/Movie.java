package model;

public class Movie {
    private final Integer movieId;
    private final String name;
    private final String genre;
    private final String year;
    private final double rating;
    private final double feePerDay;

    public Movie(Integer movieId, String name, String genre, String year, double rating, double feePerDay) {
        this.movieId = movieId;
        this.name = name;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
        this.feePerDay = feePerDay;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getYear() {
        return year;
    }

    public double getRating() {
        return rating;
    }

    public double getFeePerDay() {
        return feePerDay;
    }
}
