package net.skhu.buttondragtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    float oldXvalue;
    float oldYvalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int width = ((ViewGroup) v.getParent()).getWidth() - v.getWidth();
                int height = ((ViewGroup) v.getParent()).getHeight() - v.getHeight();

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    oldXvalue = event.getX();
                    oldYvalue = event.getY();
                    //  Log.i("Tag1", "Action Down X" + event.getX() + "," + event.getY());
                    Log.i("Tag1", "Action Down rX " + event.getRawX() + "," + event.getRawY());
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    v.setX(event.getRawX() - oldXvalue);
                    v.setY(event.getRawY() - (oldYvalue + v.getHeight()));
                    //  Log.i("Tag2", "Action Down " + me.getRawX() + "," + me.getRawY());
                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    if (v.getX() > width && v.getY() > height) {
                        v.setX(width);
                        v.setY(height);
                    } else if (v.getX() < 0 && v.getY() > height) {
                        v.setX(0);
                        v.setY(height);
                    } else if (v.getX() > width && v.getY() < 0) {
                        v.setX(width);
                        v.setY(0);
                    } else if (v.getX() < 0 && v.getY() < 0) {
                        v.setX(0);
                        v.setY(0);
                    } else if (v.getX() < 0 || v.getX() > width) {
                        if (v.getX() < 0) {
                            v.setX(0);
                            v.setY(event.getRawY() - oldYvalue - v.getHeight());
                        } else {
                            v.setX(width);
                            v.setY(event.getRawY() - oldYvalue - v.getHeight());
                        }
                    } else if (v.getY() < 0 || v.getY() > height) {
                        if (v.getY() < 0) {
                            v.setX(event.getRawX() - oldXvalue);
                            v.setY(0);
                        } else {
                            v.setX(event.getRawX() - oldXvalue);
                            v.setY(height);
                        }
                    }
                }
                return true;
            }
        });
    }
}
