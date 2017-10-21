package com.example.giphy.activities;

import android.content.Context;
import android.content.Intent;
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

public class GiphyActivity extends AppCompatActivity implements GiphyAdapter.Listener {

    public final static String GIPHY_URL = "giphy_url";
    public final static int REQUEST_CODE = 101;

    protected GiphyPresenter presenter;
    protected GiphyAdapter adapter;

    protected AppCompatEditText editText;
    protected RecyclerView recyclerView;

    // =============================================================================================
    // Static
    // =============================================================================================

    public static Intent start(Context context) {
        return new Intent(context, GiphyActivity.class);
    }

    // =============================================================================================
    // Constructor
    // =============================================================================================

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giphy);

        editText     = (AppCompatEditText) findViewById(R.id.edit_text);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        presenter    = new GiphyPresenter();
        adapter      = new GiphyAdapter(getContext());

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new GiphyActivity.SpacesItemDecoration(32));
        recyclerView.setAdapter(adapter);

        adapter.setListener(this);
        adapter.loadTrending();

    }

    public Context getContext() {
        return getApplicationContext();
    }

    // =============================================================================================
    // GiphyAdapter.Listener
    // =============================================================================================

    @Override
    public void onSelected(String url) {
        Intent data = new Intent();
        data.putExtra(GIPHY_URL, url);
        setResult(RESULT_OK, data);
        finish();
    }

    // =============================================================================================
    // SpacesItemDecoration
    // =============================================================================================

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
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

            outRect.top    = spaceHalf;
            outRect.bottom = spaceHalf;
            outRect.left   = spaceHalf;
            outRect.right  = spaceHalf;

            if (parent.getChildAdapterPosition(view) == 0 || parent.getChildAdapterPosition(view) == 1) {
                outRect.top = space;
            }
        }
    }

}
