package project.com.maktab.onlinemarket.controller.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.controller.activity.ProductInfoActivity;
import project.com.maktab.onlinemarket.controller.activity.ProductsSubCategoryActivity;
import project.com.maktab.onlinemarket.model.product.Image;
import project.com.maktab.onlinemarket.model.product.Product;
import project.com.maktab.onlinemarket.model.product.ProductCategory;
import project.com.maktab.onlinemarket.model.product.ProductLab;
import project.com.maktab.onlinemarket.network.Api;
import project.com.maktab.onlinemarket.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductInfoFragment extends Fragment {

    public static final String PRODUCT_ID_ARGS = "productIdArgs";
    private String mProductId;
    private Product mProduct;
    private ViewPager mViewPager;
    private RecyclerView mReleatedRecyclerView;
    private RecyclerView mCategoriesRecyclerView;
    private ViewPagerGalleryAdapter mAdapter;
    private FloatingActionButton mAddToShopBagFab;
    private CategoriesAdapter mCategoriesAdapter;
    private TextView mTextViewName, mTextViewPrice, mTextViewDesc;
    private ProgressDialog mProgressDialog;
    private TabLayout mTabLayout;
    private Button mProductInfoBtn;
    private ImageButton mExpandImageBtn;
    private TextView mExpandTextView;
    private ReleatedAdapter mReleatedAdapter;
    boolean isExpanded = false;

    public static ProductInfoFragment newInstance(String productId) {

        Bundle args = new Bundle();
        args.putString(PRODUCT_ID_ARGS, productId);
        ProductInfoFragment fragment = new ProductInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductId = getArguments().getString(PRODUCT_ID_ARGS);

        mProduct = ProductLab.getInstance().getProductById(mProductId);
    }

    public ProductInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        mTextViewName = view.findViewById(R.id.info_product_name);
     /*
        mTextViewDesc = view.findViewById(R.id.info_product_desc);*/
        mTextViewPrice = view.findViewById(R.id.info_product_price);
        mReleatedRecyclerView = view.findViewById(R.id.related_recycler_view);
        mViewPager = view.findViewById(R.id.photo_gallery_view_pager);
        mTabLayout = view.findViewById(R.id.photo_gallery_tab_layout);
        mProductInfoBtn = view.findViewById(R.id.info_product_detail);
        mExpandImageBtn = view.findViewById(R.id.expand_image_btn);
        mExpandTextView = view.findViewById(R.id.expand_desc_text_view);
        mAddToShopBagFab = view.findViewById(R.id.add_to_shop_fab);
        mCategoriesRecyclerView = view.findViewById(R.id.product_info_category_recycler_view);

        mCategoriesRecyclerView.setLayoutManager(getHorizontalLayoutManager());
        mReleatedRecyclerView.setLayoutManager(getHorizontalLayoutManager());

        mCategoriesAdapter = new CategoriesAdapter(mProduct.getCategories());
        mCategoriesRecyclerView.setAdapter(mCategoriesAdapter);

        mReleatedAdapter = new ReleatedAdapter(new ArrayList<Product>());
        mReleatedRecyclerView.setAdapter(mReleatedAdapter);


        mTabLayout.setupWithViewPager(mViewPager,true);

     /*   mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getString(R.string.progress_product));
        mProgressDialog.show();
*/
        showDetailsUI();

        mProductInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductAttributeDialogFragment fragment = ProductAttributeDialogFragment.newInstance(mProductId);
                fragment.show(getFragmentManager(),"Show Details");
            }
        });

        mExpandImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExpanded = !isExpanded;
                if(isExpanded){
                    mExpandTextView.setVisibility(View.VISIBLE);
/*
                    mExpandTextView.animate()
                            .translationY(mExpandTextView.getHeight())
                            .alpha(0.0f)
                            .setDuration(300);
                    mExpandTextView.setVisibility(View.GONE);*/


                }
                else mExpandTextView.setVisibility(View.GONE);
            }
        });

        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getReleatedProducts(mProduct.getRelatedProducts().toString())
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if(response.isSuccessful()){
                            List<Product> products = response.body();
                            mReleatedAdapter.setProductList(products);
                            mReleatedAdapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Toast.makeText(getActivity(), R.string.problem_response, Toast.LENGTH_SHORT).show();
                    }
                });


/*
        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getProduct(mProductId)
                .enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if(response.isSuccessful()){
                            mProduct = response.body();
//                            if(mProduct.getDescription()!=null)
                            showDetailsUI();
                            mProgressDialog.cancel();
                        }

                    }


                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        Toast.makeText(getActivity(), R.string.problem_response, Toast.LENGTH_SHORT).show();
                    }
                });*/




        return view;
    }
    private LinearLayoutManager getHorizontalLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
    }
    private void showDetailsUI() {
        mTextViewName.setText(mProduct.getName());
        mTextViewPrice.setText(mProduct.getPrice() + " $ ");
        mExpandTextView.setText(mProduct.getDescription());
        mExpandTextView.setVisibility(View.GONE);

        if (mProduct.getImages() != null && mProduct.getImages().size() > 0) {
            mAdapter = new ViewPagerGalleryAdapter(getChildFragmentManager());
            mAdapter.setImagePathList(mProduct.getImages());
            mViewPager.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

        } else mViewPager.setVisibility(View.GONE);
    }

    private class ViewPagerGalleryAdapter extends FragmentPagerAdapter {
        private List<Image> mImagePathList;

        public void setImagePathList(List<Image> imagePathList) {
            mImagePathList = imagePathList;
        }

        public ViewPagerGalleryAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return PhotoGalleryFragment.newInstance(mImagePathList.get(i).getPath());
        }

       /* @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return String.valueOf(position);
        }*/

        @Override
        public int getCount() {
            return mImagePathList.size();
        }
    }

    private class ReleatedViewHolder extends RecyclerView.ViewHolder{
        private ImageView mProductImageView;
        private TextView mProductNameTextView;
        private TextView mProductPriceTextView;
        private Product mProduct;

        public ReleatedViewHolder(@NonNull View itemView) {
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
    private class ReleatedAdapter extends RecyclerView.Adapter<ReleatedViewHolder>{
        private List<Product> mProductList;

        public ReleatedAdapter(List<Product> productList) {
            mProductList = productList;
        }

        public void setProductList(List<Product> productList) {
            mProductList = productList;
        }

        @NonNull
        @Override
        public ReleatedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.product_list_item, viewGroup, false);
            return new ReleatedViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ReleatedViewHolder releatedViewHolder, int i) {
            Product product = mProductList.get(i);
            releatedViewHolder.bind(product);
        }

        @Override
        public int getItemCount() {
            return mProductList.size();
        }
    }
    private class CategoriesViewHolder extends RecyclerView.ViewHolder{
        private TextView mChip;
        private ProductCategory mCategory;

        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            mChip = itemView.findViewById(R.id.category_btn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = ProductsSubCategoryActivity.newIntent(getActivity(), Long.parseLong(mCategory.getId()));
                    startActivity(intent);
                }
            });
        }
        public void bind(ProductCategory category){
            mCategory = category;
            mChip.setText(category.getName());
        }
    }
    private class CategoriesAdapter extends RecyclerView.Adapter<CategoriesViewHolder>{
        private List<ProductCategory> mCategoryList;

        public CategoriesAdapter(List<ProductCategory> categoryList) {
            mCategoryList = categoryList;
        }

        public void setCategoryList(List<ProductCategory> categoryList) {
            mCategoryList = categoryList;
        }

        @NonNull
        @Override
        public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.category_chips_item,viewGroup,false);
            return new CategoriesViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoriesViewHolder categoriesViewHolder, int i) {

            ProductCategory  category = mCategoryList.get(i);
            categoriesViewHolder.bind(category);

        }

        @Override
        public int getItemCount() {
            return mCategoryList.size();
        }
    }




}
