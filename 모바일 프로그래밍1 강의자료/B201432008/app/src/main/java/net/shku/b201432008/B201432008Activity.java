package net.shku.b201432008;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class B201432008Activity extends AppCompatActivity {
    private EditText editText1;
    private EditText editText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b201432008);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = editText1.getText().toString();
                editText1.setText("");
                editText2.setText(value);
            }
        });
    }
}
