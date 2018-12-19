package net.skhu.e201432008;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;
        TextView studentNumber;

        public ViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.item_textView1);
            studentNumber = view.findViewById(R.id.item_textView2);
            view.setOnClickListener(this);
        }

        public void setData(){
            Student student = studentList.get(getAdapterPosition());
            name.setText(student.getName());
            studentNumber.setText(Integer.toString(student.getStudentNumber()));
        }

        @Override
        public void onClick(View v) {
            studentList.remove(getAdapterPosition());
            MainActivity activity = (MainActivity)v.getContext();
            activity.myRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    LayoutInflater layoutInflater;
    ArrayList<Student> studentList;

    public MyRecyclerViewAdapter(Context context, ArrayList<Student> studentList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.studentList = studentList;
    }

    @Override
    public int getItemCount() {
        return studentList.size();
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
