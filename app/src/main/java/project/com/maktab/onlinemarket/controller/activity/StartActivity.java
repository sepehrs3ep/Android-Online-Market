package project.com.maktab.onlinemarket.controller.activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.controller.fragment.StartFragment;

public class StartActivity extends SingleFragmentActivity {
    public static Intent newIntent(Context context){
        return new Intent(context,StartActivity.class);
    }


    @Override
    public Fragment createFragment() {
        return StartFragment.newInstance();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_single_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
