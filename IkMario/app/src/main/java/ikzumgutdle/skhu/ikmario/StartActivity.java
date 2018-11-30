package ikzumgutdle.skhu.ikmario;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class StartActivity extends AppCompatActivity implements Runnable{
    private ImageView wifi;
    public static BluetoothSPP bluetoothSPP;
    private int timer;
    private boolean start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        wifi= findViewById(R.id.start_wifi);
        wifi.setVisibility(View.INVISIBLE);
        new Thread(this).start();

        bluetoothSPP = new BluetoothSPP(this);
        if(!bluetoothSPP.isBluetoothAvailable()) {
            Log.d("로그", "블루투스 안됨");
            this.finish();
        }else if(!bluetoothSPP.isBluetoothEnabled()) bluetoothSPP.enable();

        bluetoothSPP.setupService();
        bluetoothSPP.startService(BluetoothState.DEVICE_OTHER);

        bluetoothSPP.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            @Override
            public void onDeviceConnected(String name, String address) {
                if(!address.equals("98:D3:71:FD:2F:CE")) onDeviceDisconnected();
            }

            @Override
            public void onDeviceDisconnected() {
                Log.d("로그","연결끊김");

            }

            @Override
            public void onDeviceConnectionFailed() {
                Log.d("로그","연결실패");
            }
        });


        bluetoothSPP.setBluetoothStateListener(new BluetoothSPP.BluetoothStateListener() {
            @Override
            public void onServiceStateChanged(int state) {
                if(state == BluetoothState.STATE_CONNECTED) startActivity(new Intent(StartActivity.this, MainActivity.class));
                    // Do something when successfully connected
                else if(state == BluetoothState.STATE_CONNECTING);
                    // Do something while connecting
                else if(state == BluetoothState.STATE_LISTEN) startActivityForResult(new Intent(getApplicationContext(), DeviceList.class), BluetoothState.REQUEST_CONNECT_DEVICE);
                    // Do something when device is waiting for connection
                else if(state == BluetoothState.STATE_NONE);
                // Do something when device don't have any connection
            }
        });

        bluetoothSPP.setAutoConnectionListener(new BluetoothSPP.AutoConnectionListener() {
            @Override
            public void onAutoConnectionStarted() {
                Log.d("로그","자동연결");
            }

            @Override
            public void onNewConnection(String name, String address) {
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if(resultCode == Activity.RESULT_OK)  bluetoothSPP.connect(data);
        } else if(requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if(resultCode == Activity.RESULT_OK) {
                bluetoothSPP.setupService();
                this.finish();
            } else {
                // Do something if user doesn't choose any device (Pressed back)
                this.finish();
            }
        }
    }

    @Override
    public void run() {
        while(true){
            if(start) break;
            if(timer%1000==0) {
//                wifi.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_out));
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() { wifi.setVisibility(View.INVISIBLE);
//                    }
//                });
            }
            else if(timer%500==0) {
//                wifi.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_in));
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        wifi.setVisibility(View.VISIBLE);
//                    }
//                });
            }
            timer++;
            try {
                Thread.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
