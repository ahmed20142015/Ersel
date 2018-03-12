package ersel.greatbit.net.ersel.utilities;


import android.app.Service;

import android.content.Intent;

import android.os.IBinder;

import android.support.annotation.Nullable;
import android.util.Log;


/**
 * Created by Eslam on 3/11/2018.
 */

public class LocationUpdateService extends Service {
    public LocationUpdateService() { }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w("locationservice","service started");
        LocationManager manager = LocationManager.getInstance(LocationUpdateService.this);
        manager.findMyLocation();
        return START_NOT_STICKY;
    }
}