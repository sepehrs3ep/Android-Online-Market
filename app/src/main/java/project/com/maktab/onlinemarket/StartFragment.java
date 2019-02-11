package project.com.maktab.onlinemarket;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {
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
        return inflater.inflate(R.layout.fragment_market_main, container, false);
    }

}
