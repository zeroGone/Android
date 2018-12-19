package net.skhu.e06list;

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

    class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener, View.OnClickListener{
        @Override
        public void onClick(View view) {
            MainActivity activity = (MainActivity)view.getContext();
            activity.showItemEditDialog(super.getAdapterPosition());
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Item item = itemList.get(super.getAdapterPosition());
            item.setChecked(isChecked);
            if (itemList.getCheckedCount() <= 1)
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
            view.setOnClickListener(this);
        }

        public void setData(){
            Item item = itemList.get(getAdapterPosition());
            textView1.setText(item.getTitle());
            textView2.setText(item.getCreateTimeFormatted());
            checkBox.setChecked(item.isChecked());
        }
    }
    LayoutInflater layoutInflater;
    ItemList itemList;

    public MyRecyclerViewAdapter(Context context, ItemList itemList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.itemList = itemList;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
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
