package com.example.giphy.Interface;

import android.content.Context;
import android.content.Intent;

import com.example.giphy.activities.GiphyActivity;

public class GiphyLibrary {

    public final static String GIPHY_URL = "giphy_url";
    public final static String API_KEY = "api_key";
    public final static int REQUEST_CODE = 1001;
    public final static int PAGE_COUNT = 25;

    // =============================================================================================
    // Constructor
    // =============================================================================================

    public GiphyLibrary() throws Exception {
        throw new Exception("GiphyLibrary can't be instantiated");
    }

    // =============================================================================================
    // Methods
    // =============================================================================================

    public static Intent start(Context context, String apiKey) {
        Intent intent = new Intent(context, GiphyActivity.class);
        intent.putExtra(API_KEY, apiKey);
        return intent;
    }
}
