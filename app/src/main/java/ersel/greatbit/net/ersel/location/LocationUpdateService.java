package ersel.greatbit.net.ersel.location;


import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * Created by Eslam on 3/11/2018.
 */

public class LocationUpdateService extends Service {
    private int DELAY = 2000;
    Handler handler = new Handler();
    Runnable locationRunnable;

    public LocationUpdateService() { }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Log.w("locationservice","service started");

        handler.postDelayed(sendLocation(), 10000);

        return START_STICKY;
    }

    private Runnable sendLocation() {
        Runnable r = new Runnable() {
            @Override public void run() {
                final LocationManager manager = LocationManager.getInstance(LocationUpdateService.this);
                manager.findMyLocation();
                handler.postDelayed(this, 10000);
            }
        };
        locationRunnable = r;
        return r;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(locationRunnable);
        this.stopSelf();
    }
}