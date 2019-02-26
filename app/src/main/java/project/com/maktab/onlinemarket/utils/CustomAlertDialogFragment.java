package project.com.maktab.onlinemarket.utils;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import project.com.maktab.onlinemarket.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomAlertDialogFragment extends DialogFragment {

    private static final String DIALOG_TITLE_ARGS = "DIALOG_TITLE_ARGS";
    private String mDialogTitle;
    private OnYesNoDialogClick yesNoClick;


    public void setOnYesNoClick(OnYesNoDialogClick yesNoClick) {
        this.yesNoClick = yesNoClick;
    }
    public static CustomAlertDialogFragment newInstance(String dialogTitle) {

        Bundle args = new Bundle();
        args.putString(DIALOG_TITLE_ARGS,dialogTitle);
        CustomAlertDialogFragment fragment = new CustomAlertDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialogTitle = getArguments().getString(DIALOG_TITLE_ARGS);
    }

    public CustomAlertDialogFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder
                .setMessage(mDialogTitle)
                .setNegativeButton(R.string.no, (arg0, arg1) -> {
                    if(yesNoClick != null)
                        yesNoClick.onNoClicked();
                })

                .setPositiveButton(R.string.yes, (arg0, arg1) -> {
                    if(yesNoClick != null)
                        yesNoClick.onYesClicked();
                });
        Dialog dialog = builder.create();

/*
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
*/

        return dialog;


    }
    public interface OnYesNoDialogClick{
        void onYesClicked();
        void onNoClicked();
    }
}

