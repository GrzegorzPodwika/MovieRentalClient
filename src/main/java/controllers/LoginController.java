package controllers;

import api.MovieService;
import api.RetrofitClient;
import api.UserService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;
import data_holders.UserHolder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

import static other.Constants.*;

public class LoginController implements FlowController {

    @FXML
    public TextField userName;

    @FXML
    public PasswordField userPassword;

    @FXML
    public Button signInButton;

    @FXML
    public Button signUpButton;

    @FXML
    public Button exitButton;

    @FXML
    public Label responseLabel;

    @FXML
    public Label errorLabel;

    private MainController mainController;
    private final RetrofitClient retrofitClient = new RetrofitClient();
    private UserService userService;

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        userService = retrofitClient.getRetrofitClient().create(UserService.class);
    }

    public void onSignIn() {
        if (isUsernameOrPasswordEmpty()) {
            errorLabel.setText("Fill up all fields!");
            return;
        }

        try {
            loginUserToServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loginUserToServer() throws IOException {
        var username = userName.getText();
        var password = userPassword.getText();
        UserHolder userHolder = UserHolder.getINSTANCE();

        var call = userService.loginUser(new User(0, username, password));
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        assert response.body() != null;
                        String stringResponse = response.body().string();
                        if (stringResponse.equals("ERROR")) {
                            Platform.runLater(
                                    () -> {
                                        errorLabel.setText("Error has occurred");
                                    }
                            );
                        } else {
                            int userId = Integer.parseInt(stringResponse);
                            userHolder.setUserId(userId);
                            System.out.println("UserId is get = " + userId);
                            Platform.runLater(
                                    () -> {
                                        navigateToUserPanelActivity();
                                    }
                            );
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                errorLabel.setText("Error has occurred" + throwable.getMessage());
            }
        });
    }

    private boolean isUsernameOrPasswordEmpty() {
        return userPassword.getText().isEmpty() || userName.getText().isEmpty();
    }


    @FXML
    private void navigateToUserPanelActivity() {
        mainController.loadUserPanelIntoMainStackPane(USER_PANEL_LAYOUT);
    }

    @FXML
    public void navigateToRegisterActivity() {
        mainController.loadPaneIntoMainStackPane(REGISTER_LAYOUT);
    }

    @FXML
    public void onExit() {
        System.exit(0);
    }


}
