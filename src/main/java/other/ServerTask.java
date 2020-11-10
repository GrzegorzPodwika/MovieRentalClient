package other;

import api.AvailabilityService;
import api.RetrofitClient;
import api.ServerResponse;
import javafx.application.Platform;
import javafx.scene.control.Label;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServerTask implements Runnable {
    private final RetrofitClient retrofitClient = new RetrofitClient();
    private final AvailabilityService service =
            retrofitClient.getRetrofitClient().create(AvailabilityService.class);

    private Label serverLabel;
    private ServerStateChangeListener serverListener;

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
                            serverLabel.setText("");
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
                            serverLabel.setText("Server is not available now.");
                        });
                    }
                }
            });
        }
    }
}
