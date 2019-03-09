package project.com.maktab.onlinemarket.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.controller.fragment.SubmitInfoFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class SubmitInfoActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context){
        return new Intent(context,SubmitInfoActivity.class);
    }

    @Override
    public Fragment createFragment() {
        return SubmitInfoFragment.newInstance();
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
