package project.com.maktab.onlinemarket.model;

public class Category {

    private String id;
    private String name;

    private String slug;

    public Category(String id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }
}
