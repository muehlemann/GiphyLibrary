package com.example.giphy;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by muehlemann on 10/25/17.
 *
 */
public class GiphyLibraryTest {

    public GiphyLibrary lib;
    public MockActivity mockActivity;

    @Before
    public void setUp() {
        lib =  new GiphyLibrary();
        mockActivity = new MockActivity();
    }

    @Test
    public void testStart() {
        lib.start(mockActivity, mockActivity, "the_key");
        assertThat(lib.listener).isNotNull();
    }

    @Test
    public void testOnActivityResult() {
        lib.start(mockActivity, mockActivity, "the_key");

        Intent intent = new Intent();
        intent.putExtra(GiphyLibrary.API_KEY, "api_key");
        lib.onActivityResult(GiphyLibrary.REQUEST_CODE, Activity.RESULT_OK, intent);

        assertThat(mockActivity.listenerCalled).isTrue();
    }

    public class MockActivity extends AppCompatActivity implements GiphyLibrary.Listener {

        public boolean listenerCalled = false;

        @Override
        public void onGiphySelected(String url) {
            listenerCalled = true;
        }
    }
}