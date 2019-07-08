package com.vujacic.savo.mosistetris.Bluetooth.connections;

import android.util.Log;

import com.newtronlabs.easybluetooth.IBluetoothClient;
import com.newtronlabs.easybluetooth.IBluetoothConnectionFailedListener;

public class SampleConnectionFailedListener implements IBluetoothConnectionFailedListener {
    public static final String TAG = "easyBt";
    @Override
    public void onConnectionFailed(IBluetoothClient bluetoothClient, int i)
    {
        // Connection attempt failed.
        Log.d(TAG, "Connection Failed!");
    }
}
