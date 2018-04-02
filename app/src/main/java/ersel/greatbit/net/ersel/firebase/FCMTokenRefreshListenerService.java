package ersel.greatbit.net.ersel.firebase;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Eslam on 3/19/2018.
 */

public class FCMTokenRefreshListenerService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {

        Intent intent = new Intent(this, FCMRegistrationService.class);
        intent.putExtra("refreshed", true);
        startService(intent);
    }
}