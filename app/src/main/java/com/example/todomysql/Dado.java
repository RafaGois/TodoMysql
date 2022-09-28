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

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
