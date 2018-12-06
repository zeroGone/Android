package ikzumgutdle.skhu.ikmario;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class OutputThread implements Runnable {
    private ArrayList<Action> actions;
    private int timer;

    public OutputThread(ArrayList<String[]> list){
        timer = 0;
        actions = new ArrayList<>();
        for(String[] action:list) actions.add(new Action(action[0], action[1]));

        Collections.sort(actions, new Comparator<Action>() {
            @Override
            public int compare(Action o1, Action o2) {
                return o1.time - o2.time;
            }
        });

        Log.d("로그", Arrays.toString(actions.toArray()));
    }
    @Override
    public void run() {
        while(MainActivity.outputPlayer.isPlaying()){
            Log.d("타이머", Integer.toString(timer));
            if (actions.size()==0) break;
            Action action = actions.get(0);
            if(action.time == timer) {
                try{
                    Log.d("로그", action.name);
                    switch (action.name){
                        case "왼손 올리기":
                            StartActivity.writer.write("1".getBytes());
                            break;
                        case "왼손 내리기":
                            StartActivity.writer.write("2".getBytes());
                            break;
                        case "오른손 올리기":
                            StartActivity.writer.write("3".getBytes());
                            break;
                        case "오른손 내리기":
                            StartActivity.writer.write("4".getBytes());
                            break;
                        case "왼다리 올리기":
                            StartActivity.writer.write("5".getBytes());
                            break;
                        case "왼다리 내리기":
                            StartActivity.writer.write("6".getBytes());
                            break;
                        case "오른다리 올리기":
                            StartActivity.writer.write("7".getBytes());
                            break;
                        case "오른다리 내리기":
                            StartActivity.writer.write("8".getBytes());
                            break;
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
                actions.remove(0);
            }
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            timer ++;
        }
    }

    private class Action{
        private int time;
        private String name;
        public Action(String timeValue, String name){
            String[] time = timeValue.split(":");
            Log.d("시간", Arrays.toString(time));
            this.time = (Integer.parseInt(time[0])*60)+Integer.parseInt(time[1]);
            this.name = name;
            Log.d("시간", Integer.toString(timer));
        }

        @Override
        public String toString(){
            return name+":"+Integer.toString(time);
        }
    }
}
