package com.gino.moment.network;

import android.annotation.SuppressLint;
import android.util.Base64;

import com.squareup.picasso.OkHttp3Downloader;

import java.io.IOException;

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

    public static Base64HttpClient build() {
        if (singleton == null) {
            synchronized (Base64HttpClient.class) {
                if (singleton == null) {
                    singleton = new Base64HttpClient();
                }
            }
        }
        return singleton;
    }

    private Base64HttpClient() {
        builderOkHttpClient = new OkHttpClient.Builder();
        builderOkHttpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .build();
                Response response = chain.proceed(newRequest);
                MediaType contentType = response.body().contentType();
                String base64String = response.body().string();
                byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
                ResponseBody body = ResponseBody.create(contentType, decodedString);
                response = response.newBuilder().body(body).build();
                return response;
            }
        });
    }

    public OkHttp3Downloader getOkHttp3Downloader() {
        return new OkHttp3Downloader(builderOkHttpClient.build());
    }
}
