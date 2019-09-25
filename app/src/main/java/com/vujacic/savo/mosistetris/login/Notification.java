package com.vujacic.savo.mosistetris.login;

import com.google.firebase.database.Exclude;

public class Notification {
    @Exclude
    public String key;
    public String userKey;
    public String senderKey;
    public String title;
    public String content;
    public Integer type; //friend, match 0 1
    public Boolean completed;


    public Notification() {}
}
