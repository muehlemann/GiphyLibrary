package com.example.giphy;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class GiphyInterceptor implements Interceptor {

    private String apiKey;

    GiphyInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // Parse request
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        // Change request
        HttpUrl url = originalHttpUrl.newBuilder().addQueryParameter("api_key", this.apiKey).build();

        // Build request
        Request.Builder requestBuilder = original.newBuilder().url(url);
        Request request = requestBuilder.build();

        return chain.proceed(request);
    }
}
