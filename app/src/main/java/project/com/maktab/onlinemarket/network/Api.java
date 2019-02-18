package project.com.maktab.onlinemarket.network;

import java.util.List;

import project.com.maktab.onlinemarket.model.category.Category;
import project.com.maktab.onlinemarket.model.product.Product;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("products/?consumer_key=ck_00fdf4e3f65c5275d802b412db586ba2cac6835f&consumer_secret=cs_d2571d995db502ea4b04bfae270b92ac447eb8ba")
    Call<List<Product>> getAllProducts(@Query("orderby") String orderType);


    @GET("products/?consumer_key=ck_00fdf4e3f65c5275d802b412db586ba2cac6835f&consumer_secret=cs_d2571d995db502ea4b04bfae270b92ac447eb8ba" +
            "&orderby=average_rating")
    Call<List<Product>> getRatedProducts();

    @GET("products/?consumer_key=ck_00fdf4e3f65c5275d802b412db586ba2cac6835f&consumer_secret=cs_d2571d995db502ea4b04bfae270b92ac447eb8ba" +
            "&orderby=total_sales")
    Call<List<Product>> getVisitedProducts();

    @GET("products/{id}/?consumer_key=ck_00fdf4e3f65c5275d802b412db586ba2cac6835f&consumer_secret=cs_d2571d995db502ea4b04bfae270b92ac447eb8ba")
    Call<Product> getProduct(@Path("id") String productId);


    @GET("products/categories/?consumer_key=ck_00fdf4e3f65c5275d802b412db586ba2cac6835f&consumer_secret=cs_d2571d995db502ea4b04bfae270b92ac447eb8ba")
    Call<List<Category>> getAllCategories();

    @GET("products/categories/?consumer_key=ck_00fdf4e3f65c5275d802b412db586ba2cac6835f&consumer_secret=cs_d2571d995db502ea4b04bfae270b92ac447eb8ba")
    Call<List<Category>> getSubCategories(@Query("parent") String parentId);


    @GET("products/?consumer_key=ck_00fdf4e3f65c5275d802b412db586ba2cac6835f&consumer_secret=cs_d2571d995db502ea4b04bfae270b92ac447eb8ba")
    Call<List<Product>> getProductsSubCategoires(@Query("category") String categoryId);

    @GET("products/?consumer_key=ck_00fdf4e3f65c5275d802b412db586ba2cac6835f&consumer_secret=cs_d2571d995db502ea4b04bfae270b92ac447eb8ba")
    Call<List<Product>> searchProducts(@Query("search") String productName);


    @GET("products/?consumer_key=ck_00fdf4e3f65c5275d802b412db586ba2cac6835f&consumer_secret=cs_d2571d995db502ea4b04bfae270b92ac447eb8ba")
    Call<List<Product>> getReleatedProducts(@Query("include") String... releateds);


    @GET("products/?consumer_key=ck_00fdf4e3f65c5275d802b412db586ba2cac6835f&consumer_secret=cs_d2571d995db502ea4b04bfae270b92ac447eb8ba")
    Call<List<Product>> getAllProductWithPage(@Query("page") String pageNumber,@Query("orderby") String orderBy);


}
