package project.com.maktab.onlinemarket.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.controller.fragment.SubCategoryRecyclerFragment;
import project.com.maktab.onlinemarket.model.category.Category;
import project.com.maktab.onlinemarket.model.category.CategoryLab;

public class CategoryViewPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private CategoryViewPagerAdapter mPagerAdapter;
    private long mCurrentCategory;

    private static final String CURRENT_EXTRA = "project.com.maktab.onlinemarket.controller.isCurrent";

    public static Intent newIntent(Context context,long current){
        Intent intent = new Intent(context,CategoryViewPagerActivity.class);
        intent.putExtra(CURRENT_EXTRA,current);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view_pager);
        mViewPager = findViewById(R.id.category_view_pager);
        mTabLayout = findViewById(R.id.category_tab_layout);
        mCurrentCategory = getIntent().getLongExtra(CURRENT_EXTRA,-2);

        mTabLayout.setupWithViewPager(mViewPager);

        mPagerAdapter = new CategoryViewPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.setParentList(CategoryLab.getmCategoryInstance().getParentCategories());
        mViewPager.setAdapter(mPagerAdapter);



        if(mCurrentCategory>0){
            int index = CategoryLab.getmCategoryInstance().getCurrentCategory(mCurrentCategory);
            if(index>0)
                mViewPager.setCurrentItem(index);
        }





    }

    private class CategoryViewPagerAdapter extends FragmentStatePagerAdapter {
        private List<Category> mParentList;

        public void setParentList(List<Category> parentList) {
            mParentList = parentList;
        }

        public CategoryViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return SubCategoryRecyclerFragment.newInstance(mParentList.get(i).getId());
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mParentList.get(position).getName();
        }

        @Override
        public int getCount() {
            return mParentList.size();
        }
    }

}
