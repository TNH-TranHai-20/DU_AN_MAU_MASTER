package com.example.du_an_mau_master.dao;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.du_an_mau_master.database.dbHelper;
import com.example.du_an_mau_master.model.PhieuMuon;
import com.example.du_an_mau_master.model.Top10;

import java.util.ArrayList;

public class daoPhieuMuon {
    private final dbHelper dbHelper;
    daoSach daoSach;

    public daoPhieuMuon(Context context) {
        dbHelper = new dbHelper(context);
        daoSach = new daoSach(context);
    }

    public ArrayList<PhieuMuon> selectAll() {
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteDatabase dbb = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            Cursor cursor =db.rawQuery("SELECT * FROM tb_PhieuMuon INNER JOIN tb_ThanhVien ON tb_PhieuMuon.maTV = tb_ThanhVien.maTV INNER JOIN tb_Sach ON tb_PhieuMuon.maSach = tb_Sach.maSach", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    PhieuMuon pm = new PhieuMuon();
                    pm.setMaPhieuMuon(cursor.getInt(0));
                    pm.setMaTT(cursor.getString(1));
                    pm.setMaTV(cursor.getInt(2));
                    pm.setMaSach(cursor.getInt(3));
                    pm.setTienThue(cursor.getInt(12));
                    pm.setNgayMuon(cursor.getString(5));
                    pm.setTrangThai(cursor.getInt(6));
                    pm.setTenTV(cursor.getString(8));
                    pm.setTenSach(cursor.getString(11));
                    values.put("tienThue", cursor.getInt(12));
                    dbb.update("tb_PhieuMuon", values, "maPhieuMuon = ?", new String[]{String.valueOf(cursor.getInt(0))});
                    list.add(pm);
                    cursor.moveToNext();
                }
            }

        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return list;
    }
    public boolean insert(PhieuMuon pm) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maTT", pm.getMaTT());
        values.put("maTV", pm.getMaTV());
        values.put("maSach", pm.getMaSach());
        values.put("tienThue", pm.getTienThue());
        values.put("ngayMuon", pm.getNgayMuon());
        values.put("traSach", pm.getTrangThai());
        long row = db.insert("tb_PhieuMuon", null, values);
        return (row > 0);
    }
    public boolean update(PhieuMuon pm) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maTT", pm.getMaTT());
        values.put("maTV", pm.getMaTV());
        values.put("maSach", pm.getMaSach());
        values.put("tienThue", pm.getTienThue());
        values.put("ngayMuon", pm.getNgayMuon());
        values.put("traSach", pm.getTrangThai());
        long row = db.update("tb_PhieuMuon", values, "maPhieuMuon = ?", new String[]{String.valueOf(pm.getMaPhieuMuon())});
        return (row > 0);
    }
    public boolean delete(int maPhieuMuon) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long row = db.delete("tb_PhieuMuon", "maPhieuMuon=?", new String[]{String.valueOf(maPhieuMuon)});
        return (row > 0);
    }
    String ngayThue;
    public String getNgayThue(String maPM) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT ngayMuon FROM tb_PhieuMuon WHERE tb_PhieuMuon.maPhieuMuon = ?", new String[] {maPM});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    ngayThue = cursor.getString(0);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return ngayThue;
    }
    public ArrayList<Top10> getTop() {
        ArrayList<Top10> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor =db.rawQuery("SELECT maSach, count(maSach) AS soLuong FROM tb_PhieuMuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Top10 top10 = new Top10();
                    String tenS = daoSach.getTenS(cursor.getInt(0));
                    top10.setTenSach(tenS);
                    top10.setSoLuong(cursor.getInt(1));
                    list.add(top10);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return list;
    }
    public int getDoanhThu(String tuNgay, String denNgay) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Integer> list = new ArrayList<>();
        try {
            Cursor cursor =db.rawQuery("SELECT SUM(tienThue) FROM tb_PhieuMuon WHERE ngayMuon BETWEEN ? AND ?", new String[] {tuNgay, denNgay});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    list.add(cursor.getInt(0));
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return list.get(0);
    }
}
