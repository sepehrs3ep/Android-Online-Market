package project.com.maktab.onlinemarket.model.order;

import com.google.gson.annotations.SerializedName;

public class OrderItem {

    @SerializedName("product_id")
    private long productId;
    @SerializedName("quantity")
    private long number;

    public OrderItem(long productId, long number) {
        this.productId = productId;
        this.number = number;
    }

    public long getProductId() {
        return productId;
    }

    public long getNumber() {
        return number;
    }

    public OrderItem() {
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public void setNumber(long number) {
        this.number = number;
    }
}
