package project.com.maktab.onlinemarket.controller;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import project.com.maktab.onlinemarket.R;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    public abstract Fragment createFragment();

    @LayoutRes
    public abstract int getLayoutResId();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, createFragment())
                .commit();


    }
}
