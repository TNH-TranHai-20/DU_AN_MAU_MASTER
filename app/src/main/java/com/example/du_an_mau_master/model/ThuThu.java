package com.example.du_an_mau_master.model;

public class ThuThu {
    private String maTT;
    private String tenTT;
    private String matKhau;
    private int chucVu;

    public ThuThu() {
    }

    public ThuThu(String maTT, String tenTT, String matKhau, int chucVu) {
        this.maTT = maTT;
        this.tenTT = tenTT;
        this.matKhau = matKhau;
        this.chucVu = chucVu;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public String getTenTT() {
        return tenTT;
    }

    public void setTenTT(String tenTT) {
        this.tenTT = tenTT;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getChucVu() {
        return chucVu;
    }

    public void setChucVu(int chucVu) {
        this.chucVu = chucVu;
    }
}
