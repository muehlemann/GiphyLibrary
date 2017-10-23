package com.example.giphy.network;

import com.example.giphy.models.GIPHY;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface GiphyApi {

    @GET("/v1/gifs/trending")
    Observable<GIPHY> getTrending();

    @GET("/v1/gifs/search")
    Observable<GIPHY> getSearch(@Query("q") String q);
}
