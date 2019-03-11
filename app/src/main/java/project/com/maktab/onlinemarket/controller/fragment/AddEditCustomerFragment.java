package project.com.maktab.onlinemarket.controller.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.controller.activity.MapsActivity;
import project.com.maktab.onlinemarket.eventbus.AddCustomerMassage;
import project.com.maktab.onlinemarket.model.customer.Billing;
import project.com.maktab.onlinemarket.model.customer.Customer;
import project.com.maktab.onlinemarket.network.webservices.add_customer.CustomerProcess;
import project.com.maktab.onlinemarket.network.webservices.add_customer.CustomerResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditCustomerFragment extends VisibleFragment {
    private static final String CUSTOMER_ID_ARGS = "CUSTOMER_ID_ARGS";
    private static final String FOR_EDIT_ARGS = "FOR_EDIT_ARGS";
    private long mCustomerId;
    private boolean mIsEdit;

    @BindView(R.id.get_customer_progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.select_map_image_view)
    ImageView mMapImageView;
    @BindView(R.id.customer_name_layout)
    TextInputLayout mCustomerNameLayout;
    @BindView(R.id.customer_phone_layout)
    TextInputLayout mCustomerPhoneLayout;
    @BindView(R.id.customer_province_layout)
    TextInputLayout mCustomerProvinceLayout;
    @BindView(R.id.customer_city_layout)
    TextInputLayout mCustomerCityLayout;
    @BindView(R.id.customer_address_layout)
    TextInputLayout mCustomerAddressLayout;
    @BindView(R.id.customer_postal_code_layout)
    TextInputLayout mCustomerPostalLayout;
    @BindView(R.id.customer_email_layout)
    TextInputLayout mCustomerEmailLayout;

    @BindView(R.id.customer_name_edit_text)
    EditText mCustomerNameEditText;
    @BindView(R.id.customer_phone_edit_text)
    EditText mCustomerPhoneEditText;
    @BindView(R.id.customer_province_edit_text)
    EditText mCustomerProvinceEditText;
    @BindView(R.id.customer_city_edit_text)
    EditText mCustomerCityEditText;
    @BindView(R.id.customer_postal_code_edit_text)
    EditText mCustomerPostalEditText;
    @BindView(R.id.customer_address_edit_text)
    EditText mCustomerAddressEditText;
    @BindView(R.id.customer_email_edit_text)
    EditText mCustomerEmailEditText;

    @BindView(R.id.add_edit_customer_btn)
    Button mSubmitBtn;


    public static AddEditCustomerFragment newInstance(long customerId, boolean isEdit) {

        Bundle args = new Bundle();
        args.putLong(CUSTOMER_ID_ARGS, customerId);
        args.putBoolean(FOR_EDIT_ARGS, isEdit);
        AddEditCustomerFragment fragment = new AddEditCustomerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AddEditCustomerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomerId = getArguments().getLong(CUSTOMER_ID_ARGS);
        mIsEdit = getArguments().getBoolean(FOR_EDIT_ARGS, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_edit_customer, container, false);
        ButterKnife.bind(this, view);
        mProgressBar.setVisibility(View.GONE);
        Picasso.get().load(R.drawable.mapmap).into(mMapImageView);

        mMapImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MapsActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

        if (mIsEdit) {
            mProgressBar.setVisibility(View.VISIBLE);
            CustomerProcess customerProcess = new CustomerProcess(String.valueOf(mCustomerId));
            customerProcess.getCustomer(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getActivity(), R.string.problem_response, Toast.LENGTH_SHORT).show();
                        mProgressBar.setVisibility(View.GONE);
                        return;
                    }
                    Customer customer = (Customer) response.body();
                    mCustomerNameEditText.setText(customer.getName().concat(customer.getLastName()));
                    mCustomerPhoneEditText.setText(customer.getBilling().getPhone());
                    mCustomerEmailEditText.setText(customer.getEmail());
                    mCustomerProvinceEditText.setText(customer.getBilling().getCountry());
                    mCustomerCityEditText.setText(customer.getBilling().getCity());
                    mCustomerPostalEditText.setText(customer.getBilling().getPostCode());
                    mCustomerAddressEditText.setText(customer.getBilling().getFirstAddress());
                    mProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(getActivity(), R.string.problem_response, Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.GONE);
                }
            });


        }


        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateCustomerAddress() || !validateCustomerCity() || !validateCustomerName() || !validateCustomerProvince() || !validateCustomerPostal()
                        || !validateCustomerPhone() || !validateCustomerEmail()) {
                    return;
                }
                String name = mCustomerNameEditText.getText().toString();
                String[] splited = name.split("\\s+");
                String phone = mCustomerPhoneEditText.getText().toString();
                String province = mCustomerProvinceEditText.getText().toString();
                String city = mCustomerCityEditText.getText().toString();
                String address = mCustomerAddressEditText.getText().toString();
                String postal = mCustomerPostalEditText.getText().toString();
                String email = mCustomerEmailEditText.getText().toString();

                Customer customer = new Customer();
                customer.setName(splited[0]);
                customer.setLastName(splited[1]);
                Billing billing = new Billing();
                billing.setName(splited[0]);
                billing.setLastName(splited[1]);
                billing.setEmail(email);
                billing.setCity(city);
                billing.setCountry(province);
                billing.setPhone(phone);
                billing.setFirstAddress(address);
                billing.setPostCode(postal);

                customer.setEmail(email);

                if (mIsEdit) {
                    CustomerProcess customerProcess = new CustomerProcess(String.valueOf(mCustomerId));
                    customerProcess.updateCustomer(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(getActivity(), response.code() + "", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Toast.makeText(getActivity(), R.string.successfully + "your id is : " + ((CustomerResponse) response.body()).getId(), Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().postSticky(new AddCustomerMassage());
                            getActivity().finish();

                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(getActivity(), R.string.problem_response, Toast.LENGTH_SHORT).show();
                        }
                    });


                } else {
                    customer.setBilling(billing);
                    CustomerProcess customerProcess = new CustomerProcess(customer);
                    customerProcess.send(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(getActivity(), response.code() + "", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Toast.makeText(getActivity(), R.string.successfully + "your id is : " + ((CustomerResponse) response.body()).getId(), Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().postSticky(new AddCustomerMassage());
                            getActivity().finish();

                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(getActivity(), R.string.problem_response, Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });


        return view;
    }

    private boolean validateCustomerName() {
        if (mCustomerNameEditText.getText().toString().trim().isEmpty()) {
            mCustomerNameLayout.setError(getString(R.string.empty_error));
            requestFocus(mCustomerNameEditText);
            return false;
        } else {
            mCustomerNameLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateCustomerPhone() {
        if (mCustomerPhoneEditText.getText().toString().trim().isEmpty()) {
            mCustomerPhoneLayout.setError(getString(R.string.empty_error));
            requestFocus(mCustomerPhoneEditText);
            return false;
        } else {
            mCustomerPhoneLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateCustomerProvince() {
        if (mCustomerProvinceEditText.getText().toString().trim().isEmpty()) {
            mCustomerProvinceLayout.setError(getString(R.string.empty_error));
            requestFocus(mCustomerProvinceEditText);
            return false;
        } else {
            mCustomerProvinceLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateCustomerCity() {
        if (mCustomerCityEditText.getText().toString().trim().isEmpty()) {
            mCustomerCityLayout.setError(getString(R.string.empty_error));
            requestFocus(mCustomerCityEditText);
            return false;
        } else {
            mCustomerCityLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateCustomerPostal() {
        if (mCustomerPostalEditText.getText().toString().trim().isEmpty()) {
            mCustomerPostalLayout.setError(getString(R.string.empty_error));
            requestFocus(mCustomerPostalEditText);
            return false;
        } else {
            mCustomerPostalLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateCustomerAddress() {
        if (mCustomerAddressEditText.getText().toString().trim().isEmpty()) {
            mCustomerAddressLayout.setError(getString(R.string.empty_error));
            requestFocus(mCustomerAddressEditText);
            return false;
        } else {
            mCustomerAddressLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateCustomerEmail() {
        String emailText = mCustomerEmailEditText.getText().toString().trim();
        if (emailText.isEmpty()) {
            mCustomerEmailLayout.setError(getString(R.string.empty_error));
            requestFocus(mCustomerEmailEditText);
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            mCustomerEmailLayout.setError(getString(R.string.email_wrong));
            requestFocus(mCustomerEmailEditText);
            return false;
        } else {
            mCustomerEmailLayout.setErrorEnabled(false);
        }
        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}
