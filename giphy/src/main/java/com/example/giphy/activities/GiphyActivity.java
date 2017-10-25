package com.example.giphy.activities;

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

import com.example.giphy.Interface.GiphyLibrary;
import com.example.giphy.R;
import com.example.giphy.adapters.GiphyAdapter;
import com.example.giphy.models.GIPHY;
import com.example.giphy.presenters.GiphyPresenter;
import com.jakewharton.rxbinding.widget.RxTextView;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by muehlemann on 10/17/17.
 *
 */
public class GiphyActivity extends AppCompatActivity implements GiphyAdapter.Listener {

    protected AppCompatTextView done;
    protected AppCompatEditText editText;
    protected RecyclerView recyclerView;

    protected GiphyPresenter presenter;
    protected GiphyAdapter adapter;

    protected Subscription editTextSubscription;
    protected Subscription trendingSubscription;
    protected Subscription searchSubscription;

    private boolean loadMore;
    private int offset;

    private Observer<GIPHY> observer = new Observer<GIPHY>() {

        @Override
        public void onNext(GIPHY giphy) {

            if (loadMore) {
                adapter.response.appendData(giphy.getData());
                offset += GiphyLibrary.PAGE_COUNT;
            } else {
                adapter.response = giphy;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giphy);

        // View Bindings
        done          = (AppCompatTextView) findViewById(R.id.done);
        editText      = (AppCompatEditText) findViewById(R.id.edit_text);
        recyclerView  = (RecyclerView) findViewById(R.id.recycler_view);

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
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                return false;
            }
        });

        // Set Up Views
        setUpDone();
        setUpEditText();

        // Load Gifs
        getTrending(0);
    }

    protected Context getContext() {
        return getApplicationContext();
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
                        recyclerView.smoothScrollToPosition(0);
                        getSearch(charSequence.toString(), 0);
                    }
                });
    }


    // =============================================================================================
    // Methods
    // =============================================================================================

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
                .subscribeOn(Schedulers.newThread())
                .subscribe(observer);
    }

    protected void getSearch(String query, int offset) {

        if (searchSubscription != null) {
            searchSubscription.unsubscribe();
        }
        searchSubscription = presenter.getSearch(query, offset)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(observer);
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
}
