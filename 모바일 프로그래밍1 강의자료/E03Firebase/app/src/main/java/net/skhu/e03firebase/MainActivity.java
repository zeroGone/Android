package net.skhu.e03firebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DatabaseReference myData01 = FirebaseDatabase.getInstance().getReference("myData01");
        myData01.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                TextView textView = findViewById(R.id.textView);
                textView.setText(value);
                Log.d("내태그", "받은 데이터: " + value);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("내태그", "서버 에러: ", databaseError.toException());
            }
        });

        EditText editText = findViewById(R.id.editText);
        Button button = findViewById(R.id.btnSaveIntoServer);
        button.setOnClickListener((v)->myData01.setValue(editText.getText().toString()));
    }
}
