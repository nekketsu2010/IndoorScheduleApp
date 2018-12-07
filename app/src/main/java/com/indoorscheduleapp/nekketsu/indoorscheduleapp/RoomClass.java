package com.indoorscheduleapp.nekketsu.indoorscheduleapp;

public class RoomClass {
    private String number;
    private String name;

    public RoomClass(String number, String name){
        this.number = number;
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }
}
