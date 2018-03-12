package ersel.greatbit.net.ersel.utilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Eslam on 3/12/2018.
 */

public class BootCompletedBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, LocationUpdateService.class);
        PendingIntent pintent = PendingIntent
                .getService(context, 0, i, 0);

        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        // Start service every hour
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                36*10, pintent);
    }
}
