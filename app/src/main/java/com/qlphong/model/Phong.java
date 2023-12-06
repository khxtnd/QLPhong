package com.qlphong.model;

import java.io.Serializable;

public class Phong implements Serializable {
    private int ma;
    private String ten, mota;


    public Phong(int ma, String ten, String mota) {
        this.ma = ma;
        this.ten = ten;
        this.mota = mota;
    }

    public Phong(String ten, String mota) {
        this.ten = ten;
        this.mota = mota;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}
