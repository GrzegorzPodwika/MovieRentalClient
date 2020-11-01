package model;

public class Fee {
    private Integer rentId;
    private Integer movieId;
    private String movieName;
    private String rentDate;
    private String returnDate;
    private Double rentFee;

    public Fee(Integer rentId, Integer movieId, String movieName, String rentDate, String returnDate, Double rentFee) {
        this.rentId = rentId;
        this.movieId = movieId;
        this.movieName = movieName;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.rentFee = rentFee;
    }

    public Integer getRentId() {
        return rentId;
    }

    public void setRentId(Integer rentId) {
        this.rentId = rentId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getRentDate() {
        return rentDate;
    }

    public void setRentDate(String rentDate) {
        this.rentDate = rentDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Double getRentFee() {
        return rentFee;
    }

    public void setRentFee(Double rentFee) {
        this.rentFee = rentFee;
    }
}
