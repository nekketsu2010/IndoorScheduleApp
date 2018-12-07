package com.indoorscheduleapp.nekketsu.indoorscheduleapp;

import android.content.Intent;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import java.io.Serializable;
import java.util.Calendar;

public class ScheduleRegistTimeActivity extends AppCompatActivity {

    private CheckBox onceCheck;
    private CheckBox[] daysCheck = new CheckBox[7];

    private RadioGroup roomList;

    private TimePicker beginTimePick, endTimePick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_regist_time);

        onceCheck = findViewById(R.id.onceCheck);
        daysCheck[0] = findViewById(R.id.sun);
        daysCheck[1] = findViewById(R.id.mon);
        daysCheck[2] = findViewById(R.id.tue);
        daysCheck[3] = findViewById(R.id.wed);
        daysCheck[4] = findViewById(R.id.thu);
        daysCheck[5] = findViewById(R.id.fri);
        daysCheck[6] = findViewById(R.id.sat);

        roomList = findViewById(R.id.roomList);
        for(int i=0; i<ShareData.roomClasses.size(); i++){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(ShareData.roomClasses.get(i).getNumber() + " " + ShareData.roomClasses.get(i).getName());
            roomList.addView(radioButton);
        }

        beginTimePick = findViewById(R.id.beginTime_picker);
        endTimePick = findViewById(R.id.endTime_picker);
    }

    public void addTime(View view){
        TimeClass timeClass = new TimeClass();

        //UNIPAからの登録でないのでタイプは１に
        timeClass.setType(1);

        //１回のみかどうかの追加
        timeClass.setOnce(onceCheck.isChecked());

        //曜日の追加
        boolean days[] = new boolean[]
                {daysCheck[0].isChecked(), daysCheck[1].isChecked(), daysCheck[2].isChecked(), daysCheck[3].isChecked(),
                        daysCheck[4].isChecked(), daysCheck[5].isChecked(), daysCheck[6].isChecked()};
        timeClass.setDay(days);

        //部屋の設定
        for(int i=0; i<roomList.getTouchables().size(); i++){
            View view1 = roomList.getTouchables().get(i);
            RadioButton button = (RadioButton) view1;
            if(button.getText().toString().equals(ShareData.roomClasses.get(i).getNumber() + " " + ShareData.roomClasses.get(i).getName()) && button.isChecked()){
                timeClass.setRoomName(ShareData.roomClasses.get(i).getName());
            }
        }
//        for(View view1 : roomList.getTouchables()){
//            RadioButton button = (RadioButton) view1;
//            for(int i=0; i<ShareData.roomClasses.size(); i++){
//                RoomClass room = ShareData.roomClasses.get(i);
//                if(button.getText().toString().equals(room.getNumber() + " " + room.getName()) && button.isChecked()){
//                    timeClass.setRoomName(room.getName());
//                }
//            }
//        }

        //開始・終了時間の設定
        Calendar[] time = new Calendar[2];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            time = Utility.createCalendar(beginTimePick.getHour(), beginTimePick.getMinute(), endTimePick.getHour(), endTimePick.getMinute());
            timeClass.setBeginTime(time[0]);
            timeClass.setEndTime(time[1]);
        }

        Intent intent = new Intent();
        intent.putExtra("time", timeClass);
        setResult(RESULT_OK, intent);
        finish();
    }
}
