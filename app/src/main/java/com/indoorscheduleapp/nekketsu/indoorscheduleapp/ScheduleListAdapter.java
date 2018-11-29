package com.indoorscheduleapp.nekketsu.indoorscheduleapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ScheduleListAdapter extends ArrayAdapter<ScheduleClass> {
    private int mResource;
    private List<ScheduleClass> mItems;
    private LayoutInflater mInflater;

    public ScheduleListAdapter(Context context, int resource, List<ScheduleClass> items) {
        super(context, resource, items);
        mResource = resource;
        mItems = items;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView != null) {
            view = convertView;
        }
        else {
            view = mInflater.inflate(mResource, null);
        }

        // リストビューに表示する要素を取得
        ScheduleClass item = mItems.get(position);

        // タイトルを設定
        TextView title = (TextView)view.findViewById(R.id.subjectName);
        title.setText(item.getName());

        return view;
    }
}
