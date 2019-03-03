package project.com.maktab.onlinemarket.eventbus;

import android.app.Notification;

public class NotificationMassageEvent {
private Notification mNotification;
private int mReqCode;

    public NotificationMassageEvent(Notification notification, int reqCode) {
        mNotification = notification;
        mReqCode = reqCode;
    }

    public Notification getNotification() {
        return mNotification;
    }

    public int getReqCode() {
        return mReqCode;
    }
}
