package ersel.greatbit.net.ersel.activities;

import android.Manifest;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.firebase.FirebaseApp;

import ersel.greatbit.net.ersel.R;
import ersel.greatbit.net.ersel.fragments.LoginFragment;
import ersel.greatbit.net.ersel.fragments.OrdersFragment;
import ersel.greatbit.net.ersel.location.LocationUpdateService;
import ersel.greatbit.net.ersel.utilities.MyJobService;
import ersel.greatbit.net.ersel.utilities.SharedPrefManager;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;

    private final static int REQUEST_CHECK_SETTINGS = 2000;
    private final static int PLAY_SERVICES_REQUEST = 1000;


    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
 //        Log.w("token",SharedPrefManager.getInstance(this).getToken());

        // Request location Permission
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                1);


        if (checkPlayServices()){
            buildGoogleApiClient();
        }

        if (SharedPrefManager.getInstance(this).isLogin()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.mainContent, OrdersFragment.newInstance("", ""), "OrdersFragment")
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.mainContent, LoginFragment.newInstance("", ""), "LoginFragment")
                    .commit();
        }


//      //   Init location service
//        final Intent intent = new Intent(this, LocationUpdateService.class);
//        PendingIntent pintent = PendingIntent
//                .getService(this, 0, intent, 0);
//
//           alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        // Start service every hour
//        alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
//                10000, pintent);



//
//        ComponentName componentName = new ComponentName(this, MyJobService.class);
//        JobInfo jobInfo =
//                new JobInfo.Builder(1, componentName).setPeriodic(5000).build();
//    /*
//     * setPeriodic(long intervalMillis)
//     * Specify that this job should recur with the provided interval,
//     * not more than once per period.
//     */
//        JobScheduler jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
//        int jobId = jobScheduler.schedule(jobInfo);
//        if(jobScheduler.schedule(jobInfo)>0){
//            Toast.makeText(MainActivity.this,
//                    "Successfully scheduled job: " + jobId,
//                    Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(MainActivity.this,
//                    "RESULT_FAILURE: " + jobId,
//                    Toast.LENGTH_SHORT).show();
//        }

//        final Handler handler = new Handler();
//         Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//
//                startService(intent);
//                handler.postDelayed(this, 5000);
//            }
//        };
//        handler.postDelayed(runnable, 5000);




        // register broadcast
//        BootCompletedBroadCast bootCompletedBroadCast = new BootCompletedBroadCast();
//        IntentFilter filter = new IntentFilter("android.intent.action.BOOT_COMPLETED");
//        this.registerReceiver(bootCompletedBroadCast, filter);

    }

    // Permission check functions




    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        mGoogleApiClient.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {

                final Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location requests here
                        //   getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });


    }
    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this,resultCode,
                        PLAY_SERVICES_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
