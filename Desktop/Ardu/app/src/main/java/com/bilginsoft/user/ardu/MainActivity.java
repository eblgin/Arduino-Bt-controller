package com.bilginsoft.user.ardu;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    Button i1,i2,i3,i4;
    TextView t1;
    String adress =null, name = null;
    BluetoothAdapter adapter = null;
    BluetoothSocket btsocket = null;
    Set<BluetoothDevice> pairedDevices;
    UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        t1 = (TextView) findViewById(R.id.txtv);
        try {
            buetooth_connect_dvice();
        } catch (IOException e) {
            e.printStackTrace();
        }
        i1 = (Button)findViewById(R.id.btn);
        i1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){led_on_off("f");}
                if (event.getAction() == MotionEvent.ACTION_UP){led_on_off("b");}
                return true;
            }
        });
        i2 = (Button)findViewById(R.id.btn1);
        i2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){led_on_off("z");}
                if (event.getAction() == MotionEvent.ACTION_UP){led_on_off("b");}
                return true;
            }
        });
        i3 = (Button)findViewById(R.id.btn2);
        i3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){led_on_off("c");}
                if (event.getAction() == MotionEvent.ACTION_UP){led_on_off("b");}
                return true;
            }
        });
        i4 = (Button)findViewById(R.id.btn3);
        i4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){led_on_off("g");}
                if (event.getAction() == MotionEvent.ACTION_UP){led_on_off("b");}
                return true;
            }
        });
    }

    private void led_on_off(String i) {
        try{
            if (btsocket!=null){
                btsocket.getOutputStream().write(i.toString().getBytes());

            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void buetooth_connect_dvice() throws IOException {
        try{
        adapter = BluetoothAdapter.getDefaultAdapter();
        adress = adapter.getAddress();
        pairedDevices = adapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice bt : pairedDevices) {
                adress = bt.getAddress().toString();
                name = bt.getName().toString();
                Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();

            }
        }
    }catch (Exception we){}
        adapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice dispositivo = adapter.getRemoteDevice(adress);
        btsocket= dispositivo.createRfcommSocketToServiceRecord(uuid);
        btsocket.connect();
        try{
            t1.setText("BT Name: "+name+"\nBT Address: "+adress);
        }catch (Exception e){}

    }
}
