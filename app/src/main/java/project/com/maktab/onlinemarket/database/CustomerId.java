package project.com.maktab.onlinemarket.database;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class CustomerId {
    @Id(autoincrement = true)
    private Long id;

    private long customerId;

    @Generated(hash = 2086015804)
    public CustomerId(Long id, long customerId) {
        this.id = id;
        this.customerId = customerId;
    }

    @Generated(hash = 1486981033)
    public CustomerId() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    


}
