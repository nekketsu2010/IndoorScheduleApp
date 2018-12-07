package com.indoorscheduleapp.nekketsu.indoorscheduleapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity{

    ListView listView;
    ScheduleListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //部屋情報の読み込み
        try {
            InputStream is = this.getAssets().open("room_db.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str;
            while((str=br.readLine()) != null){
                String[] csv = str.split(",");
                RoomClass room = new RoomClass(csv[0], csv[1]);
                ShareData.roomClasses.add(room);
            }
            is.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("更新", "初回の更新だよ");
        listView = findViewById(R.id.listView);
        adapter = new ScheduleListAdapter(this, R.layout.schedule_item, UserData.scheduleClasses);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);

        Intent intent = new Intent(getApplication(), WifiService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        }else{
            startService(intent);
        }
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            // タップしたアイテムの取得
            final ListView listView = (ListView)parent;
            ScheduleClass item = (ScheduleClass) listView.getItemAtPosition(position);  // SampleListItemにキャスト

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(item.getName());
            builder.setMessage("編集または削除");
            builder.setPositiveButton("編集", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //編集ボタンを押したら
                    Intent intent = new Intent(getApplication(), ScheduleRegistActivity.class);
                    intent.putExtra("index", position);
                    startActivityForResult(intent, 0);
                }
            });
            builder.setNeutralButton("削除", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //削除ボタンを押したら
                    UserData.scheduleClasses.remove(position);
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("完了")
                            .setMessage("削除が完了しました");
                    adapter.notifyDataSetChanged();
                }
            });
            builder.show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.d("更新", "リストを更新するよ");
        adapter = new ScheduleListAdapter(this, R.layout.schedule_item, UserData.scheduleClasses);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);
    }

    // アクションバーを表示するメソッド
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    // オプションメニューのアイテムが選択されたときに呼び出されるメソッド
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.schedule:
                Intent intent = new Intent(getApplication(), ScheduleRegistActivity.class);
                startActivityForResult(intent, 0);
                return true;
            case R.id.unipa:
                Intent intent1 = new Intent(getApplication(), UnipaActivity.class);
                startActivityForResult(intent1, 0);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
