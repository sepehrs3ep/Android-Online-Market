package project.com.maktab.onlinemarket.controller.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.controller.activity.CategoryViewPagerActivity;
import project.com.maktab.onlinemarket.controller.activity.CompleteProductListActivity;
import project.com.maktab.onlinemarket.controller.activity.MainMarketActivity;
import project.com.maktab.onlinemarket.controller.activity.ProductInfoActivity;
import project.com.maktab.onlinemarket.eventbus.BadgeMassageEvent;
import project.com.maktab.onlinemarket.model.category.Category;
import project.com.maktab.onlinemarket.model.category.CategoryLab;
import project.com.maktab.onlinemarket.model.product.Product;
import project.com.maktab.onlinemarket.model.product.ProductLab;
import project.com.maktab.onlinemarket.utils.SharedPref;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainMarketFragment extends Fragment {

    private RecyclerView mNewProductRecyclerView, mRateProductsRecyclerView, mVisitedProductsRecyclerView, mChipsRecyclerView, mFeaturedRecyclerView;
    private RecyclerViewProductAdapter mNewProductAdapter, mRateProductAdapter, mVisitedProductAdapter,
            mFeaturedAdapter;

    private List<Category> mChipsCategoryList;
    private TextView textCartItemCount;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private TextView mNewTemplate, mRateTemplate, mVisitTemplate , mFeaturedTemplate;

    private ViewPager mMainViewPager;
    private TabLayout mMainTabLayout;
    private List<Integer> mDrawbleImages;


    public static MainMarketFragment newInstance() {

        Bundle args = new Bundle();

        MainMarketFragment fragment = new MainMarketFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupBadge();
    }

    public MainMarketFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateBadgesEvent(BadgeMassageEvent badgeMassageEvent) {
        setupBadge();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChipsCategoryList = CategoryLab.getmCategoryInstance().getParentCategories();
        setHasOptionsMenu(true);
        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        mDrawbleImages = new ArrayList<>();
        mDrawbleImages.add(R.drawable.market1);
        mDrawbleImages.add(R.drawable.market2);
        mDrawbleImages.add(R.drawable.market3);
        mDrawbleImages.add(R.drawable.market4);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_main_market, menu);


        final MenuItem menuItem = menu.findItem(R.id.action_cart);

        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });


    }

    private void setTemplate(TextView template) {
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
            case android.R.id.home:
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }

                return true;
            case R.id.action_cart:
                ShopBagDialogFragment shopBagDialogFragment = ShopBagDialogFragment.newInstance();
                shopBagDialogFragment.show(getFragmentManager(), "show the bag from main");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupBadge() {
        int bagSize = ProductLab.getInstance().getShoppingBag().size();

        if (textCartItemCount != null) {
            if (bagSize == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(bagSize, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
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
        mRateTemplate = view.findViewById(R.id.rated_complete_text_template);
        mVisitTemplate = view.findViewById(R.id.visited_complete_text_template);
        mMainViewPager = view.findViewById(R.id.main_photo_gallery_view_pager);
        mMainTabLayout = view.findViewById(R.id.main_photo_gallery_tab_layout);
        mFeaturedTemplate =  view.findViewById(R.id.featured_complete_text_template);
        mFeaturedRecyclerView = view.findViewById(R.id.featured_recycler_view);

        mMainViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return PhotoGalleryFragment.newInstance(null, mDrawbleImages.get(position), true);
            }

            @Override
            public int getCount() {
                return mDrawbleImages.size();
            }
        });

        mMainTabLayout.setupWithViewPager(mMainViewPager, true);

        mMainTabLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);


        mDrawerLayout = view.findViewById(R.id.drawer_layout);
        mNavigationView = view.findViewById(R.id.navigation_view);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, R.string.drawer_open, R.string.drawer_close);

        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.list_category_menu_item:

                        Intent intent = CategoryViewPagerActivity.newIntent(getActivity(), -2);
                        startActivity(intent);

                        return true;
                    case R.id.list_like_menu_item:
                        LikedProductsDialogFragment fragment = LikedProductsDialogFragment.newInstance();
                        fragment.show(getFragmentManager(), "show liked list");
                        return true;
                    default:
                        return false;
                }
            }
        });

        mActionBarDrawerToggle.syncState();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SpannableString spannableAllList = new SpannableString(getString(R.string.all_product_list));
        ClickableSpan clickableSpanAllList = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                switch (textView.getId()) {
                    case R.id.new_poducts_complete_text_template:
                        sendAllListIntent("date");
                        break;
                    case R.id.rated_complete_text_template:
                        sendAllListIntent("rating");
                        break;
                    case R.id.visited_complete_text_template:
                        sendAllListIntent("popularity");
                        break;
                    case R.id.featured_complete_text_template:
                        sendAllListIntent(CompleteProductListFragment.getIsFeaturedProduct());
                        break;
                }
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
        mFeaturedTemplate.setText(spannableAllList);
        mVisitTemplate.setText(spannableAllList);

        setTemplate(mFeaturedTemplate);
        setTemplate(mNewTemplate);
        setTemplate(mRateTemplate);
        setTemplate(mVisitTemplate);


        ((MainMarketActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mFeaturedRecyclerView.setLayoutManager(getHorizontalLayoutManager());
        mChipsRecyclerView.setLayoutManager(getHorizontalLayoutManager());
        mNewProductRecyclerView.setLayoutManager(getHorizontalLayoutManager());
        mRateProductsRecyclerView.setLayoutManager(getHorizontalLayoutManager());
        mVisitedProductsRecyclerView.setLayoutManager(getHorizontalLayoutManager());

        mFeaturedAdapter = new RecyclerViewProductAdapter(ProductLab.getInstance().getFeaturedProducts());
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

        mFeaturedRecyclerView.setAdapter(mFeaturedAdapter);
        mNewProductRecyclerView.setAdapter(mNewProductAdapter);
        mRateProductsRecyclerView.setAdapter(mRateProductAdapter);
        mVisitedProductsRecyclerView.setAdapter(mVisitedProductAdapter);


        return view;
    }

    private void sendAllListIntent(String type) {
        Intent intent = null;
        if(type.equalsIgnoreCase(CompleteProductListFragment.getIsFeaturedProduct()))
           intent  = CompleteProductListActivity.newIntent(getActivity(), "date", -1, "nothing", false, false,true);
        else
         intent = CompleteProductListActivity.newIntent(getActivity(), type, -1, "nothing", false, false,false);
        startActivity(intent);
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
