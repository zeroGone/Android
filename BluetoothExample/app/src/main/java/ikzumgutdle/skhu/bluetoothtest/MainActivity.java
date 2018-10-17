package ikzumgutdle.skhu.bluetoothtest;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BluetoothAdapter bluetoothAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Log.d("LOG:", "블루투스 지원 상태 확인");
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //1.블루투스 지원 확인
        if(bluetoothAdapter == null)  Toast.makeText(this,"블루투스 지원 안함", Toast.LENGTH_LONG);
        else {
            Toast.makeText(this,"블루투스 지원함", Toast.LENGTH_SHORT);
            //2.블루투스 활성 확인
            if(bluetoothAdapter.isEnabled()) Log.d("LOG", "블루투스 ON");
            else{
                Log.d("LOG", "블루투스 OFF");
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                this.startActivityForResult(intent, 2);
            }
        }
    }
}
