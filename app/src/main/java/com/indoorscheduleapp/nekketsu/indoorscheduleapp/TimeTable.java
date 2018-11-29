package com.indoorscheduleapp.nekketsu.indoorscheduleapp;

import java.util.Date;

public class TimeTable {
    private String name;
    private Date beginTime = new Date();
    private Date endTime = new Date();

    public TimeTable()
    {

    }

    public void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }

    public void setTime(Date beginTime, Date endTime)
    {
        this.beginTime = beginTime;
        this.endTime = endTime;
    }
    public Date getBeginTime()
    {
        return beginTime;
    }
    public Date getEndTime()
    {
        return endTime;
    }

}
