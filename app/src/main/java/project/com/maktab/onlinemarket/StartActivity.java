package project.com.maktab.onlinemarket;

import android.support.v4.app.Fragment;
import android.os.Bundle;

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
