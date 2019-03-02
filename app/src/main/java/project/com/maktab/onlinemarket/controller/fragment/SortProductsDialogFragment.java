package project.com.maktab.onlinemarket.controller.fragment;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.eventbus.ProductSortMassage;

/**
 * A simple {@link Fragment} subclass.
 */
public class SortProductsDialogFragment extends DialogFragment {
    private static final String CURRENT_SORT_ARGS = "CURRENT_SORT_ARGS";
    private int mCurrentSort;
    public static final String RADIO_BUTTON_TAG = "RADIO_BUTTON_TAG";

    RadioGroup mSortRadioGroup;
    public enum Sorts {
        NEWEST(0),
        RATED(1),
        VISITED(2),
        HIGH_TO_LOW(3),
        LOW_TO_HIGH(4),
        FEATURED(5);


        int index;

        public int getIndex() {
            return index;
        }

        Sorts(int i) {
            index = i;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentSort = getArguments().getInt(CURRENT_SORT_ARGS);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.WRAP_CONTENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    public static SortProductsDialogFragment newInstance(int sort) {

        Bundle args = new Bundle();
        args.putInt(CURRENT_SORT_ARGS, sort);
        SortProductsDialogFragment fragment = new SortProductsDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public SortProductsDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sort_products_dialog, container, false);
        mSortRadioGroup = view.findViewById(R.id.sorts_radio_group);


        Sorts currentSort = getEnumSorts(mCurrentSort);
        switch (currentSort) {
            case NEWEST:
                mSortRadioGroup.check(R.id.newest_check_box);
                break;
            case RATED:
                mSortRadioGroup.check(R.id.rated_check_box);
                break;
            case VISITED:
                mSortRadioGroup.check(R.id.visited_check_box);
                break;
            case HIGH_TO_LOW:
                mSortRadioGroup.check(R.id.high_to_low_check_box);
                break;
            case LOW_TO_HIGH:
                mSortRadioGroup.check(R.id.low_to_high_check_box);
                break;
        }



        mSortRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selected = 0;
                switch (checkedId) {
                    case R.id.newest_check_box:
                        selected = Sorts.NEWEST.getIndex();
                        break;
                    case R.id.rated_check_box:
                        selected = Sorts.RATED.getIndex();
                        break;
                    case R.id.visited_check_box:
                        selected = Sorts.VISITED.getIndex();
                        break;
                    case R.id.high_to_low_check_box:
                        selected = Sorts.HIGH_TO_LOW.getIndex();
                        break;
                    case R.id.low_to_high_check_box:
                        selected = Sorts.LOW_TO_HIGH.getIndex();
                        break;
                }

                if (selected == mCurrentSort) {
                    dismiss();
                } else {
                    EventBus.getDefault().post(new ProductSortMassage(selected));
                    dismiss();

                }
            }
        });

        return view;
    }

    public static Sorts getEnumSorts(int currentSort) {
        Sorts sort = null;
        for (Sorts sorts : Sorts.values()) {
            if (sorts.getIndex() == currentSort)
                sort = sorts;
        }
        return sort;
    }


    public static int getEnumIndexByString(Context context, String sortText) {
        if (sortText.equalsIgnoreCase(context.getString(R.string.check_box_newest)))
            return Sorts.NEWEST.getIndex();
        else if (sortText.equalsIgnoreCase(context.getString(R.string.check_box_rated)))
            return Sorts.RATED.getIndex();
        else if (sortText.equalsIgnoreCase(context.getString(R.string.check_box_selles)))
            return Sorts.VISITED.getIndex();
        else if (sortText.equalsIgnoreCase(context.getString(R.string.check_box_cost_low_high)))
            return Sorts.LOW_TO_HIGH.getIndex();
        else if (sortText.equalsIgnoreCase(context.getString(R.string.check_box_cost_high_low)))
            return Sorts.HIGH_TO_LOW.getIndex();
        else return -1;


    }


    public static int getStringSorts(int currentSort) {
        Sorts sort = getEnumSorts(currentSort);
        switch (sort) {
            case NEWEST:
                return R.string.check_box_newest;
            case RATED:
                return R.string.check_box_rated;
            case VISITED:
                return R.string.check_box_selles;
            case HIGH_TO_LOW:
                return R.string.check_box_cost_high_low;
            case LOW_TO_HIGH:
                return R.string.check_box_cost_low_high;
            default:
                return -1;

        }

    }


}
