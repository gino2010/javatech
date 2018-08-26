package com.gino.moment.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Base64;

import com.squareup.picasso.OkHttp3Downloader;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Base64HttpClient {
    @SuppressLint("StaticFieldLeak")
    private static volatile Base64HttpClient singleton = null;
    private OkHttpClient.Builder builderOkHttpClient;
    private Context context;

    public static Base64HttpClient build(Context context) {
        if (singleton == null) {
            synchronized (Base64HttpClient.class) {
                if (singleton == null) {
                    singleton = new Base64HttpClient(context);
                }
            }
        }
        return singleton;
    }

    private Base64HttpClient(Context context) {
        this.context = context;

        builderOkHttpClient = new OkHttpClient.Builder();
        builderOkHttpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .build();
                Response response = chain.proceed(newRequest);
//                try {
                MediaType contentType = response.body().contentType();
                String base64String = response.body().string();
//                base64String = base64String.replace("data:image/jpeg;base64,", "");
                byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
                ResponseBody body = ResponseBody.create(contentType, decodedString);
                response = response.newBuilder().body(body).build();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                return response;
            }
        });
    }

    public OkHttp3Downloader getOkHttp3Downloader() {
//        int cacheSize = 50 * 1024 * 1024;
//        Cache cache = new Cache(context.getCacheDir(), cacheSize);
//        builderOkHttpClient.cache(cache);
        return new OkHttp3Downloader(builderOkHttpClient.build());
    }
}
