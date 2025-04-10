package com.example.android.mp3musicapp.Model;

import java.io.Serializable;

public class ChuDe implements Serializable {
    private int idChuDe;
    private String tenChuDe;
    private String hinhChuDe;

    // Constructor mặc định
    public ChuDe() {
    }

    // Constructor đầy đủ
    public ChuDe(int idChuDe, String tenChuDe, String hinhChuDe) {
        this.idChuDe = idChuDe;
        this.tenChuDe = tenChuDe;
        this.hinhChuDe = hinhChuDe;
    }

    // Getter và Setter
    public int getIdChuDe() {
        return idChuDe;
    }

    public void setIdChuDe(int idChuDe) {
        this.idChuDe = idChuDe;
    }

    public String getTenChuDe() {
        return tenChuDe;
    }

    public void setTenChuDe(String tenChuDe) {
        this.tenChuDe = tenChuDe;
    }

    public String getHinhChuDe() {
        return hinhChuDe;
    }

    public void setHinhChuDe(String hinhChuDe) {
        this.hinhChuDe = hinhChuDe;
    }
}