package com.indoorscheduleapp.nekketsu.indoorscheduleapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpResponsAsync extends AsyncTask<String, Void, String> {
    private Listener listener;

    @Override
    protected String doInBackground(String... strings) {
        String urlSt = "http://d0259c06.ngrok.io/sample-game-server/libsvm/predict";

        HttpURLConnection httpConn = null;
        StringBuilder sb = new StringBuilder();
        String word = "rssi=" + strings[0];

        try{
            //URL設定
            URL url = new URL(urlSt);
            httpConn = (HttpURLConnection)url.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setInstanceFollowRedirects(false);
            httpConn.setDoInput(true);
            httpConn.setReadTimeout(10000);
            httpConn.setConnectTimeout(20000);
            httpConn.connect();

            OutputStream outStream = null;
            InputStream inputStream = null;

            try{
                Log.d("debug", word);
                outStream = httpConn.getOutputStream();
                outStream.write( word.getBytes("UTF-8"));
                outStream.flush();
                Log.d("debug","flush");
            }catch(IOException e){
                e.printStackTrace();
            }finally {
                if(inputStream != null){
                    inputStream.close();
                }
            }
            final int status = httpConn.getResponseCode();
            if(status == HttpURLConnection.HTTP_OK){
                Log.d("debug", "HTTP_OK");
            }
            else{
                Log.d("debug", String.valueOf(status));
            }

            inputStream = httpConn.getInputStream();
            if(inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    Log.d("debug", line);
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if(httpConn != null){
                httpConn.disconnect();
            }
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);

        if(listener != null){
            listener.onSuccess(result);
        }
    }

    void setListener(Listener listener) {
        this.listener = listener;
    }

    interface Listener {
        void onSuccess(String result);
    }
}

