package com.example.muehlemann.giphylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.giphy.Interface.GiphyLibrary;
import com.example.giphy.activities.GiphyActivity;
import com.example.giphy.adapters.GiphyAdapter;

public class MainActivity extends AppCompatActivity {

    private final static String API_KEY = "dc6zaTOxFJmzC";

    public AppCompatImageView imageView;
    public AppCompatButton button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (AppCompatImageView) this.findViewById(R.id.image_view);

        button = (AppCompatButton) this.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(GiphyLibrary.start(MainActivity.this, API_KEY), GiphyLibrary.REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GiphyLibrary.REQUEST_CODE:
                    String url = data.getStringExtra(GiphyLibrary.GIPHY_URL);
                    Log.d("GIF: ", url);
                    Glide.with(MainActivity.this).load(url).into(imageView);
                    break;
                default:
                    break;
            }
        }

    }
}
