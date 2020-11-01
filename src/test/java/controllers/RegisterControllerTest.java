package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import other.Constants;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class RegisterControllerTest extends ApplicationTest {
    private RegisterController registerController;

    @Before
    public void setUp() throws Exception {
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(RegisterController.class.getClassLoader().getResource("layouts/" + Constants.REGISTER_LAYOUT));
        Parent mainNode = loader.load();

        registerController = loader.getController();

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
    public void shouldEmptyLoginShowError() {
        //given
        clickOn("#userPassword");
        write("test_password");
        clickOn("#userConfirmPassword");
        write("test_password");

        //when
        clickOn("#signUpButton");

        //then
        assertThat(registerController.errorLabel.getText(), is("Fill up all fields!"));
    }

    @Test
    public void shouldEmptyPasswordShowError() {
        //given
        clickOn("#userName");
        write("test_login");
        clickOn("#userConfirmPassword");
        write("test_password");

        //when
        clickOn("#signUpButton");

        //then
        assertThat(registerController.errorLabel.getText(), is("Fill up all fields!"));
    }

    @Test
    public void shouldEmptyConfirmPasswordShowError() {
        //given
        clickOn("#userName");
        write("test_login");
        clickOn("#userPassword");
        write("test_password");

        //when
        clickOn("#signUpButton");

        //then
        assertThat(registerController.errorLabel.getText(), is("Fill up all fields!"));
    }

    @Test
    public void shouldNonMatchPasswordShowError() {
        //given
        clickOn("#userPassword");
        write("test_password");
        clickOn("#userConfirmPassword");
        write("test_passwordAnother");

        //when
        clickOn("#signUpButton");

        //then
        assertThat(registerController.passwordEquality.getText(), is("Password doesn't match"));
    }

    @Test
    public void shouldMatchPasswordShowConfirmMessage() {
        //given
        clickOn("#userPassword");
        write("test_password");
        clickOn("#userConfirmPassword");
        write("test_password");

        //when
        clickOn("#signUpButton");

        //then
        assertThat(registerController.passwordEquality.getText(), is("Password is correct"));
    }

/*    @Test
    public void shouldRegistrationWithNoServerShowError() {
        //given
        clickOn("#userName");
        write("tt");
        clickOn("#userPassword");
        write("tt");
        clickOn("#userConfirmPassword");
        write("tt");


        //when
        clickOn("#signUpButton");
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //then
        assertThat(registerController.errorLabel.getText(), is("Server is not available."));
    }*/

    @Test
    public void shouldRegistrationAlreadyExistsUserShowError() {
        //given
        clickOn("#userName");
        write("gg");
        clickOn("#userPassword");
        write("gg");
        clickOn("#userConfirmPassword");
        write("gg");


        //when
        clickOn("#signUpButton");
/*        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //then
        assertThat(registerController.errorLabel.getText(), is("The account already exists. Go back"));
    }
}