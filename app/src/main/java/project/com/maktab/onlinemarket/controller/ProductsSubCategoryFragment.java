package project.com.maktab.onlinemarket.controller;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.com.maktab.onlinemarket.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsSubCategoryFragment extends Fragment {
    private RecyclerView mRecyclerView;

    private static final String CATEGORY_ID_ARGS = "categoryIdArgs";
    private String mCategoryId;

    public static ProductsSubCategoryFragment newInstance(String categoryId) {

        Bundle args = new Bundle();
        args.putString(CATEGORY_ID_ARGS,categoryId);
        ProductsSubCategoryFragment fragment = new ProductsSubCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryId = getArguments().getString(CATEGORY_ID_ARGS);
    }

    public ProductsSubCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products_sub_category, container, false);

        return view;
    }

}
