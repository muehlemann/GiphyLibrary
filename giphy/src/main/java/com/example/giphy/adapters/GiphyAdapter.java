package com.example.giphy.adapters;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.giphy.R;
import com.example.giphy.models.GIPHY;
import com.example.giphy.models.GiphyImage;
import com.example.giphy.views.GiphyView;

public class GiphyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private Listener listener;
    public GIPHY response;


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
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GiphyView(inflater.inflate(R.layout.view_giphy, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (response != null) {

            final GiphyImage giphyImg = response.getData().get(position).images;

            GiphyView view = (GiphyView) holder;
            view.loadGif(giphyImg);
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
                                listener.onSelected(giphyImg.fixed_height.url);
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

}
