package com.example.giphy.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Window;

import com.example.giphy.R;
import com.example.giphy.adapters.GiphyAdapter;
import com.example.giphy.models.GIPHY;
import com.example.giphy.models.GiphyImage;
import com.example.giphy.presenters.GiphyPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class GiphyActivity extends AppCompatActivity {

    protected GiphyPresenter presenter;
    protected GiphyAdapter adapter;

    protected AppCompatEditText editText;
    protected RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_giphy);

        editText = (AppCompatEditText) findViewById(R.id.edit_text);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        presenter = new GiphyPresenter();
        adapter = new GiphyAdapter(getContext());

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

    }

    protected Context getContext() {
        return getApplicationContext();
    }
}
