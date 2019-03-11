package project.com.maktab.onlinemarket.controller.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.SearchView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.controller.activity.CompleteProductListActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchProductsDialogFragment extends VisibleDialogFragment {
    private SearchView mSearchView;


    public static SearchProductsDialogFragment newInstance() {

        Bundle args = new Bundle();

        SearchProductsDialogFragment fragment = new SearchProductsDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SearchProductsDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_products_dialog, container, false);
        mSearchView = view.findViewById(R.id.search_fragment_search_view);

        mSearchView.setFocusable(true);
        mSearchView.setIconified(false);
        mSearchView.requestFocusFromTouch();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = CompleteProductListActivity.newIntent(getActivity(),"nothing",-1,s,true,false,false);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return view;
    }

}
