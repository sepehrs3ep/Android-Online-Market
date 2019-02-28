package project.com.maktab.onlinemarket.model.product;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    private String id;
    private String name;

    @SerializedName("date_created")
    private String date;

    private String description;

    @SerializedName("total_sales")
    private String sales;

    @SerializedName("average_rating")
    private String rate;

    private String price;


    @SerializedName("regular_price")
    private String regularPrice;

    private List<Image> images;

    private List<ProductCategory> categories;

    @SerializedName("attributes")
    private List<ProductAttribute> mProductAttributes;

    @SerializedName("related_ids")
    private List<String> relatedProducts;

    public String getPrice() {
        return price;
    }

    public List<ProductAttribute> getProductAttributes() {
        return mProductAttributes;
    }

    public List<String> getRelatedProducts() {
        return relatedProducts;
    }

    public Product(String id, String name, String date, String description, String sales, String rate, String price, String regularPrice, List<Image> images, List<ProductCategory> categories, List<ProductAttribute> productAttributes, List<String> relatedProducts) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.description = description;
        this.sales = sales;
        this.rate = rate;
        this.price = price;
        this.regularPrice = regularPrice;
        this.images = images;
        this.categories = categories;
        this.mProductAttributes = productAttributes;
        this.relatedProducts = relatedProducts;
    }

    public String getRegularPrice() {
        return regularPrice;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getSales() {
        return sales;
    }

    public String getRate() {
        return rate;
    }

    public List<Image> getImages() {
        return images;
    }

    public List<ProductCategory> getCategories() {
        return categories;
    }
}
