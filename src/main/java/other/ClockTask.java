package other;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClockTask implements Runnable{

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private Label clockLabel;

    public ClockTask(Label clockLabel) {
        this.clockLabel = clockLabel;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LocalTime currentTime = LocalTime.now();
            final String time = currentTime.format(dateTimeFormatter);
            Platform.runLater( () -> {
                clockLabel.setText(time);
            });
        }
    }
}
