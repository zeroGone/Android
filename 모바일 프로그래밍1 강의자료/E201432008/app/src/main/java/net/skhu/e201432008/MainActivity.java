package net.skhu.e201432008;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Student> studentList;
    MyRecyclerViewAdapter myRecyclerViewAdapter;
    StudentCreateDialogFragment studentCreateDialogFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        studentList = new ArrayList<>();

        myRecyclerViewAdapter = new MyRecyclerViewAdapter(this, studentList);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myRecyclerViewAdapter);

        Button b = findViewById(R.id.btnAdd);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input1 = findViewById(R.id.input_name);
                EditText input2 = findViewById(R.id.input_studentNumber);
                String name = input1.getText().toString();
                String temp = input2.getText().toString();
                if(name.equals("")) Toast.makeText(getApplicationContext(), "이름 입력해주세요", Toast.LENGTH_LONG).show();
                else if(temp.equals("")) Toast.makeText(getApplicationContext(), "학번 입력해주세요", Toast.LENGTH_LONG).show();
                else{
                    int number = Integer.parseInt(input2.getText().toString());
                    studentList.add(new Student(name, number));
                    myRecyclerViewAdapter.notifyDataSetChanged();
                }
                input1.setText("");
                input2.setText("");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void showStudentCreateDialog() {
        if (studentCreateDialogFragment == null)
            studentCreateDialogFragment = new StudentCreateDialogFragment();
        studentCreateDialogFragment.show(getSupportFragmentManager(), "EditDialog"); // 화면에 대화상자 보이기
    }

    @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.action_remove) {
            studentList.clear();
            myRecyclerViewAdapter.notifyDataSetChanged();
            return true;
        }else if(id == R.id.action_create) showStudentCreateDialog();
        return super.onOptionsItemSelected(menuItem);
    }
}
