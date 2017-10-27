package com.example.giphy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

class GiphyActivity extends AppCompatActivity implements GiphyAdapter.Listener {

    AppCompatTextView done;
    AppCompatEditText editText;
    RecyclerView recyclerView;

    GiphyPresenter presenter;
    GiphyAdapter adapter;

    ExecutorService cacheExecutor;
    Subscription editTextSubscription;
    Subscription trendingSubscription;
    Subscription searchSubscription;

    boolean loadMore;
    int offset;

    Observer<GIPHY> observer = new Observer<GIPHY>() {

        @Override
        public void onNext(final GIPHY giphy) {

            // Caching Strategy
            cacheExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    cache(giphy);
                }
            });

            // Adapter Changes
            if (loadMore) {
                adapter.appendResponse(giphy);
                offset += GiphyLibrary.PAGE_COUNT;
            } else {
                adapter.setResponse(giphy);
                offset = 0;
            }

            adapter.notifyDataSetChanged();
            loadMore = false;
        }

        @Override
        public void onCompleted() {
            // no-op
        }

        @Override
        public void onError(Throwable err) {
            // no-op
        }

    };

    // =============================================================================================
    // Constructor
    // =============================================================================================

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giphy);

        // View Bindings
        done          = (AppCompatTextView) this.findViewById(R.id.done);
        editText      = (AppCompatEditText) this.findViewById(R.id.edit_text);
        recyclerView  = (RecyclerView) this.findViewById(R.id.recycler_view);

        // Executor
        cacheExecutor = Executors.newFixedThreadPool(1);

        // Presenter
        presenter = new GiphyPresenter(getIntent().getStringExtra(GiphyLibrary.API_KEY));

        // Adapter
        adapter = new GiphyAdapter(getContext());
        adapter.setListener(this);

        // Recycler
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Load More Items
                if (loadMore) {
                    return;
                }

                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                int visibleItems = layoutManager.getChildCount();
                int totalItems   = layoutManager.getItemCount();
                int pastItems    = layoutManager.findFirstVisibleItemPosition();

                if ((visibleItems + pastItems) >= totalItems) {
                    loadMore = true;
                    loadMore();
                }
            }
        });
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // Dismiss Keyboard
                dismissKeyboard();
                return false;
            }
        });

        // Set Up Views
        setUpDone();
        setUpEditText();

        // Load Gifs
        getTrending(0);
    }

    // =============================================================================================
    // GiphyAdapter.Listener
    // =============================================================================================

    @Override
    public void onSelected(String url) {
        Intent data = new Intent();
        data.putExtra(GiphyLibrary.GIPHY_URL, url);
        setResult(RESULT_OK, data);
        finish();
    }

    // =============================================================================================
    // View Actions
    // =============================================================================================

    protected void setUpDone() {

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    protected void setUpEditText() {

        if (editTextSubscription != null) {
            editTextSubscription.unsubscribe();
        }
        editTextSubscription = RxTextView.textChanges(editText)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {

                        String query = charSequence.toString();

                        if (query.equals("")) {
                            recyclerView.smoothScrollToPosition(0);
                            getTrending(0);
                        } else {
                            recyclerView.smoothScrollToPosition(0);
                            getSearch(query, 0);
                        }
                    }
                });
    }


    // =============================================================================================
    // Methods
    // =============================================================================================

    protected Context getContext() {
        return getApplicationContext();
    }

    protected void dismissKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    protected void loadMore() {

        loadMore = true;
        if (editText.getText().length() > 0) {
            getSearch(editText.getText().toString(), offset);
        } else {
            getTrending(offset);
        }
    }

    protected void getTrending(int offset) {

        if (trendingSubscription != null) {
            trendingSubscription.unsubscribe();
        }
        trendingSubscription = presenter.getTrending(offset)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    protected void getSearch(String query, int offset) {

        if (searchSubscription != null) {
            searchSubscription.unsubscribe();
        }
        searchSubscription = presenter.getSearch(query, offset)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    protected void cache(GIPHY response) {

        for (Gif gif: response.getData()) {
            Glide.with(getContext())
                    .load(gif.images.fixed_height_small.url)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .centerCrop())
                    .transition(DrawableTransitionOptions.withCrossFade());
        }
    }
}
