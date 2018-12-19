package net.skhu.e05list;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Item item = arrayList.get(super.getAdapterPosition());
            item.setChecked(isChecked);
            if (isChecked) ++checkedItemCount; else --checkedItemCount;
            if (checkedItemCount <= 1)
                ((Activity) textView1.getContext()).invalidateOptionsMenu();
        }

        TextView textView1, textView2;
        CheckBox checkBox;

        public ViewHolder(View view){
            super(view);
            textView1 = view.findViewById(R.id.textView1);
            textView2 = view.findViewById(R.id.textView2);
            checkBox = view.findViewById(R.id.checkBox);
            checkBox.setOnCheckedChangeListener(this);
        }

        public void setData(){
            Item item = arrayList.get(getAdapterPosition());
            textView1.setText(item.getTitle());
            textView2.setText(item.getCreateTimeFormatted());
            checkBox.setChecked(item.isChecked());
        }
    }
    int checkedItemCount = 0;

    LayoutInflater layoutInflater;
    ArrayList<Item> arrayList;

    public MyRecyclerViewAdapter(Context context, ArrayList<Item> arrayList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int index) {
        viewHolder.setData();
    }

}
