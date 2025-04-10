package com.example.android.mp3musicapp.Model;

import java.io.Serializable;

public class PlayList implements Serializable {
    private int idPlayList;
    private String ten;
    private String hinhNen;

    // Constructor mặc định
    public PlayList() {
    }

    // Constructor đầy đủ
    public PlayList(int idPlayList, String ten, String hinhNen) {
        this.idPlayList = idPlayList;
        this.ten = ten;
        this.hinhNen = hinhNen;
    }

    // Getter và Setter
    public int getIdPlayList() {
        return idPlayList;
    }

    public void setIdPlayList(int idPlayList) {
        this.idPlayList = idPlayList;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHinhNen() {
        return hinhNen;
    }

    public void setHinhNen(String hinhNen) {
        this.hinhNen = hinhNen;
    }
}