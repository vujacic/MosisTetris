package com.vujacic.savo.mosistetris.Bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class AcceptThread extends Thread {
    private final BluetoothServerSocket mmServerSocket;
    private BluetoothAdapter bluetoothAdapter;

    public AcceptThread() {
        // Use a temporary object that is later assigned to mmServerSocket
        // because mmServerSocket is final.
        BluetoothServerSocket tmp = null;
                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
            // MY_UUID is the app's UUID string, also used by the client code.
            tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("TetrisMosis", UUID.fromString("ko bi reko da neko stavi ovo za kljuc"));
        } catch (IOException e) {
            Log.e("bluetooth", "Socket's listen() method failed", e);
        }
        mmServerSocket = tmp;
    }

    public void run() {
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned.
        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                Log.e("bluetooth", "Socket's accept() method failed", e);
                break;
            }

            if (socket != null) {
                // A connection was accepted. Perform work associated with
                // the connection in a separate thread.
                try {
                    //manageMyConnectedSocket(socket);
                    mmServerSocket.close();
                }catch (IOException e){
                    Log.e("bluetooth", "Socket's accept() method failed", e);
                    break;
                }
                break;
            }
        }
    }

    // Closes the connect socket and causes the thread to finish.
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) {
            Log.e("bluetooth", "Could not close the connect socket", e);
        }
    }
}
