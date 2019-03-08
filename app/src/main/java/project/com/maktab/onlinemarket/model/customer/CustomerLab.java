package project.com.maktab.onlinemarket.model.customer;

import java.util.List;

import project.com.maktab.onlinemarket.OnlineMarketApp;
import project.com.maktab.onlinemarket.database.CustomerId;
import project.com.maktab.onlinemarket.database.CustomerIdDao;

public class CustomerLab {
    private static CustomerLab mCustomerLabInstance;
    private CustomerIdDao mCustomerIdDao;

    private CustomerLab() {
        mCustomerIdDao = OnlineMarketApp.getAppInstance().getDaoSession().getCustomerIdDao();
    }
    public void addCustomer(CustomerId customerId){
        List<CustomerId> result = mCustomerIdDao.queryBuilder()
                .where(CustomerIdDao.Properties.CustomerId.eq(customerId.getCustomerId()))
                .list();
        if(result==null&&result.size()<=0)
            mCustomerIdDao.insert(customerId);
    }
    public Long getCustomerId(long customerId){
        List<CustomerId> result = mCustomerIdDao.queryBuilder()
                .where(CustomerIdDao.Properties.CustomerId.eq(customerId))
                .list();
        if(result!=null&&result.size()>0)
            return result.get(0).getCustomerId();
        return null;

    }
    public List<CustomerId> getAllCustomers(){
        return mCustomerIdDao.loadAll();
    }

    public static CustomerLab getmCustomerLabInstance() {
        if(mCustomerLabInstance==null)
            mCustomerLabInstance = new CustomerLab();
        return mCustomerLabInstance;
    }
}
