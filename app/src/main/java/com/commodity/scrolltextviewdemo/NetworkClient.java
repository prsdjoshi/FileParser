package com.commodity.scrolltextviewdemo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;

/**
 * {@link #loadImage(String)} is where you should start.
 */
public class NetworkClient {

    private static final NetworkClient NETWORK_CLIENT = new NetworkClient();
    private final OkHttpClient mOkHttpClient;

    public NetworkClient() {
        mOkHttpClient = new OkHttpClient();
    }

    public static NetworkClient getInstance() {
        return NETWORK_CLIENT;
    }

    /**
     * Loads an Image from the internet.
     */
    public Observable<Bitmap> loadImage(String imageUrl) {
        return Observable.fromCallable(() -> {
            final Request loadRequest = new Request.Builder()
                    .url(imageUrl)
                    .build();

            final Response response = mOkHttpClient
                    .newCall(loadRequest)
                    .execute();

            return BitmapFactory.decodeStream(response.body().byteStream());
        });
    }

}
