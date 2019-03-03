package project.com.maktab.onlinemarket.controller.fragment;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.eventbus.NotificationMassageEvent;

/**
 * A simple {@link Fragment} subclass.
 */
public class VisibleDialogFragment extends DialogFragment {

    public VisibleDialogFragment() {
    }
    @Override
    public void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);


    }

    @Subscribe(priority = 1,threadMode = ThreadMode.POSTING)
    public void onMassageEvent(NotificationMassageEvent massageEvent) {

        EventBus.getDefault().cancelEventDelivery(massageEvent);

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
