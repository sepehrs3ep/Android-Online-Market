package project.com.maktab.onlinemarket.controller.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.controller.activity.ProductFilterActivity;
import project.com.maktab.onlinemarket.controller.activity.ProductInfoActivity;
import project.com.maktab.onlinemarket.eventbus.FilterProductMassage;
import project.com.maktab.onlinemarket.eventbus.ProductSortMassage;
import project.com.maktab.onlinemarket.model.category.Category;
import project.com.maktab.onlinemarket.model.category.CategoryLab;
import project.com.maktab.onlinemarket.model.product.Product;
import project.com.maktab.onlinemarket.model.product.ProductLab;
import project.com.maktab.onlinemarket.network.Api;
import project.com.maktab.onlinemarket.network.RetrofitClientInstance;
import project.com.maktab.onlinemarket.utils.GenerateSnackBar;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompleteProductListFragment extends Fragment {
    private static final String ORDER_BY_ARGS = "orderByArgs";
    private static final String CATEGORY_ID_ARGS = "categoryIdArgs";
    private static final String IS_SUB_CATEGORY_ARGS = "IS_SUB_CATEGORY_ARGS";
    private static final String SEARCH_STRING_ARGS = "SEARCH_STRING_ARGS";
    private static final String IS_FROM_SEARCH_ARGS = "IS_FROM_SEARCH_ARGS";
    private static final String DESC_ORDER = "desc";
    private static final String ASC_ORDER = "asc";
    private static final String DATE = "date";
    private static final String RATE = "rating";
    private static final String VISITED = "popularity";
    private static final String PRICE = "price";
    public static final String SHOULD_HANDLE_FILTER = "SHOULD_HANDLE_FILTER";
    private static final String COMPLETE_PRODUCT_TAG = "COMPLETE_PRODUCT_TAG";
    private static final String IS_FEATURED_PRODUCT = "IS_FEATURED_PRODUCT";
    private boolean mIsFeatured;
    private long mCategoryId;
    private String mOrder;
    private String mSearchedString;
    private boolean mIsFromSearch;
    private boolean mIsSubCategory;
    private String mOrderType;
    private CompleteAdapter mAdapter;
    private static int mPageCounter = 1;
    private List<Product> mProductList;
    private static boolean NO_MORE_PAGE;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private TextView textCartItemCount;
    private boolean mIsGridShow;
    private int mSortType;

    private CardView mFilterCardView, mSortCardView;
    private ImageButton mChangeRecyclerLayoutImageBtn;
    private TextView mSortTypeTextView;

    public static String getIsFeaturedProduct() {
        return IS_FEATURED_PRODUCT;
    }

    public static CompleteProductListFragment newInstance(String orderBy, long categoryId, String searchItem, boolean isFromSearch,
                                                          boolean isSubCategory) {

        Bundle args = new Bundle();
        args.putString(ORDER_BY_ARGS, orderBy);
        args.putLong(CATEGORY_ID_ARGS, categoryId);
        args.putBoolean(IS_SUB_CATEGORY_ARGS, isSubCategory);
        args.putString(SEARCH_STRING_ARGS, searchItem);
        args.putBoolean(IS_FROM_SEARCH_ARGS, isFromSearch);
        CompleteProductListFragment fragment = new CompleteProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setHasOptionsMenu(true);
        mOrder = DESC_ORDER;
        mOrderType = getArguments().getString(ORDER_BY_ARGS);
        mCategoryId = getArguments().getLong(CATEGORY_ID_ARGS);
        mSearchedString = getArguments().getString(SEARCH_STRING_ARGS);
        mIsSubCategory = getArguments().getBoolean(IS_SUB_CATEGORY_ARGS, false);
        mIsFromSearch = getArguments().getBoolean(IS_FROM_SEARCH_ARGS, false);

        if (mOrderType.equalsIgnoreCase(IS_FEATURED_PRODUCT)) {
            mIsFeatured = true;
            mOrderType = DATE;
            mOrder = DESC_ORDER;
        }

        mPageCounter = 1;
        NO_MORE_PAGE = false;
        mIsGridShow = false;
        ProductFilterFragment.mFilteredAttributes = new ArrayList<>();
    }

    public CompleteProductListFragment() {
        // Required empty public constructor
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void filterProducts(FilterProductMassage filterProductMassage) {
        FilterProductMassage massage = EventBus.getDefault().removeStickyEvent(FilterProductMassage.class);
        if (ProductFilterFragment.mFilteredAttributes.size() > 0) {
            mPageCounter = 1;
            mProductList.clear();
            mAdapter.setProductList(mProductList);
            mAdapter.notifyDataSetChanged();
            new ProductsAsynceTask(getActivity()).execute(mPageCounter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(COMPLETE_PRODUCT_TAG, "is on destroy and object will remove");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complete_products_list, container, false);
        mRecyclerView = view.findViewById(R.id.products_sub_category_recycler);
        mProgressBar = view.findViewById(R.id.sub_category_progress_bar);
        mFilterCardView = view.findViewById(R.id.filter_products_card_view);
        mSortCardView = view.findViewById(R.id.sort_products_card_view);
        mChangeRecyclerLayoutImageBtn = view.findViewById(R.id.recycler_view_layout_image_btn);
        mSortTypeTextView = view.findViewById(R.id.sorted_type_text_view);

        mSortTypeTextView.setText(R.string.check_box_newest);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        mFilterCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsSubCategory) {
                    sendFilterIntent(mProductList.get(0).getId());
                    /*ProductFilterFragment filterFragment = ProductFilterFragment.newInstance(mProductList.get(0).getId());
                    filterFragment.show(getFragmentManager(), "show filtered");*/
                } else {
                    sendFilterIntent(SHOULD_HANDLE_FILTER);
                    /*ProductFilterFragment filterFragment = ProductFilterFragment.newInstance(SHOULD_HANDLE_FILTER);
                    filterFragment.show(getFragmentManager(), "show handle filtered");*/
                }

            }
        });
        mSortCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortProductsDialogFragment fragment = SortProductsDialogFragment.newInstance(
                        SortProductsDialogFragment.getEnumIndexByString(getActivity(), mSortTypeTextView.getText().toString()));
                fragment.show(getFragmentManager(), "show checkboxes");
            }
        });

        mChangeRecyclerLayoutImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsGridShow = !mIsGridShow;
                changeRecyclerLayout();
            }
        });


        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (mIsFromSearch) {
            mOrderType = DATE;
            actionBar.setTitle(mSearchedString);
        } else if (mIsSubCategory) {
            mOrderType = DATE;
            Category category = CategoryLab.getmCategoryInstance().getCategory(mCategoryId);
            if (category != null)
                actionBar.setTitle(category.getName());
        } else {
            String order;
            if (mOrderType.equalsIgnoreCase("date")) {
                order = getString(R.string.new_products);
                mSortTypeTextView.setText(R.string.check_box_newest);
            } else if (mOrderType.equalsIgnoreCase("rating")) {
                order = getString(R.string.rated_products);
                mSortTypeTextView.setText(R.string.check_box_rated);

            } else {
                order = getString(R.string.visited_products);
                mSortTypeTextView.setText(R.string.check_box_selles);
            }

            actionBar.setTitle(order);
        }


        setAdapter();
        new ProductsAsynceTask(getActivity()).execute(mPageCounter);


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    if (!NO_MORE_PAGE) {
                        mPageCounter++;
                        new ProductsAsynceTask(getActivity()).execute(mPageCounter);

                    }
                }
            }
        });


        return view;

    }

    private void sendFilterIntent(String intentExtra) {
        Intent intent = ProductFilterActivity.newIntent(getActivity(), intentExtra);
        startActivity(intent);
    }


    private void changeRecyclerLayout() {
        if (mIsGridShow) {
            mChangeRecyclerLayoutImageBtn.setImageResource(R.drawable.ic_linear_layout);


            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));


            mRecyclerView.setAdapter(mAdapter);
        } else {
            mChangeRecyclerLayoutImageBtn.setImageResource(R.drawable.ic_grid_layout);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(mAdapter);
        }
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

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSortChanged(ProductSortMassage productSortMassage) {
        mSortType = productSortMassage.getEnumIndex();

        String currentSortText = mSortTypeTextView.getText().toString();

        int stringResource = SortProductsDialogFragment.getStringSorts(mSortType);
        String receivedSort = getString(stringResource);

        mSortTypeTextView.setText(receivedSort);

        if (currentSortText.equalsIgnoreCase(receivedSort))
            return;
        else {
            mPageCounter = 1;
            mProductList.clear();
            mAdapter.setProductList(mProductList);
            mAdapter.notifyDataSetChanged();

            SortProductsDialogFragment.Sorts sorts = SortProductsDialogFragment.getEnumSorts(mSortType);
            switch (sorts) {
                case NEWEST:
                    mOrderType = DATE;
                    mOrder = DESC_ORDER;
                    break;
                case RATED:
                    mOrderType = RATE;
                    mOrder = DESC_ORDER;
                    break;
                case VISITED:
                    mOrderType = VISITED;
                    mOrder = DESC_ORDER;
                    break;
                case LOW_TO_HIGH:
                    mOrderType = PRICE;
                    mOrder = ASC_ORDER;
                    break;
                case HIGH_TO_LOW:
                    mOrderType = PRICE;
                    mOrder = DESC_ORDER;
                    break;
            }
            new ProductsAsynceTask(getActivity()).execute(mPageCounter);

        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_products_menu:
                SearchProductsDialogFragment fragment = SearchProductsDialogFragment.newInstance();
                fragment.show(getFragmentManager(), "Search");
                return true;
            case R.id.action_cart:
                ShopBagDialogFragment shopBagDialogFragment = ShopBagDialogFragment.newInstance();
                shopBagDialogFragment.show(getFragmentManager(), "show the bag from main");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
        private boolean mHasNoProduct = false;

        Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), R.string.no_page, Snackbar.LENGTH_LONG);

        public ProductsAsynceTask(Context context) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(getString(R.string.progress_product));
            mHasNoProduct = false;
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            super.onPostExecute(products);
            mProgressBar.setVisibility(View.GONE);
            if (mHasNoProduct) {
                new GenerateSnackBar(getContext(), R.string.no_item).getSnackbar().show();
                return;
            } else if (products == null || products.size() <= 0) {
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
                if (mIsSubCategory)
                    response = RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                            .getProductsSubCategoires(String.valueOf(pageCounter), String.valueOf(mCategoryId), mOrderType, mOrder,
                                    ProductFilterFragment.mFilteredAttributes.toString())
                            .execute();
                else if (mIsFromSearch)
                    response = RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                            .searchProducts(String.valueOf(pageCounter), mSearchedString, mOrderType, mOrder
                                    , ProductFilterFragment.mFilteredAttributes.toString())
                            .execute();
                else if (mIsFeatured)
                    response = RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                            .getFeaturedProducts(String.valueOf(pageCounter), mOrderType, mOrder,
                                    ProductFilterFragment.mFilteredAttributes.toString())
                            .execute();
                else
                    response = RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                            .getAllProductWithPage(String.valueOf(pageCounter), mOrderType, mOrder,
                                    ProductFilterFragment.mFilteredAttributes.toString()).execute();
                if (response.isSuccessful()) {
                    productList = response.body();
                    if (productList == null || productList.size() <= 0)
                        mHasNoProduct = true;

                } else publishProgress(getString(R.string.problem_response));
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
            mPriceTextView.setText(getString(R.string.price_format, product.getPrice()));
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
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.product_complete_list_item, viewGroup, false);
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

}
