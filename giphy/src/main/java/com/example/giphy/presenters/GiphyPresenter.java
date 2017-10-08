package com.example.giphy.presenters;

import com.example.giphy.models.GIPHY;
import com.example.giphy.network.GiphyApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class GiphyPresenter {

    private static final String BASE_URL = "https://api.giphy.com";

    private Retrofit retrofit;
    private GiphyApi giphyApi;

    public GiphyPresenter() {
        this.retrofit = getRetrofit();
        this.giphyApi = retrofit.create(GiphyApi.class);
    }

    public Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    // =============================================================================================
    // Calls
    // =============================================================================================

    public Observable<GIPHY> getTrending() {
        return giphyApi.getTrending();
    }
}
