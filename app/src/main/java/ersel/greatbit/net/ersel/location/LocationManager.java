package ersel.greatbit.net.ersel.location;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import ersel.greatbit.net.ersel.http.HttpService;
import ersel.greatbit.net.ersel.http.IHttpService;
import ersel.greatbit.net.ersel.models.BaseResponse;
import ersel.greatbit.net.ersel.utilities.ConnectionDetector;
import ersel.greatbit.net.ersel.utilities.SharedPrefManager;
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

        }
        return locationManager;
    }
    private void findMyLocation() {
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

            if(ConnectionDetector.getInstance(mContext).isConnectingToInternet())
                sendLocationToServer(myLocation.latitude,myLocation.longitude);
            else
                Toast.makeText(mContext, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();

        } else {
          //  new GPSTracker(mContext).showSettingsAlert();
        }
    }

    private void sendLocationToServer(double lat , double lng){
        iHttpService = HttpService.createService(IHttpService.class, SharedPrefManager.getInstance(mContext).getToken());
        Call<BaseResponse> call = iHttpService.updateLocation(lat,lng);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                   Toast.makeText(mContext, "Location updated", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(mContext, "Error upload location", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(mContext, "Faill", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
