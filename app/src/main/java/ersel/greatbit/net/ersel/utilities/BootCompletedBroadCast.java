package ersel.greatbit.net.ersel.utilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import ersel.greatbit.net.ersel.location.LocationUpdateService;

/**
 * Created by Eslam on 3/12/2018.
 */

public class BootCompletedBroadCast extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

        if(SharedPrefManager.getInstance(context).isLogin()) {
            final Intent i = new Intent(context, LocationUpdateService.class);
            Integer api = Integer.valueOf(Build.VERSION.SDK);
            if(api > 25)
                context.startForegroundService(i);
            else
                context.startService(i);
        }
    }
}
