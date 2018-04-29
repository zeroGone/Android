package com.example.user.challenge04;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.*;
import android.text.*;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText inputText;
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = (EditText) findViewById(R.id.editText);
        inputText.addTextChangedListener(new Watcher());

        sendButton = (Button) findViewById(R.id.send);
        textView = (TextView) findViewById(R.id.byte01);
    }

    public void onWindowFocusChanged(boolean hasFocus){
        int inputtextWidth = inputText.getWidth();
        inputText.setTextSize(TypedValue.COMPLEX_UNIT_PX, 100);
        float korSize = inputText.getPaint().measureText("한");
        inputText.setTextSize(TypedValue.COMPLEX_UNIT_PX,inputtextWidth/9/(korSize/100));
    }


    public void sendButton(View v) {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = inputText.getText().toString();//텍스트 입력받은것을 string 타입으로 저장
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void cancelButton(View v) {
        finish();
    }

    public class Watcher implements TextWatcher {
        private int count = 0;
        private int length = 80;

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            textView.setText(this.count + "/" + this.length + " 바이트");
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String str = inputText.getText().toString();
            this.count = str.length();
        }

        public void afterTextChanged(Editable s) {
            textView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(80)});
            textView.setText(count + "/" + length + " 바이트");
        }
    }
}


