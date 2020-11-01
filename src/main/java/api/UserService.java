package api;

import model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {

    @POST("/addUser")
    Call<Integer> addUser(@Body User user);

    @POST("/loginUser")
    Call<ResponseBody> loginUser(@Body User user);
}
