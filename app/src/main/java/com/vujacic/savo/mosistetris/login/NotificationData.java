package com.vujacic.savo.mosistetris.login;

import android.support.v4.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class NotificationData {
    private Notification notification;
    private String pass, name;

    private DatabaseReference db;
    private static final String FIREBASE_CHILD = "notifications";
//    private static final String USERS = "users";

    private NotificationData() {
        db = FirebaseDatabase.getInstance().getReference();
//        db.child(FIREBASE_CHILD).addListenerForSingleValueEvent(parentEventListener);

    }



    public interface NotUpdatedListener{
        void onNotUpdated();
    };
    NotificationData.NotUpdatedListener updateListener;
    public void setEventListener(NotificationData.NotUpdatedListener listener){
        updateListener=listener;
    }


    ValueEventListener parentEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
//            if(updateListener!=null){
//                updateListener.onUserUpdated();
//            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            String userKey = dataSnapshot.getKey();
            notification = dataSnapshot.getValue(Notification.class);
            notification.key = userKey;
            if(notification.completed == false) {
                if(updateListener!=null){
                    updateListener.onNotUpdated();
                }
            }

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
        public static final NotificationData instance = new NotificationData();
    }

    public static NotificationData getInstance(){
        return NotificationData.SingletonHolder.instance;
    }

    public void getNotifications() {
        User usr = UserData.getInstance().getUser();
        Query query=db.child(FIREBASE_CHILD)
                .orderByChild("userKey")
                .equalTo(usr.key);
        query.addChildEventListener(childEventListener);
        query.addListenerForSingleValueEvent(parentEventListener);
    }

    public Notification getNotification() {
        return notification;
    }

    public void add(Notification notification) {
        String key = db.push().getKey();
        db.child(FIREBASE_CHILD).child(key).setValue(notification);
    }

    public void update(Notification notification) {
        db.child(FIREBASE_CHILD).child(notification.key).setValue(notification);
    }
}
