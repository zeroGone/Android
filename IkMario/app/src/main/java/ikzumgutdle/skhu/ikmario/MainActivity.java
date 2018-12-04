package ikzumgutdle.skhu.ikmario;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity implements Runnable {

    private MediaPlayer player;
    private SeekBar controller;
    private TextView musicCurrentTime;
    private TextView musicSize;
    private SimpleDateFormat simpleDateFormat;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simpleDateFormat = new SimpleDateFormat("mm:ss");
        controller = findViewById(R.id.music_controller);
        musicCurrentTime = findViewById(R.id.music_current);
        musicSize = findViewById(R.id.music_size);
        player = new MediaPlayer();
        handler = new Handler();
        FirebaseApp.initializeApp(this);
        StorageReference music = FirebaseStorage.getInstance().getReference().child("music").child("marionette.mp3");
        music.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                try {
                    player.reset();
                    player.setDataSource(task.getResult().toString());
                    player.prepare();
                    controller.setMax(player.getDuration());
                    musicSize.setText(simpleDateFormat.format(player.getDuration()));
                    musicCurrentTime.setText(simpleDateFormat.format(player.getCurrentPosition()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void motorButtonClick(View v){
        switch (v.getId()){
            case R.id.motor1:
                StartActivity.bluetoothSPP.send("1", false);
                break;
            case R.id.motor2:
                StartActivity.bluetoothSPP.send("2", false);
                break;
            case R.id.motor3:
                StartActivity.bluetoothSPP.send("3", false);
                break;
            case R.id.motor4:
                StartActivity.bluetoothSPP.send("4", false);
                break;
        }
    }

    public void musicButtonClick(View v) {
        switch (v.getId()) {
            case R.id.music_play:
                player.start();
                new Thread(this).start();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            musicCurrentTime.setText(simpleDateFormat.format(player.getCurrentPosition()));
                            musicCurrentTime.post(this);
                        }catch (IllegalStateException e){
                            handler.removeCallbacks(this);
                        }
                    }
                });
                break;
            case R.id.music_stop:
                player.stop();
                break;
            case R.id.music_rewind:
                player.seekTo(player.getCurrentPosition()-2000);
                break;
            case R.id.music_ff:
                player.seekTo(player.getCurrentPosition()+2000);
                break;
        }
    }

    @Override
    public void run() {
        while(player.isPlaying()) {
            try {
                Thread.sleep(1000);
                controller.setProgress(player.getCurrentPosition());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IllegalStateException e){
                handler.removeCallbacks(this);
                break;
            }
        }
    }
}
