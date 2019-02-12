package project.com.maktab.onlinemarket.model;

import java.util.List;

public class Root {

    private List<Product> products;

    public Root(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }
}
