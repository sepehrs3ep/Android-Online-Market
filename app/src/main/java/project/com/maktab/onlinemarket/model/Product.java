package project.com.maktab.onlinemarket.model;

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

    private List<Category> categories;

    public String getPrice() {
        return price;
    }

    public Product(String id, String name, String date, String description, String sales, String rate, String price, List<Image> images, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.description = description;
        this.sales = sales;
        this.rate = rate;
        this.price = price;
        this.images = images;
        this.categories = categories;
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

    public List<Category> getCategories() {
        return categories;
    }
}
