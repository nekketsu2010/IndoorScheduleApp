package com.indoorscheduleapp.nekketsu.indoorscheduleapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WifiService extends Service {

    private ArrayList<String> bssids = new ArrayList<>();

    @Override
    public void onCreate(){
        super.onCreate();
        try {
            InputStream is = this.getAssets().open("bssid_db.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = "";
            while((line=br.readLine()) != null){
                String[] csv = line.split(",");
                bssids.add(csv[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 検索時に呼ばれる BroadcastReceiver を登録
        registerReceiver(changeWifi,
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        WifiManager wm = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        wm.startScan();

        int requestCode = intent.getIntExtra("REQUEST_CODE",0);
        Context context = getApplicationContext();
        String channelId = "default";
        String title = "屋内測位講義資料オープン";/*context.getString(R.string.app_name);*/

        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Notification　Channel 設定
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(
                    channelId, title, NotificationManager.IMPORTANCE_DEFAULT);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);

                Notification notification = new Notification.Builder(context, channelId)
                        .setContentTitle(title)
                        // android標準アイコンから
                        .setSmallIcon(android.R.drawable.ic_media_play)
                        .setContentText("Wifi取得中！")
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setWhen(System.currentTimeMillis())
                        .build();

                // startForeground
                startForeground(1, notification);
            }
        }else{
            showNotification(this,"Wifi取得中！", false);
        }

        return START_NOT_STICKY;
    }

    //wifi情報が変わったとき実行
    protected final BroadcastReceiver changeWifi = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //サーバに問い合わせて部屋を特定する
//            unregisterReceiver(this);
            WifiManager wm = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
            List<ScanResult> scanResults = wm.getScanResults();
            String result = "51018 ";

            for(int i=0; i<bssids.size(); i++){
                for(ScanResult scanResult : scanResults){
                    String bssid = bssids.get(i);
                    if(scanResult.BSSID.equals(bssid)){
                        //i+1を使うべきだということは留意
                        result += (i+1) + ":" + scanResult.level + " ";
                        break;
                    }
                }
            }
            //resultをサーバへPOST送信→部屋番号の受け取り
            HttpResponsAsync httpResponsAsync = new HttpResponsAsync();
            httpResponsAsync.setListener(createListener());
            httpResponsAsync.execute(result);

            //通知ができる状態なら通知する


            // 検索時に呼ばれる BroadcastReceiver を登録
            registerReceiver(changeWifi,
                    new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
            wm.startScan();
        }
    };

    private HttpResponsAsync.Listener createListener(){
        return new HttpResponsAsync.Listener() {
            @Override
            public void onSuccess(String result) {
                //ここで部屋番号が文字列型で戻る(ex.51018.0)
                Log.d("返却値", result);
                result = result.trim();
                double resultDouble = Double.parseDouble(result);
                int resultInt = (int)resultDouble;
                String roomNumber = String.valueOf(resultInt);
                try {
                    InputStream is = getApplicationContext().getAssets().open("room_db.txt");
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String line = "";
                    while((line=br.readLine())!=null){
                        Log.d("room", line.split(",")[1]);
                        if(roomNumber.equals(line.split(",")[0])){
                            //部屋名をとりあえずログで出力
                            Log.d("roomName", line.split(",")[1]);
                            showNotification(getApplicationContext(), line.split(",")[1], true);
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // 通知バーを出す
    private void showNotification(final Context ctx, String content, boolean erasable) {
//        String title = getApplicationContext().getString(R.string.app_name);
        int uuid = UUID.randomUUID().hashCode();

        NotificationManager mgr = (NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(ctx, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);

        int requestCode = intent.getIntExtra("REQUEST_CODE",0);
        Context context = getApplicationContext();
        String channelId = "default";
        String title = "屋内測位講義資料オープン";/*context.getString(R.string.app_name);*/

        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Notification　Channel 設定
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(
                    channelId, title , NotificationManager.IMPORTANCE_DEFAULT);

            if(notificationManager != null){
                notificationManager.createNotificationChannel(channel);

                Notification notification = new Notification.Builder(context, channelId)
                        .setContentTitle(title)
                        // android標準アイコンから
                        .setSmallIcon(android.R.drawable.ic_media_play)
                        .setContentText(content)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setWhen(System.currentTimeMillis())
                        .build();
                int flag = Notification.FLAG_NO_CLEAR;
                if(erasable){
                    flag = Notification.FLAG_ONLY_ALERT_ONCE;
                }
                notification.flags = flag;
                // startForeground
                notificationManager.notify(uuid, notification);
            }
        }else{
            // 通知バーを表示する
            // 通知バーの内容を決める
            Notification n = new NotificationCompat.Builder(ctx)
                    .setSmallIcon(android.R.drawable.ic_media_play)
                    .setTicker("サービスが起動しました。")
                    .setWhen(System.currentTimeMillis())    // 時間
                    .setContentTitle(title)
                    .setContentText(content)
                    .setGroup(title)
                    .setContentIntent(contentIntent)// インテント
                    .build();
            int flag = Notification.FLAG_NO_CLEAR;
            if(erasable){
                flag = Notification.FLAG_ONLY_ALERT_ONCE;
            }
            n.flags = flag;
            mgr.notify(uuid, n);
        }
    }
}
