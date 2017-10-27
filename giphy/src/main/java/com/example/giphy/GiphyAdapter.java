package com.example.giphy;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

class GiphyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    LayoutInflater inflater;
    Listener listener;
    GIPHY response;

    // =============================================================================================
    // Interface
    // =============================================================================================

    interface Listener {
        void onSelected(String url);
    }

    // =============================================================================================
    // Constructor
    // =============================================================================================

    GiphyAdapter(@NonNull Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    // =============================================================================================
    // Life Cycle
    // =============================================================================================

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GiphyView(inflater.inflate(R.layout.view_giphy, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (response != null) {

            final Gif gif = response.getData(position);

            GiphyView view = (GiphyView) holder;
            view.loadGif(gif);
            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Animator shrinkY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.9f).setDuration(100);
                    Animator shrinkX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.9f).setDuration(100);
                    Animator growY   = ObjectAnimator.ofFloat(view, "scaleY", 0.9f, 1.0f).setDuration(100);
                    Animator growX   = ObjectAnimator.ofFloat(view, "scaleX", 0.9f, 1.0f).setDuration(100);

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
                                listener.onSelected(gif.getUrl());
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {
                            // no-op
                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {
                            // no-op
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

    // =============================================================================================
    // Methods
    // =============================================================================================

    void setResponse(GIPHY response) {
        this.response = response;
    }

    void appendResponse(GIPHY response) {
        this.response.appendData(response.getData());
    }

    void setListener(Listener listener) {
        this.listener = listener;
    }

}
