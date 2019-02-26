package project.com.maktab.onlinemarket.utils;

import android.app.Activity;
import android.content.Context;

import com.google.android.material.snackbar.Snackbar;

public class GenerateSnackBar {
    private Snackbar mSnackbar;
    public GenerateSnackBar(Activity context,int stringId) {
        mSnackbar =Snackbar.make(context.findViewById(android.R.id.content), stringId, Snackbar.LENGTH_LONG);
    }

    public Snackbar getSnackbar() {
        return mSnackbar;
    }
}
