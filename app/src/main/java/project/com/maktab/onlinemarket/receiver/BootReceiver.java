package project.com.maktab.onlinemarket.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import project.com.maktab.onlinemarket.service.PollService;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            PollService.setServiceAlarm(context);
        }

    }
}
