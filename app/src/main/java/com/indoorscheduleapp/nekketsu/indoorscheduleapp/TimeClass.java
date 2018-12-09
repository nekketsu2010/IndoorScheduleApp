package com.indoorscheduleapp.nekketsu.indoorscheduleapp;

import java.io.Serializable;
import java.util.Calendar;

public class TimeClass implements Serializable {
    private String roomName; //情報通信サービス研究室　などが入る

    private int type = 0; //0:講義、1:スケジュール（ゼミなど）
    private boolean isOnce = false; //１回のみかどうか

    //isOnceがFalseのときつかうよ
    private boolean[] day = new boolean[7]; //日～土　何曜日に発動するのか

    //type=0のとき使用
    private String timeTable; //千住１限　といった時間割の文字列

    private Calendar beginTime = Calendar.getInstance();
    private Calendar endTime = Calendar.getInstance();

    //通知をしたかどうか（通知は一日一回）
    private boolean isNotice = false;

    public TimeClass(){
        beginTime.set(Calendar.HOUR_OF_DAY, 23);
    }

    public void setRoomName(String roomName)
    {
        this.roomName = roomName;
    }
    public String getRoomName()
    {
        return roomName;
    }

    public void setType(int type)
    {
        this.type = type;
    }
    public int getType()
    {
        return type;
    }

    public void setOnce(boolean once) {
        isOnce = once;
    }
    public boolean getOnce() {
        return isOnce;
    }

    public void setDay(boolean[] day)
    {
        this.day = day;
    }

    public void setOneDay(boolean day, int num){
        this.day[num] = day;
    }

    public boolean[] getDay()
    {
        return day;
    }

    public void setTimeTable(String timeTable)
    {
        this.timeTable = timeTable;
    }
    public String getTimeTable()
    {
        return timeTable;
    }

    public void setBeginTime(Calendar beginTime) {
        this.beginTime = beginTime;
    }

    public Calendar getBeginTime() {
        return beginTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setNotice(boolean notice) {
        isNotice = notice;
    }
    public boolean getNotice() {
        return isNotice;
    }
}
