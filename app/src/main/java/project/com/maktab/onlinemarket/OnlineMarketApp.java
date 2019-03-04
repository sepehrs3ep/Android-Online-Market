package project.com.maktab.onlinemarket;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.database.Database;

import androidx.core.app.NotificationManagerCompat;
import project.com.maktab.onlinemarket.database.DaoMaster;
import project.com.maktab.onlinemarket.database.DaoSession;
import project.com.maktab.onlinemarket.database.DevOpenHelper;
import project.com.maktab.onlinemarket.eventbus.NotificationMassageEvent;
import project.com.maktab.onlinemarket.service.PollService;
import project.com.maktab.onlinemarket.utils.Services;

public class OnlineMarketApp extends Application {

    private static OnlineMarketApp mInstance;
    private DaoSession mDaoSession;
    private static final String DB_NAME = "Market";

    public static OnlineMarketApp getAppInstance() {
        return mInstance;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public static String getDbName() {
        return DB_NAME;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createAppNotificationChanel();

        if(!PollService.isAlarmOn(this))
        PollService.setServiceAlarm(this);


        EventBus.getDefault().register(this);
        Log.d(Services.NOTIF_TAG,"started alarm manager");
//        PollService.setServiceAlarm(this);

        /*Intent intent  = AlarmService.newIntent(this);
        startService(intent);*/

        DevOpenHelper devOpenHelper = new DevOpenHelper(this,DB_NAME);

        Database database =devOpenHelper.getWritableDb();
        mDaoSession = new DaoMaster(database).newSession();

        mInstance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        EventBus.getDefault().unregister(this);
        Log.d(Services.NOTIF_TAG,"come on terminate");
//        Intent intent  = AlarmService.newIntent(this);
//        stopService(AlarmService.newIntent(this));

    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMassageEvent(NotificationMassageEvent massageEvent){
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(massageEvent.getReqCode(), massageEvent.getNotification());
    }
    private void createAppNotificationChanel() {
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(getString(R.string.channel_id), getString(R.string.channel_name), NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(getString(R.string.channel_desc));
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
}
