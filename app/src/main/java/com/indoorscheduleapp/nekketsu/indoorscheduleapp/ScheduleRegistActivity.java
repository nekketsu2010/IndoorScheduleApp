package com.indoorscheduleapp.nekketsu.indoorscheduleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ScheduleRegistActivity extends AppCompatActivity {

    private RadioGroup typeGroup;
    private RadioGroup templateGroup;
    private LinearLayout pickerGroup;

    private Button addButton;

    private EditText scheduleNameText;
    private CheckBox onceCheck;
    private CheckBox[] daysCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_regist);

        addButton = findViewById(R.id.addButton);
        //はじめ追加ボタンを押せなくしておく
        addButton.setEnabled(false);

        scheduleNameText = findViewById(R.id.scheduleNameText);
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

        typeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = findViewById(checkedId);
                if(button.equals(findViewById(R.id.templateButton))){
                    pickerGroup.setEnabled(false);
                    typeGroup.setEnabled(true);
                }
                else{
                    pickerGroup.setEnabled(true);
                    typeGroup.setEnabled(false);
                }
            }
        });
    }

    //追加ボタンが押されたら（追加処理）
    public void addButtonClicked(View view){
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

        //タイプの追加（0:テンプレ　1:時間設定）
        int type = 0;
        if(findViewById(R.id.pickerButton).isSelected()){
            type = 1;
        }
        schedule.setType(type);
    }
}
