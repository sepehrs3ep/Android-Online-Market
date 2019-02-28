package project.com.maktab.onlinemarket.model.product;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ProductAttribute {


    private long id;

    private String name;

    private List<String> options;

    private long position;

    public ProductAttribute(long id, String name, List<String> options, long position) {
        this.id = id;
        this.name = name;
        this.options = options;
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getOptions() {
        return options;
    }

    public long getPosition() {
        return position;
    }


}
