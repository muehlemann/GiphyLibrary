package com.example.giphy.presenters;

import com.example.giphy.models.GIPHY;
import com.example.giphy.network.GiphyApi;
import com.example.giphy.network.GiphyInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class GiphyPresenter {

    private static final String BASE_URL = "https://api.giphy.com";

    private Retrofit retrofit;
    private GiphyApi giphyApi;

    public GiphyPresenter(String apiKey) {
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

    public Observable<GIPHY> getTrending(int offset) {
        return giphyApi.getTrending(offset);
    }

    public Observable<GIPHY> getSearch(String query, int offset) {
        return giphyApi.getSearch(query, offset);
    }
}
