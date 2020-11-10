package api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AvailabilityService {

    @GET(".")
    Call<ServerResponse> isServerAvailable();
}
