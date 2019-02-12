package project.com.maktab.onlinemarket.controller;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.model.Image;
import project.com.maktab.onlinemarket.model.Product;
import project.com.maktab.onlinemarket.model.ProductLab;

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
        mTextViewPrice = view.findViewById(R.id.info_product_price);
        mTextViewDesc = view.findViewById(R.id.info_product_desc);
        mViewPager = view.findViewById(R.id.photo_gallery_view_pager);

        mTextViewName.setText(mProduct.getName());
        mTextViewPrice.setText(mProduct.getPrice());
        mTextViewDesc.setText(mProduct.getDescription());


        if (mProduct.getImages() != null && mProduct.getImages().size() > 0) {
            mAdapter = new ViewPagerGalleryAdapter(getChildFragmentManager());
            mAdapter.setImagePathList(mProduct.getImages());
            mViewPager.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

        } else mViewPager.setVisibility(View.GONE);


        return view;
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
