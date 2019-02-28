package project.com.maktab.onlinemarket.network;

import java.util.List;

import project.com.maktab.onlinemarket.model.category.Category;
import project.com.maktab.onlinemarket.model.product.Product;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("products/?")
    Call<List<Product>> getAllProducts(@Query("orderby") String orderType);


    @GET("products/{id}/?")
    Call<Product> getProduct(@Path("id") String productId);


    @GET("products/categories/?per_page=100")
    Call<List<Category>> getAllCategories();

    @GET("products/categories/?per_page=100")
    Call<List<Category>> getSubCategories(@Query("parent") String parentId);


    @GET("products/?")
    Call<List<Product>> getProductsSubCategoires(@Query("page") String pageNumber ,@Query("category") String categoryId);

    @GET("products/?")
    Call<List<Product>> searchProducts(@Query("search") String productName);


    @GET("products/?")
    Call<List<Product>> getReleatedProducts(@Query("include") String... releateds);


    @GET("products/?")
    Call<List<Product>> getAllProductWithPage(@Query("page") String pageNumber, @Query("orderby") String orderBy);


}
