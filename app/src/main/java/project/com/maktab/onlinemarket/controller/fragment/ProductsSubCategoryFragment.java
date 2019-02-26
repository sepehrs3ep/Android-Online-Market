package project.com.maktab.onlinemarket.controller.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.controller.activity.ProductInfoActivity;
import project.com.maktab.onlinemarket.model.product.Product;
import project.com.maktab.onlinemarket.network.Api;
import project.com.maktab.onlinemarket.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsSubCategoryFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private ProgressDialog mProgressDialog;

    private static final String CATEGORY_ID_ARGS = "categoryIdArgs";
    private long mCategoryId;

    public static ProductsSubCategoryFragment newInstance(long categoryId) {

        Bundle args = new Bundle();
        args.putLong(CATEGORY_ID_ARGS,categoryId);
        ProductsSubCategoryFragment fragment = new ProductsSubCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryId = getArguments().getLong(CATEGORY_ID_ARGS);
    }

    public ProductsSubCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products_sub_category, container, false);
        mRecyclerView = view.findViewById(R.id.products_sub_category_recycler);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("getting products");
        mProgressDialog.show();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getProductsSubCategoires(String.valueOf(mCategoryId))
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if(response.isSuccessful()){
                            List<Product> products = response.body();
                            if(products==null||products.size()<=0){
                                Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), R.string.no_item, Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                            mAdapter = new RecyclerAdapter(products);
                            mRecyclerView.setAdapter(mAdapter);
                            mProgressDialog.cancel();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Toast.makeText(getActivity(), R.string.problem_response, Toast.LENGTH_SHORT).show();

                    }
                });




        return view;
    }
    private class RecyclerViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        private TextView mNameTextView;
        private TextView mPriceTextView;
        private Product mProduct;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.product_sub_category_image_view_item);
            mNameTextView = itemView.findViewById(R.id.product_sub_category_name_text_view);
            mPriceTextView  = itemView.findViewById(R.id.product_sub_category_price_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = ProductInfoActivity.newIntent(getActivity(),mProduct.getId());
                    startActivity(intent);
                }
            });
        }
        public void bind(Product product){
            mProduct = product;
            mNameTextView.setText(product.getName());
            mPriceTextView.setText(product.getPrice() + " $ ");
            if(product.getImages()!=null&&product.getImages().size()>0){
                Picasso.get().load(product.getImages().get(0).getPath())
                        .placeholder(R.drawable.shop)
                        .into(mImageView);
            }

        }
    }
    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{
        private List<Product> mProductList;

        public RecyclerAdapter(List<Product> productList) {
            mProductList = productList;
        }

        public void setProductList(List<Product> productList) {
            mProductList = productList;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.product_sub_category_list_item,viewGroup,false);

            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
            Product product = mProductList.get(i);
            recyclerViewHolder.bind(product);
        }

        @Override
        public int getItemCount() {
            return mProductList.size();
        }
    }

}
