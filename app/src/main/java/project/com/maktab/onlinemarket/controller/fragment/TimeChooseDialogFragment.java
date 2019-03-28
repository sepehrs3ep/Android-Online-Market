package project.com.maktab.onlinemarket.controller.fragment;


import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.com.maktab.onlinemarket.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeChooseDialogFragment extends DialogFragment {
    @BindView(R.id.submit_time_btn)
    Button mSubmitBtn;
    @BindView(R.id.custom_time_edit_text)
    EditText mCustomTimeEditText;
    @BindView(R.id.custom_time_input_layout)
    TextInputLayout mCustomTimeInputLayout;
    @BindView(R.id.selected_time_spinner)
    Spinner mSelectTimeSpinner;
    @BindView(R.id.service_toggle_btn)
    ToggleButton mServiceToggleBtn;



    public static TimeChooseDialogFragment newInstance() {

        Bundle args = new Bundle();

        TimeChooseDialogFragment fragment = new TimeChooseDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public TimeChooseDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_time_choose_dialog, container, false);
        ButterKnife.bind(this,view);

        enableWidgets(false);

        mServiceToggleBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){

                enableWidgets(true);

            }else {
                enableWidgets(false);

            }
        });




        return view;
    }

    private void enableWidgets(boolean b) {
        mCustomTimeInputLayout.setEnabled(b);
        mCustomTimeEditText.setEnabled(b);
        mSelectTimeSpinner.setEnabled(b);
    }

    private boolean validateTimeEditText() {
        if (mCustomTimeEditText.getText().toString().trim().isEmpty()) {
            mCustomTimeInputLayout.setError(getString(R.string.empty_error));
            requestFocus(mCustomTimeEditText);
            return false;
        } else {
            mCustomTimeInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }



}
