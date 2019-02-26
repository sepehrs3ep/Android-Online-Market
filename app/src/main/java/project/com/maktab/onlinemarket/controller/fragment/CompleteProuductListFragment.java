package project.com.maktab.onlinemarket.controller.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.controller.activity.ProductInfoActivity;
import project.com.maktab.onlinemarket.model.product.Product;
import project.com.maktab.onlinemarket.network.Api;
import project.com.maktab.onlinemarket.network.RetrofitClientInstance;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompleteProuductListFragment extends Fragment {
    private static final String ORDER_BY_ARGS = "orderByArgs";
    private String mOrderType;
    private CompleteAdapter mAdapter;
    private static int mPageCounter = 1;
    private List<Product> mProductList;
    private static boolean NO_MORE_PAGE ;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;


    public static CompleteProuductListFragment newInstance(String orderBy) {

        Bundle args = new Bundle();
        args.putString(ORDER_BY_ARGS, orderBy);
        CompleteProuductListFragment fragment = new CompleteProuductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrderType = getArguments().getString(ORDER_BY_ARGS);
        mPageCounter = 1;
        NO_MORE_PAGE = false;
    }

    public CompleteProuductListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products_sub_category, container, false);
        mRecyclerView = view.findViewById(R.id.products_sub_category_recycler);
        mProgressBar = view.findViewById(R.id.sub_category_progress_bar);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setAdapter();
        new ProductsAsynceTask(getActivity()).execute(mPageCounter);


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    if(!NO_MORE_PAGE){
                    mPageCounter++;
                    new ProductsAsynceTask(getActivity()).execute(mPageCounter);

                    }
                }
            }
        });



        return view;

    }

    public void setAdapter() {
        if (isAdded()) {
            if (mAdapter == null) {
                mProductList = new ArrayList<>();
                mAdapter = new CompleteAdapter(mProductList);
                mRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.setProductList(mProductList);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private class ProductsAsynceTask extends AsyncTask<Integer, String, List<Product>> {
        private ProgressDialog mProgressDialog;
        Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), R.string.no_page, Snackbar.LENGTH_LONG);
        public ProductsAsynceTask(Context context) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(getString(R.string.progress_product));
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            super.onPostExecute(products);
            mProgressBar.setVisibility(View.GONE);
            if(products==null||products.size()<=0){
                snackbar.show();
                NO_MORE_PAGE = true;
                return;
            }

            mProductList.addAll(products);
            setAdapter();

        }

        @Override
        protected List<Product> doInBackground(Integer... integers) {
            int pageCounter = integers[0];
            Response<List<Product>> response = null;
            List<Product> productList = null;
            try {
                response = RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                        .getAllProductWithPage(String.valueOf(pageCounter), mOrderType).execute();
                if (response.isSuccessful()) {
                    productList = response.body();
                }
            } catch (IOException e) {
                e.printStackTrace();
                publishProgress("problem with your request");
            }


            return productList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String toastString = values[0];
            Toast.makeText(getActivity(), toastString, Toast.LENGTH_SHORT).show();
        }
    }

    private class CompleteViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mNameTextView;
        private TextView mPriceTextView;
        private Product mProduct;

        public CompleteViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.product_sub_category_image_view_item);
            mNameTextView = itemView.findViewById(R.id.product_sub_category_name_text_view);
            mPriceTextView = itemView.findViewById(R.id.product_sub_category_price_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = ProductInfoActivity.newIntent(getActivity(), mProduct.getId());
                    startActivity(intent);
                }
            });
        }

        public void bind(Product product) {
            mProduct = product;
            mNameTextView.setText(product.getName());
            mPriceTextView.setText(product.getPrice() + " $ ");
            if (product.getImages() != null && product.getImages().size() > 0) {
                Picasso.get().load(product.getImages().get(0).getPath())
                        .placeholder(R.drawable.shop)
                        .into(mImageView);
            }

        }
    }

    private class CompleteAdapter extends RecyclerView.Adapter<CompleteViewHolder> {
        private List<Product> mProductList;

        public CompleteAdapter(List<Product> productList) {
            mProductList = productList;
        }

        public void setProductList(List<Product> productList) {
            mProductList = productList;
        }

        @NonNull
        @Override
        public CompleteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.product_sub_category_list_item, viewGroup, false);
            return new CompleteViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CompleteViewHolder completeViewHolder, int i) {
            Product product = mProductList.get(i);
            completeViewHolder.bind(product);

        }

        @Override
        public int getItemCount() {
            return mProductList.size();
        }
    }

}
