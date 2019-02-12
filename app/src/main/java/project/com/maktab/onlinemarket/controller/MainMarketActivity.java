package project.com.maktab.onlinemarket.controller;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import project.com.maktab.onlinemarket.R;

public class MainMarketActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return MainMarketFragment.newInstance();
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
