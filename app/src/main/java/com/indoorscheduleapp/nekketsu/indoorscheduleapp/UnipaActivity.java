package com.indoorscheduleapp.nekketsu.indoorscheduleapp;

import android.os.Debug;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
                                            schedule.setName(Name);
                                            schedule.setOneDay(true, i);
                                            UserData.scheduleClasses.add(schedule);
                                        }
                                        for(int k=0; k<UserData.scheduleClasses.size(); k++){
                                            if(UserData.scheduleClasses.get(k).getName().equals(Name)){
                                                UserData.scheduleClasses.get(k).setOneDay(true, i);
                                                break;
                                            }
                                            else if(k == UserData.scheduleClasses.size()-1){
                                                //ここに来るということは、同名のスケジュールは登録されていないということ！
                                                ScheduleClass schedule = new ScheduleClass();
                                                schedule.setName(Name);
                                                schedule.setOneDay(true, i);
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

    public void addSchedule(JSONObject subject, int num){
        try {
            String TimeName = subject.getString("TimeName");
            String Name = subject.getString("Name");

            ScheduleClass schedule = new ScheduleClass();
            schedule.setName(Name);
            schedule.setOneDay(true, num);
            schedule.setType(0);
            //時間を決める際のswitch文
            switch(TimeName){
                case "J1限":
                    break;
            }
            UserData.scheduleClasses.add(schedule);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
