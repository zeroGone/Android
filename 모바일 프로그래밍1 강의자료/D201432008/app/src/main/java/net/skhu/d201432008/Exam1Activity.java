package net.skhu.d201432008;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class Exam1Activity extends AppCompatActivity {
    private Spinner spinner;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam1);

        spinner = findViewById(R.id.spinner_userType);
        editText = findViewById(R.id.editText1);

        String[] stringArray = { "관리자", "사용자", "손님" };
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stringArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button button = findViewById(R.id.btnOk);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1 = spinner.getSelectedItem().toString();
                editText.setText(text1);
            }
        });

        RadioGroup radioGroup = findViewById(R.id.radioGroup1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String str = radioButton.getText().toString();
                editText.setText(str);
            }
        });

    }
}
