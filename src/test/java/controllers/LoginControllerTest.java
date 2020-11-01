package controllers;

import controllers.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import other.Constants;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class LoginControllerTest extends ApplicationTest {
    private LoginController loginController;

    @Before
    public void setUp() throws Exception {

    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(LoginController.class.getClassLoader().getResource("layouts/" + Constants.LOGIN_LAYOUT));
        Parent mainNode = loader.load();

        loginController = loader.getController();
        
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
    public void shouldEmptyPasswordShowError() {
        clickOn("#userName");
        write("Test_user_name");
        clickOn("#signInButton");

        assertThat(loginController.errorLabel.getText(), is("Fill up all fields!"));
    }

    @Test
    public void shouldEmptyLoginShowError() {
        //given
        clickOn("#userPassword");
        write("Test_user_password");
        clickOn("#signInButton");

        //then
        assertThat(loginController.errorLabel.getText(), is("Fill up all fields!"));
    }


}