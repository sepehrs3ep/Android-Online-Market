package project.com.maktab.onlinemarket.model.category;

import java.util.ArrayList;
import java.util.List;

public class CategoryLab {
    private static CategoryLab mCategoryInstance;
    private List<Category> mAllCategories;
    private List<Category> mParentCategories;
    private List<Category> mSubCategories;

    private CategoryLab() {
        mAllCategories = new ArrayList<>();
        mParentCategories = new ArrayList<>();
        mSubCategories = new ArrayList<>();
    }

    public void setAllCategories(List<Category> allCategories) {
        mAllCategories = allCategories;
    }

    private void getParentList(){
        for(Category category:mAllCategories){
            if(category.getParent()==0)
                mParentCategories.add(category);
            else
                mSubCategories.add(category);
        }
    }

    public List<Category> getAllCategories() {
        return mAllCategories;
    }

    public List<Category> getParentCategories() {
        return mParentCategories;
    }

    public List<Category> getSubCategories() {
        return mSubCategories;
    }

    public static CategoryLab getmCategoryInstance() {
        if(mCategoryInstance==null)
            mCategoryInstance = new CategoryLab();

        return mCategoryInstance;
    }
}
