package model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

public class RentedMovie {
    private int userId;
    private int movieId;
    private String rentDate;
    private String returnDate;
    private double rentFee;

    public RentedMovie(int userId, int movieId, String rentDate, String returnDate, double rentFee) {
        this.userId = userId;
        this.movieId = movieId;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.rentFee = rentFee;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setRentDate(String rentDate) {
        this.rentDate = rentDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public void setRentFee(double rentFee) {
        this.rentFee = rentFee;
    }

    public int getUserId() {
        return userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getRentDate() {
        return rentDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public double getRentFee() {
        return rentFee;
    }

    @Override
    public String toString() {
        return "RentedMovie{" +
                "  userId=" + userId +
                ", movieId=" + movieId +
                ", rentDate=" + rentDate +
                ", returnDate=" + returnDate +
                ", rentFee=" + rentFee +
                '}';
    }
}
