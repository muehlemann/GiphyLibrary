package com.example.giphy.adapters;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
    private Listener listener;
    private GIPHY response;

    // =============================================================================================
    // Interface
    // =============================================================================================

    public interface Listener {
        void onSelected(String url);
    }

    // =============================================================================================
    // Constructor
    // =============================================================================================

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

            final String url = response.getData().get(position).images.fixed_width.url;

            GiphyView view = (GiphyView) holder;
            view.loadGif(url);
            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Animator shrinkY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.9f).setDuration(200);
                    Animator shrinkX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.9f).setDuration(200);
                    Animator growY   = ObjectAnimator.ofFloat(view, "scaleY", 0.9f, 1.0f).setDuration(200);
                    Animator growX   = ObjectAnimator.ofFloat(view, "scaleX", 0.9f, 1.0f).setDuration(200);

                    AnimatorSet set = new AnimatorSet();
                    set.play(shrinkX);
                    set.play(shrinkY);
                    set.play(growX).after(shrinkX);
                    set.play(growY).after(shrinkY);
                    set.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            if (listener != null) {
                                listener.onSelected(url);
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                    set.start();

                }
            });

        }
    }

    @Override
    public int getItemCount() {
        if (response != null) {
            return response.getData().size();
        }

        return 0;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void loadTrending() {
        Thread thread = new Thread(new Runnable() {
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
