package project.com.maktab.onlinemarket.model.attributes;

public class Attributes {

    private long id;
    private String name;
    private String slug;

    public Attributes(long id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }
}
