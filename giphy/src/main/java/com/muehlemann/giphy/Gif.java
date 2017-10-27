package com.muehlemann.giphy;

class Gif {

    static class Container {

        public String url;
        public int width;
        public int height;
    }

    static class GiphyImage {

        public Container fixed_height;
        public Container fixed_height_small;
    }

    GiphyImage images;

    String getUrl() {
        return images.fixed_height.url;
    }

    String getUrlSmall() {
        return images.fixed_height_small.url;
    }

}