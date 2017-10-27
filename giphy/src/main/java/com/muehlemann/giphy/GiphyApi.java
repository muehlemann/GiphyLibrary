package com.muehlemann.giphy;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

interface GiphyApi {

    @GET("/v1/gifs/trending")
    Observable<GIPHY> getTrending(
            @Query("offset") int offset
    );

    @GET("/v1/gifs/search")
    Observable<GIPHY> getSearch(
            @Query("q") String query,
            @Query("offset") int offset
    );
}
