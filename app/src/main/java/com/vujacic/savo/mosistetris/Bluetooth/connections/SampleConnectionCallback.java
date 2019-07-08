package com.vujacic.savo.mosistetris.Bluetooth.connections;

import android.util.Log;

import com.newtronlabs.easybluetooth.IBluetoothClient;
import com.newtronlabs.easybluetooth.IBluetoothConnectionCallback;

public class SampleConnectionCallback implements IBluetoothConnectionCallback {
    public static final String TAG = "easyBt";
    @Override
    public void onConnected(IBluetoothClient bluetoothClient)
    {
        // Connection successful.
        Log.d(TAG, "Connected to: " + bluetoothClient.getNodeId());

        // We can start sending data now.
        bluetoothClient.sendData("ClientGreeting", "Hello Server!!".getBytes());

    }

    @Override
    public void onConnectionSuspended(IBluetoothClient bluetoothClient, int reason)
    {
        // Connection lost.
        if(reason == REASON_CONNECTION_CLOSED)
        {
            Log.d(TAG, "Connection to :" +bluetoothClient.getNodeId() + " ended.");
        }
    }
}
