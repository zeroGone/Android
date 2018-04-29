package com.example.user.challenge06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText id = (EditText)findViewById(R.id.id);
        final EditText password = (EditText)findViewById(R.id.password);
        Button roginButton = (Button)findViewById(R.id.roginButton);

        roginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a=id.getText().toString();
                String b=password.getText().toString();
                if(a.length()==0&&b.length()==0)
                    Toast.makeText(getApplicationContext(), "아이디와 패스워드를 둘다 입력하시오" , Toast.LENGTH_LONG).show();
                else if(a.length()==0)
                    Toast.makeText(getApplicationContext(), "아이디를 입력하시오" , Toast.LENGTH_LONG).show();
                else if(b.length()==0)
                    Toast.makeText(getApplicationContext(), "패스워드를 입력하시오" , Toast.LENGTH_LONG).show();
                else{
                    Intent intent = new Intent(getApplicationContext(),menu.class);
                    startActivity(intent);
                }
            }
        });
    }
}
