package com.vujacic.savo.mosistetris.login;

import com.google.firebase.database.Exclude;

public class Friends {
    @Exclude
    public String key;
    public String first;
    public String second;

    public Friends() {

    }
}