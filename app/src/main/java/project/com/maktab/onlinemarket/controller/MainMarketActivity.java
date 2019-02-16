package project.com.maktab.onlinemarket.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import project.com.maktab.onlinemarket.R;

public class MainMarketActivity extends SingleFragmentActivity {
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, MainMarketActivity.class);
        return intent;
    }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
