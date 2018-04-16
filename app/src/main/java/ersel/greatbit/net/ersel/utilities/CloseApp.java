package ersel.greatbit.net.ersel.utilities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ersel.greatbit.net.ersel.activities.MainActivity;

/**
 * Created by Eslam on 4/16/2018.
 */

public class CloseApp extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MainActivity.closeActivity();
        Intent start=new Intent(context,MainActivity.class);
        start.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(start);
    }
}
