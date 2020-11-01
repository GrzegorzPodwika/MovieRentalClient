package controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import other.ClockTask;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static other.Constants.FEE_LAYOUT;
import static other.Constants.RENT_FRAGMENT;

public class UserPanelController implements FlowController {


    @FXML public StackPane bodyStackPane;
    @FXML public Label clockLabel;

    private MainController mainController;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    @FXML
    public void initialize() {
        initClock();

        setIntoBodyLayout(RENT_FRAGMENT);
    }

    private void setIntoBodyLayout(String fragment) {
        String fullPath = "layouts/" + fragment;
        FXMLLoader loader = new FXMLLoader(this.getClass().getClassLoader().getResource(fullPath));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        bodyStackPane.getChildren().clear();
        bodyStackPane.getChildren().add(pane);
    }

    private void initClock() {
        executorService.execute(new ClockTask(clockLabel));
    }


    @FXML
    public void onRentInMenuClicked() {
        setIntoBodyLayout(RENT_FRAGMENT);
    }

    @FXML
    public void onFeesInMenuClicked() {
        setIntoBodyLayout(FEE_LAYOUT);
    }

    @FXML
    public void onExit() {
        System.exit(0);
    }
}
