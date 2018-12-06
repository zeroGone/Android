package ikzumgutdle.skhu.ikmario;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements Runnable {

    private MediaPlayer player;
    public static MediaPlayer outputPlayer;
    private SeekBar controller;
    private TextView musicCurrentTime;
    private TextView musicSize;
    private TextView actionListHeader;
    private SimpleDateFormat simpleDateFormat;
    private Handler handler;
    private ArrayList<String> actionList;
    private ArrayList<String[]> outputList;
    private ExpandableListView actionListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simpleDateFormat = new SimpleDateFormat("mm:ss");
        controller = findViewById(R.id.music_controller);
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
                    outputPlayer = new MediaPlayer();
                    outputPlayer.reset();
                    outputPlayer.setDataSource(task.getResult().toString());
                    outputPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        actionList = new ArrayList<>();
        outputList = new ArrayList<>();
        actionList.add("왼손 올리기");
        actionList.add("왼손 내리기");
        actionList.add("오른손 올리기");
        actionList.add("오른손 내리기");
        actionList.add("왼다리 올리기");
        actionList.add("왼다리 내리기");
        actionList.add("오른다리 올리기");
        actionList.add("오른다리 내리기");
        actionList.add("안녕");

        final RecyclerView outputListView = findViewById(R.id.action_list);
        outputListView.setLayoutManager(new LinearLayoutManager(this));
        outputListView.setItemAnimator(new DefaultItemAnimator());
        outputListView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        outputListView.setAdapter(new ActionListAdapter(this, outputList));

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        actionListHeader = inflater.inflate(R.layout.actionlist_item, null).findViewById(R.id.action_list_name);
        actionListHeader.setText("동작 목록");

        actionListView = findViewById(R.id.action_list_view);
        actionListView.setAdapter(new BaseExpandableListAdapter() {
            @Override
            public int getGroupCount() {
                return 1;
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                return actionList.size();
            }

            @Override
            public Object getGroup(int groupPosition) {
                return actionList;
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return actionList.get(childPosition);
            }

            @Override
            public long getGroupId(int groupPosition) {
                return 0;
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                actionListHeader.setTextSize(30);
                actionListHeader.setGravity(Gravity.CENTER);
                actionListHeader.setTypeface(null, Typeface.BOLD);
                return actionListHeader;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                LayoutInflater infalInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final Button button = (Button)infalInflater.inflate(R.layout.action_item, null);
                button.setText(actionList.get(childPosition));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        actionListHeader.setText(button.getText());
                        actionListView.collapseGroup(0);
                    }
                });
                return button;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return false;
            }
        });

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String actionName = actionListHeader.getText().toString();
                EditText minute = findViewById(R.id.input_minutes);
                EditText second = findViewById(R.id.input_seconds);
                String temp1 = minute.getText().toString();
                String temp2 = second.getText().toString();
                String[] size = musicSize.getText().toString().split(":");
                int mm = Integer.parseInt(size[0]);
                int ms = Integer.parseInt(size[1]);
                try{
                    int m = Integer.parseInt(temp1);
                    int s = Integer.parseInt(temp2);
                    if(m>mm||m<0) Toast.makeText(getApplicationContext(), "분 잘못 입력", Toast.LENGTH_LONG).show();
                    else if(s<0||s>=60) Toast.makeText(getApplicationContext(), "초 잘못 입력", Toast.LENGTH_LONG).show();
                    else if(actionName.equals("동작 목록")) Toast.makeText(getApplicationContext(), "동작을 선택해주세요!", Toast.LENGTH_LONG).show();
                    else{
                        String[] str = {temp1+":"+temp2, actionName};
                        outputList.add(str);
                        outputListView.invalidate();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "시간 제대로 입력해주세요", Toast.LENGTH_LONG).show();
                }
                actionListHeader.setText("동작 목록");
                minute.setText("");
                second.setText("");
            }
        });

        Button outputButton = findViewById(R.id.output_button);
        outputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outputPlayer.start();
                new Thread(new OutputThread(outputList)).start();
                for(int i=0; i<outputList.size(); i++) outputList.remove(i);
                outputListView.invalidate();
            }
        });

        Button motorButton = findViewById(R.id.motor_layout_button);
        motorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MotorControl.class);
                startActivity(intent);
            }
        });
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
            case R.id.music_stop:
                player.pause();
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
