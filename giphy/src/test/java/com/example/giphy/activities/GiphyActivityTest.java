package com.example.giphy.activities;

import android.app.Activity;

import com.example.giphy.BuildConfig;
import com.example.giphy.helper.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.robolectric.Robolectric.buildActivity;

/**
 * Created by muehlemann on 10/25/17.
 *
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, packageName = "com.example.giphy")
public class GiphyActivityTest {

    @Rule
    public TestRule rx = new RxSchedulersOverrideRule();

    private GiphyActivity activity;

    @Before
    public void setUp() {

        activity = Robolectric.buildActivity(GiphyActivity.class).create().get();
    }

    @Test
    public void testSetUpDone() {
        activity.setUpDone();
        assertThat(activity.done.hasOnClickListeners()).isTrue();
    }

    @Test
    public void testSetUpEditText() {
        activity.setUpEditText();
        assertThat(activity.editTextSubscription).isNotNull();

    }

    @Test
    public void testLoadMore() {
        activity.loadMore();
        assertThat(activity.loadMore).isTrue();
    }

    @Test
    public void testGetTrending() {
        activity.getTrending(0);
        assertThat(activity.trendingSubscription).isNotNull();
    }

    @Test
    public void testGetSearch() {
        activity.getSearch("query", 0);
        assertThat(activity.searchSubscription).isNotNull();
    }

    @Test
    public void testOnSelected() {
        activity.onSelected("url");
        assertThat(activity.isFinishing());
    }

}