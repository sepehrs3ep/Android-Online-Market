package project.com.maktab.onlinemarket.controller.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.controller.activity.AddEditCustomerActivity;
import project.com.maktab.onlinemarket.database.ShoppingBag;
import project.com.maktab.onlinemarket.eventbus.AddCustomerMassage;
import project.com.maktab.onlinemarket.model.customer.Customer;
import project.com.maktab.onlinemarket.model.order.Order;
import project.com.maktab.onlinemarket.model.order.OrderItem;
import project.com.maktab.onlinemarket.model.product.ProductLab;
import project.com.maktab.onlinemarket.network.webservices.add_customer.CustomerProcess;
import project.com.maktab.onlinemarket.network.webservices.add_order.OrderProcess;
import project.com.maktab.onlinemarket.network.webservices.add_order.OrderResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubmitInfoFragment extends VisibleFragment {

    @BindView(R.id.customer_info_recycler)
    RecyclerView mCustomerInfoRecycler;
    @BindView(R.id.add_info_float_btn)
    FloatingActionButton mAddCustomerFloatBtn;
    @BindView(R.id.submit_info_progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.submit_customer_info_btn)
    Button mSubmitCustomerBtn;
    int selected_position = 0;
    private CustomerInfoAdapter mAdapter;
    private List<Customer> mCustomerList;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomerList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_submit_info, container, false);
        ButterKnife.bind(this, view);
        mCustomerInfoRecycler.setLayoutManager(getHorizontalLayoutManager());
        updateUI();

        mAddCustomerFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddEditCustomerActivity.newIntent(getActivity(), -1, false);
                startActivity(intent);
            }
        });

        mSubmitCustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Customer customer = mCustomerList.get(selected_position);

                List<OrderItem> orderItemList = new ArrayList<>();
                List<ShoppingBag> shoppingBags = ProductLab.getInstance().getAllShopBagEntity();
                for (ShoppingBag bags : shoppingBags) {

                    OrderItem orderItem = new OrderItem();
                    orderItem.setNumber(bags.getProductNumber());
                    orderItem.setProductId(bags.getId());

                }

                Order order = new Order();
                order.setOrderItemsList(orderItemList);
                order.setBilling(customer.getBilling());

                OrderProcess orderProcess = new OrderProcess(order);
                orderProcess.send(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getActivity(), R.string.problem_response + response.code() + " ", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(getActivity(), R.string.successfully + ((OrderResponse) response.body()).getId() + " ", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(getActivity(), R.string.problem_response, Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


        return view;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void addCustomerListener(AddCustomerMassage addCustomerMassage) {
        EventBus.getDefault().removeStickyEvent(addCustomerMassage);
        updateUI();
    }

    private void updateUI() {
        mProgressBar.setVisibility(View.VISIBLE);

        if (isAdded()) {
            CustomerProcess customerProcess = new CustomerProcess();
            customerProcess.getAllCustomerList(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getActivity(), R.string.problem_response, Toast.LENGTH_SHORT).show();
                        mProgressBar.setVisibility(View.GONE);
                        return;
                    }
                    mCustomerList = (List<Customer>) response.body();
                    if (mAdapter == null) {
                        mAdapter = new CustomerInfoAdapter();
                        mCustomerInfoRecycler.setAdapter(mAdapter);
                    } else {
//                        mAdapter.setCustomerList(customerList);
                        mAdapter.notifyDataSetChanged();
                    }
                    mProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(getActivity(), R.string.problem_response, Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.GONE);
                }
            });

        }

    }


    private LinearLayoutManager getHorizontalLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
    }


    class CustomerInfoHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.edit_customer_info_image_view)
        ImageView mEditCustomerImageView;

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

        private Customer mCustomer;

        public CustomerInfoHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mEditCustomerImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = AddEditCustomerActivity.newIntent(getActivity(),mCustomer.getId(),true);
                    startActivity(intent);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

                    // Updating old as well as new positions
                    mAdapter.notifyItemChanged(selected_position);
                    selected_position = getAdapterPosition();
                    mAdapter.notifyItemChanged(selected_position);

                }
            });


        }

        public void bind(Customer customer) {
            mCustomer = customer;
            mCustomerNameTv.setText(customer.getName().concat(customer.getLastName()));
            mProvinceTextView.setText(getString(R.string.province, customer.getBilling().getCountry()));
            mCityTextView.setText(getString(R.string.city, customer.getBilling().getCity()));
            mAddressTextView.setText(customer.getBilling().getFirstAddress());
            mNumberTextView.setText(customer.getBilling().getPhone());

        }

    }

    private class CustomerInfoAdapter extends RecyclerView.Adapter<CustomerInfoHolder> {

        @NonNull
        @Override
        public CustomerInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.customer_info_list_item, parent, false);
            return new CustomerInfoHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomerInfoHolder holder, int position) {
            holder.itemView.setBackgroundColor(selected_position == position ? Color.GREEN : Color.TRANSPARENT);
            Customer customer = mCustomerList.get(position);
            holder.bind(customer);
        }

        @Override
        public int getItemCount() {
            return mCustomerList.size();
        }
    }

}
