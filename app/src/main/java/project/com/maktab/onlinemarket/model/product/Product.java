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

    private List<Image> images;

    private List<ProductCategory> categories;

    private List<Attribute> attributes;

    public String getPrice() {
        return price;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public Product(String id, String name, String date, String description, String sales, String rate, String price, List<Image> images, List<ProductCategory> categories, List<Attribute> attributes) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.description = description;
        this.sales = sales;
        this.rate = rate;
        this.price = price;
        this.images = images;
        this.categories = categories;
        this.attributes = attributes;
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
