package project.com.maktab.onlinemarket;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogLab {
    private ProgressDialog mProgressDialog;
    private static ProgressDialogLab mProgressInstance;

    private ProgressDialogLab() {

    }

    public static ProgressDialogLab getmProgressInstance() {

        if(mProgressInstance==null){
            mProgressInstance = new ProgressDialogLab();
        }

        return mProgressInstance;
    }
    public ProgressDialog getProgressDialog(){
        if(mProgressDialog!=null)
            return mProgressDialog;
        return null;
    }

    public ProgressDialog getProgressDialog(Context context) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(context.getString(R.string.load_data));
        return mProgressDialog;
    }
}
