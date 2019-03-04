package project.com.maktab.onlinemarket.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.core.app.NotificationManagerCompat;
import project.com.maktab.onlinemarket.eventbus.NotificationMassageEvent;

public class NotificationEventBusService extends Service {
    public NotificationEventBusService() {
    }

    public static Intent newIntent(Context context){
        return new Intent(context,NotificationEventBusService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void showNotification(NotificationMassageEvent massageEvent){
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(massageEvent.getReqCode(), massageEvent.getNotification());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
