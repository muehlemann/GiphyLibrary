package com.example.giphy.activities;

import com.example.giphy.helper.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.robolectric.Robolectric.buildActivity;

/**
 * Created by muehlemann on 10/25/17.
 *
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class GiphyActivityTest {

    @Rule
    public TestRule rx = new RxSchedulersOverrideRule();

    public GiphyActivity activity;

    @Before
    public void setUp() throws Exception {

        activity = buildActivity(GiphyActivity.class).create().start().resume().visible().get();
    }

    @Test
    public void testSetUpDone() {
        activity.setUpDone();

        assertThat(activity.done.hasOnClickListeners()).isTrue();
    }

}