package project.com.maktab.onlinemarket.controller;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.model.product.Product;
import project.com.maktab.onlinemarket.model.product.ProductLab;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainMarketFragment extends Fragment {

    private RecyclerView mNewProductRecyclerView , mRateProductsRecyclerView , mVisitedProductsRecyclerView;
    private RecyclerViewProductAdapter mNewProductAdapter ,mRateProductAdapter,mVisitedProductAdapter;


    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private ActionBarDrawerToggle mActionBarDrawerToggle;

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
        mNewProductRecyclerView = view.findViewById(R.id.new_product_recycler_view);
        mVisitedProductsRecyclerView = view.findViewById(R.id.visited_products_recycler_view);
        mRateProductsRecyclerView = view.findViewById(R.id.rated_products_recycler_view);

        mDrawerLayout = view.findViewById(R.id.drawer_layout);
        mNavigationView = view.findViewById(R.id.navigation_view);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, R.string.drawer_open, R.string.drawer_close);

        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);

        mActionBarDrawerToggle.syncState();
        ((MainMarketActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.list_category_menu_item:




                        return true;


                    default:
                        return false;
                }
            }
        });

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mNewProductRecyclerView.setLayoutManager(layoutManager);
        mRateProductsRecyclerView.setLayoutManager(layoutManager1);
        mVisitedProductsRecyclerView.setLayoutManager(layoutManager2);

        mNewProductAdapter = new RecyclerViewProductAdapter(ProductLab.getInstance().getNewProducts());
        mRateProductAdapter = new RecyclerViewProductAdapter(ProductLab.getInstance().getRatedProducts());
        mVisitedProductAdapter = new RecyclerViewProductAdapter(ProductLab.getInstance().getVisitedProducts());


        mNewProductRecyclerView.setAdapter(mNewProductAdapter);
        mRateProductsRecyclerView.setAdapter(mRateProductAdapter);
        mVisitedProductsRecyclerView.setAdapter(mVisitedProductAdapter);


        return view;
    }

    private class RecyclerViewProductHolder extends RecyclerView.ViewHolder {
        private ImageView mProductImageView;
        private TextView mProductNameTextView;
        private TextView mProductPriceTextView;
        private Product mProduct;

        public RecyclerViewProductHolder(@NonNull View itemView) {
            super(itemView);
            mProductImageView = itemView.findViewById(R.id.product_image_list_item);
            mProductNameTextView = itemView.findViewById(R.id.product_name_list_item);
            mProductPriceTextView = itemView.findViewById(R.id.product_price_list_item);
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
            if (product.getImages() != null && product.getImages().size() > 0)
                Picasso.get().load(product.getImages().get(0).getPath()).into(mProductImageView);

            mProductNameTextView.setText(product.getName());
            mProductPriceTextView.setText(product.getPrice());
        }
    }

    private class RecyclerViewProductAdapter extends RecyclerView.Adapter<RecyclerViewProductHolder> {
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
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.product_list_item, viewGroup, false);
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
