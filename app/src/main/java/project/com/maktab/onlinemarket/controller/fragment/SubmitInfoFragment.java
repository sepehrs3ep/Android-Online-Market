package project.com.maktab.onlinemarket.controller.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.model.customer.Customer;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubmitInfoFragment extends Fragment {

    @BindView(R.id.customer_info_recycler)
    RecyclerView mCustomerInfoRecycler;
    @BindView(R.id.add_info_float_btn)
    FloatingActionButton mAddCustomerFloatBtn;

    public static SubmitInfoFragment newInstance() {

        Bundle args = new Bundle();

        SubmitInfoFragment fragment = new SubmitInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SubmitInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_submit_info, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    private class CustomerInfoHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.customer_name_text_view)
        TextView mCustomerNameTv;
        @BindView(R.id.province_text_view)
        TextView mProvinceTextView;
        @BindView(R.id.city_text_view)
        TextView mCityTextView;
        @BindView(R.id.address_text_view)
        TextView mAddressTextView;
        @BindView(R.id.call_number_text_view)
        TextView mNumberTextView;

        public CustomerInfoHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Customer customer) {
            mCustomerNameTv.setText(customer.getName().concat(customer.getLastName()));
            mProvinceTextView.setText(customer.getBilling().getCountry());
            mCityTextView.setText(customer.getBilling().getCity());
            mAddressTextView.setText(customer.getBilling().getFirstAddress());
            mNumberTextView.setText(customer.getBilling().getPhone());

        }

    }

    private class CustomerInfoAdapter extends RecyclerView.Adapter<CustomerInfoHolder> {
        private List<Customer> mCustomerList;

        public CustomerInfoAdapter(List<Customer> customerList) {
            mCustomerList = customerList;
        }

        public void setCustomerList(List<Customer> customerList) {
            mCustomerList = customerList;
        }

        @NonNull
        @Override
        public CustomerInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.customer_info_list_item, parent, false);
            return new CustomerInfoHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomerInfoHolder holder, int position) {
            Customer customer = mCustomerList.get(position);
            holder.bind(customer);
        }

        @Override
        public int getItemCount() {
            return mCustomerList.size();
        }
    }

}
