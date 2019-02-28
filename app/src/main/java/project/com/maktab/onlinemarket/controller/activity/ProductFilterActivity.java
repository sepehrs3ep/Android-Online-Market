/*
package project.com.maktab.onlinemarket.controller.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.controller.fragment.ProductFilterFragment;

public class ProductFilterActivity extends SingleFragmentActivity {
    private static final String PRODUCT_ID_EXTRA = "project.com.maktab.onlinemarket.controller.activity.PRODUCT_ID_EXTRA";

    public static Intent newIntent(Context context, String productId) {
        Intent intent = new Intent(context, ProductFilterActivity.class);
        intent.putExtra(PRODUCT_ID_EXTRA, productId);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return ProductFilterFragment.newInstance(getIntent().getStringExtra(PRODUCT_ID_EXTRA));
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_single_fragment;
    }

}
*/
