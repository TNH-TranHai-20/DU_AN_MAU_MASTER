package com.example.du_an_mau_master.dao;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.du_an_mau_master.database.dbHelper;
import com.example.du_an_mau_master.model.ThuThu;


import java.util.ArrayList;

public class daoThuThu {
    private final dbHelper dbHelper;

    public daoThuThu(Context context) {
        dbHelper = new dbHelper(context);
    }

    public ArrayList<ThuThu> selectAll() {
        ArrayList<ThuThu> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor =db.rawQuery("SELECT * FROM tb_ThuThu", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    ThuThu thuThu = new ThuThu();
                    thuThu.setMaTT(cursor.getString(0));
                    thuThu.setTenTT(cursor.getString(1));
                    thuThu.setMatKhau(cursor.getString(2));
                    thuThu.setChucVu(cursor.getInt(3));
                    list.add(thuThu);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return list;
    }
    public boolean insert(ThuThu tt) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maTT", tt.getMaTT());
        values.put("tenTT", tt.getTenTT());
        values.put("matKhau", tt.getMatKhau());
        values.put("chucVu", tt.getChucVu());
        long row = db.insert("tb_ThuThu", null, values);
        return (row > 0);
    }
    public boolean update(ThuThu tt) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maTT", tt.getMaTT());
        values.put("tenTT", tt.getTenTT());
        values.put("matKhau", tt.getMatKhau());
        values.put("chucVu", tt.getChucVu());
        long row = db.update("tb_ThuThu", values, "maTT = ?", new String[]{String.valueOf(tt.getMaTT())});
        return (row > 0);
    }
    public boolean checklogin(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tb_ThuThu WHERE maTT=? and matKhau=?", new String[] {username, password});
//        int row = cursor.getCount();
        if (cursor.getCount() != 0)
            return true;
        else
            return false;
    }
}
