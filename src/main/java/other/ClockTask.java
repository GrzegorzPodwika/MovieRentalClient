package other;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Semaphore;

public class ClockTask implements Runnable{

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final Label clockLabel;
    private final Semaphore mutex = new Semaphore(1);

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
                try {
                    mutex.acquire();
                    //System.out.println("MUTEX LOCKED");
                    clockLabel.setText(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    mutex.release();
                    //System.out.println("MUTEX released");
                }
            });
        }
    }
}
