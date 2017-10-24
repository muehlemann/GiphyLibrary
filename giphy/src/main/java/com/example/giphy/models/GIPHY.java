package com.example.giphy.models;

import java.util.ArrayList;

public class GIPHY {

    private ArrayList<Gif> data;

    public ArrayList<Gif> getData(){
        return data;
    }

    public void appendData(ArrayList<Gif> data) {
        this.data.addAll(data);
    }
}


