package com.example.giphy;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

class GiphyPresenter {

    private static final String BASE_URL = "https://api.giphy.com";

    private Retrofit retrofit;
    private GiphyApi giphyApi;

    GiphyPresenter(String apiKey) {
        this.retrofit = getRetrofit(apiKey);
        this.giphyApi = retrofit.create(GiphyApi.class);
    }

    // =============================================================================================
    // Retrofit Configurations
    // =============================================================================================

    private Retrofit getRetrofit(String apiKey) {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getHttpClient(apiKey))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    private OkHttpClient getHttpClient(String apiKey) {
        return new OkHttpClient.Builder().addInterceptor(new GiphyInterceptor(apiKey)).build();
    }

    // =============================================================================================
    // Calls
    // =============================================================================================

    Observable<GIPHY> getTrending(int offset) {
        return giphyApi.getTrending(offset);
    }

    Observable<GIPHY> getSearch(String query, int offset) {
        return giphyApi.getSearch(query, offset);
    }
}
