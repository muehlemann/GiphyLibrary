package com.example.giphy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import static org.mockito.Mockito.spy;

/**
 * Created by muehlemann on 10/25/17.
 *
 */
public class GiphyAdapterTest {

    private GiphyAdapter adapter;

    @Before
    public void setUp() {

        adapter = new GiphyAdapter(RuntimeEnvironment.application);
    }

    @Test
    public void testOnCreateViewHolder() {

        RecyclerView.ViewHolder holder = adapter.onCreateViewHolder(new FrameLayout(RuntimeEnvironment.application), 0);
    }

    @Test
    public void testOnBindViewHolder() {

    }

    @Test
    public void testGetItemCount() {

    }

    @Test
    public void testSetListener() {

    }

}