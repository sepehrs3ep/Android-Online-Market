package project.com.maktab.onlinemarket.controller.fragment;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.model.product.Attribute;
import project.com.maktab.onlinemarket.model.product.Product;
import project.com.maktab.onlinemarket.model.product.ProductLab;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductAttributeDialogFragment extends DialogFragment {

    private static final String PRODUCT_ID_ARGS = "productIdArgs";
    private Product mProduct;
    private RecyclerView mRecyclerView;
    private AttrAdapter mAdapter;
    private boolean mIsExpanded = false;


    public static ProductAttributeDialogFragment newInstance(String productId) {

        Bundle args = new Bundle();
        args.putString(PRODUCT_ID_ARGS,productId);
        ProductAttributeDialogFragment fragment = new ProductAttributeDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String productId = getArguments().getString(PRODUCT_ID_ARGS);
        mProduct = ProductLab.getInstance().getProductById(productId);
    }

    public ProductAttributeDialogFragment() {
        // Required empty public constructor
    }

 /*   @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }*/

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = RecyclerView.LayoutParams.MATCH_PARENT;
        params.height = RecyclerView.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_category_recycler,container,false);
        mRecyclerView = view.findViewById(R.id.sub_category_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new AttrAdapter(mProduct.getAttributes());
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    private class AttrViewHolder extends RecyclerView.ViewHolder{
        private ImageButton mImageButton;
        private TextView mAttrDescTextView;
        private TextView mAttrTitleTextView;
        private Attribute mAttribute;

        public AttrViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageButton = itemView.findViewById(R.id.expand_attr_image_btn);
            mAttrDescTextView = itemView.findViewById(R.id.expand_attr_text_view);
            mAttrTitleTextView = itemView.findViewById(R.id.attr_title_text_view);
            mAttrDescTextView.setVisibility(View.GONE);

            mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIsExpanded = !mIsExpanded;
                    if(mIsExpanded)
                        mAttrDescTextView.setVisibility(View.VISIBLE);
                    else mAttrDescTextView.setVisibility(View.GONE);


                }
            });

        }
        public void bind(Attribute attribute){
            mAttribute = attribute;
            mAttrTitleTextView.setText(attribute.getName());
            mAttrDescTextView.setText(mAttribute.getOptions().toString());
        }
    }
    private class AttrAdapter extends RecyclerView.Adapter<AttrViewHolder>{
        private List<Attribute> mAttributeList;

        public AttrAdapter(List<Attribute> attributeList) {
            mAttributeList = attributeList;
        }

        public void setAttributeList(List<Attribute> attributeList) {
            mAttributeList = attributeList;
        }

        @NonNull
        @Override
        public AttrViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.attribute_list_item,viewGroup,false);
            return new AttrViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AttrViewHolder attrViewHolder, int i) {
            Attribute attribute = mAttributeList.get(i);
            attrViewHolder.bind(attribute);
        }

        @Override
        public int getItemCount() {
            return mAttributeList.size();
        }
    }



}
