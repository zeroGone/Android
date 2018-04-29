package com.example.user.project7;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CustomDialog extends Dialog {
    public CustomDialog(Context context){//생성자
        super(context);
    }

    private Button set;
    private Button cancel;
    private EditText year;
    private EditText month;
    private EditText date;
    private String str;
    private Button reserve;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);

        set=(Button)findViewById(R.id.set);
        cancel=(Button)findViewById(R.id.cancel);
        year=(EditText)findViewById(R.id.year);
        month=(EditText)findViewById(R.id.month);
        date=(EditText)findViewById(R.id.date);

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reserve.setText(year.getText().toString()+"년 "+month.getText().toString()+"월 "+date.getText().toString()+"일");
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void getButton(Button reserve) {
        this.reserve=reserve;
    }
}
