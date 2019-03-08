package project.com.maktab.onlinemarket.service;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import androidx.annotation.RequiresApi;
import project.com.maktab.onlinemarket.utils.Services;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PollJobService extends JobService {
    private static final int JOB_ID = 45;
    private static final String NOTIF_TAG = "NOTIF_TAG";
    private PollAsyncTask mPollTask;


    public PollJobService() {

    }


    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(NOTIF_TAG,"come on start job");
        mPollTask = new PollAsyncTask();
        mPollTask.execute(params);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        if (mPollTask != null)
            mPollTask.cancel(true);


        return false;
    }

    public static void scheduleService(Context context, boolean isOn) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
        if (isOn) {
            JobInfo.Builder jobBuilder = new JobInfo.Builder(JOB_ID, new ComponentName(context, PollJobService.class))
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                    .setPersisted(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                jobBuilder.setMinimumLatency(TimeUnit.MINUTES.toMillis(1));
            } else {
                jobBuilder.setPeriodic(TimeUnit.MINUTES.toMillis(15));
            }

            JobInfo jobInfo = jobBuilder.build();
            jobScheduler.schedule(jobInfo);
        } else {
            jobScheduler.cancel(JOB_ID);
        }
    }

    public static boolean isScheduled(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
        for (JobInfo jobInfo : jobScheduler.getAllPendingJobs()) {
            if (jobInfo.getId() == JOB_ID) {
                return true;
            }
        }
        return false;
    }


    private class PollAsyncTask extends AsyncTask<JobParameters, Void, Void> {

        @Override
        protected Void doInBackground(JobParameters... jobParameters) {
            Log.d(NOTIF_TAG,"come on do in background task");
            Services.pollServerAndShowNotification(PollJobService.this);

            JobParameters parameters = jobParameters[0];


            jobFinished(parameters, false);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                scheduleService(PollJobService.this, true);
            }


            return null;
        }
    }
}
