package com.indoorscheduleapp.nekketsu.indoorscheduleapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ScheduleClass implements Serializable {
    private String name;


    private ArrayList<TimeClass> times = new ArrayList<>();

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

    public void addTimes(TimeClass timeClass){
        this.times.add(timeClass);
    }
    public TimeClass getTime(int num){
        return times.get(num);
    }
    public ArrayList<TimeClass> getTimes() { return times; }

    public int timeCount(){
        return times.size();
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
    public int documentCount()
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
