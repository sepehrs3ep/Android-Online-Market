package project.com.maktab.onlinemarket.database;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class FavoriteProducts {
    
    @Id(autoincrement = true)
    private Long id;

    private String productId;

    @Generated(hash = 853441874)
    public FavoriteProducts(Long id, String productId) {
        this.id = id;
        this.productId = productId;
    }

    @Generated(hash = 1487100775)
    public FavoriteProducts() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }



}
