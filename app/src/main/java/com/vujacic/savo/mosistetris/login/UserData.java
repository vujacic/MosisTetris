package com.vujacic.savo.mosistetris.login;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;
import java.util.ArrayList;

public class UserData {
    private User user;
    private String pass, name;

    private DatabaseReference db;
    private static final String FIREBASE_CHILD = "users";
//    private static final String USERS = "users";

    private UserData() {
        db = FirebaseDatabase.getInstance().getReference();
//        db.child(FIREBASE_CHILD).addListenerForSingleValueEvent(parentEventListener);

    }



    public interface UserUpdatedListener{
        void onUserUpdated();
    };
    UserUpdatedListener updateListener;
    public void setEventListener(UserUpdatedListener listener){
        updateListener=listener;
    }


    ValueEventListener parentEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(!dataSnapshot.exists()) {
                user = null;
            }
            if(updateListener!=null){
                updateListener.onUserUpdated();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            String userKey = dataSnapshot.getKey();
            user = dataSnapshot.getValue(User.class);
            user.key = userKey;
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//            String hey = "promenio se";
//            if(hey == null)
//            {
//
//            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private static class SingletonHolder{
        public static final UserData instance = new UserData();
    }

    public static UserData getInstance(){
        return SingletonHolder.instance;
    }

    public void login(String name, String pass) {
        this.name = name; this.pass = pass;
        Query query=db.child(FIREBASE_CHILD)
                .orderByChild("name")
                .equalTo(name)
                .limitToFirst(1);
        query.addChildEventListener(childEventListener);
        query.addListenerForSingleValueEvent(parentEventListener);
    }

    public User getUser() {
        return user;
    }

    public void add(User user) {
        String key = db.push().getKey();
        db.child(FIREBASE_CHILD).child(key).setValue(user);
    }

    public void update(User user) {
        String name = this.user.name;
        String id = this.user.key;
        this.user = user;
        this.user.name = name; this.user.key = id;
        db.child(FIREBASE_CHILD).child(user.key).setValue(user);
    }

}
