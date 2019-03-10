package project.com.maktab.onlinemarket.model.order;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import project.com.maktab.onlinemarket.model.customer.Billing;

public class Order {

    private Billing billing;

    @SerializedName("line_items")
    private List<OrderItem> orderItemsList;

    public Order(Billing billing, List<OrderItem> orderItemsList) {
        this.billing = billing;
        this.orderItemsList = orderItemsList;
    }

    public Billing getBilling() {
        return billing;
    }

    public List<OrderItem> getOrderItemsList() {
        return orderItemsList;
    }

    public Order() {
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    public void setOrderItemsList(List<OrderItem> orderItemsList) {
        this.orderItemsList = orderItemsList;
    }
}
