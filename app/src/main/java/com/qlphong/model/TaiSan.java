package com.qlphong.model;

import java.io.Serializable;

public class TaiSan implements Serializable {
    private int ma;
    private String ten, loai, vitri;
    private int giatri;

    public TaiSan(int ma, String ten, String loai, String vitri, int giatri) {
        this.ma = ma;
        this.ten = ten;
        this.loai = loai;
        this.vitri = vitri;
        this.giatri = giatri;
    }

    public TaiSan(String ten, String loai, String vitri, int giatri) {
        this.ten = ten;
        this.loai = loai;
        this.vitri = vitri;
        this.giatri = giatri;
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

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getVitri() {
        return vitri;
    }

    public void setVitri(String vitri) {
        this.vitri = vitri;
    }

    public int getGiatri() {
        return giatri;
    }

    public void setGiatri(int giatri) {
        this.giatri = giatri;
    }
}
