package com.vujacic.savo.mosistetris;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.newtronlabs.easybluetooth.BluetoothClient;
import com.newtronlabs.easybluetooth.BluetoothServer;
import com.newtronlabs.easybluetooth.IBluetoothClient;
import com.newtronlabs.easybluetooth.IBluetoothServer;
import com.vujacic.savo.mosistetris.Bluetooth.Constants;
import com.vujacic.savo.mosistetris.Bluetooth.MyBluetoothService;
import com.vujacic.savo.mosistetris.Bluetooth.connections.Client;
import com.vujacic.savo.mosistetris.Bluetooth.connections.SampleConnectionCallback;
import com.vujacic.savo.mosistetris.Bluetooth.connections.SampleConnectionFailedListener;
import com.vujacic.savo.mosistetris.Bluetooth.connections.SampleDataCallback;
import com.vujacic.savo.mosistetris.Bluetooth.connections.SampleDataSentCallback;
import com.vujacic.savo.mosistetris.Bluetooth.connections.StaticQueue;
import com.vujacic.savo.mosistetris.Game.GameSurface;
import com.vujacic.savo.mosistetris.Game.GameThread;
import com.vujacic.savo.mosistetris.Game.Helpers.Scoreboard;
import com.vujacic.savo.mosistetris.databinding.ActivityMainBinding;

import java.util.Queue;

public class MainActivity extends AppCompatActivity {
    GameSurface gs;
    GameThread gt;
    int delay;
    boolean doubleBackToExitPressedOnce = false;
    ActivityMainBinding mBinding;
    Scoreboard scoreboard;
    BluetoothAdapter mblBluetoothAdapter;
    MyBluetoothService myBluetoothService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        getSupportActionBar().hide();

        //setContentView(new GameSurface(this));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        handleIntents();

        gs=findViewById(R.id.gs);
        gt=gs.gameThread;
        scoreboard = gs.rules.scoreboard;
        mBinding.setScoreb(scoreboard);

        ImageButton rotate=findViewById(R.id.rotacija);
        buttonListeners(rotate,gs.queue);
        ImageButton down=findViewById(R.id.down);
        buttonListeners(down,gs.queueD);
//        rotate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable(){
//                    @Override
//                    public void run() {
//                        gs.queue.add(0);
//
//                    }
//                },67);
//            }
//        });

        ImageButton left=findViewById(R.id.left);
        buttonListeners(left,gs.queueL);
//        left.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable(){
//                    @Override
//                    public void run() {
//                        gs.queueL.add(0);
//
//                    }
//                },67);
//            }
//        });

        ImageButton right=findViewById(R.id.right);
        buttonListeners(right,gs.queueR);
//        right.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable(){
//                    @Override
//                    public void run() {
//                        gs.queueR.add(0);
//
//                    }
//                },67);
//            }
//        });
        Toast.makeText(this,"activ created",Toast.LENGTH_SHORT).show();
    }
    private void buttonListeners(ImageButton b,final Queue<Integer> q)
    {
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable(){
//                    @Override
//                    public void run() {
//                        q.add(0);
//
//                    }
//                },67);
//            }
//        });

        b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Handler handler=new Handler();
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    handler.postDelayed(new Runnable(){
                        @Override
                        public void run() {
                            q.add(0);

                        }
                    },67);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    handler.postDelayed(new Runnable(){
                        @Override
                        public void run() {
                            q.add(1);

                        }
                    },67);
                }

//                handler.postDelayed(new Runnable(){
//                    @Override
//                    public void run() {
//                        if(event.getAction() == MotionEvent.ACTION_DOWN) {
//                            q.add(1);
//                        }
//                        else if (event.getAction() == MotionEvent.ACTION_UP) {
//                            q.add(2);
//                        }
//
//                    }
//                },67);
                return false;
            }
        });
    }





    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this,"activ start",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,"activ resumed",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this,"activ restart",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this,"activ paused",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this,"activ stopped",Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Client.client.disconnect();
        Toast.makeText(this,"activ destroyed",Toast.LENGTH_SHORT).show();
        if (myBluetoothService != null) {
            myBluetoothService.stop();
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    void handleIntents(){

//        try{
//            Handler h = new Handler();
//            myBluetoothService = new MyBluetoothService(this,h);
//            Intent findPeopleIntent=getIntent();
//            Bundle devBundle=findPeopleIntent.getExtras();
//            if(devBundle!=null) {
//                BluetoothDevice device=devBundle.getParcelable("device");
//                //myBluetoothService.connect(device);
//                BluetoothDevice serverDev = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(device.getAddress());
//                IBluetoothClient client = new BluetoothClient.Builder(this.getApplication(), serverDev, ParcelUuid.fromString("24001101-0000-1000-8000-00805F9B34FB"))
//                        .setConnectionCallback(new SampleConnectionCallback())
//                        // Let's also get notified if it fails
//                        .setConnectionFailedListener(new SampleConnectionFailedListener())
//                        // Receive data from the server
//                        .setDataCallback(new SampleDataCallback())
//                        // Be notified when the data is sent to the server or fails to send.
//                        .setDataSentCallback(new SampleDataSentCallback()).build();
//                // Connect to server
//                client.connect();
//
//            }else {
//                //myBluetoothService.start();
//                IBluetoothServer btSer =new BluetoothServer.Builder(this.getApplicationContext(),
//                        "EasyBtService", ParcelUuid.fromString("24001101-0000-1000-8000-00805F9B34FB"))
//                        .build();
//                if(btSer == null) {
//                    Toast.makeText(this,"Ne moze se napravi server",Toast.LENGTH_SHORT);
//                } else {
//                    // Block until a client connects.
//                    IBluetoothClient btClient = btSer.accept();
//                    // Set a data callback to receive data from the remote device.
//                    btClient.setDataCallback(new SampleDataCallback());
//                    // Set a connection callback to be notified of connection changes.
//                    btClient.setConnectionCallback(new SampleConnectionCallback());
//                    // Set a data send callback to be notified when data is sent of fails to send.
//                    btClient.setDataSentCallback(new SampleDataSentCallback());
//                    btClient.sendData("ServerGreeting", "Hello Client".getBytes());
//                    //We don't want to accept any other clients.
//                    btSer.disconnect();
//                }
//
//
//            }
//
//        }catch (Exception e){
//            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
//        }
    }

//    private final Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            //FragmentActivity activity = getActivity();
//            switch (msg.what) {
////                case Constants.MESSAGE_STATE_CHANGE:
////                    switch (msg.arg1) {
////                        case MyBluetoothService.STATE_CONNECTED:
////                            setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
////                            mConversationArrayAdapter.clear();
////                            break;
////                        case BluetoothChatService.STATE_CONNECTING:
////                            setStatus(R.string.title_connecting);
////                            break;
////                        case BluetoothChatService.STATE_LISTEN:
////                        case BluetoothChatService.STATE_NONE:
////                            setStatus(R.string.title_not_connected);
////                            break;
////                    }
////                    break;
//                case Constants.MESSAGE_WRITE:
//                    byte[] writeBuf = (byte[]) msg.obj;
//                    // construct a string from the buffer
//                    String writeMessage = new String(writeBuf);
//                    //mConversationArrayAdapter.add("Me:  " + writeMessage);
//                    break;
//                case Constants.MESSAGE_READ:
//                    byte[] readBuf = (byte[]) msg.obj;
//                    // construct a string from the valid bytes in the buffer
//                    String readMessage = new String(readBuf, 0, msg.arg1);
//                    //mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
//                    break;
//                case Constants.MESSAGE_DEVICE_NAME:
//                    // save the connected device's name
//                    //mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
//                    if (null != this) {
////                        Toast.makeText(this, "Connected to "
////                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//                case Constants.MESSAGE_TOAST:
////                    if (null != activity) {
////                        Toast.makeText(activity, msg.getData().getString(Constants.TOAST),
////                                Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//            }
//        }
//    };
}
