package project.com.maktab.onlinemarket.controller.fragment;


import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import project.com.maktab.onlinemarket.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopBagDialogFragment extends DialogFragment {
    @BindView(R.id.shop_final_sum_text_view)
    private TextView mShopFinalSumTextView;
    @BindView(R.id.submit_shop_bag_btn)
    private MaterialButton mSubmitShopBagBtn;
    @BindView(R.id.shop_bag_recycler_view)
    private RecyclerView mShopBagRecyclerView;

    public static ShopBagDialogFragment newInstance() {

        Bundle args = new Bundle();

        ShopBagDialogFragment fragment = new ShopBagDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ShopBagDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_shop_bag_dialog, container, false);
        ButterKnife.bind(getActivity());



        return view;
    }

}
