package com.vujacic.savo.mosistetris.login;

import android.location.Location;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MapData {
    private User user;
    private ArrayList<User> myPlaces;
    private HashMap<String, Integer> myPlacesKeyIndexMapping;
    private String pass, name;
    private long lastUpdateTime = 0;

    private DatabaseReference db;
    private static final String FIREBASE_CHILD = "users";
//    private static final String USERS = "users";

    private MapData() {
        myPlaces=new ArrayList<User>();
        myPlacesKeyIndexMapping = new HashMap<>();
        db = FirebaseDatabase.getInstance().getReference();
//        db.child(FIREBASE_CHILD).addListenerForSingleValueEvent(parentEventListener);

    }



    public interface UserUpdatedListener{
        void onListUpdated();
    };
    MapData.UserUpdatedListener updateListener;
    public void setEventListener(MapData.UserUpdatedListener listener){
        updateListener=listener;
    }


    ValueEventListener parentEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(!dataSnapshot.exists()) {
                user = null;
            }
            if(updateListener!=null){
                updateListener.onListUpdated();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//            String userKey = dataSnapshot.getKey();
//            user = dataSnapshot.getValue(User.class);
//            user.key = userKey;

            String myPlaceKey = dataSnapshot.getKey();
            if(!myPlacesKeyIndexMapping.containsKey(myPlaceKey)){
                User myPlace = dataSnapshot.getValue(User.class);
                myPlace.key= myPlaceKey;
                if(isItMe(myPlace)) {
                    return;
                }
                myPlaces.add(myPlace);
                myPlacesKeyIndexMapping.put(myPlaceKey,myPlaces.size()-1);
                if(updateListener!=null){
                    updateListener.onListUpdated();
                }
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            String myPlaceKey = dataSnapshot.getKey();
            User myPlace = dataSnapshot.getValue(User.class);
            myPlace.key=myPlaceKey;
            if(isItMe(myPlace)) {
                return;
            }
            if(myPlacesKeyIndexMapping.containsKey(myPlaceKey)){
                int index = myPlacesKeyIndexMapping.get(myPlaceKey);
                myPlaces.set(index,myPlace);
            }else{
                myPlaces.add(myPlace);
                myPlacesKeyIndexMapping.put(myPlaceKey,myPlaces.size()-1);
            }
            if(updateListener!= null){
                updateListener.onListUpdated();
            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String myPlaceKey = dataSnapshot.getKey();
//            if(isItMe(myPlaceKey)) {
//                return;
//            }
            if(myPlacesKeyIndexMapping.containsKey(myPlaceKey)){
                int index=myPlacesKeyIndexMapping.get(myPlaceKey);
                myPlaces.remove(index);
                recreateKeyMapping();
                if(updateListener!=null){
                    updateListener.onListUpdated();
                }
            }
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private void recreateKeyMapping(){
        myPlacesKeyIndexMapping.clear();
        for(int i = 0;i<myPlaces.size();i++){
            myPlacesKeyIndexMapping.put(myPlaces.get(i).key,i);
        }
    }

    private static class SingletonHolder{
        public static final MapData instance = new MapData();
    }

    public static MapData getInstance(){
        return MapData.SingletonHolder.instance;
    }
    public ArrayList<User> getCloseUsers() {
        return myPlaces;
    }

    public void update(long currentTime) {
        long mins = 300000;//5minuta
        if(currentTime - lastUpdateTime < mins) {
            return;
        }
        lastUpdateTime = currentTime - mins;
        Query query=db.child(FIREBASE_CHILD)
                .orderByChild("timeStamp")
                .startAt(lastUpdateTime);
        query.addChildEventListener(childEventListener);
        query.addListenerForSingleValueEvent(parentEventListener);
    }

    public boolean isItMe(User usr) {
        User user = UserData.getInstance().getUser();
        float metres[] = new float[1];
        Location.distanceBetween(user.lat,user.lng,usr.lat,usr.lng,metres);
        if(metres[0] > 100) {
            return false;
        }
        if(user.key == usr.key) {
            return true;
        }
        return false;
    }


//    public User getUser() {
//        return user;
//    }
//
//    public void add(User user) {
//        String key = db.push().getKey();
//        db.child(FIREBASE_CHILD).child(key).setValue(user);
//    }
//
//    public void update(User user) {
//        String name = this.user.name;
//        String id = this.user.key;
//        this.user = user;
//        this.user.name = name; this.user.key = id;
//        db.child(FIREBASE_CHILD).child(user.key).setValue(user);
//    }
}
