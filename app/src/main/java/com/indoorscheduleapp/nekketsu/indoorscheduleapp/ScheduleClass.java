package com.indoorscheduleapp.nekketsu.indoorscheduleapp;

import java.util.ArrayList;
import java.util.Date;

public class ScheduleClass {
    private String name;
    private String roomName;
    private int type = 0; //0:講義、1:スケジュール（ゼミなど）
    private boolean isOnce = false; //１回のみかどうか
    //isOnceがFalseのときつかうよ
    private boolean[] day = new boolean[7]; //日～土　何曜日に発動するのか

    //type=0のとき使用
    private String timeTable; //千住１限　といった時間割の文字列

    //type=1のとき使用
    private Date beginTime = new Date();
    private Date endTime = new Date();

    private ArrayList<DocumentClass> documents = new ArrayList<>(); //資料のリスト

    private boolean isClass = false;

    public ScheduleClass() {}

    public void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return this.name;
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
    public boolean isOnce() {
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

    public void setBeginTime(Date beginTime)
    {
        this.beginTime = beginTime;
    }
    public Date getBeginTime()
    {
        return beginTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }
    public Date getEndTime()
    {
        return endTime;
    }

    public void addDocument(DocumentClass document)
    {
        documents.add(document);
    }
    public void removeDocument(int num)
    {
        documents.remove(num);
    }
    public DocumentClass getDocument(int num)
    {
        return documents.get(num);
    }
    public void renewDocument(DocumentClass document, int num)
    {
        documents.set(num,document);
    }
    public int size()
    {
        return documents.size();
    }

    public void setIsClass(boolean isClass)
    {
        this.isClass = isClass;
    }
    public boolean getIsClass()
    {
        return isClass;
    }

}
