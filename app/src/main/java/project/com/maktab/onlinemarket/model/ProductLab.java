package project.com.maktab.onlinemarket.model;

import java.util.ArrayList;
import java.util.List;

public class ProductLab {
    private static ProductLab mInstance;
    private List<Product> mNewProducts;
    private List<Product> mRatedProducts;
    private List<Product> mVisitedProducts;

    public List<Product> getRatedProducts() {
        return mRatedProducts;
    }

    public void setRatedProducts(List<Product> ratedProducts) {
        mRatedProducts = ratedProducts;
    }

    public List<Product> getVisitedProducts() {
        return mVisitedProducts;
    }

    public void setVisitedProducts(List<Product> visitedProducts) {
        mVisitedProducts = visitedProducts;
    }

    private ProductLab() {
        mNewProducts = new ArrayList<>();
    }

    public static ProductLab getInstance() {
        if (mInstance == null)
            mInstance = new ProductLab();

        return mInstance;
    }

    public List<Product> getNewProducts() {
        return mNewProducts;
    }

    public void setNewProducts(List<Product> products) {
        mNewProducts = products;
    }

    public Product getProductById(String id) {
        for (Product product : mNewProducts) {
            if (product.getId().equalsIgnoreCase(id))
                return product;
        }

        return null;
    }
}
