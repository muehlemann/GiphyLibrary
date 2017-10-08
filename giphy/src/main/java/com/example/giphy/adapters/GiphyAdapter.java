package com.example.giphy.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.giphy.R;
import com.example.giphy.models.GIPHY;
import com.example.giphy.presenters.GiphyPresenter;
import com.example.giphy.views.GiphyView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class GiphyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private GiphyPresenter presenter;
    private GIPHY response;

    public GiphyAdapter(@NonNull Context context) {
        this.inflater  = LayoutInflater.from(context);
        this.presenter = new GiphyPresenter();

        this.loadTrending();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GiphyView(inflater.inflate(R.layout.view_giphy, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (response != null) {
            GiphyView view = (GiphyView) holder;
            view.loadGif(response.getData().get(position).images.fixed_width.url);
        }
    }

    @Override
    public int getItemCount() {
        if (response != null) {
            return response.getData().size();
        }

        return 0;
    }

    // =============================================================================================
    // Internals
    // =============================================================================================

    public void loadTrending() {
        Thread thread = new Thread(new Runnable(){
            public void run() {

                presenter.getTrending()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<GIPHY>() {

                            @Override
                            public void onNext(GIPHY response) {
                                GiphyAdapter.this.response = response;
                                GiphyAdapter.this.notifyDataSetChanged();
                            }

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("SHIT", "ERROR");
                                e.printStackTrace();
                            }
                        });
            }
        });

        thread.start();
    }

}
