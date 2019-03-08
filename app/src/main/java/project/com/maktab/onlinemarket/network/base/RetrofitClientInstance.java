package project.com.maktab.onlinemarket.network.base;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit retrofitInstance;
    private static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    private static final String USER_NAME = "ck_00fdf4e3f65c5275d802b412db586ba2cac6835f";
    private static final String PASSWORD = "cs_d2571d995db502ea4b04bfae270b92ac447eb8ba";


    public static Retrofit getRetrofitInstance() {
        if (retrofitInstance == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(100,TimeUnit.SECONDS)
                    .writeTimeout(100,TimeUnit.SECONDS)
                    .connectTimeout(100,TimeUnit.SECONDS)
                    .addInterceptor(new BasicAuthInterceptor(USER_NAME, PASSWORD))
                    .build();


            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }


        return retrofitInstance;
    }
    private static class BasicAuthInterceptor implements Interceptor {

        private String credentials;

        public BasicAuthInterceptor(String user, String password) {
            this.credentials = Credentials.basic(user, password);
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request authenticatedRequest = request.newBuilder()
                    .header("Authorization", credentials).build();
            return chain.proceed(authenticatedRequest);
        }

    }
}
