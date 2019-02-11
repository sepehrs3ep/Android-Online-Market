package project.com.maktab.onlinemarket;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;


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


        if(isNetworkAvailable()){


        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getActivity().finishAffinity();
                }
            },5000);


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

}
