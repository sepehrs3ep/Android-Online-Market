package project.com.maktab.onlinemarket.network.webservices.add_order;

import project.com.maktab.onlinemarket.model.order.Order;
import project.com.maktab.onlinemarket.network.base.Api;
import project.com.maktab.onlinemarket.network.base.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;

public class OrderProcess {
    private Order mOrder;

    public OrderProcess(Order order) {
        mOrder = order;
    }
    public void send(Callback callback){
        Call<OrderResponse> call = RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .addOrder(mOrder);
        call.enqueue(callback);
    }

}
