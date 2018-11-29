package com.indoorscheduleapp.nekketsu.indoorscheduleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ScheduleRegistActivity extends AppCompatActivity {

    private RadioGroup typeGroup;
    private RadioGroup templateGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_regist);

        typeGroup = findViewById(R.id.typeGroup);
        templateGroup = findViewById(R.id.templateGroup);

        typeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = findViewById(checkedId);
                button.setEnabled(false);
                String id = null;
                Log.d("チャンネル", String.valueOf(checkedId));
            }
        });
    }
}
