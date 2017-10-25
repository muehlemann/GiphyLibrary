package com.example.giphy.adapters;

import android.view.ViewGroup;

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

        adapter.onCreateViewHolder(spy(ViewGroup.class), 0);
    }

}