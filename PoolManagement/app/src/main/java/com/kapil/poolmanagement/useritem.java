package com.kapil.poolmanagement;

public class useritem {

    public String cards;
    public String names;
    public  String contact;
    public  String gender;
    public String visits;
    public String validity;


    public useritem(String card,String name,String cont, String gend, String val, String vis) {
        this.cards = card;
        this.names = name;
        this.contact = cont;
        this.gender = gend;
        this.validity = val;
        this.visits = vis;
    }
}
