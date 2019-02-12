package project.com.maktab.onlinemarket.controller;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import project.com.maktab.onlinemarket.R;

public class StartActivity extends SingleFragmentActivity {

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
