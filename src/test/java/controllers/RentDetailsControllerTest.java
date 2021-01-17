package controllers;

import data_holders.MovieHolder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import model.Movie;
import model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import other.Constants;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class RentDetailsControllerTest extends ApplicationTest {
    private RentDetailsController rentDetailsController;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(RentDetailsController.class.getClassLoader().getResource("layouts/" + Constants.RENT_DETAILS_DIALOG));
        Parent mainNode = loader.load();

        rentDetailsController = loader.getController();

        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[] {});
    }

    @Test
    public void shouldAnyEmptyDateShowError() {
        clickOn("#confirmButton");

        assertThat(rentDetailsController.errorEmptyDateLabel.getText(), is("Wypełnij daty!"));
    }

    @Test
    public void shouldBadReturnDateShowError() {
        LocalDate now = LocalDate.now();
        LocalDate returnDateAfterNow = now.minusDays(5);

        rentDetailsController.rentDatePicker.setValue(now);
        rentDetailsController.returnDatePicker.setValue(returnDateAfterNow);
        clickOn("#confirmButton");

        assertThat(rentDetailsController.errorEmptyDateLabel.getText(), is("Zła data zwrotu!"));
    }

    @Test
    public void shouldEqualReturnDateShowError() {
        LocalDate now = LocalDate.now();

        rentDetailsController.rentDatePicker.setValue(now);
        rentDetailsController.returnDatePicker.setValue(now);
        clickOn("#confirmButton");

        assertThat(rentDetailsController.errorEmptyDateLabel.getText(), is("Zła data zwrotu!"));
    }
}