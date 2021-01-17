package model;

public class Movie {
    private Integer movieId;

    private String name;
    private String genre;
    private String year;
    private double rating;
    private double feePerDay;

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

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setFeePerDay(double feePerDay) {
        this.feePerDay = feePerDay;
    }
}
