package project.com.maktab.onlinemarket.model.category;

import java.util.ArrayList;
import java.util.List;

public class CategoryLab {
    private static final long DIGITAL_PRODUCT = 30;
    private static final long FASION_CLOTHES = 34;
    private static final long NO_CATEGORY = 0;
    private static final long SPORT = 37;


    private static CategoryLab mCategoryInstance;
    private List<Category> mAllCategories;

    private List<Category> mParentCategories;

    private CategoryLab() {
        mAllCategories = new ArrayList<>();
        mParentCategories = new ArrayList<>();
    }

    public void setAllCategories(List<Category> allCategories) {
        mAllCategories.clear();
        mParentCategories.clear();
        mAllCategories = allCategories;
        generateParentList();
    }

    public int getCurrentCategory(long id){
        for(int i=0;i<mParentCategories.size();i++){
            if(mParentCategories.get(i).getId()==id)
                return i;
        }
        return -1;
    }

    private void generateParentList(){
        for(Category category:mAllCategories){
            if(category.getParent()==0)
                mParentCategories.add(category);
        }
    }


    public List<Category> getAllCategories() {
        return mAllCategories;
    }

    public List<Category> getParentCategories() {
        return mParentCategories;
    }


    public static CategoryLab getmCategoryInstance() {
        if(mCategoryInstance==null)
            mCategoryInstance = new CategoryLab();

        return mCategoryInstance;
    }
}
