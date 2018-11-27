package ikzumgutdle.skhu.bluetoothtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Main2Activity extends AppCompatActivity {

    private EditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        input = findViewById(R.id.input);
        Button button = findViewById(R.id.sendButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = input.getText().toString();
                try{
                    MainActivity.writer.write(value.getBytes());
                }catch(IOException e){
                    e.printStackTrace();
                }
                input.setText("");
            }
        });
    }
}
