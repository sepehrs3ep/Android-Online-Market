package project.com.maktab.onlinemarket.controller.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import project.com.maktab.onlinemarket.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFilterFragment extends Fragment {
    @BindView(R.id.attribute_recycler_view)
    RecyclerView mAttributeRecyclerView;
    @BindView(R.id.attribute_term_recycler_view)
    RecyclerView mTermAttrRecyclerView;
    @BindView(R.id.do_filter_btn)
    Button mDoFilterBtn;

    private static final String PRODUCT_ID_ARGS = "PRODUCT_ID_ARGS";
    private String mProductId;


    public static ProductFilterFragment newInstance(String productId) {

        Bundle args = new Bundle();
        args.putString(PRODUCT_ID_ARGS,productId);
        ProductFilterFragment fragment = new ProductFilterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductId = getArguments().getString(PRODUCT_ID_ARGS);
    }

    public ProductFilterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_filter, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

}
