package project.com.maktab.onlinemarket.network.webservices.add_customer;

import java.util.List;

import project.com.maktab.onlinemarket.model.customer.Customer;
import project.com.maktab.onlinemarket.network.base.Api;
import project.com.maktab.onlinemarket.network.base.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;

public class CustomerProcess {
    private Customer mCustomer;
    private String mCustomerId;

    public CustomerProcess(Customer customer) {
        mCustomer = customer;
    }

    public CustomerProcess(String customerId) {
        mCustomerId = customerId;
    }

    public CustomerProcess() {
    }

    public void send(Callback callback) {
        Call<CustomerResponse> call = RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .addCustomer(mCustomer);
        call.enqueue(callback);
    }
    public void getCustomer(Callback callback){
        Call<Customer> call = RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getCustomer(mCustomerId);
        call.enqueue(callback);
    }
    public void getAllCustomerList(Callback callback){
        Call<List<Customer>> call = RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getAllCustomers();
        call.enqueue(callback);
    }
    public void updateCustomer(Callback callback){
        Call<CustomerResponse> call = RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .updateCustomer(mCustomerId);
        call.enqueue(callback);

    }

}
