package project.com.maktab.onlinemarket.controller;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.model.product.Image;
import project.com.maktab.onlinemarket.model.product.Product;
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
    private ViewPagerGalleryAdapter mAdapter;
    private TextView mTextViewName, mTextViewPrice, mTextViewDesc;
    private ProgressDialog mProgressDialog;

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

//        mProduct = ProductLab.getInstance().getProductById(mProductId);
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
        mTextViewPrice = view.findViewById(R.id.info_product_price);
        mTextViewDesc = view.findViewById(R.id.info_product_desc);
        mViewPager = view.findViewById(R.id.photo_gallery_view_pager);

        mProgressDialog.setMessage(getString(R.string.progress_product));
        mProgressDialog.show();


        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getProduct(mProductId)
                .enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if(response.isSuccessful()){
                            mProduct = response.body();
                            showDetailsUI();
                            mProgressDialog.cancel();
                        }

                    }


                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        Toast.makeText(getActivity(), R.string.problem_response, Toast.LENGTH_SHORT).show();
                    }
                });




        return view;
    }

    private void showDetailsUI() {
        mTextViewName.setText(mProduct.getName());
        mTextViewPrice.setText(mProduct.getPrice());
        mTextViewDesc.setText(mProduct.getDescription());


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

        @Override
        public int getCount() {
            return mImagePathList.size();
        }
    }

}
