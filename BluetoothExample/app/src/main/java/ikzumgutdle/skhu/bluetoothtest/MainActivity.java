package ikzumgutdle.skhu.bluetoothtest;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BluetoothAdapter bluetoothAdapter;
    private final int REQUEST_ENABLE_BT = 2;
    private final int REQUEST_ENABLE_CL = 3;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //기기를 찾았다면
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                //인텐트로 기기에 대한 블루투스 디바이스를 얻어오고
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                foundAdapter.add(String.format("이름:%s, 주소:%s", device.getName(), device.getAddress()));
                foundAdapter.notifyDataSetChanged();
                Log.d("로그",device.getAddress()+"검색됨");

            }
        }
    };//블루투스 기기들의 받아오기 위한 리시버;

    private ArrayList<String> bondedList;
    private ArrayList<String> foundList;
    private ListView foundListView;
    private ArrayAdapter<String> foundAdapter;
    private IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //블루투스 지원 안하면 끝냄
        if (bluetoothAdapter == null) {
            Log.d("로그","블루투스 지원안함");
            this.finish();
        }else if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, this.REQUEST_ENABLE_CL);
        }else{
            bondedList = new ArrayList<String>();
            foundList = new ArrayList<String>();

            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            for(BluetoothDevice bluetoothDevice: pairedDevices) bondedList.add(String.format("이름:%s, 주소:%s", bluetoothDevice.getName(), bluetoothDevice.getAddress()));
            //페어링 된 기기들 검색
            ListView bondedListView = findViewById(R.id.bt_bonded);
            ArrayAdapter<String> bondedAdapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, bondedList);
            bondedListView.setAdapter(bondedAdapter);

            Button button = findViewById(R.id.button);
            button.setOnClickListener(this);

            foundListView = findViewById(R.id.bt_found);
            foundAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, foundList);
            foundListView.setAdapter(foundAdapter);

            filter = new IntentFilter();
            filter.addAction(BluetoothDevice.ACTION_FOUND);
            registerReceiver(receiver,filter);
        }
    }

    @Override
    protected void onPause(){
        unregisterReceiver(receiver);
        super.onPause();
    }

    @Override
    protected void onResume(){
        registerReceiver(receiver,filter);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        //블루투스가 안켜져있으면
        if (!bluetoothAdapter.isEnabled()) {
            //키게함
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            this.startActivityForResult(intent, REQUEST_ENABLE_BT);
        }else {
            //블루투스 지원 되고 활성화되었으면
            Toast.makeText(this, "블루투스 지원함", Toast.LENGTH_SHORT).show();
            bluetoothAdapter.startDiscovery();

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ENABLE_CL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //퍼미션 허락했을경우
                    bluetoothAdapter.startDiscovery();
                } else {
                    //퍼미션 거부당했을 경우
                    Toast.makeText(this, "권한 허락해주세요", Toast.LENGTH_LONG).show();
                    this.finish();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    //화면전화 이후 결과값
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == REQUEST_ENABLE_BT) bluetoothAdapter.startDiscovery();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
