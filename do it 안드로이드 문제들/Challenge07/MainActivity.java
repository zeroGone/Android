package com.example.user.project7;

import android.content.DialogInterface;
import android.content.Intent;
import android.renderscript.Script;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button date = (Button)findViewById(R.id.date);

        SimpleDateFormat now = new SimpleDateFormat("yyyy년 MM월 DD일", Locale.KOREA);
        String thisDate = now.format(new Date());
        date.setText(thisDate);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog test = new CustomDialog(MainActivity.this);
                test.getButton(date);
                test.show();
            }
        });

        Button save = (Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name=(EditText)findViewById(R.id.nameID);
                EditText age=(EditText)findViewById(R.id.ageID);
                String nameStr = name.getText().toString();
                int ageStr=Integer.parseInt(age.getText().toString());
                String output = date.getText().toString();
                Toast.makeText(getApplicationContext(),"이름:"+nameStr+",나이:"+ageStr+",생년월일:"+output,Toast.LENGTH_LONG).show();
            }
        });
    }

}
