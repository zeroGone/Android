package ikzumgutdle.skhu.ikmario;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class StartActivity extends AppCompatActivity implements Runnable{
    public static final String MARIO_SEUNGIK_ADDRESS = "98:D3:71:FD:2F:CE";
    public static OutputStream writer;
    private BluetoothAdapter bluetoothAdapter;
    private BroadcastReceiver receiver;
    private boolean start;
    private boolean bluetoothOnCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null) this.finish();
    }

    private void bluetoothStart(){
        start = true;
        findViewById(R.id.bluetoothOnPlz).setVisibility(View.INVISIBLE);
        new Thread(this).start();

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        for(BluetoothDevice bluetoothDevice: pairedDevices) {
            if(bluetoothDevice.getAddress().equals(MARIO_SEUNGIK_ADDRESS)){
                new Thread(new ConnectThread(bluetoothDevice)).start();
            }
        }

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(BluetoothDevice.ACTION_FOUND)){
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if(device.getAddress().equals(MARIO_SEUNGIK_ADDRESS)){
                        new Thread(new ConnectThread(device)).start();
                    }
                }
            }
        };

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);
        bluetoothAdapter.startDiscovery();
    };

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
        start = false;
    }

    @Override
    public void onResume(){
        super.onResume();
        if(!bluetoothAdapter.isEnabled()) {
            bluetoothOnCheck = false;
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                        if(bluetoothOnCheck){
                            unregisterReceiver(this);
                            bluetoothStart();
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.start_wifi).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.bluetoothOnPlz).setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    }
                }
            };
            IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(receiver, filter);
        }else{
            bluetoothOnCheck = true;
            new Thread(this).start();
            bluetoothStart();
        }
    }

    @Override
    public void run() {
        final ImageView wifi = findViewById(R.id.start_wifi);
        int timer = 0;
        while(start){
            if(timer%1000==0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        wifi.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out));
                        wifi.setVisibility(View.INVISIBLE); }
                });
            }
            else if(timer%500==0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        wifi.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in));
                        wifi.setVisibility(View.VISIBLE); }
                });
            }
            timer++;
            try { Thread.sleep(1); }
            catch (InterruptedException e){ e.printStackTrace(); }
        }
    }

    private class ConnectThread implements Runnable{
        private final BluetoothSocket bluetoothSocket;

        public ConnectThread(BluetoothDevice device){
            BluetoothSocket socket = null;
            try{
                socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            }catch(IOException e){
                e.printStackTrace();
            }
            bluetoothSocket = socket;
        }
        @Override
        public void run() {
            if(bluetoothAdapter.isDiscovering()) bluetoothAdapter.cancelDiscovery();
            try{
                bluetoothSocket.connect();
                writer = bluetoothSocket.getOutputStream();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                start = false;
                finish();
            }catch (IOException e){
                e.printStackTrace();
                try{
                    bluetoothSocket.close();
                }catch (IOException e2){
                    e2.printStackTrace();
                }
            }
        }
    }
}
