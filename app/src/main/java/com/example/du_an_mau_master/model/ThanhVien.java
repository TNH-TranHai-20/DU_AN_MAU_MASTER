package com.example.du_an_mau_master.model;

public class ThanhVien {
    private int maTV;
    private String tenTV;
    private String namSinh;

    public ThanhVien() {
    }

    public ThanhVien(String tenTV, String namSinh) {
        this.tenTV = tenTV;
        this.namSinh = namSinh;
    }

    public ThanhVien(int maTV, String tenTV, String namSinh) {
        this.maTV = maTV;
        this.tenTV = tenTV;
        this.namSinh = namSinh;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public String getTenTV() {
        return tenTV;
    }

    public void setTenTV(String tenTV) {
        this.tenTV = tenTV;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }
}
