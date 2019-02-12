package project.com.maktab.onlinemarket.controller;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.model.Product;
import project.com.maktab.onlinemarket.model.ProductLab;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainMarketFragment extends Fragment {

    private RecyclerView mProductRecyclerView;
    private RecyclerViewProductAdapter mProductAdapter;


    public static MainMarketFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MainMarketFragment fragment = new MainMarketFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MainMarketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_market, container, false);
        mProductRecyclerView = view.findViewById(R.id.product_recycler_view);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mProductRecyclerView.setLayoutManager(layoutManager);
        mProductAdapter = new RecyclerViewProductAdapter(ProductLab.getInstance().getProducts());
        mProductRecyclerView.setAdapter(mProductAdapter);


        return view;
    }

    private class RecyclerViewProductHolder extends RecyclerView.ViewHolder{
        private ImageView mProductImageView;
        private TextView mProductNameTextView;
        private TextView mProductPriceTextView;

        public RecyclerViewProductHolder(@NonNull View itemView) {
            super(itemView);
            mProductImageView = itemView.findViewById(R.id.product_image_list_item);
            mProductNameTextView = itemView.findViewById(R.id.product_name_list_item);
            mProductPriceTextView = itemView.findViewById(R.id.product_price_list_item);
        }
        public void bind(Product product){
            Picasso.get().load(product.getImages().get(0).getPath()).into(mProductImageView);
            mProductNameTextView.setText(product.getName());
            mProductPriceTextView.setText(product.getPrice());
        }
    }
    private class RecyclerViewProductAdapter extends RecyclerView.Adapter<RecyclerViewProductHolder>{
        private List<Product> mProductList;

        public RecyclerViewProductAdapter(List<Product> productList) {
            mProductList = productList;
        }

        public void setProductList(List<Product> productList) {
            mProductList = productList;
        }

        @NonNull
        @Override
        public RecyclerViewProductHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.product_list_item,viewGroup,false);
            return new RecyclerViewProductHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewProductHolder recyclerViewProductHolder, int i) {
            Product product = mProductList.get(i);
            recyclerViewProductHolder.bind(product);

        }

        @Override
        public int getItemCount() {
            return mProductList.size();
        }
    }

}
