package project.com.maktab.onlinemarket.controller.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.controller.fragment.CompleteProductListFragment;

public class CompleteProductListActivity extends SingleFragmentActivity {
    private static final String ORDER_BY_EXTRA = "project.com.maktab.onlinemarket.controllerOrderByExtra";
    private static final String CATEGORY_ID_EXTRA = "CATEGORY_ID_EXTRA";
    private static final String IS_FROM_CATEGORY_EXTRA = "IS_FROM_CATEGORY_EXTRA";
    private static final String SEARCH_STRING_EXTRA = "SEARCH_STRING_EXTRA";
    private static final String IS_FROM_SEARCH = "IS_FROM_SEARCH";


    public static Intent newIntent(Context context, String orderBy, long categoryId,String searchedString , boolean isSearch, boolean isSubCategory) {
        Intent intent = new Intent(context, CompleteProductListActivity.class);
        intent.putExtra(ORDER_BY_EXTRA, orderBy);
        intent.putExtra(CATEGORY_ID_EXTRA, categoryId);
        intent.putExtra(IS_FROM_CATEGORY_EXTRA, isSubCategory);
        intent.putExtra(SEARCH_STRING_EXTRA,searchedString);
        intent.putExtra(IS_FROM_SEARCH,isSearch);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return CompleteProductListFragment.newInstance(
                getIntent().getStringExtra(ORDER_BY_EXTRA),
                getIntent().getLongExtra(CATEGORY_ID_EXTRA, -1),
                getIntent().getStringExtra(SEARCH_STRING_EXTRA),
                getIntent().getBooleanExtra(IS_FROM_SEARCH,false),
                getIntent().getBooleanExtra(IS_FROM_CATEGORY_EXTRA, false)
        );
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_single_fragment;
    }

}
