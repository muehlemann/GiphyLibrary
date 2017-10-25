package com.example.giphy.views;

import android.support.v7.widget.AppCompatImageView;
import android.widget.FrameLayout;

import com.example.giphy.BuildConfig;
import com.example.giphy.models.FixedHeight;
import com.example.giphy.models.GiphyImage;

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

        FixedHeight fixedHeight = new FixedHeight();
        fixedHeight.url = "url";

        GiphyImage giphyImage = new GiphyImage();
        giphyImage.fixed_height = fixedHeight;

        view.loadGif(giphyImage);
    }

}