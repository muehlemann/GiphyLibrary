package com.example.giphy;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by muehlemann on 10/25/17.
 *
 */
public class GiphyPresenterTest {

    private GiphyPresenter presenter;

    @Before
    public void setUp() {
        presenter = new GiphyPresenter("API_KEY");
    }

    @Test
    public void testGetTrending() {
        assertThat(presenter.getTrending(0).getClass()).isEqualTo(Observable.class);
    }

    @Test
    public void testGetSearch() {
        assertThat(presenter.getSearch("query", 0).getClass()).isEqualTo(Observable.class);
    }
}