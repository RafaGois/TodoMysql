package com.example.todomysql;

public class Dado {

    private int id;
    private String text;
    private String date;

    public Dado(int id, String text, String date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }
}
