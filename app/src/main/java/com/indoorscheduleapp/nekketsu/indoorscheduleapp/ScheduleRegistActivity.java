package com.indoorscheduleapp.nekketsu.indoorscheduleapp;

import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class ScheduleRegistActivity extends AppCompatActivity{

    private RadioGroup typeGroup;
    private RadioGroup templateGroup;
    private ArrayList<View> templates = new ArrayList<>();
    private LinearLayout pickerGroup;
    private ArrayList<View> pickers = new ArrayList<>();

    private Button addButton;

    private EditText scheduleNameText;
    private CheckBox onceCheck;
    private CheckBox[] daysCheck = new CheckBox[7];

    private TimePicker beginTimePicker, endTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_regist);

        addButton = findViewById(R.id.addButton);
        //はじめ追加ボタンを押せなくしておく
        addButton.setEnabled(false);

        scheduleNameText = findViewById(R.id.scheduleNameText);
        scheduleNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                addButton.setEnabled(!scheduleNameText.getText().toString().isEmpty());
            }
        });
        onceCheck = findViewById(R.id.onceCheck);
        daysCheck[0] = findViewById(R.id.sun);
        daysCheck[1] = findViewById(R.id.mon);
        daysCheck[2] = findViewById(R.id.tue);
        daysCheck[3] = findViewById(R.id.wed);
        daysCheck[4] = findViewById(R.id.thu);
        daysCheck[5] = findViewById(R.id.fri);
        daysCheck[6] = findViewById(R.id.sat);

        typeGroup = findViewById(R.id.typeGroup);
        templateGroup = findViewById(R.id.templateGroup);
        pickerGroup = findViewById(R.id.pickerGroup);

        beginTimePicker = findViewById(R.id.beginTime_picker);
        endTimePicker = findViewById(R.id.endTime_picker);

        templates = templateGroup.getTouchables();
        pickers = pickerGroup.getTouchables();

        ArrayList<View> types = typeGroup.getTouchables();
        types.get(0).setSelected(true);
        for(View view : pickers){
            view.setEnabled(false);
        }
        beginTimePicker.setEnabled(false);
        endTimePicker.setEnabled(false);

        typeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = findViewById(checkedId);
                if(button.equals(findViewById(R.id.templateButton))){
                    for(View view : pickers){
                        view.setEnabled(false);
                    }
                    for(View view : templates){
                        view.setEnabled(true);
                    }
                    beginTimePicker.setEnabled(false);
                    endTimePicker.setEnabled(false);
                }
                else{
                    for(View view : pickers){
                        view.setEnabled(true);
                    }
                    for(View view : templates){
                        view.setEnabled(false);
                    }
                    beginTimePicker.setEnabled(true);
                    endTimePicker.setEnabled(true);
                }
            }
        });
    }

    //追加ボタンが押されたら（追加処理）
    public void addButtonClicked(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //オブジェクトの作成
                        ScheduleClass schedule = new ScheduleClass();

                        //予定名の追加
                        schedule.setName(scheduleNameText.getText().toString());

                        //１回のみかどうかの追加
                        schedule.setOnce(onceCheck.isChecked());

                        //曜日の追加
                        boolean days[] = new boolean[]
                                {daysCheck[0].isChecked(), daysCheck[1].isChecked(), daysCheck[2].isChecked(), daysCheck[3].isChecked(),
                                        daysCheck[4].isChecked(), daysCheck[5].isChecked(), daysCheck[6].isChecked()};
                        schedule.setDay(days);

                        //タイプの追加（0:テンプレ　1:時間設定）
                        int type = 0;
                        if(findViewById(R.id.pickerButton).isSelected()){
                            type = 1;
                        }
                        schedule.setType(type);

                        //テンプレ時間のセット(type=0のときしか使わないけど)
                        if(type == 0){
                            for(View template : templates){
                                if(template.isSelected()){
                                    if(template.equals(findViewById(R.id.J1T))){
                                        Calendar[] cal = createCalendar(9, 10, 10, 0);
                                        schedule.setBeginTime(cal[0]);
                                        schedule.setBeginTime(cal[1]);
                                    } else if(template.equals(findViewById(R.id.J2T))){
                                        Calendar[] cal = createCalendar(10, 10, 11, 00);
                                        schedule.setBeginTime(cal[0]);
                                        schedule.setEndTime(cal[1]);
                                    } else if(template.equals(findViewById(R.id.J3T))){
                                        Calendar[] cal = createCalendar(11, 10, 12, 00);
                                        schedule.setBeginTime(cal[0]);
                                        schedule.setEndTime(cal[1]);
                                    } else if(template.equals(findViewById(R.id.J4T))){
                                        Calendar[] cal = createCalendar(12, 40, 13, 30);
                                        schedule.setBeginTime(cal[0]);
                                        schedule.setEndTime(cal[1]);
                                    } else if(template.equals(findViewById(R.id.J5T))){
                                        Calendar[] cal = createCalendar(13, 40, 14, 30);
                                        schedule.setBeginTime(cal[0]);
                                        schedule.setEndTime(cal[1]);
                                    } else if(template.equals(findViewById(R.id.J6T))){
                                        Calendar[] cal = createCalendar(14, 40, 15, 30);
                                        schedule.setBeginTime(cal[0]);
                                        schedule.setEndTime(cal[1]);
                                    } else if(template.equals(findViewById(R.id.J7T))){
                                        Calendar[] cal = createCalendar(15, 40, 16, 30);
                                        schedule.setBeginTime(cal[0]);
                                        schedule.setEndTime(cal[1]);
                                    } else if(template.equals(findViewById(R.id.J8T))){
                                        Calendar[] cal = createCalendar(16, 40, 17, 30);
                                        schedule.setBeginTime(cal[0]);
                                        schedule.setEndTime(cal[1]);
                                    } else if(template.equals(findViewById(R.id.S1T))){
                                        Calendar[] cal = createCalendar(9, 20, 11, 00);
                                        schedule.setBeginTime(cal[0]);
                                        schedule.setEndTime(cal[1]);
                                    } else if(template.equals(findViewById(R.id.S2T))){
                                        Calendar[] cal = createCalendar(11, 10, 12, 50);
                                        schedule.setBeginTime(cal[0]);
                                        schedule.setEndTime(cal[1]);
                                    } else if(template.equals(findViewById(R.id.S3T))){
                                        Calendar[] cal = createCalendar(13, 40, 15, 20);
                                        schedule.setBeginTime(cal[0]);
                                        schedule.setEndTime(cal[1]);
                                    } else if(template.equals(findViewById(R.id.S4T))){
                                        Calendar[] cal = createCalendar(15, 30, 17, 10);
                                        schedule.setBeginTime(cal[0]);
                                        schedule.setEndTime(cal[1]);
                                    } else if(template.equals(findViewById(R.id.S5T))){
                                        Calendar[] cal = createCalendar(17, 20, 19, 00);
                                        schedule.setBeginTime(cal[0]);
                                        schedule.setEndTime(cal[1]);
                                    }
                                }
                            }
                            //タイムピッカー時間のセット（type=1のとき）
                        }else{
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                Calendar[] cal = createCalendar(beginTimePicker.getHour(), beginTimePicker.getMinute(), endTimePicker.getHour(), endTimePicker.getMinute());
                                schedule.setBeginTime(cal[0]);
                                schedule.setEndTime(cal[1]);
                            }
                        }

                        UserData.scheduleClasses.add(schedule);
                        Log.d("追加", "スケジュールを追加しました！" + schedule.getName());
                        finish();
                    }
                });
            }
        }).start();
    }

    public static Calendar[] createCalendar(int beginHour, int beginMinute, int endHour, int endMinute){
        Calendar beginCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        beginCal.set(Calendar.HOUR_OF_DAY, beginHour);
        beginCal.set(Calendar.MINUTE, beginMinute);
        endCal.set(Calendar.HOUR_OF_DAY, endHour);
        endCal.set(Calendar.MINUTE, endMinute);
        return new Calendar[]{beginCal, endCal};
    }
}
