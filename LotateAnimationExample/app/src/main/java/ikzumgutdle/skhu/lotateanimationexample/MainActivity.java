package ikzumgutdle.skhu.lotateanimationexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageButton button = findViewById(R.id.circle);
        Log.d("test", "버튼위치값:x:"+Integer.toString((int)button.getPivotX())+",y:"+Integer.toString((int)button.getPivotY()));

        button.setOnTouchListener(new View.OnTouchListener() {
            private int x;
            RotateAnimation rotateAnimation;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int eventAction = event.getAction();
                switch (eventAction){
                    case MotionEvent.ACTION_DOWN:
                        x = (int)event.getX();
                        Log.d("test", "down:x:"+Integer.toString(x)+",y:"+Integer.toString((int)event.getY()));
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("test", "up:x:"+Integer.toString((int)event.getX())+",y:"+Integer.toString((int)event.getY()));
                        x = x-(int)event.getX();
                        Log.d("test","x변화 값:"+Integer.toString(x));
                        if(x>0){
                            rotateAnimation = new RotateAnimation(360,180,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                        }else{
                            rotateAnimation = new RotateAnimation(180,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                        }
                        rotateAnimation.setDuration(2000);
                        button.startAnimation(rotateAnimation);
                        break;
                }
                return false;
            }
        });
    }
}
