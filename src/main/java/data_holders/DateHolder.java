package data_holders;

import java.time.LocalDate;

public final class DateHolder {
    private LocalDate rentDate;
    private LocalDate returnDate;
    private boolean addMovie;
    private final static DateHolder INSTANCE = new DateHolder();

    private DateHolder() {}

    public static DateHolder getINSTANCE() {
        return INSTANCE;
    }

    public LocalDate getRentDate() {
        return rentDate;
    }

    public void setRentDate(LocalDate rentDate) {
        this.rentDate = rentDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isAddMovie() {
        return addMovie;
    }

    public void setAddMovie(boolean addMovie) {
        this.addMovie = addMovie;
    }
}
