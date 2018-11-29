package com.indoorscheduleapp.nekketsu.indoorscheduleapp;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity{

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        ScheduleListAdapter adapter = new ScheduleListAdapter(this, R.layout.schedule_item, UserData.scheduleClasses);
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
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // タップしたアイテムの取得
            ListView listView = (ListView)parent;
            ScheduleClass item = (ScheduleClass) listView.getItemAtPosition(position);  // SampleListItemにキャスト

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Tap No. " + String.valueOf(position));
            builder.setMessage(item.getName());
            builder.show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        ScheduleListAdapter adapter = new ScheduleListAdapter(this, R.layout.schedule_item, UserData.scheduleClasses);
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
                startActivity(intent);
                return true;
            case R.id.unipa:
                Intent intent1 = new Intent(getApplication(), UnipaActivity.class);
                startActivity(intent1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
