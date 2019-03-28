package project.com.maktab.onlinemarket.controller.fragment;


import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.service.PollService;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeChooseDialogFragment extends VisibleDialogFragment implements AdapterView.OnItemSelectedListener {
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
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_time_choose_dialog, container, false);
        ButterKnife.bind(this,view);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.time_spinner,
                android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSelectTimeSpinner.setAdapter(spinnerAdapter);

        enableWidgets(false);

        mSelectTimeSpinner.setOnItemSelectedListener(this);

        mSubmitBtn.setOnClickListener(v -> {
            if(!validateTimeEditText()){
                return;
            }

            PollService.setServiceAlarm(getActivity(),true, TimeUnit.HOURS.toMillis(Long.valueOf(mCustomTimeEditText.getText().toString())));
            Toast.makeText(getActivity(), getString(R.string.submit_time_done), Toast.LENGTH_SHORT).show();


        });


        mServiceToggleBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){

                enableWidgets(true);

            }else {

                PollService.setServiceAlarm(getActivity(),false,1l);
                Toast.makeText(getActivity(), getString(R.string.service_turmed_off), Toast.LENGTH_SHORT).show();
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
        }
        else if(Integer.valueOf(mCustomTimeEditText.getText().toString())<1){
            mCustomTimeInputLayout.setError(getString(R.string.time_limit));
            requestFocus(mCustomTimeEditText);
            return false;
        }
        else {
            mCustomTimeInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                mCustomTimeEditText.setText("3");
                break;
            case 1:
                mCustomTimeEditText.setText("5");
                break;
            case 2:
                mCustomTimeEditText.setText("8");
                break;
            case 3:
                mCustomTimeEditText.setText("12");
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
