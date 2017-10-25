package com.example.giphy.views;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.giphy.R;
import com.example.giphy.models.GiphyImage;

/**
 * Created by muehlemann on 10/16/17.
 *
 */
public class GiphyView extends RecyclerView.ViewHolder {

    protected Context context;
    protected AppCompatImageView imageView;

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

    public void loadGif(GiphyImage giphyImage) {

        Glide.with(context)
                .load(giphyImage.fixed_height.url)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(R.drawable.ic_placeholder)
                        .error(R.drawable.ic_error)
                        .centerCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }
}
