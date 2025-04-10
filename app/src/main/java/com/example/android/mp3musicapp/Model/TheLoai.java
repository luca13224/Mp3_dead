package com.example.android.mp3musicapp.Model;

import java.io.Serializable;

public class TheLoai implements Serializable {
    private int idTheLoai;
    private String idChuDe; // Liên kết với ChuDe (tùy chọn)
    private String tenTheLoai;
    private String hinhTheLoai;

    // Constructor mặc định
    public TheLoai() {
    }

    // Constructor đầy đủ
    public TheLoai(int idTheLoai, String idChuDe, String tenTheLoai, String hinhTheLoai) {
        this.idTheLoai = idTheLoai;
        this.idChuDe = idChuDe;
        this.tenTheLoai = tenTheLoai;
        this.hinhTheLoai = hinhTheLoai;
    }

    // Getter và Setter
    public int getIdTheLoai() {
        return idTheLoai;
    }

    public void setIdTheLoai(int idTheLoai) {
        this.idTheLoai = idTheLoai;
    }

    public String getIdChuDe() {
        return idChuDe;
    }

    public void setIdChuDe(String idChuDe) {
        this.idChuDe = idChuDe;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }

    public String getHinhTheLoai() {
        return hinhTheLoai;
    }

    public void setHinhTheLoai(String hinhTheLoai) {
        this.hinhTheLoai = hinhTheLoai;
    }
}