package project.com.maktab.onlinemarket.controller;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.com.maktab.onlinemarket.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubCategoryRecyclerFragment extends Fragment {
    private static final String CATEGORY_ID_ARGS = "categoryIdArgs";
    private String mCategoryId;

    public static SubCategoryRecyclerFragment newInstance(String categoryId) {

        Bundle args = new Bundle();
        args.putString(CATEGORY_ID_ARGS,categoryId);
        SubCategoryRecyclerFragment fragment = new SubCategoryRecyclerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryId = getArguments().getString(CATEGORY_ID_ARGS);
    }

    public SubCategoryRecyclerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub_category_recycler, container, false);
    }

}
