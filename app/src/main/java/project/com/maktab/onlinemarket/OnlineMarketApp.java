package project.com.maktab.onlinemarket;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import org.greenrobot.greendao.database.Database;

import project.com.maktab.onlinemarket.database.DaoMaster;
import project.com.maktab.onlinemarket.database.DaoSession;
import project.com.maktab.onlinemarket.database.DevOpenHelper;

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

        DevOpenHelper devOpenHelper = new DevOpenHelper(this,DB_NAME);

        Database database =devOpenHelper.getWritableDb();
        mDaoSession = new DaoMaster(database).newSession();

        mInstance = this;
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
