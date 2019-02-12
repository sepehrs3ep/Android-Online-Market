package project.com.maktab.onlinemarket.network;

import project.com.maktab.onlinemarket.model.Root;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("products?consumer_key=ck_00fdf4e3f65c5275d802b412db586ba2cac6835f&consumer_secret=cs_d2571d995db502ea4b04bfae270b92ac447eb8ba")
    Call<Root> getRoot();

}
