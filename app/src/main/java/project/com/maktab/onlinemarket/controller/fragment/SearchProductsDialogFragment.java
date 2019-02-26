package project.com.maktab.onlinemarket.controller.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
public class SearchProductsDialogFragment extends DialogFragment {
    private RecyclerView mRecyclerView;
    private SearchView mSearchView;
    private Toolbar mToolbar;
    private SearchAdapter mAdapter;
    private ProgressDialog mProgressDialog;


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
        mRecyclerView = view.findViewById(R.id.search_fragment_recycler_view);
        mSearchView = view.findViewById(R.id.search_fragment_search_view);
        mToolbar = view.findViewById(R.id.search_fragment_bar);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getString(R.string.progress_product));


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new SearchAdapter(new ArrayList<Product>());

        mRecyclerView.setAdapter(mAdapter);






        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                mProgressDialog.show();
                RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                        .searchProducts(s)
                        .enqueue(new Callback<List<Product>>() {
                            @Override
                            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                                if(response.isSuccessful()){
                                    List<Product> list = response.body();
                                    mAdapter.setProductList(list);
                                    mAdapter.notifyDataSetChanged();
                                    mProgressDialog.cancel();

                                }

                            }

                            @Override
                            public void onFailure(Call<List<Product>> call, Throwable t) {
                                Toast.makeText(getActivity(), R.string.problem_response, Toast.LENGTH_SHORT).show();
                            }
                        });



                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return view;
    }
    private class SearchViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        private TextView mNameTextView;
        private TextView mPriceTextView;
        private Product mProduct;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.product_sub_category_image_view_item);
            mNameTextView = itemView.findViewById(R.id.product_sub_category_name_text_view);
            mPriceTextView  = itemView.findViewById(R.id.product_sub_category_price_text_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = ProductInfoActivity.newIntent(getActivity(),mProduct.getId());
                    dismiss();
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
    private class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder>{
        private List<Product> mProductList;

        public SearchAdapter(List<Product> productList) {
            mProductList = productList;
        }

        public void setProductList(List<Product> productList) {
            mProductList = productList;
        }


        @NonNull
        @Override
        public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.product_sub_category_list_item,viewGroup,false);
            return new SearchViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {
            Product product = mProductList.get(i);
            searchViewHolder.bind(product);
        }

        @Override
        public int getItemCount() {
            return mProductList.size();
        }
    }


}
