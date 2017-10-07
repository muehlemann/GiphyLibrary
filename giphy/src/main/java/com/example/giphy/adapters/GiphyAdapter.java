package com.example.giphy.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.giphy.R;
import com.example.giphy.network.GiphyApi;
import com.example.giphy.presenters.GiphyPresenter;
import com.example.giphy.views.GiphyView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class GiphyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private GiphyPresenter presenter;

    public GiphyAdapter(@NonNull Context context) {
        this.context   = context;
        this.inflater  = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GiphyView(inflater.inflate(R.layout.view_giphy, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        GiphyView view = (GiphyView) holder;
        view.loadGif("https://media.giphy.com/media/xT9IgNPVbGsa0Wd8li/giphy.gif");

    }

    @Override
    public int getItemCount() {
        return 50;
    }


}
