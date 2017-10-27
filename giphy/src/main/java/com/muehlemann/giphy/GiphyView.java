package com.muehlemann.giphy;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.giphy.R;

public final class GiphyView extends RecyclerView.ViewHolder {

    Context context;
    AppCompatImageView imageView;

    // =============================================================================================
    // Constructor
    // =============================================================================================

    public GiphyView(View itemView) {
        super(itemView);

        context = itemView.getContext();
        imageView = itemView.findViewById(R.id.image_view);
    }

    // =============================================================================================
    // Methods
    // =============================================================================================

    public void loadGif(Gif gif) {

        Glide.with(context)
                .load(gif.getUrlSmall())
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .placeholder(R.drawable.ic_placeholder)
                        .error(R.drawable.ic_error)
                        .centerCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }
}
