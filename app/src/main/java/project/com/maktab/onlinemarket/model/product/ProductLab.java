package project.com.maktab.onlinemarket.model.product;

import android.os.Build;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.RequiresApi;
import project.com.maktab.onlinemarket.OnlineMarketApp;
import project.com.maktab.onlinemarket.database.FavoriteProducts;
import project.com.maktab.onlinemarket.database.FavoriteProductsDao;
import project.com.maktab.onlinemarket.database.ShoppingBag;
import project.com.maktab.onlinemarket.database.ShoppingBagDao;

public class ProductLab {
    private static ProductLab mInstance;
    private List<Product> mNewProducts;
    private List<Product> mRatedProducts;
    private List<Product> mVisitedProducts;
    private ShoppingBagDao mBagDao;
    private FavoriteProductsDao mFavoriteProductsDao;

    public List<Product> getRatedProducts() {
        return mRatedProducts;
    }

    public void setRatedProducts(List<Product> ratedProducts) {
        mRatedProducts = ratedProducts;
    }

    public List<Product> getVisitedProducts() {
        return mVisitedProducts;
    }

    public void setVisitedProducts(List<Product> visitedProducts) {
        mVisitedProducts = visitedProducts;
    }

    private ProductLab() {
        mNewProducts = new ArrayList<>();
        mBagDao = OnlineMarketApp.getAppInstance().getDaoSession().getShoppingBagDao();
        mFavoriteProductsDao = OnlineMarketApp.getAppInstance().getDaoSession().getFavoriteProductsDao();
    }

    public void addToBag(String id) {
        List<ShoppingBag> checkList = mBagDao.queryBuilder()
                .where(ShoppingBagDao.Properties.ProductId.eq(id))
                .list();
        if (checkList != null && checkList.size() > 0)
            return;
        ShoppingBag shoppingBag = new ShoppingBag();
        shoppingBag.setProductId(id);
        mBagDao.insert(shoppingBag);
    }

    public void addToFavorite(String id) {
        FavoriteProducts favoriteProducts = new FavoriteProducts();
        favoriteProducts.setProductId(id);
        mFavoriteProductsDao.insert(favoriteProducts);
    }

    public void deleteFromBag(String productId) {

        List<ShoppingBag> result = mBagDao.queryBuilder()
                .where(ShoppingBagDao.Properties.ProductId.eq(productId))
                .list();
        ShoppingBag bag = result.get(0);
        mBagDao.delete(bag);
    }

    public boolean isFavorite(String id) {
        List<FavoriteProducts> result = mFavoriteProductsDao.queryBuilder().where(
                FavoriteProductsDao.Properties.ProductId.eq(id)
        ).list();
        return result.size() > 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<String> getFavoriteProducts() {
        List<FavoriteProducts> list = mFavoriteProductsDao.loadAll();
        List<String> result = list.stream().map(FavoriteProducts::getProductId).collect(Collectors.toList());
        return result;
    }

    public List<String> getShoppingBag() {
        List<ShoppingBag> list = mBagDao.loadAll();
        List<String> result = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = list.stream().map(ShoppingBag::getProductId).collect(Collectors.toList());
        }
        return result;
    }


    public static ProductLab getInstance() {
        if (mInstance == null)
            mInstance = new ProductLab();

        return mInstance;
    }

    public List<Product> getNewProducts() {
        return mNewProducts;
    }

    public void setNewProducts(List<Product> products) {
        mNewProducts = products;
    }

    public Product getProductById(String id) {
        for (Product product : mNewProducts) {
            if (product.getId().equalsIgnoreCase(id))
                return product;
        }

        return null;
    }

}
