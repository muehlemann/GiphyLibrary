package com.example.giphy;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.muehlemann.giphy.GiphyLibrary;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by muehlemann on 10/25/17.
 *
 */
public class GiphyLibraryTest {

    private GiphyLibrary lib;
    private MockActivity mockActivity;
    private MockFragment mockFragment;

    @Before
    public void setUp() {
        lib =  new GiphyLibrary();
        mockActivity = new MockActivity();
        mockFragment = new MockFragment();
    }

    @Test
    public void testStartActivity() {
        lib.start(mockActivity, mockActivity, "the_key");
        assertThat(lib.listener).isNotNull();
    }

    @Test
    public void testStartFragment() {
        lib.start(mockFragment, mockFragment, "the_key");
        assertThat(lib.listener).isNotNull();
    }

    @Test
    public void testOnActivityResultForActivity() {
        lib.start(mockActivity, mockActivity, "the_key");

        Intent intent = new Intent();
        intent.putExtra(GiphyLibrary.API_KEY, "api_key");
        lib.onActivityResult(GiphyLibrary.REQUEST_CODE, Activity.RESULT_OK, intent);

        assertThat(mockActivity.listenerCalled).isTrue();
    }

    @Test
    public void testOnActivityResultForFragment() {
        lib.start(mockFragment, mockFragment, "the_key");

        Intent intent = new Intent();
        intent.putExtra(GiphyLibrary.API_KEY, "api_key");
        lib.onActivityResult(GiphyLibrary.REQUEST_CODE, Activity.RESULT_OK, intent);

        assertThat(mockFragment.listenerCalled).isTrue();
    }

    public class MockActivity extends AppCompatActivity implements GiphyLibrary.Listener {

        public boolean listenerCalled = false;

        @Override
        public void onGiphySelected(String url) {
            listenerCalled = true;
        }
    }

    public class MockFragment extends Fragment implements GiphyLibrary.Listener {

        public boolean listenerCalled = false;

        @Override
        public void onGiphySelected(String url) {
            listenerCalled = true;
        }
    }
}