package com.example.giphy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.giphy.models.GIPHY;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
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
        // no-op
    }

    @Test
    public void testOnBindViewHolder() {
        // no-op
    }

    @Test
    public void testGetItemCount() {
        adapter.response = mock(GIPHY.class);
        assertThat(adapter.response).isNotNull();
    }

    @Test
    public void testGetItemCountForNull() {
        assertThat(adapter.response).isNull();
    }

    @Test
    public void testSetListener() {

        GiphyAdapter.Listener listener = new GiphyAdapter.Listener() {
            @Override
            public void onSelected(String url) {

            }
        };

        adapter.setListener(listener);
        assertThat(adapter.listener).isNotNull();

    }

}