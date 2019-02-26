package project.com.maktab.onlinemarket.controller.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.controller.activity.CategoryViewPagerActivity;
import project.com.maktab.onlinemarket.controller.activity.CompleteProductListActivity;
import project.com.maktab.onlinemarket.controller.activity.MainMarketActivity;
import project.com.maktab.onlinemarket.controller.activity.ProductInfoActivity;
import project.com.maktab.onlinemarket.model.category.Category;
import project.com.maktab.onlinemarket.model.category.CategoryLab;
import project.com.maktab.onlinemarket.model.product.Product;
import project.com.maktab.onlinemarket.model.product.ProductLab;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainMarketFragment extends Fragment {

    private RecyclerView mNewProductRecyclerView, mRateProductsRecyclerView, mVisitedProductsRecyclerView, mChipsRecyclerView;
    private RecyclerViewProductAdapter mNewProductAdapter, mRateProductAdapter, mVisitedProductAdapter;

    private List<Category> mChipsCategoryList;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private TextView mNewTemplate, mRateTemplate, mVisitTemplate;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChipsCategoryList = CategoryLab.getmCategoryInstance().getParentCategories();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_main_market, menu);
    }
    private void setTemplate(TextView template){
        template.setMovementMethod(LinkMovementMethod.getInstance());
        template.setHighlightColor(Color.TRANSPARENT);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_products_menu:
                SearchProductsDialogFragment fragment = SearchProductsDialogFragment.newInstance();
                fragment.show(getFragmentManager(), "Search");

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_market, container, false);
        mNewProductRecyclerView = view.findViewById(R.id.new_product_recycler_view);
        mVisitedProductsRecyclerView = view.findViewById(R.id.visited_products_recycler_view);
        mRateProductsRecyclerView = view.findViewById(R.id.rated_products_recycler_view);
        mChipsRecyclerView = view.findViewById(R.id.chips_recyclerView);
        mNewTemplate = view.findViewById(R.id.new_poducts_complete_text_template);
        mRateTemplate = view.findViewById(R.id.rate_poducts_complete_text_template);
        mVisitTemplate = view.findViewById(R.id.visited_poducts_complete_text_template);

        mDrawerLayout = view.findViewById(R.id.drawer_layout);
        mNavigationView = view.findViewById(R.id.navigation_view);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, R.string.drawer_open, R.string.drawer_close);

        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);

        mActionBarDrawerToggle.syncState();

        ((MainMarketActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SpannableString spannableAllList = new SpannableString(getString(R.string.all_product_list));
        ClickableSpan clickableSpanAllList = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent intent = CompleteProductListActivity.newIntent(getActivity(), "date");
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        spannableAllList.setSpan(clickableSpanAllList, 0, getString(R.string.all_product_list).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mNewTemplate.setText(spannableAllList);

        mRateTemplate.setText(spannableAllList);

        mVisitTemplate.setText(spannableAllList);

        setTemplate(mNewTemplate);
        setTemplate(mRateTemplate);
        setTemplate(mVisitTemplate);



        ((MainMarketActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.list_category_menu_item:

                        Intent intent = CategoryViewPagerActivity.newIntent(getActivity(), -2);
                        startActivity(intent);

                        return true;


                    default:
                        return false;
                }
            }
        });


        mChipsRecyclerView.setLayoutManager(getHorizontalLayoutManager());
        mNewProductRecyclerView.setLayoutManager(getHorizontalLayoutManager());
        mRateProductsRecyclerView.setLayoutManager(getHorizontalLayoutManager());
        mVisitedProductsRecyclerView.setLayoutManager(getHorizontalLayoutManager());

        mNewProductAdapter = new RecyclerViewProductAdapter(ProductLab.getInstance().getNewProducts());
        mRateProductAdapter = new RecyclerViewProductAdapter(ProductLab.getInstance().getRatedProducts());
        mVisitedProductAdapter = new RecyclerViewProductAdapter(ProductLab.getInstance().getVisitedProducts());

        mChipsRecyclerView.setAdapter(new RecyclerView.Adapter() {

            class ChipsViewHolder extends RecyclerView.ViewHolder {
                private TextView mChip;
                private Category mCategory;

                public ChipsViewHolder(@NonNull View itemView) {
                    super(itemView);
                    mChip = itemView.findViewById(R.id.category_btn);
                }

                public void bind(Category category) {
                    mCategory = category;
                    mChip.setText(category.getName());
                }
            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View viewHolderView = LayoutInflater.from(getActivity()).inflate(R.layout.category_chips_item, viewGroup, false);
                return new ChipsViewHolder(viewHolderView);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final Category category = mChipsCategoryList.get(i);
                ChipsViewHolder chipsViewHolder = (ChipsViewHolder) viewHolder;
                chipsViewHolder.bind(category);
                ((ChipsViewHolder) viewHolder).mChip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = CategoryViewPagerActivity.newIntent(getActivity(), category.getId());
                        startActivity(intent);
                    }
                });

            }

            @Override
            public int getItemCount() {
                return mChipsCategoryList.size();
            }
        });

        mNewProductRecyclerView.setAdapter(mNewProductAdapter);
        mRateProductsRecyclerView.setAdapter(mRateProductAdapter);
        mVisitedProductsRecyclerView.setAdapter(mVisitedProductAdapter);


        return view;
    }

    private LinearLayoutManager getHorizontalLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
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
            mProductPriceTextView.setText(product.getPrice() + " $ ");
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
