package ersel.greatbit.net.ersel.utilities;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Eslam on 3/12/2018.
 */

public class LocationManager {
    private static LocationManager locationManager;
    private static Context mContext;

    private LocationManager (Context context){
        mContext=context;

    }
    public static synchronized LocationManager getInstance(Context context){
        if(locationManager == null){
            locationManager = new LocationManager(context);
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

        } else {
          //  new GPSTracker(mContext).showSettingsAlert();
        }
    }

    private void sendLocationToServer(){

    }

}
