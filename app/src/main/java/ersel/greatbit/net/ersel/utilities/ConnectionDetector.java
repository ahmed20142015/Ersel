package ersel.greatbit.net.ersel.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class ConnectionDetector {


    private Context _context;
    private static ConnectionDetector detector;

    public ConnectionDetector(Context context)

    {
        this._context = context;
    }

    public static synchronized ConnectionDetector getInstance(Context context) {
        if (detector == null) {

            detector = new ConnectionDetector(context);
        }

        return detector;
    }

    public boolean isConnectingToInternet() {

        ConnectivityManager cm = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }


    }


}

