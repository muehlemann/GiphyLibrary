package com.example.giphy.network;

import retrofit2.http.GET;
import rx.Observable;

public interface GiphyApi {

    @GET("/v1/gifs/trending&api_key=dc6zaTOxFJmzC")
    Observable<String> getTrending();
}
