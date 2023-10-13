package com.example.du_an_mau_master.model;

public class Top10 {
    private String tenSach;
    private int soLuong;

    public Top10() {
    }

    public Top10(String tenSach, int soLuong) {
        this.tenSach = tenSach;
        this.soLuong = soLuong;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
