package controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import other.ClockTask;
import other.ServerStateChangeListener;
import other.ServerTask;

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
    @FXML public Label serverLabel;

    private MainController mainController;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    @FXML
    public void initialize() {
        initClock();
        initObservationOfServerAvailability();
        setIntoBodyLayout(RENT_FRAGMENT);
    }

    private void initClock() {
        executorService.execute(new ClockTask(clockLabel));
    }

    private void initObservationOfServerAvailability() {
        executorService.execute(new ServerTask(serverLabel));
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
