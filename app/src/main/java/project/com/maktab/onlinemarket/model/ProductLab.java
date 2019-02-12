package project.com.maktab.onlinemarket.model;

import java.util.ArrayList;
import java.util.List;

public class ProductLab {
    private static ProductLab mInstance;
    private List<Product> mProducts;

    private ProductLab() {
        mProducts = new ArrayList<>();
    }

    public static ProductLab getInstance() {
        return mInstance;
    }

    public List<Product> getProducts() {
        return mProducts;
    }

    public void setProducts(List<Product> products) {
        mProducts = products;
    }
}
