package com.muehlemann.giphy;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class GiphyLibrary extends AppCompatActivity {

    public final static String GIPHY_URL = "giphy_url";
    public final static String API_KEY = "api_key";
    public final static int REQUEST_CODE = 1001;
    public final static int PAGE_COUNT = 25;

    public Listener listener;

    // =============================================================================================
    // Interface
    // =============================================================================================

    public interface Listener {
        void onGiphySelected(String url);
    }

    // =============================================================================================
    // Methods
    // =============================================================================================

    public void start(Activity activity, Listener listener, String apiKey) {

        this.listener = listener;

        Intent intent = new Intent(activity, GiphyActivity.class);
        intent.putExtra(API_KEY, apiKey);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GiphyLibrary.REQUEST_CODE:
                    if (listener != null) {
                        listener.onGiphySelected(data.getStringExtra(GiphyLibrary.GIPHY_URL));
                    }
                    break;
                default:
                    break;
            }
        }

    }
}
