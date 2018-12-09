package com.indoorscheduleapp.nekketsu.indoorscheduleapp;

import android.os.Debug;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

public class UnipaActivity extends AppCompatActivity {

    String[] days = new String[]{"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
    EditText user_id, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unipa);

        user_id = findViewById(R.id.useridText);
        password = findViewById(R.id.PasswordText);
    }

    public void onButtonGet(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://d0259c06.ngrok.io//sample-game-server/unipa_load?user_id=" + user_id.getText().toString() + "&password=" + password.getText().toString());
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    final String str = InputStreamToString(con.getInputStream());
                    Log.d("HTTP", str);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //"""エラー！"""なら止める
                            if(str.contains("エラー")){
                                Toast toast = Toast.makeText(getApplicationContext(), "パスワードが違うよ！", Toast.LENGTH_SHORT);
                                toast.show();
                                return;
                            }

                            //取得したJSONを変換してスケジュールに追加していく
                            try {
                                JSONObject parentJsonObj = new JSONObject(str);
                                for(int i=0; i<parentJsonObj.names().length(); i++){
                                    JSONArray Day = parentJsonObj.getJSONArray(parentJsonObj.names().get(i).toString());
                                    for(int j=0; j<Day.length(); j++){
                                        //TimeName:○限、Name:科目名
                                        JSONObject subject = Day.getJSONObject(j);
                                        String TimeName = subject.getString("TimeName");
                                        String Name = subject.getString("Name");
                                        //もしないときこうなります
                                        if(UserData.scheduleClasses.size()==0){
                                            ScheduleClass schedule = new ScheduleClass();
                                            TimeClass timeClass = new TimeClass();
                                            Calendar[] time = timeTableTime(TimeName);
                                            timeClass.setBeginTime(time[0]);
                                            timeClass.setEndTime(time[1]);
                                            schedule.setName(Name);
                                            timeClass.setOneDay(true, i);
                                            schedule.addTimes(timeClass);
                                            UserData.scheduleClasses.add(schedule);
                                            continue;
                                        }
                                        for(int k=0; k<UserData.scheduleClasses.size(); k++){
                                            if(UserData.scheduleClasses.get(k).getName().equals(Name)){
                                                ScheduleClass scheduleClass = UserData.scheduleClasses.get(k);
                                                for(int l=0; l<UserData.scheduleClasses.get(k).timeCount(); l++){
                                                    TimeClass timeClass = scheduleClass.getTime(l);
                                                    //開始時間と終了時間が同じなら曜日のみ追加
                                                    Calendar[] time = timeTableTime(TimeName);
                                                    if(timeClass.getBeginTime().get(Calendar.HOUR_OF_DAY) == time[0].get(Calendar.HOUR_OF_DAY)
                                                            && timeClass.getBeginTime().get(Calendar.MINUTE) == time[0].get(Calendar.MINUTE)
                                                            && timeClass.getEndTime().get(Calendar.HOUR_OF_DAY) == time[1].get(Calendar.HOUR_OF_DAY)
                                                            && timeClass.getEndTime().get(Calendar.MINUTE) == time[1].get(Calendar.MINUTE)){
                                                        timeClass.setOneDay(true, i);
                                                        scheduleClass.getTimes().set(l, timeClass);
                                                        UserData.scheduleClasses.set(k, scheduleClass);
                                                    }
                                                    else if(l == UserData.scheduleClasses.get(k).timeCount()-1){
                                                        TimeClass timeClass1 = new TimeClass();
                                                        timeClass1.setBeginTime(time[0]);
                                                        timeClass1.setEndTime(time[1]);
                                                        scheduleClass.addTimes(timeClass1);
                                                        UserData.scheduleClasses.set(k, scheduleClass);
                                                    }
                                                }
                                                break;
                                            }
                                            else if(k == UserData.scheduleClasses.size()-1){
                                                //ここに来るということは、同名のスケジュールは登録されていないということ！
                                                ScheduleClass schedule = new ScheduleClass();
                                                TimeClass timeClass = new TimeClass();
                                                Calendar[] time = timeTableTime(TimeName);
                                                timeClass.setBeginTime(time[0]);
                                                timeClass.setEndTime(time[1]);
                                                schedule.setName(Name);
                                                timeClass.setOneDay(true, i);
                                                schedule.addTimes(timeClass);
                                                UserData.scheduleClasses.add(schedule);
                                            }
                                        }
                                    }
                                }
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }).start();
    }

    // InputStream -> String
    static String InputStreamToString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            Log.d("一行",line);
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    public Calendar[] timeTableTime(String TimeName){
        Calendar[] time = new Calendar[2];
        time[0] = Calendar.getInstance();
        time[1] = Calendar.getInstance();
        switch(TimeName){
            case "J1限":
                time = Utility.createCalendar(9, 10, 10, 00);
                break;
            case "J2限":
                time = Utility.createCalendar(10, 10, 11, 00);
                break;
            case "J3限":
                time = Utility.createCalendar(11, 10, 12, 00);
                break;
            case "J4限":
                time = Utility.createCalendar(12, 40, 13, 30);
                break;
            case "J5限":
                time = Utility.createCalendar(13, 40, 14, 30);
                break;
            case "J6限":
                time = Utility.createCalendar(14, 40, 15, 30);
                break;
            case "J7限":
                time = Utility.createCalendar(15, 40, 16, 30);
                break;
            case "J8限":
                time = Utility.createCalendar(16, 40, 17, 30);
                break;
            case "1限":
                time = Utility.createCalendar(9, 20, 11, 00);
                break;
            case "2限":
                time = Utility.createCalendar(11, 10, 12, 50);
                break;
            case "3限":
                time = Utility.createCalendar(13, 40, 15, 20);
                break;
            case "4限":
                time = Utility.createCalendar(15, 30, 17, 10);
                break;
            case "5限":
                time = Utility.createCalendar(17, 20, 19, 00);
                break;
        }
        return time;
    }

}
