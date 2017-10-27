package com.example.giphy;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by muehlemann on 10/25/17.
 *
 */
public class GiphyInterceptorTest {

    private GiphyInterceptor interceptor;

    @Before
    public void setUp() {

        interceptor = new GiphyInterceptor("api_key");
    }

    @Test
    public void testIntercept() throws IOException {

        Interceptor.Chain chain = new ChainBuilder().build();
        assertThat(interceptor.intercept(chain).getClass()).isEqualTo(Response.class);
    }

}