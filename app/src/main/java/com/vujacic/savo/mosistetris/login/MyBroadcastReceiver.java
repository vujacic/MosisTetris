package com.vujacic.savo.mosistetris.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String key=intent.getExtras().getString("kimes");
        //Integer key = intent.getIntExtra("zmaj",0);
        //Toast.makeText(context, key, Toast.LENGTH_LONG).show();
        User user = UserData.getInstance().getUser();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        Friends f = new Friends();
        f.first = user.key;
        f.second = MapData.getInstance().getCloseUsers().get(0).key;
        String realKey = db.push().getKey();
        db.child("friends").child(realKey).setValue(f);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(0);
    }


}
