package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

import static other.Constants.*;

public class MainController {
    private Stage primaryStage;

    @FXML
    public StackPane mainStackPane;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void initialize() {
        loadPaneIntoMainStackPane(LOGIN_LAYOUT);
    }

    public void loadPaneIntoMainStackPane(String layoutName) {
        Pane pane = initializePane(layoutName);
        setScreen(pane);
    }

    private Pane initializePane(String layoutName) {
        String fullPath = "layouts/" + layoutName;
        FXMLLoader loader = new FXMLLoader(this.getClass().getClassLoader().getResource(fullPath));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FlowController flowController = loader.getController();
        flowController.setMainController(this);

        return pane;
    }

    public void setScreen(Pane pane) {
        mainStackPane.getChildren().clear();
        mainStackPane.getChildren().add(pane);
    }

    public void loadUserPanelIntoMainStackPane(String layoutName) {
        Pane pane = initializePane(layoutName);
        resizePrimaryStage();
        setScreen(pane);
    }

    private void resizePrimaryStage() {
        primaryStage.setMinHeight(700.0);
        primaryStage.setMinWidth(1200.0);
    }


}
