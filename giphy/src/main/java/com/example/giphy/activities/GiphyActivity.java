package com.example.giphy.activities;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.Window;

import com.example.giphy.R;
import com.example.giphy.adapters.GiphyAdapter;
import com.example.giphy.presenters.GiphyPresenter;

public class GiphyActivity extends AppCompatActivity {

    protected GiphyPresenter presenter;
    protected GiphyAdapter adapter;

    protected AppCompatEditText editText;
    protected RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giphy);

        editText     = (AppCompatEditText) findViewById(R.id.edit_text);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        presenter    = new GiphyPresenter();
        adapter      = new GiphyAdapter(getContext());

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new GiphyActivity.SpacesItemDecoration(32));
        recyclerView.setAdapter(adapter);

        adapter.loadTrending();

    }

    protected Context getContext() {
        return getApplicationContext();
    }

    protected class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private final int space;
        private final int spaceHalf;

        SpacesItemDecoration(int space) {
            this.space = space;
            this.spaceHalf = space / 2;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if (parent.getPaddingLeft() != spaceHalf || parent.getPaddingRight() != spaceHalf) {
                parent.setPadding(spaceHalf, 0, spaceHalf, 0);
                parent.setClipToPadding(true);
            }

            outRect.top = spaceHalf;
            outRect.bottom = spaceHalf;
            outRect.left = spaceHalf;
            outRect.right = spaceHalf;

            if (parent.getChildAdapterPosition(view) == 0 || parent.getChildAdapterPosition(view) == 1) {
                outRect.top = space;
            }
        }
    }
}
