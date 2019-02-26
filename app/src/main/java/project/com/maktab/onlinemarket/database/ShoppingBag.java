package project.com.maktab.onlinemarket.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ShoppingBag {

    @Id(autoincrement = true)
    private Long id;

    private String productId;

    @Generated(hash = 921778862)
    public ShoppingBag(Long id, String productId) {
        this.id = id;
        this.productId = productId;
    }

    @Generated(hash = 1113443636)
    public ShoppingBag() {
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
