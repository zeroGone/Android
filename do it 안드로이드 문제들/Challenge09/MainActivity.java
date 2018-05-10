package com.example.pc.challenge09;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String[]> dataList;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (Button)findViewById(R.id.addButton);
        final ListView listView = (ListView)findViewById(R.id.listView);

        final EditText inputName = (EditText)findViewById(R.id.inputName);
        final EditText inputPhone = (EditText)findViewById(R.id.inputPhone);
        final EditText inputBirth = (EditText)findViewById(R.id.inputBirth);

        dataList=new ArrayList<String[]>();

        final TextView userNumber = (TextView)findViewById(R.id.userNumber);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();
                String birth = inputBirth.getText().toString();
                String phone = inputPhone.getText().toString();
                String[] str={name,birth,phone};

                dataList.add(str);

                inputBirth.setText("");
                inputPhone.setText("");
                inputName.setText("");

                userNumber.setText(dataList.size()+"명");
            }
        });

        MyAdapter myAdapter = new MyAdapter(this,dataList);
        listView.setAdapter(myAdapter);


    }

    private class MyAdapter extends BaseAdapter{
        private Context context;
        private ArrayList<String[]> dataList;

        public MyAdapter(Context context, ArrayList<String[]> dataList){
            this.context=context;
            this.dataList=dataList;
        }

        @Override
        public int getCount() {
            return this.dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return this.dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView= LayoutInflater.from(this.context).inflate(R.layout.item,null);
                TextView userName = (TextView)convertView.findViewById(R.id.userNum);
                TextView userPhone = (TextView)convertView.findViewById(R.id.userPhone);
                TextView userBirth = (TextView)convertView.findViewById(R.id.userBirth);
                userName.setText(dataList.get(position)[0]);
                userBirth.setText(dataList.get(position)[1]);
                userPhone.setText(dataList.get(position)[2]);
            }
            return convertView;
        }
    }
}
