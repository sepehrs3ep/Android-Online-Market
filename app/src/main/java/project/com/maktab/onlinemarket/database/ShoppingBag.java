package project.com.maktab.onlinemarket.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ShoppingBag {

    @Id(autoincrement = true)
    private Long id;

    private String productId;

    private int productNumber;

    @Generated(hash = 1296866025)
    public ShoppingBag(Long id, String productId, int productNumber) {
        this.id = id;
        this.productId = productId;
        this.productNumber = productNumber;
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

    public int getProductNumber() {
        return this.productNumber;
    }

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }




}
