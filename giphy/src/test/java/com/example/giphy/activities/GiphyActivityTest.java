package com.example.giphy.activities;

import com.example.giphy.helper.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.robolectric.Robolectric.buildActivity;

/**
 * Created by muehlemann on 10/25/17.
 *
 */
@RunWith(RobolectricTestRunner.class)
public class GiphyActivityTest {

    @Rule
    public TestRule rx = new RxSchedulersOverrideRule();

    public GiphyActivity activity;

    @Before
    public void setUp() {

        activity = buildActivity(GiphyActivity.class).create().get();
    }

    @Test
    public void testSetUpDone() {
        activity.setUpDone();

        assertThat(activity.done.hasOnClickListeners()).isTrue();
    }

    @Test
    public void testSetUpEditText() {
        activity.setUpEditText();

    }

    @Test
    public void testLoadMore() {
        activity.loadMore();

    }

    @Test
    public void testGetTrending() {
        activity.getTrending(0);
    }

    @Test
    public void testGetSearch() {
        activity.getSearch("query", 0);

    }

    @Test
    public void testOnSelected() {
        activity.onSelected("url");

    }

}