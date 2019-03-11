package project.com.maktab.onlinemarket.controller.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.controller.fragment.CompleteProductListFragment;
import project.com.maktab.onlinemarket.model.category.CategoryLab;
import project.com.maktab.onlinemarket.model.product.Product;
import project.com.maktab.onlinemarket.model.product.ProductLab;
import project.com.maktab.onlinemarket.network.base.Api;
import project.com.maktab.onlinemarket.network.base.RetrofitClientInstance;

public class StartActivity extends AppCompatActivity {
    @BindView(R.id.try_again_btn)
    Button mTryAgainButton;
    @BindView(R.id.bag_lottie_animation)
    LottieAnimationView mBagAnimationView;
    @BindView(R.id.no_internet_lottie_animation)
  LottieAnimationView mInternetAnimationView;

    public static Intent newIntent(Context context){
        return new Intent(context,StartActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FullScreencall();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        mTryAgainButton.setVisibility(View.GONE);

        startInit();

        mTryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInit();
            }
        });




    }
    private void startInit() {
        if (isNetworkAvailable()) {
            mBagAnimationView.setVisibility(View.VISIBLE);
            mInternetAnimationView.setVisibility(View.GONE);
            mBagAnimationView.playAnimation();
            mTryAgainButton.setVisibility(View.GONE);

            new InitProductsAsynceTask().execute();

        } else {

            Toast.makeText(StartActivity.this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
               mBagAnimationView.cancelAnimation();
            mBagAnimationView.setVisibility(View.GONE);
            mInternetAnimationView.setVisibility(View.VISIBLE);
            mInternetAnimationView.playAnimation();
            mTryAgainButton.setVisibility(View.VISIBLE);


        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void FullScreencall() {
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    private class InitProductsAsynceTask extends AsyncTask<Void, String, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                ProductLab.getInstance().setNewProducts(generateLists("date"));
                ProductLab.getInstance().setRatedProducts(generateLists("rating"));
                ProductLab.getInstance().setVisitedProducts(generateLists("popularity"));
                ProductLab.getInstance().setFeaturedProducts(generateLists(CompleteProductListFragment.getIsFeaturedProduct()));
                CategoryLab.getmCategoryInstance().setAllCategories(RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                        .getAllCategories().execute().body());
                ProductLab.getInstance().clearDuplicate();
            } catch (IOException e) {
                e.printStackTrace();
                publishProgress(getString(R.string.problem_response));
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String toastString = values[0];
            Toast.makeText(StartActivity.this, toastString, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = MainMarketActivity.getIntent(StartActivity.this);
            startActivity(intent);
        }
    }

    private List<Product> generateLists(String type) throws IOException {
        if (type.equalsIgnoreCase(CompleteProductListFragment.getIsFeaturedProduct())) {
            return RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                    .getFeaturedProducts().execute().body();
        }
        return RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getAllProducts(type).execute().body();
    }

    /*
    @Override
    public Fragment createFragment() {
        return StartFragment.newInstance();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_single_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }*/

}
