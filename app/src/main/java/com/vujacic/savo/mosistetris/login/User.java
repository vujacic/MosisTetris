package com.vujacic.savo.mosistetris.login;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.security.Timestamp;

public class User {
    @Exclude
    public String key;
    public String name;
    public String pass;
    public Double lat;
    public Double lng;
    public long timeStamp;
    public String mac;

    public String getName_pass() {
        return this.name + "_" + this.pass;
    }

//    public void setName_pass() {
//        this.name_pass = this.name + "_" + this.pass;
//    }

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public User(){}






}
