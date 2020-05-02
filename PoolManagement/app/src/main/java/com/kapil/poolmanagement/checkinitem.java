package com.kapil.poolmanagement;

import android.content.Intent;

import java.util.Comparator;

public class checkinitem {

    public String cards;
    public String names;
    public String times;
    public String dates;

    public checkinitem(String card,String date,String time, String name) {
        this.cards = card;
        this.dates = date;
        this.times = time;
        this.names = name;
    }



}
