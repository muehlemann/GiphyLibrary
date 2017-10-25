package com.example.giphy.Interface;

import android.content.Intent;

import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by muehlemann on 10/25/17.
 *
 */
public class GiphyLibraryTest {

    @Test
    public void testStart() {
        Intent intent = GiphyLibrary.start(RuntimeEnvironment.application, "the_key");
        assertThat(intent.getClass()).isEqualTo(Intent.class);
    }

}