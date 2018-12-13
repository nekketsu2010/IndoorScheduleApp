package com.indoorscheduleapp.nekketsu.indoorscheduleapp;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class ScheduleRegistActivity extends AppCompatActivity{
    private static final int RESULT_PICK_IMAGEFILE = 1000;

    private ScheduleClass schedule = new ScheduleClass();
    private int num;
    private ListView TimeList;

    private Button addButton;

    private EditText scheduleNameText;

    private TextView fileNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_schedule_regist);
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

        TimeList = findViewById(R.id.TimeList);

        addButton = findViewById(R.id.addButton);
        //はじめ追加ボタンを押せなくしておく
        addButton.setEnabled(false);

        fileNameText = findViewById(R.id.fileName);

        //MainActivityからscheduleが送られてきたら代入する（送られてくるのはインデックスのみ）
        Intent intent = getIntent();
        num = intent.getIntExtra("index", -1);
        if(num != -1){
            schedule = UserData.scheduleClasses.get(num);
            scheduleNameText.setText(schedule.getName());
            TimeClassAdapter adapter = new TimeClassAdapter(this, R.layout.time_item, schedule.getTimes());
            TimeList.setAdapter(adapter);
            if(schedule.documentCount() != 0){
                fileNameText.setText(schedule.getDocument(0).getDocumentName());
            }
        }

        //生成したscheduleのTimeClassのリストを反映
        TimeClassAdapter adapter = new TimeClassAdapter(this, R.layout.time_item, schedule.getTimes());
        TimeList.setAdapter(adapter);
    }

    //追加ボタンが押されたら（追加処理）
    public void addButtonClicked(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //予定名の追加
                        schedule.setName(scheduleNameText.getText().toString());

                        if(num != -1){
                            UserData.scheduleClasses.set(num, schedule);
                            Log.d("上書き", "スケジュールを上書きしました！" + schedule.getName());
                        }
                        else{
                            UserData.scheduleClasses.add(schedule);
                            Log.d("追加", "スケジュールを追加しました！" + schedule.getName());
                        }
                        finish();
                    }
                });
            }
        }).start();
    }

    //時間追加ボタンが押されたら
    public void addTimeButtonClicked(View view){
        Intent intent = new Intent(this, ScheduleRegistTimeActivity.class);
        intent.putExtra("schedule", schedule);
        startActivityForResult(intent, 0);
    }

    //時間追加アクティビティーから帰ってきたとき
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode) {
            //SecondActivityから戻ってきた場合
            case (0):
                if (resultCode == RESULT_OK) {
                    //OKボタンを押して戻ってきたときの処理
                    //ここでスケジュールを受け取るよ（１つTimeClassが追加されて戻ってくる）
                    schedule.addTimes((TimeClass) data.getSerializableExtra("time"));
                    //生成したscheduleのTimeClassのリストを反映
                    TimeList = findViewById(R.id.TimeList);
                    TimeClassAdapter adapter = new TimeClassAdapter(this, R.layout.time_item, schedule.getTimes());
                    TimeList.setAdapter(adapter);
                } else if (resultCode == RESULT_CANCELED) {
                    //キャンセルボタンを押して戻ってきたときの処理
                    //何もしない
                } else {
                    //その他
                }
                break;
            case(RESULT_PICK_IMAGEFILE):
                if(resultCode == RESULT_OK){
                    Uri uri = null;
                    if (data != null) {
                        uri = data.getData();
                        DocumentClass document = new DocumentClass();
                        int length = uri.getPath().split("/").length-1;
                        Log.d("FileName", uri.getPath().split("/")[length]);
                        Log.d("FilePass", uri.getPath().split(":")[1]);
                        document.setDocumentName(uri.getPath().split("/")[length]);
                        document.setDocumentPass(uri.getPath().split(":")[1]);
                        document.setUri(uri);
                        for(int i=0; i<schedule.documentCount(); i++){
                            schedule.removeDocument(i);
                        }
                        schedule.addDocument(document);
                        fileNameText.setText(document.getDocumentName());
                        try {
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    //講義資料追加ボタンを押したとき
    public void addDocumentButtonClicked(View view){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, RESULT_PICK_IMAGEFILE);
    }
}
