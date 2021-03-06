package ersel.greatbit.net.ersel.location;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import ersel.greatbit.net.ersel.http.HttpService;
import ersel.greatbit.net.ersel.http.IHttpService;
import ersel.greatbit.net.ersel.models.BaseResponse;
import ersel.greatbit.net.ersel.utilities.ConnectionDetector;
import ersel.greatbit.net.ersel.utilities.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyService extends Service
{

    private static final String TAG = "BOOMBOOMTESTGPS";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 10000;
    private static final float LOCATION_DISTANCE = 20f;
    private static IHttpService iHttpService;

    private class LocationListener implements android.location.LocationListener
    {
        Location mLastLocation;

        public LocationListener(String provider)
        {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location)
        {
            if (SharedPrefManager.getInstance(MyService.this).getLastLat().equalsIgnoreCase("")){
                SharedPrefManager.getInstance(MyService.this).setLastLat(location.getLatitude()+"");
                SharedPrefManager.getInstance(MyService.this).setLastLong(location.getLongitude()+"");
            }

            if (compareDistance(location)>20f){


                if (mLocationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)&&
                        mLocationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)){
                    if(ConnectionDetector.getInstance(MyService.this).isConnectingToInternet())
                        sendLocationToServer(location.getLatitude(),location.getLongitude());
                    else
                        Toast.makeText(MyService.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();

                }

                //update location
                SharedPrefManager.getInstance(MyService.this).setLastLat(location.getLatitude()+"");
                SharedPrefManager.getInstance(MyService.this).setLastLong(location.getLongitude()+"");


            }








        }

        @Override
        public void onProviderDisabled(String provider)
        {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider)
        {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[] {
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate()
    {
        Log.e(TAG, "onCreate");
        initializeLocationManager();

        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy()
    {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    private void sendLocationToServer(double lat , double lng){
        iHttpService = HttpService.createService(IHttpService.class, SharedPrefManager.getInstance(this).getToken());
        Call<BaseResponse> call = iHttpService.updateLocation(lat,lng);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                   // Toast.makeText(MyService.this, "Location updated", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(MyService.this, "Error upload location", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
            }
        });

    }

    private float compareDistance(Location location){

        Location locationA = new Location("point A");

        locationA.setLatitude(Double.parseDouble(SharedPrefManager.getInstance(MyService.this).getLastLat()));
        locationA.setLongitude(Double.parseDouble(SharedPrefManager.getInstance(MyService.this).getLastLong()));

        return locationA.distanceTo(location);

    }

}