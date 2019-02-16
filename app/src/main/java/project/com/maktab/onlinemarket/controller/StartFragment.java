package project.com.maktab.onlinemarket.controller;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.io.IOException;
import java.util.List;

import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.model.Product;
import project.com.maktab.onlinemarket.model.ProductLab;
import project.com.maktab.onlinemarket.network.Api;
import project.com.maktab.onlinemarket.network.RetrofitClientInstance;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {
    private LottieAnimationView mBagAnimationView;
    private LottieAnimationView mInternetAnimationView;


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

        mBagAnimationView = view.findViewById(R.id.bag_lottie_animation);
        mInternetAnimationView = view.findViewById(R.id.no_internet_lottie_animation);
        mInternetAnimationView.setVisibility(View.GONE);


        if (isNetworkAvailable()) {
  /*          Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
            Api api = retrofit.create(Api.class);
            api.getAllProducts("date").enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    ProductLab.getInstance().setNewProducts(response.body());
                    Intent intent = MainMarketActivity.getIntent(getActivity());
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    Toast.makeText(getActivity(), R.string.problem_response, Toast.LENGTH_SHORT).show();

                }
            });

*/
            new InitProductsAsynceTask().execute();


        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getActivity().finishAffinity();
                }
            }, 5000);


            Toast.makeText(getActivity(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            mBagAnimationView.cancelAnimation();
            mBagAnimationView.setVisibility(View.GONE);
            mInternetAnimationView.setVisibility(View.VISIBLE);
            mInternetAnimationView.playAnimation();


        }

        return view;
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
                List<Product> newProducts = RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                        .getAllProducts("date").execute().body();

               /* List<Product> ratedProducts = RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                        .getRatedProducts().execute().body();
                List<Product> visitedProducts = RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                        .getVisitedProducts().execute().body();*/
                ProductLab.getInstance().setNewProducts(newProducts);
                ProductLab.getInstance().setRatedProducts(newProducts);
                ProductLab.getInstance().setVisitedProducts(newProducts);
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


}
