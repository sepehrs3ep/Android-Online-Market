package project.com.maktab.onlinemarket.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import project.com.maktab.onlinemarket.R;

public class ProductInfoActivity extends SingleFragmentActivity {
    private static final String PRODUCT_ID_EXTRA = "productIdExtra";
    private String mProductId;


    public static Intent newIntent(Context context, String productId){

        Intent intent = new Intent(context,ProductInfoActivity.class);
        intent.putExtra(PRODUCT_ID_EXTRA,productId);
        return intent;
    }


    @Override
    public Fragment createFragment() {
        return ProductInfoFragment.newInstance(mProductId);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_single_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
         mProductId = getIntent().getStringExtra(PRODUCT_ID_EXTRA);

    /*     getSupportFragmentManager().beginTransaction()
                 .replace(R.id.info_fragmnet_container,ProductInfoFragment.newInstance(mProductId))
                 .commit();
*/

    }
}
