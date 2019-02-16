package project.com.maktab.onlinemarket.controller;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import project.com.maktab.onlinemarket.R;

public class CategoryViewPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,AppCompatActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view_pager);
        mViewPager = findViewById(R.id.category_view_pager);
        mTabLayout = findViewById(R.id.category_tab_layout);

        mTabLayout.setupWithViewPager(mViewPager);

    }
}
