package com.indoorscheduleapp.nekketsu.indoorscheduleapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RoomClassListAdapter extends ArrayAdapter<RoomClass> {

    private int mResource;
    private List<RoomClass> mItems;
    private LayoutInflater mInflater;

    public RoomClassListAdapter(Context context, int resource, List<RoomClass> items) {
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
        RoomClass item = mItems.get(position);

        // タイトルを設定
        TextView title = (TextView)view.findViewById(R.id.roomName);
        title.setText(item.getNumber() + " " + item.getName());

        return view;
    }

}
