package com.example.android.mp3musicapp.Model;

import java.io.Serializable;

public class BaiHat implements Serializable {
    private int idBaiHat;
    private String tenBaiHat;
    private String hinhBaiHat;
    private String caSi;
    private String linkBaiHat;
    private boolean isLiked;

    public int getIdBaiHat() { return idBaiHat; }
    public void setIdBaiHat(int idBaiHat) { this.idBaiHat = idBaiHat; }
    public String getTenBaiHat() { return tenBaiHat; }
    public void setTenBaiHat(String tenBaiHat) { this.tenBaiHat = tenBaiHat; }
    public String getHinhBaiHat() { return hinhBaiHat; }
    public void setHinhBaiHat(String hinhBaiHat) { this.hinhBaiHat = hinhBaiHat; }
    public String getCaSi() { return caSi; }
    public void setCaSi(String caSi) { this.caSi = caSi; }
    public String getLinkBaiHat() { return linkBaiHat; }
    public void setLinkBaiHat(String linkBaiHat) { this.linkBaiHat = linkBaiHat; }
    public boolean isLiked() { return isLiked; } // Getter
    public void setIsLiked(boolean isLiked) { this.isLiked = isLiked; } // Setter
}