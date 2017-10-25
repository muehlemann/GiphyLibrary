package com.example.giphy.views;

import android.view.View;

import com.example.giphy.models.GiphyImage;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

/**
 * Created by muehlemann on 10/25/17.
 *
 */
public class GiphyViewTest {

    private GiphyView view;

    @Before
    public void setUp() {
        View itemView = mock(View.class);
        view = new GiphyView(itemView);
    }

    @Test
    public void testLoadGif() {

        view.loadGif(spy(GiphyImage.class));
    }

}