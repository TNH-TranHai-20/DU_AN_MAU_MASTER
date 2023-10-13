package com.example.du_an_mau_master.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelper extends SQLiteOpenHelper {
    private static final String dbName = "QLTV";
    private static final int version = 1;
    public dbHelper(@Nullable Context context) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Bảng thủ thư
        String tb_ThuThu = "CREATE TABLE tb_ThuThu(maTT text primary key, tenTT text not null, matKhau text not null, chucVu integer)";
        String data_ThuThu = "INSERT INTO tb_ThuThu VALUES ('TranHai', 'Trần Ngọc Hải', 'tranhai', 0)";
        db.execSQL(tb_ThuThu);
        db.execSQL(data_ThuThu);
        // Bảng thành viên
        String tb_ThanhVien= "CREATE TABLE tb_ThanhVien(maTV integer primary key autoincrement, tenTV text not null, namSinh text not null)";
        db.execSQL(tb_ThanhVien);
        String data_ThanhVien = "INSERT INTO tb_ThanhVien VALUES ( 1, 'Xuân Bắc', '29/12/2004'), ( 2, 'Ngọc Hải', '20/10/2004'), ( 3, 'Ngọc Minh', '13/03/2001')";
        db.execSQL(data_ThanhVien);
        // Bảng loai sách
        String tb_LoaiSach= "CREATE TABLE tb_LoaiSach(maLoai integer primary key autoincrement, tenLoai text not null)";
        db.execSQL(tb_LoaiSach);
        // Bảng sách
        String tb_Sach= "CREATE TABLE tb_Sach(maSach integer primary key autoincrement, tenSach text not null, giaThue integer not null, maLoai integer REFERENCES tb_LoaiSach(maLoai))";
        db.execSQL(tb_Sach);
        // Bảng Phiếu Mượn
        String tb_PhieuMuon= "CREATE TABLE tb_PhieuMuon(maPhieuMuon integer primary key autoincrement, maTT text REFERENCES tb_ThuThu(maTT), maTV integer REFERENCES tb_ThanhVien(maTV), maSach integer REFERENCES tb_Sach(maSach), tienThue integer not null,  ngayMuon text not null, traSach integer not null)";
        db.execSQL(tb_PhieuMuon);
//        String data_PhieuMuon = "INSERT INTO tb_PhieuMuon VALUES ( 1, 'TT01', 1, 1, 2000, '20/10/2019', 1)";
//        db.execSQL(data_PhieuMuon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS tb_ThuThu");
            db.execSQL("DROP TABLE IF EXISTS tb_ThanhVien");
            db.execSQL("DROP TABLE IF EXISTS tb_LoaiSach");
            db.execSQL("DROP TABLE IF EXISTS tb_Sach");
            db.execSQL("DROP TABLE IF EXISTS tb_PhieuMuon");
            onCreate(db);
        }
    }
}
