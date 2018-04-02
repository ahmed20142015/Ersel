package ersel.greatbit.net.ersel.firebase;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import ersel.greatbit.net.ersel.http.HttpService;
import ersel.greatbit.net.ersel.http.IHttpService;
import ersel.greatbit.net.ersel.models.BaseResponse;
import ersel.greatbit.net.ersel.utilities.ConnectionDetector;
import ersel.greatbit.net.ersel.utilities.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Eslam on 3/19/2018.
 */

public class FCMRegistrationService  extends IntentService {

    private IHttpService iHttpService;
    SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
    public FCMRegistrationService() {
        super("FCM");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        iHttpService = HttpService.createService(IHttpService.class, SharedPrefManager.getInstance(this).getToken());
        // get token from Firebase
        String token = FirebaseInstanceId.getInstance().getToken();

        Log.e("Token is ",token);

        // check if intent is null or not if it isn't null we will ger refreshed value and
        // if its true we will override token_sent value to false and apply
        if (intent.getExtras() != null) {
            boolean refreshed = intent.getExtras().getBoolean("refreshed");
            if (refreshed)
                sharedPrefManager.setSendToken(false);
        }

        // if token_sent value is false then use method sendTokenToServer to send token to server
        if (!sharedPrefManager.getSendToken() && sharedPrefManager.isLogin())
            if(ConnectionDetector.getInstance(this).isConnectingToInternet())
            sendTokenToServer(token);
        else
                Toast.makeText(this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
    }


    // method use volley to send token to server and stop the service when done or error happened
    public void sendTokenToServer(final String token) {
        Call<BaseResponse> call = iHttpService.sendTokenToServer(token);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful() && response.body().getStatusCode().equalsIgnoreCase("100")){
                    sharedPrefManager.setSendToken(true);
                    Log.e("Registration Service", "Response : Send Token Success");
                    stopSelf();
                }
                else {
                    sharedPrefManager.setSendToken(false);
                    Log.e("Registration Service", "Response : Send Token error");
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                sharedPrefManager.setSendToken(false);
                Log.e("Registration Service", "Error :Send Token Failed");
                stopSelf();
            }
        });

    }

}
