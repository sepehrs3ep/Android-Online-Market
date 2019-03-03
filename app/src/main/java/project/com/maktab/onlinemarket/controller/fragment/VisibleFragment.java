package project.com.maktab.onlinemarket.controller.fragment;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
public class VisibleFragment extends Fragment {
    private FragmentBroadCast mBroadCast;

    public VisibleFragment() {
        // Required empty public constructor
    }
    @Override
    public void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);

     /*   IntentFilter intentFilter = new IntentFilter(Services.ACTION_SEND_NOTIFICATION);
        getActivity().registerReceiver(mBroadCast,intentFilter,Services.NOTIF_PERM,null);*/
    }

    @Subscribe(priority = 1,threadMode = ThreadMode.POSTING)
    public void onMassageEvent(NotificationMassageEvent massageEvent) {

        EventBus.getDefault().cancelEventDelivery(massageEvent);

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
//        getActivity().unregisterReceiver(mBroadCast);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mBroadCast = new FragmentBroadCast();
    }


    private class FragmentBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, getString(R.string.notif_received), Toast.LENGTH_SHORT).show();
            setResultCode(Activity.RESULT_CANCELED);
        }
    }

}
