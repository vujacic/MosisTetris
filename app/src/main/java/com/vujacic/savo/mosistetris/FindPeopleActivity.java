package com.vujacic.savo.mosistetris;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Activity;
import android.os.ParcelUuid;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.newtronlabs.easybluetooth.BluetoothClient;
import com.newtronlabs.easybluetooth.BluetoothServer;
import com.newtronlabs.easybluetooth.IBluetoothClient;
import com.newtronlabs.easybluetooth.IBluetoothServer;
import com.vujacic.savo.mosistetris.Bluetooth.AcceptThread;
import com.vujacic.savo.mosistetris.Bluetooth.ConnectThread;
import com.vujacic.savo.mosistetris.Bluetooth.MyBluetoothService;
import com.vujacic.savo.mosistetris.Bluetooth.connections.Client;
import com.vujacic.savo.mosistetris.Bluetooth.connections.SampleConnectionCallback;
import com.vujacic.savo.mosistetris.Bluetooth.connections.SampleConnectionFailedListener;
import com.vujacic.savo.mosistetris.Bluetooth.connections.SampleDataCallback;
import com.vujacic.savo.mosistetris.Bluetooth.connections.SampleDataSentCallback;
import com.vujacic.savo.mosistetris.Bluetooth.connections.StaticQueue;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Set;

public class FindPeopleActivity extends AppCompatActivity implements View.OnClickListener {

    int REQUEST_ENABLE_BT = 1;
    int SELECT_SERVER = 2;
    BluetoothAdapter mBluetoothAdapter;
    static final int PERMISSION_ACCES_FINE_LOCATION=1;
    static final int MAKE_DISCOVERABLE=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_people);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // No Bluetooth support
            finish();
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetoothIntent =
                    new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT);
        }

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_ACCES_FINE_LOCATION);
        }

//        Intent discoverableIntent = new
//                Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//        startActivity(discoverableIntent);



        Button start=(Button)findViewById(R.id.start);
        Button listen=(Button)findViewById(R.id.listen);

        start.setOnClickListener(this);
        listen.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_ENABLE_BT && resultCode==Activity.RESULT_OK) {
            BluetoothAdapter BT = BluetoothAdapter.getDefaultAdapter();
            String address = BT.getAddress();
            String name = BT.getName();
            String toastText = name + " : " + address;
            Toast.makeText(this, toastText, Toast.LENGTH_LONG).show();
        }
        if(requestCode == SELECT_SERVER && resultCode == Activity.RESULT_OK) {
            try{
                Bundle deviceBundle=data.getExtras();
                String mac = deviceBundle.getString("device");
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(mac);
//                if(ct != null){
//                    ct.cancel();
//                }


                BluetoothDevice serverDev = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(device.getAddress());
                Client.client = new BluetoothClient.Builder(this.getApplication(), serverDev, ParcelUuid.fromString("24001101-0000-1000-8000-00805F9B34FB"))
                        .setConnectionCallback(new SampleConnectionCallback())
                        // Let's also get notified if it fails
                        .setConnectionFailedListener(new SampleConnectionFailedListener())
                        // Receive data from the server
                        .setDataCallback(new SampleDataCallback())
                        // Be notified when the data is sent to the server or fails to send.
                        .setDataSentCallback(new SampleDataSentCallback()).build();
                // Connect to server
                Client.client.connect();

                while (StaticQueue.remove() == null) {
                    Thread.sleep(100);
                }
                StaticQueue.canSend = false;

                Intent  newGameIntent = new Intent(this,MainActivity.class);
                //newGameIntent.putExtra("client", client);
                startActivity(newGameIntent);
//                String name = device.getName();
//                Toast.makeText(this,name, Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode == MAKE_DISCOVERABLE && resultCode == 120){
            IBluetoothServer btSer =new BluetoothServer.Builder(this.getApplicationContext(),
                    "EasyBtService", ParcelUuid.fromString("24001101-0000-1000-8000-00805F9B34FB"))
                    .build();
            if(btSer == null) {
                Toast.makeText(this,"Ne moze se napravi server",Toast.LENGTH_SHORT);
            } else {
                // Block until a client connects.
                Client.client = btSer.accept();
                // Set a data callback to receive data from the remote device.
                Client.client.setDataCallback(new SampleDataCallback());
                // Set a connection callback to be notified of connection changes.
                Client.client.setConnectionCallback(new SampleConnectionCallback());
                // Set a data send callback to be notified when data is sent of fails to send.
                Client.client.setDataSentCallback(new SampleDataSentCallback());
                Client.client.sendData("ServerGreeting", "Hello Client".getBytes());
                //We don't want to accept any other clients.
                btSer.disconnect();
            }
            Intent  newGameIntent = new Intent(this,MainActivity.class);
            startActivity(newGameIntent);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:{
                Set<BluetoothDevice> pairedDevices =
                        mBluetoothAdapter.getBondedDevices();
                ArrayList<String> pairedDeviceStrings = new ArrayList<String>();
                if (pairedDevices.size() > 0) {
                    for (BluetoothDevice device : pairedDevices) {
                        pairedDeviceStrings.add(device.getName() + "\n" + device.getAddress());
                    }
                }

                Intent  showDevicesIntent = new Intent(this,PeopleList.class);
                showDevicesIntent.putStringArrayListExtra("devices", pairedDeviceStrings);
                startActivityForResult(showDevicesIntent,SELECT_SERVER);

//                setResult(Activity.RESULT_OK);
//                finish();
                break;
            }
            case R.id.listen:{
//                setResult(Activity.RESULT_CANCELED);
//                finish();

                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                startActivityForResult(discoverableIntent,MAKE_DISCOVERABLE);



//                if(at!=null){
//                    at.cancel();
//                }

                break;
            }
//            case R.id.editmyplace_location_button:{
//                Intent i =new Intent(this,MyPlacesMapsActivity.class);
//                i.putExtra("state",MyPlacesMapsActivity.SELECT_COORDINATES);
//                startActivityForResult(i,1);
//            }
        }
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode)
//        {
//            case PERMISSION_ACCES_FINE_LOCATION:{
//                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
//                    if(state==SHOW_MAP)
//                        mMap.setMyLocationEnabled(true);
//                    else if(state==CENTER_PLACE_ON_MAP)
//                        setOnMapClickListener();
//                    else
//                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(placeLoc,15));
//                    addMyPlaceMarkers();
//                }
//                return;
//            }
//        }
//    }

}
