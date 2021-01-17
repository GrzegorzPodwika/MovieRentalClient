package controllers;

import api.ServerResponse;
import api.ServiceGenerator;
import api.UserService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

import static other.Constants.*;

public class RegisterController implements FlowController {

    @FXML public ImageView GoBackButton;
    @FXML public TextField userName;
    @FXML public PasswordField userPassword;
    @FXML public Button signUpButton;
    @FXML public PasswordField userConfirmPassword;
    @FXML public Text passwordEquality;
    @FXML public Label errorLabel;

    private MainController mainController;
    private final UserService userService = ServiceGenerator.createService(UserService.class);


    @FXML
    public void initialize() {

        userConfirmPassword.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (userPassword.getText().equals(newValue)) {
                passwordEquality.setText("Password is correct");
                passwordEquality.setFill(Color.GREEN);
            } else {
                passwordEquality.setText("Password doesn't match");
                passwordEquality.setFill(Color.RED);
            }
        }));
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void onSignUp() {
        if (isUsernameOrPasswordEmpty()){
            errorLabel.setText("Fill up all fields!");
            return;
        } else {
            errorLabel.setText("");
        }

        try {
            postUserToServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isUsernameOrPasswordEmpty() {
        return userPassword.getText().isEmpty() || userName.getText().isEmpty() || userConfirmPassword.getText().isEmpty();
    }

    private void postUserToServer() throws IOException{
        var username = userName.getText();
        var password = userPassword.getText();

        var registerUserCall = userService.addUser(new User(0, username, password));
        registerUserCall.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Platform.runLater( () -> handleResponseCode(response.body()));

                } else {
                    Platform.runLater( () -> errorLabel.setText("Server is not available."));
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {
                Platform.runLater( () -> errorLabel.setText("Server is not available."));
            }
        });

    }

    private void handleResponseCode(Integer responseCode) {
        if (responseCode != null) {
            if (responseCode == ServerResponse.OK.getCode()) {
                navigateToLoginActivity();
            } else if(responseCode == ServerResponse.ERROR.getCode()) {
                errorLabel.setText("Unexpected error has occurred.");
                System.out.println("Response code = " + responseCode);
            } else if(responseCode == ServerResponse.ALREADY_EXIST.getCode()) {
                errorLabel.setText("The account already exists. Go back");
                System.out.println("Response code = " + responseCode);
            }else {
                errorLabel.setText("Unexpected error has occurred.");
                System.out.println("Error = " + responseCode);
            }
        } else {
            errorLabel.setText("Unexpected error has occurred.");
        }
    }

    @FXML
    public void navigateToLoginActivity() {
        mainController.loadPaneIntoMainStackPane(LOGIN_LAYOUT);
    }

    @FXML
    public void onExit(ActionEvent actionEvent) {
        System.exit(0);
    }

}
