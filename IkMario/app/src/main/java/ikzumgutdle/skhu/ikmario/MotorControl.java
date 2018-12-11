package ikzumgutdle.skhu.ikmario;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class MotorControl extends AppCompatActivity implements Runnable{
    private MediaPlayer player;
    private SeekBar controller;
    private SimpleDateFormat simpleDateFormat;
    private TextView musicCurrentTime;
    private TextView musicSize;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motor_control);

        simpleDateFormat = new SimpleDateFormat("mm:ss");
        controller = findViewById(R.id.music_controller2);
        controller.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) player.seekTo(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        musicCurrentTime = findViewById(R.id.control_music_current);
        musicSize = findViewById(R.id.control_music_size);
        player = new MediaPlayer();
        handler = new Handler();
        FirebaseApp.initializeApp(this);
        StorageReference music = FirebaseStorage.getInstance().getReference().child("music").child("babyshark.mp3");
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
        try{
            switch (v.getId()){
                case R.id.left_hand_up:
                    StartActivity.writer.write("1".getBytes());
                    break;
                case R.id.left_hand_down:
                    StartActivity.writer.write("2".getBytes());
                    break;
                case R.id.left_leg_up:
                    StartActivity.writer.write("5".getBytes());
                    break;
                case R.id.left_leg_down:
                    StartActivity.writer.write("6".getBytes());
                    break;
                case R.id.right_hand_up:
                    StartActivity.writer.write("3".getBytes());
                    break;
                case R.id.right_hand_down:
                    StartActivity.writer.write("4".getBytes());
                    break;
                case R.id.right_leg_up:
                    StartActivity.writer.write("7".getBytes());
                    break;
                case R.id.right_leg_down:
                    StartActivity.writer.write("8".getBytes());
                    break;
                case R.id.hello:
                    StartActivity.writer.write("9".getBytes());
                    break;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void musicButtonClick2(View v) {
        switch (v.getId()) {
            case R.id.control_music_play:
                try{
                    StartActivity.writer.write("0".getBytes());
                }catch (IOException e){
                    e.printStackTrace();
                }
                player.start();
                new Thread(this).start();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            musicCurrentTime.setText(simpleDateFormat.format(player.getCurrentPosition()));
                            musicCurrentTime.post(this);
                        } catch (IllegalStateException e) {
                            handler.removeCallbacks(this);
                        }
                    }
                });
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int i = 0;
                        while(player.isPlaying()){
                            Log.d("쓰레드:",Integer.toString(i++));
                            Log.d("마리오네트:",Integer.toString(player.getCurrentPosition()/1000));

                            try{
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }).start();
                break;
            case R.id.control_music_stop:
                player.pause();
                break;
            case R.id.control_music_rewind:
                player.seekTo(player.getCurrentPosition()-2000);
                break;
            case R.id.control_music_ff:
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
