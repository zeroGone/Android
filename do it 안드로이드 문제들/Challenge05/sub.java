package com.example.user.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class sub extends AppCompatActivity {
    Button user;
    Button sales;
    Button item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub);

        Button user = (Button)findViewById(R.id.user);
        Button sales = (Button)findViewById(R.id.sales);
        Button item = (Button)findViewById(R.id.item);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"고객관리",Toast.LENGTH_SHORT).show();
                sub.this.finish();
            }
        });

        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"매출관리",Toast.LENGTH_SHORT).show();
                sub.this.finish();
            }
        });

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"물품관리",Toast.LENGTH_SHORT).show();
                sub.this.finish();
            }
        });
    }
}
