package project.com.maktab.onlinemarket.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import project.com.maktab.onlinemarket.R;

public class CompleteProductListActivity extends AppCompatActivity {
    private static final String ORDER_BY_EXTRA = "project.com.maktab.onlinemarket.controllerOrderByExtra";
    private String mOrderBy;

    public static Intent newIntent(Context context,String orderBy){
        Intent intent = new Intent(context,CompleteProductListActivity.class);
        intent.putExtra(ORDER_BY_EXTRA,orderBy);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        mOrderBy = getIntent().getStringExtra(ORDER_BY_EXTRA);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container,CompleteProuductListFragment.newInstance(mOrderBy))
                .commit();
    }
}
