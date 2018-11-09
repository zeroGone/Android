package net.skhu.ondevicemusiclistexample;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<File> musicList = new ArrayList<File>();
        String sDCardPath = Environment.getRootDirectory()
        Log.d("여기",sDCardPath);
    }
}
