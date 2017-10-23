package com.example.muehlemann.giphylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.giphy.Interface.GiphyLibrary;
import com.example.giphy.activities.GiphyActivity;
import com.example.giphy.adapters.GiphyAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivityForResult(GiphyLibrary.start(this, "dc6zaTOxFJmzC"), GiphyLibrary.REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GiphyLibrary.REQUEST_CODE:
                Log.d("Giphy: ", data.getStringExtra(GiphyLibrary.GIPHY_URL));
                break;
            default:
                break;
        }

    }
}
