package ikzumgutdle.skhu.ikmario;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ActionListAdapter extends RecyclerView.Adapter{

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView time;
        TextView name;

        public ViewHolder(View view){
            super(view);
            time = view.findViewById(R.id.action_list_time);
            name = view.findViewById(R.id.action_list_name);
        }
    }

    LayoutInflater layoutInflater;
    ArrayList<String[]> arrayList;
    ViewHolder viewHolder;

    public ActionListAdapter(Context context, ArrayList<String[]> arrayList){
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.actionlist_item, viewGroup, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        this.viewHolder.name.setText(arrayList.get(i)[1]);
        this.viewHolder.time.setText(arrayList.get(i)[0]);
    }
}
