package project.com.maktab.onlinemarket.controller.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.controller.activity.ProductInfoActivity;
import project.com.maktab.onlinemarket.model.product.Product;
import project.com.maktab.onlinemarket.model.product.ProductLab;
import project.com.maktab.onlinemarket.network.base.Api;
import project.com.maktab.onlinemarket.network.base.RetrofitClientInstance;
import project.com.maktab.onlinemarket.utils.CustomAlertDialogFragment;
import project.com.maktab.onlinemarket.utils.GenerateSnackBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LikedProductsDialogFragment extends VisibleDialogFragment {
    @BindView(R.id.like_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.like_progress_bar)
    ProgressBar mProgressBar;

    private List<Product> mLikedProductList;

    private LikeRecyclerAdapter mAdapter;

    public static LikedProductsDialogFragment newInstance() {

        Bundle args = new Bundle();

        LikedProductsDialogFragment fragment = new LikedProductsDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = RecyclerView.LayoutParams.MATCH_PARENT;
        params.height = RecyclerView.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    public LikedProductsDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_liked_products_dialog, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProgressBar.setVisibility(View.VISIBLE);
        List<String> favoriteIdList = ProductLab.getInstance().getFavoriteProducts();
        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getReleatedProducts(favoriteIdList.toString())
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getActivity(), R.string.problem_response, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mLikedProductList = response.body();
                        if(mLikedProductList==null||mLikedProductList.size()<=0)
                            new GenerateSnackBar(getActivity(),R.string.no_item).getSnackbar().show();
                        updateUI();
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Toast.makeText(getActivity(), R.string.problem_response, Toast.LENGTH_SHORT).show();
                    }
                });


        return view;
    }

    private void updateUI() {

        if (mLikedProductList == null || mLikedProductList.size() <= 0)
            new GenerateSnackBar(getActivity(), R.string.no_item).getSnackbar().show();
        if (mAdapter == null) {
            mAdapter = new LikeRecyclerAdapter(mLikedProductList);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setProductList(mLikedProductList);
            mAdapter.notifyDataSetChanged();
        }
    }

    class LikeProductViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_like_image)
        ImageView mProductImage;
        @BindView(R.id.product_like_name_text_view)
        TextView mProductName;
        @BindView(R.id.delete_product_like_btn)
        Button mDeleteProductBtn;

        private Product mProduct;


        public LikeProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mDeleteProductBtn.setOnClickListener(v -> {
                CustomAlertDialogFragment dialogFragment = CustomAlertDialogFragment.newInstance(getString(R.string.like_dialog_question));
                dialogFragment.show(getFragmentManager(), "sure like for delete");
                dialogFragment.setOnYesNoClick(new CustomAlertDialogFragment.OnYesNoDialogClick() {
                    @Override
                    public void onYesClicked() {
                        ProductLab.getInstance().removeFromFavorite(mProduct.getId());
                        mLikedProductList.remove(mProduct);
                        updateUI();

                    }

                    @Override
                    public void onNoClicked() {
                        dialogFragment.dismiss();
                    }
                });


            });
            mProductImage.setOnClickListener(v -> {
                Intent intent = ProductInfoActivity.newIntent(getActivity(), mProduct.getId());
                startActivity(intent);
            });

        }

        public void bind(Product product) {
            mProduct = product;
            Picasso.get().load(product.getImages().get(0).getPath()).into(mProductImage);
            mProductName.setText(product.getName());

        }
    }

    private class LikeRecyclerAdapter extends RecyclerView.Adapter<LikeProductViewHolder> {
        private List<Product> mProductList;

        public LikeRecyclerAdapter(List<Product> productList) {
            mProductList = productList;
        }

        public void setProductList(List<Product> productList) {
            mProductList = productList;
        }

        @NonNull
        @Override
        public LikeProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.like_list_item, parent, false);
            return new LikeProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull LikeProductViewHolder holder, int position) {
            Product product = mProductList.get(position);
            holder.bind(product);
        }

        @Override
        public int getItemCount() {
            return mProductList.size();
        }
    }

}
