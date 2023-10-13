package com.example.du_an_mau_master.model;

public class PhieuMuon {
    private int maPhieuMuon;
    private String maTT;
    private int maTV;
    private int maSach;
    private int tienThue;
    private String ngayMuon;
    private int trangThai;
    private String tenTV;
    private String tenSach;

    public PhieuMuon() {
    }

    public PhieuMuon(String maTT, int maTV, int maSach, int tienThue, String ngayMuon, int trangThai, String tenTV, String tenSach) {
        this.maTT = maTT;
        this.maTV = maTV;
        this.maSach = maSach;
        this.tienThue = tienThue;
        this.ngayMuon = ngayMuon;
        this.trangThai = trangThai;
        this.tenTV = tenTV;
        this.tenSach = tenSach;
    }

    public PhieuMuon(String maTT, int maTV, int maSach, int tienThue, String ngayMuon, int trangThai) {
        this.maTT = maTT;
        this.maTV = maTV;
        this.maSach = maSach;
        this.tienThue = tienThue;
        this.ngayMuon = ngayMuon;
        this.trangThai = trangThai;
    }

    public int getMaPhieuMuon() {
        return maPhieuMuon;
    }

    public void setMaPhieuMuon(int maPhieuMuon) {
        this.maPhieuMuon = maPhieuMuon;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getTienThue() {
        return tienThue;
    }

    public void setTienThue(int tienThue) {
        this.tienThue = tienThue;
    }

    public String getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(String ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getTenTV() {
        return tenTV;
    }

    public void setTenTV(String tenTV) {
        this.tenTV = tenTV;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }
}
