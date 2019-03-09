package project.com.maktab.onlinemarket.controller.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.com.maktab.onlinemarket.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditCustomerFragment extends Fragment {
    private static final String CUSTOMER_ID_ARGS = "CUSTOMER_ID_ARGS";
    private static final String FOR_EDIT_ARGS = "FOR_EDIT_ARGS";
    private long mCustomerId;
    private boolean mIsEdit;


    public static AddEditCustomerFragment newInstance(long customerId,boolean isEdit) {

        Bundle args = new Bundle();
        args.putLong(CUSTOMER_ID_ARGS,customerId);
        args.putBoolean(FOR_EDIT_ARGS,isEdit);
        AddEditCustomerFragment fragment = new AddEditCustomerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AddEditCustomerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomerId = getArguments().getLong(CUSTOMER_ID_ARGS);
        mIsEdit = getArguments().getBoolean(FOR_EDIT_ARGS,false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_edit_customer, container, false);

        return view;
    }



}
