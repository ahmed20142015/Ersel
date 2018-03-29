package ersel.greatbit.net.ersel.utilities;

/**
 * Created by Eslam on 3/29/2018.
 */


import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

//Require API Level 21
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {

    public MyJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Toast.makeText(this,
                "MyJobService.onStartJob()",
                Toast.LENGTH_SHORT).show();
  /*
   * True - if your service needs to process
   * the work (on a separate thread).
   * False - if there's no more work to be done for this job.
   */
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