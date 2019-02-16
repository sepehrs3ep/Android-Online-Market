package project.com.maktab.onlinemarket.model.category;

import project.com.maktab.onlinemarket.model.product.Image;

public class Category {

    private long id;

    private String name;

    private long parent;

    private long count;

    private String slug;

    private Image image;

    public Category(long id, String name, long parent, long count, String slug, Image image) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.count = count;
        this.slug = slug;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getParent() {
        return parent;
    }

    public long getCount() {
        return count;
    }

    public String getSlug() {
        return slug;
    }

    public Image getImage() {
        return image;
    }
}
