package com.indoorscheduleapp.nekketsu.indoorscheduleapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class TimeClassAdapter extends ArrayAdapter<TimeClass> {

    private int mResource;
    private List<TimeClass> mItems;
    private LayoutInflater mInflater;

    String[] days = new String[]{getContext().getString(R.string.sun), getContext().getString(R.string.mon), getContext().getString(R.string.tue), getContext().getString(R.string.wed),
            getContext().getString(R.string.thu), getContext().getString(R.string.fri), getContext().getString(R.string.sat)};

    public TimeClassAdapter(Context context, int resource, List<TimeClass> items) {
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
        TimeClass item = mItems.get(position);

        // タイトルを設定
        TextView title = (TextView)view.findViewById(R.id.time);

        //表示文字の設定
        String text = "";
        if(item.getOnce()){
            text += "一回のみ";
        }
        else{
            for(int i=0; i<days.length; i++){
                if(item.getDay()[i]){
                    text += days[i];
                }
            }
        }
        //開始時間～終了時間
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        text += " " + sdf.format(item.getBeginTime().getTime()) + "～" + sdf.format(item.getEndTime().getTime());
        //UNIPAからだったとき
        if(item.getType()==0){
            text += " " + getContext().getString(R.string.fromUNIPA);
        }
        title.setText(text);

        return view;
    }

}
