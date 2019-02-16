package project.com.maktab.onlinemarket.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ProductsSubCategoryActivity extends AppCompatActivity {
    private static final String CATEGORY_ID_EXTRA = "project.com.maktab.onlinemarket.controller.categoryId";
    private String mCategoryId;

    public static Intent newIntent(Context context,String categoryId){
        Intent intent = new Intent(context, ProductsSubCategoryActivity.class);
        intent.putExtra(CATEGORY_ID_EXTRA,categoryId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
