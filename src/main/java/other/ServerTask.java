package other;

import api.AvailabilityService;
import api.ServerResponse;
import api.ServiceGenerator;
import javafx.application.Platform;
import javafx.scene.control.Label;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.concurrent.Semaphore;

public class ServerTask implements Runnable {
    private final AvailabilityService service = ServiceGenerator.createService(AvailabilityService.class);

    private Label serverLabel;
    private ServerStateChangeListener serverListener;
    private final Semaphore mutex = new Semaphore(1);

    public ServerTask(Label serverLabel) {
        this.serverLabel = serverLabel;
    }

    public ServerTask(ServerStateChangeListener serverListener) {
        this.serverListener = serverListener;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            var call = service.isServerAvailable();
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    if (serverListener != null) {
                        serverListener.serverStateAvailable();
                    }

                    if (serverLabel != null) {
                        Platform.runLater(() -> {
                            try {
                                mutex.acquire();
                                serverLabel.setText("");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                mutex.release();
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable throwable) {
                    if (serverListener != null) {
                        serverListener.serverStateNotAvailable();
                    }

                    if (serverLabel != null) {

                        Platform.runLater(() -> {
                            try {
                                mutex.acquire();
                                serverLabel.setText("Server is not available now.");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                mutex.release();
                            }
                        });

                    }
                }
            });
        }
    }
}
