package ersel.greatbit.net.ersel.utilities;

/**
 * Created by Eslam on 3/29/2018.
 */


import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import ersel.greatbit.net.ersel.location.LocationManager;
import ersel.greatbit.net.ersel.location.LocationUpdateService;

//Require API Level 21
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {

    public MyJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        LocationManager manager = LocationManager.getInstance(MyJobService.this);
        manager.findMyLocation();
        Toast.makeText(this,
                "My Job Service Started",
                Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Toast.makeText(this,
                "MyJobService.onStopJob()",
                Toast.LENGTH_SHORT).show();
        return false;
    }

}