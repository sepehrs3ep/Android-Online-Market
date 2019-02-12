package project.com.maktab.onlinemarket.model;

import com.google.gson.annotations.SerializedName;

public class Image {

    private String id;

    @SerializedName("date_created")
    private String date;

    @SerializedName("src")
    private String path;

    public Image(String id, String date, String path) {
        this.id = id;
        this.date = date;
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getPath() {
        return path;
    }
}
