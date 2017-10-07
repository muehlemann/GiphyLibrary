package com.example.giphy.views;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.giphy.R;

public class GiphyView extends RecyclerView.ViewHolder {

    private Context context;
    private AppCompatImageView imageView;

    public GiphyView(View itemView) {
        super(itemView);

        context = itemView.getContext();
        imageView = itemView.findViewById(R.id.image_view);
    }

    // =============================================================================================
    // Methods
    // =============================================================================================

    public void loadGif(String url) {
        Glide.with(context).load(url).into(imageView);
    }
}
