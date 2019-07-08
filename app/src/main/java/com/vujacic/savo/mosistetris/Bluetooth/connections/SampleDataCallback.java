package com.vujacic.savo.mosistetris.Bluetooth.connections;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.newtronlabs.easybluetooth.IBluetoothDataReceivedCallback;
import com.newtronlabs.easybluetooth.IBluetoothMessageEvent;
import com.vujacic.savo.mosistetris.Game.GameSurface;

public class SampleDataCallback implements IBluetoothDataReceivedCallback {
    public static final String TAG = "easyBt";

    public SampleDataCallback(){

    }


    @Override
    public void onDataReceived(IBluetoothMessageEvent messageEvent)
    {
//        if(gs!= null) {
//            switch (messageEvent.getTag()) {
//                case "init": gs.rules.increaseRun();
//            }
//        }
//        Handler handler = new Handler(Looper.getMainLooper());
//        handler.post(new Runnable() {
//            public void run() {
//                // UI code goes here
//                StaticQueue.add(messageEvent.getTag());
//            }
//        });

        StaticQueue.add(new StringByteArr(messageEvent.getTag(),messageEvent.getData()));
       // StaticQueue.stack.push(new StringByteArr(messageEvent.getTag(),messageEvent.getData()));


        // Data was received.
        Log.d(TAG, "Received: " + messageEvent.getTag() + " Data: " + new String(messageEvent.getData()));
    }
}
