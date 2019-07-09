package com.vujacic.savo.mosistetris;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.vujacic.savo.mosistetris.Bluetooth.ConnectThread;

import java.util.ArrayList;

public class PeopleList extends AppCompatActivity {

    ArrayList<String> devices = new ArrayList<String>();
    BluetoothAdapter mBluetoothAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_list);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        try{
            Intent findPeopleIntent=getIntent();
            Bundle positionBundle=findPeopleIntent.getExtras();
            devices=positionBundle.getStringArrayList("devices");
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }



        ListView bluetoothList=(ListView)findViewById(R.id.bluetoothList);
        bluetoothList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,devices));
        bluetoothList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Bundle positionBundle =new Bundle();
//                positionBundle.putInt("position",position);
                String macAdr = devices.get(position).split("\\n")[1];
                //BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAdr);
                Intent data = new Intent();
                data.putExtra("device", macAdr);
                setResult(RESULT_OK, data);
                mBluetoothAdapter.cancelDiscovery();
                finish();

//                Intent intent=new Intent(PeopleList.this,FindPeopleActivity.class);
//                intent.putExtras(positionBundle);
//                startActivity(intent);
            }
        });

        // Register for broadcasts when a device is discovered.
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

        mBluetoothAdapter.startDiscovery();


    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                devices.add(deviceName + "\n" + deviceHardwareAddress);
                setList();
            }
        }
    };

    private void setList(){
        ListView bluetoothList=(ListView)findViewById(R.id.bluetoothList);
        bluetoothList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,devices));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mBluetoothAdapter.cancelDiscovery();

        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(receiver);
    }


}
