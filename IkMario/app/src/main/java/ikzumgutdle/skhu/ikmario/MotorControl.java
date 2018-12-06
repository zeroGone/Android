package ikzumgutdle.skhu.ikmario;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

public class MotorControl extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motor_control);
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
                case R.id.jump:
                    StartActivity.writer.write("0".getBytes());
                    break;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
