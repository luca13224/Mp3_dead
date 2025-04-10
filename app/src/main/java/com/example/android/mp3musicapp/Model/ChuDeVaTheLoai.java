package com.example.android.mp3musicapp.Model;

import java.io.Serializable;
import java.util.List;

public class ChuDeVaTheLoai implements Serializable {
    private List<TheLoai> theLoai;
    private List<ChuDe> chuDe;

    // Constructor mặc định
    public ChuDeVaTheLoai() {
    }

    // Constructor đầy đủ
    public ChuDeVaTheLoai(List<TheLoai> theLoai, List<ChuDe> chuDe) {
        this.theLoai = theLoai;
        this.chuDe = chuDe;
    }

    // Getter và Setter
    public List<TheLoai> getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(List<TheLoai> theLoai) {
        this.theLoai = theLoai;
    }

    public List<ChuDe> getChuDe() {
        return chuDe;
    }

    public void setChuDe(List<ChuDe> chuDe) {
        this.chuDe = chuDe;
    }
}