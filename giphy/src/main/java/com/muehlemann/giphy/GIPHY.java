package com.muehlemann.giphy;

import java.util.ArrayList;

class GIPHY {

    private ArrayList<Gif> data;

    ArrayList<Gif> getData(){
        return data;
    }

    Gif getData(int pos){
        return data.get(pos);
    }

    void appendData(ArrayList<Gif> data) {
        this.data.addAll(data);
    }
}