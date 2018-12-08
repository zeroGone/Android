package net.skhu.recyclerviewtest;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<String>();
        list.add("김영곤");
        list.add("정형일");
        list.add("양진민");
        list.add("김영곤");
        list.add("정형일");
        list.add("양진민"); list.add("김영곤");
        list.add("정형일");
        list.add("양진민"); list.add("김영곤");
        list.add("정형일");
        list.add("양진민"); list.add("김영곤");
        list.add("정형일");
        list.add("양진민");

        MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(this, list);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myRecyclerViewAdapter);


        ExpandableListView listView = findViewById(R.id.list_view);
        listView.setAdapter(new BaseExpandableListAdapter() {
            @Override
            public int getGroupCount() {
                return 1;
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                return list.size();
            }

            @Override
            public Object getGroup(int groupPosition) {
                return list;
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return list.get(childPosition);
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
                LayoutInflater infalInflater = (LayoutInflater) getApplicationContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = infalInflater.inflate(R.layout.item, null);
                return convertView;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                LayoutInflater infalInflater = (LayoutInflater) getApplicationContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = infalInflater.inflate(R.layout.item, null);
                return convertView;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return false;
            }
        });

    }
}
