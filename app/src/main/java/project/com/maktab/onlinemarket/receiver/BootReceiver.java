package project.com.maktab.onlinemarket.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import project.com.maktab.onlinemarket.service.PollService;
import project.com.maktab.onlinemarket.utils.SharedPref;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            if(SharedPref.isAlarmOn()){
            PollService.setServiceAlarm(context,true,SharedPref.getTimeIntervals());
            }
        }

    }
}
