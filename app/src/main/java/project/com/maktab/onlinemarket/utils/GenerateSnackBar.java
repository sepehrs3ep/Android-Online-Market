package project.com.maktab.onlinemarket.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class GenerateSnackBar {
    private Snackbar mSnackbar;
    public GenerateSnackBar(Context context,int stringId) {
        View rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
        mSnackbar =Snackbar.make(rootView, stringId, Snackbar.LENGTH_LONG);
    }

    public Snackbar getSnackbar() {
        return mSnackbar;
    }
}
