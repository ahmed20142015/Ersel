package ersel.greatbit.net.ersel.http;

import ersel.greatbit.net.ersel.models.GetShipments;
import ersel.greatbit.net.ersel.models.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Eslam on 3/11/2018.
 */

public interface IHttpService {
    @FormUrlEncoded
    @POST("Login")
    Call<LoginResponse> loginUser(@Field("mobile") String mobile,@Field("password") String password);

    @GET("Shipments")
    Call<GetShipments>getShipments(@Query("status") int status);
}
