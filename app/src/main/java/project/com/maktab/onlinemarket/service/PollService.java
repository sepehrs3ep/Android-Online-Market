package project.com.maktab.onlinemarket.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;

import java.util.concurrent.TimeUnit;

import project.com.maktab.onlinemarket.utils.Services;


public class PollService extends IntentService {
    private static final String TAG = "PollService";
    private static final long TIME_INTERVAL = TimeUnit.MINUTES.toMillis(1);

    public static Intent newIntent(Context context) {
        return new Intent(context, PollService.class);
    }

    public PollService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (!isOnline())
            return;

        Services.pollServerAndShowNotification(this);

    }

    public static void setServiceAlarm(Context context) {
        Intent i = newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
//        if(!isAlarmOn(context))
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), TIME_INTERVAL, pi);


    }



    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
