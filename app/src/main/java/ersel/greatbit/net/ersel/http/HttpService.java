package ersel.greatbit.net.ersel.http;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import ersel.greatbit.net.ersel.http.interceptors.TokenInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Eslam on 3/8/2018.
 */

public class HttpService {
    private static final String API_BASE_URL = "http://service.ersel.net:5000/";
    private static Retrofit.Builder builder = null;

    static {
        if (builder == null) {
            builder = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create()));
        }
    }

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass, final String token) {
        return createService(serviceClass,token,"");
    }

    public static <S> S createService(Class<S> serviceClass, final String token,String param) {
        OkHttpClient.Builder httpBuilder = new OkHttpClient().newBuilder();

        httpBuilder.readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS);



        httpBuilder.addInterceptor(new TokenInterceptor(token));

        //TODO: remove this before production
        //httpBuilder.addNetworkInterceptor(new StethoInterceptor());

        OkHttpClient client = httpBuilder.build();

        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}