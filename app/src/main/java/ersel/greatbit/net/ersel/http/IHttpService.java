package ersel.greatbit.net.ersel.http;

import ersel.greatbit.net.ersel.models.BaseResponse;
import ersel.greatbit.net.ersel.models.GetShipments;
import ersel.greatbit.net.ersel.models.LoginResponse;
import ersel.greatbit.net.ersel.models.ShipmentDetails;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
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

    @GET("ShipmentDetails")
    Call<ShipmentDetails>shipmentDetails(@Query("id") int id);

    @FormUrlEncoded
    @POST("Geos")
    Call<BaseResponse> updateLocation(@Field("latitude") double latitude, @Field("longitude") double longitude);

//    @PUT("ShipmentStatus/{id}/{type}/{status}/{notes}")
//    Call<BaseResponse>updateStatus(@Path("id") int id ,@Path("type") int type ,@Path("status") int status ,@Path("notes") String notes );

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "ShipmentStatus", hasBody = true)
    Call<BaseResponse> updateStatus(@Field("id") int id,@Field("type") int type,@Field("status") int status,@Field("notes") String notes);

}
