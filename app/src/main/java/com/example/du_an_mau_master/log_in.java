package com.example.du_an_mau_master;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.du_an_mau_master.dao.daoThuThu;


public class log_in extends AppCompatActivity {
    EditText txtTaiKhoan, txtMatKhau;
    CheckBox chkLuuMatKhau;
    daoThuThu daoThuThu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        daoThuThu = new daoThuThu(this);
        Button btnDangNhap = findViewById(R.id.btnDangNhap);
        txtTaiKhoan = findViewById(R.id.txtTaiKhoan);
        txtMatKhau = findViewById(R.id.txtMatKhau);
        chkLuuMatKhau = findViewById(R.id.chkLuuMatKhau);
        txtTaiKhoan.setText("");
        txtMatKhau.setText("");
        chkMatKhau();
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtTaiKhoan.getText().toString();
                String password = txtMatKhau.getText().toString();
                if(username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(log_in.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (daoThuThu.checklogin(username, password)) {
                        Toast.makeText(log_in.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        luuMatKhau(username, password, chkLuuMatKhau.isChecked());
                        Intent intent = new Intent(log_in.this, MainActivity.class);
                        intent.putExtra("maTT", username);
                        startActivity(intent);
                    } else {
                        Toast.makeText(log_in.this, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void luuMatKhau(String user, String pass, boolean status) {
        SharedPreferences sharedPreferences = getSharedPreferences("LuuTaiKhoan.txt", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!status) {
            editor.clear();
        } else {
            editor.putString("User", user);
            editor.putString("Pass", pass);
            editor.putBoolean("Luu", status);
        }
        editor.commit();
    }
    public void chkMatKhau() {
        SharedPreferences sharedPreferences = getSharedPreferences("LuuTaiKhoan.txt", MODE_PRIVATE);
        boolean chk = sharedPreferences.getBoolean("Luu", false);
        if (chk) {
            txtTaiKhoan.setText(sharedPreferences.getString("User", ""));
            txtMatKhau.setText(sharedPreferences.getString("Pass", ""));
        }
    }
}