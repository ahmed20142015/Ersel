package ersel.greatbit.net.ersel.http.interceptors;

import android.util.Log;

import java.io.IOException;
import java.util.Map;

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
        }

        return response;
    }
}
