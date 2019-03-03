package project.com.maktab.onlinemarket.controller.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import androidx.fragment.app.Fragment;
import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.controller.activity.MainMarketActivity;
import project.com.maktab.onlinemarket.model.category.CategoryLab;
import project.com.maktab.onlinemarket.model.product.Product;
import project.com.maktab.onlinemarket.model.product.ProductLab;
import project.com.maktab.onlinemarket.network.base.Api;
import project.com.maktab.onlinemarket.network.base.RetrofitClientInstance;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends VisibleFragment {
    /*   private LottieAnimationView mBagAnimationView;
       private LottieAnimationView mInternetAnimationView;*/
    private Button mTryAgainButton;


    public static StartFragment newInstance() {

        Bundle args = new Bundle();

        StartFragment fragment = new StartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_start, container, false);

        /*mBagAnimationView = view.findViewById(R.id.bag_lottie_animation);
        mInternetAnimationView = view.findViewById(R.id.no_internet_lottie_animation);
        mInternetAnimationView.setVisibility(View.GONE);*/
        mTryAgainButton = view.findViewById(R.id.try_again_btn);
        mTryAgainButton.setVisibility(View.GONE);


        startInit();

        mTryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInit();
            }
        });

        return view;
    }

    private void startInit() {
        if (isNetworkAvailable()) {
            /*mBagAnimationView.setVisibility(View.VISIBLE);
            mInternetAnimationView.setVisibility(View.GONE);
            mBagAnimationView.playAnimation();*/
            mTryAgainButton.setVisibility(View.GONE);

            new InitProductsAsynceTask().execute();

        } else {
         /*   new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    getActivity().finishAffinity();
                }
            }, 5000);
*/

            Toast.makeText(getActivity(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
          /*  mBagAnimationView.cancelAnimation();
            mBagAnimationView.setVisibility(View.GONE);
            mInternetAnimationView.setVisibility(View.VISIBLE);
            mInternetAnimationView.playAnimation();*/
            mTryAgainButton.setVisibility(View.VISIBLE);


        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
            Toast.makeText(getActivity(), toastString, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = MainMarketActivity.getIntent(getActivity());
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


}
