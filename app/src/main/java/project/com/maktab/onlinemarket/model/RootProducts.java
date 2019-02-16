package project.com.maktab.onlinemarket.model;

import java.util.List;

public class RootProducts {

    private List<Product> products;

    public RootProducts(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }
}
