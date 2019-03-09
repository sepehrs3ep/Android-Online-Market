package project.com.maktab.onlinemarket.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.controller.fragment.AddEditCustomerFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class AddEditCustomerActivity extends SingleFragmentActivity {
    private static final String CUSTOMER_ID_EXTRA = "CUSTOMER_ID_EXTRA";
    private static final String CUSTOMER_FOR_EDIT_EXTRA = "CUSTOMER_FOR_EDIT_EXTRA";

    public static Intent newIntent(Context context,long customerId, boolean isEdit){
        Intent intent = new Intent(context,AddEditCustomerActivity.class);
        intent.putExtra(CUSTOMER_ID_EXTRA,customerId);
        intent.putExtra(CUSTOMER_FOR_EDIT_EXTRA,isEdit);
        return intent;
    }


    @Override
    public Fragment createFragment() {
        return AddEditCustomerFragment.newInstance(
                getIntent().getLongExtra(CUSTOMER_ID_EXTRA,-1),
                getIntent().getBooleanExtra(CUSTOMER_FOR_EDIT_EXTRA,false)
        );
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_single_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
