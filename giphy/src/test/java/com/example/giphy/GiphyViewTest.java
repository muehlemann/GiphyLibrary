package com.example.giphy;

import android.support.v7.widget.AppCompatImageView;
import android.widget.FrameLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * Created by muehlemann on 10/25/17.
 *
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, packageName = "com.example.giphy")
public class GiphyViewTest {

    private GiphyView view;

    @Before
    public void setUp() {

        view = new GiphyView(new FrameLayout(RuntimeEnvironment.application));
        view.imageView = new AppCompatImageView(RuntimeEnvironment.application);
    }

    @Test
    public void testLoadGif() {

        Gif gif = new Gif();
        gif.images = new Gif.GiphyImage();
        gif.images.fixed_height_small = new Gif.Container();
        gif.images.fixed_height_small.url = "url";
        view.loadGif(gif);
    }

}