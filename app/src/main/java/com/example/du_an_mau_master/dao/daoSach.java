package com.example.du_an_mau_master.dao;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.du_an_mau_master.database.dbHelper;
import com.example.du_an_mau_master.model.Sach;

import java.util.ArrayList;

public class daoSach {
    private final dbHelper dbHelper;

    public daoSach(Context context) {
        dbHelper = new dbHelper(context);
    }
    public ArrayList<Sach> selectAll() {
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor =db.rawQuery("SELECT * FROM tb_Sach INNER JOIN tb_LoaiSach ON tb_Sach.maLoai = tb_LoaiSach.maLoai", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Sach sach = new Sach();
                    sach.setMaSach(cursor.getInt(0));
                    sach.setTenSach(cursor.getString(1));
                    sach.setGiaThue(cursor.getInt(2));
                    sach.setMaLoai(cursor.getInt(3));
                    sach.setTenLoai(cursor.getString(5));
                    list.add(sach);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return list;
    }
    public Sach getID(String id) {
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor =db.rawQuery("SELECT * FROM tb_Sach WHERE maSach=?", new String[]{id});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Sach sach = new Sach();
                    sach.setMaSach(cursor.getInt(0));
                    sach.setTenSach(cursor.getString(1));
                    sach.setGiaThue(cursor.getInt(2));
                    sach.setMaLoai(cursor.getInt(3));
                    sach.setTenLoai(cursor.getString(5));
                    list.add(sach);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return list.get(0);
    }
    public boolean insert(Sach sach) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenSach", sach.getTenSach());
        values.put("giaThue", sach.getGiaThue());
        values.put("maLoai", sach.getMaLoai());
        long row = db.insert("tb_Sach", null, values);
        return (row > 0);
    }
    public boolean update(Sach sach) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenSach", sach.getTenSach());
        values.put("giaThue", sach.getGiaThue());
        values.put("maLoai", sach.getMaLoai());
        long row = db.update("tb_Sach", values, "maSach = ?", new String[]{String.valueOf(sach.getMaSach())});
        return (row > 0);
    }
    public boolean delete(int maSach) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long row = db.delete("tb_Sach", "maSach=?", new String[]{String.valueOf(maSach)});
        return (row > 0);
    }
    int row;
    public int getMaS(String tenS) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT maSach FROM tb_Sach WHERE tb_Sach.tenSach = ?", new String[] {tenS});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    row = cursor.getInt(0);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return row;
    }
    String tenSach;
    public String getTenS(int maSach) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT tenSach FROM tb_Sach WHERE tb_Sach.maSach = ?", new String[] {String.valueOf(maSach)});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    tenSach = cursor.getString(0);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return tenSach;
    }
    int tienThue;
    public int getTienThue(String tenS) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT giaThue FROM tb_Sach WHERE tb_Sach.tenSach = ?", new String[] {tenS});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    tienThue = cursor.getInt(0);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return tienThue;
    }
}
