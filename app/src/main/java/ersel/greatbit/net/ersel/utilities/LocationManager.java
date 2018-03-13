package ersel.greatbit.net.ersel.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import ersel.greatbit.net.ersel.http.HttpService;
import ersel.greatbit.net.ersel.http.IHttpService;
import ersel.greatbit.net.ersel.models.BaseResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Eslam on 3/12/2018.
 */

public class LocationManager {
    private static LocationManager locationManager;
    private static Context mContext;
    private static IHttpService iHttpService;

    private LocationManager (Context context){
        mContext=context;

    }
    public static synchronized LocationManager getInstance(Context context){
        if(locationManager == null){
            locationManager = new LocationManager(context);
            iHttpService = HttpService.createService(IHttpService.class,SharedPrefManager.getInstance(context).getToken());
        }
        return locationManager;
    }
    public void findMyLocation() {
        android.location.LocationManager locationManager = (android.location.LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);

        boolean gps_enabled = false;
        boolean network_enabled = false;


        try {
            gps_enabled = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
            network_enabled = locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (gps_enabled || network_enabled == true) {
            LatLng myLocation = new LatLng(
                    new GPSTracker(mContext).getLatitude(), new GPSTracker(mContext).getLongitude());
            Log.w("locationservice",myLocation.toString());

            //Send Location to server
             sendLocationToServer(myLocation.latitude,myLocation.longitude);
        } else {
          //  new GPSTracker(mContext).showSettingsAlert();
        }
    }

    private void sendLocationToServer(double lat , double lng){
        Call<BaseResponse> call = iHttpService.updateLocation(lat,lng);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    Log.w("locationservice",response.body().getStatus());
                }
                else
                    Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(mContext, "Faill", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
