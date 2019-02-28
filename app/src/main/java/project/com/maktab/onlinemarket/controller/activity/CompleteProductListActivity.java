package project.com.maktab.onlinemarket.controller.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.controller.fragment.CompleteProuductListFragment;

public class CompleteProductListActivity extends SingleFragmentActivity {
    private static final String ORDER_BY_EXTRA = "project.com.maktab.onlinemarket.controllerOrderByExtra";
    private static final String CATEGORY_ID_EXTRA = "CATEGORY_ID_EXTRA";
    private static final String IS_FROM_CATEGORY_EXTRA = "IS_FROM_CATEGORY_EXTRA";


    public static Intent newIntent(Context context,String orderBy,long categoryId,boolean isSubCategory){
        Intent intent = new Intent(context,CompleteProductListActivity.class);
        intent.putExtra(ORDER_BY_EXTRA,orderBy);
        intent.putExtra(CATEGORY_ID_EXTRA,categoryId);
        intent.putExtra(IS_FROM_CATEGORY_EXTRA,isSubCategory);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return CompleteProuductListFragment.newInstance(
                getIntent().getStringExtra(ORDER_BY_EXTRA),
                getIntent().getLongExtra(CATEGORY_ID_EXTRA,-1),
                getIntent().getBooleanExtra(IS_FROM_CATEGORY_EXTRA,false)
        );
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_single_fragment;
    }

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        mOrderBy = getIntent().getStringExtra(ORDER_BY_EXTRA);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CompleteProuductListFragment.newInstance(mOrderBy))
                .commit();
    }*/
}
