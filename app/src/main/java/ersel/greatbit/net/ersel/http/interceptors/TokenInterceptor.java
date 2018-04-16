package ersel.greatbit.net.ersel.http.interceptors;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.io.IOException;
import java.util.Map;

import ersel.greatbit.net.ersel.R;
import ersel.greatbit.net.ersel.activities.MainActivity;
import ersel.greatbit.net.ersel.fragments.LoginFragment;
import ersel.greatbit.net.ersel.fragments.OrderDetailsFragment;
import ersel.greatbit.net.ersel.location.MyService;
import ersel.greatbit.net.ersel.utilities.CloseApp;
import ersel.greatbit.net.ersel.utilities.MyApp;
import ersel.greatbit.net.ersel.utilities.SharedPrefManager;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Eslam on 3/8/2018.
 */

public class TokenInterceptor implements Interceptor {
    private String token;

    public TokenInterceptor(String token) {
        this.token = token;

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        okhttp3.Request originalRequest = chain.request();
        okhttp3.Request.Builder newBuilder = originalRequest.newBuilder();

        if (token != null) {
            newBuilder.header("auth", token);
        }


        newBuilder.method(originalRequest.method(), originalRequest.body());

        Response response = chain.proceed(newBuilder.build());

        if (response.code() == 401) {
            Log.d("Http","401 returned with token: " + token + " when going to url: "
                    + originalRequest.url().toString() + " Headers:" + originalRequest.headers().toString());
            //the user token is unauthorized, we should reset the token and let the user re-verify
            //we should logout the user and show login fragment
            SharedPrefManager.getInstance(MyApp.getAppContext()).setToken(null);
            final Intent intent = new Intent(MyApp.getAppContext(), MyService.class);
            MyApp.getAppContext().stopService(intent);
            MyApp.getAppContext().sendBroadcast(new Intent(MyApp.getAppContext(), CloseApp.class));

         }

        return response;
    }
}
