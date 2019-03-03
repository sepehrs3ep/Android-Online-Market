package project.com.maktab.onlinemarket.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import androidx.core.app.NotificationCompat;
import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.controller.activity.StartActivity;
import project.com.maktab.onlinemarket.model.product.Product;
import project.com.maktab.onlinemarket.network.base.Api;
import project.com.maktab.onlinemarket.network.base.RetrofitClientInstance;
import retrofit2.Response;

public class Services {

    public static final int PENDING_INTENT_REQUEST_CODE = 23;

    public static void pollServerAndShowNotification(Context context) {

        try {
            Response<List<Product>> response = RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                    .getAllProducts("date").execute();

            if (response.isSuccessful()) {
                Product product = response.body().get(0);
                if (product.getId().equalsIgnoreCase(SharedPref.getProductLastId())) {
                    //old Result
                } else {
                    //new Result


                }
                SharedPref.setProductLastId(product.getId());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void showNotification(Context context, Product product) {
        Notification notification = null;
        Intent intent = StartActivity.newIntent(context);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, PENDING_INTENT_REQUEST_CODE, intent, 0);

        try {
            notification = new NotificationCompat.Builder(context, context.getString(R.string.channel_id))
                    .setSmallIcon(R.drawable.marketonline)
                    .setContentTitle(context.getString(R.string.notif_new_product))
                    .setContentInfo(product.getName())
                    .setLargeIcon(getBitmap(new URL(product.getImages().get(0).getPath())))
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

    private static Bitmap getBitmap(URL url) {
        Bitmap image = null;
        try {
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}
