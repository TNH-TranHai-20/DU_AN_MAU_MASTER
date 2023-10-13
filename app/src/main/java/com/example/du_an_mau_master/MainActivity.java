package com.example.du_an_mau_master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.du_an_mau_master.dao.daoThuThu;
import com.example.du_an_mau_master.fragment.Fragment_DoanhThu;
import com.example.du_an_mau_master.fragment.Fragment_LoaiSach;
import com.example.du_an_mau_master.fragment.Fragment_PhieuMuon;
import com.example.du_an_mau_master.fragment.Fragment_Sach;
import com.example.du_an_mau_master.fragment.Fragment_ThanhVien;
import com.example.du_an_mau_master.fragment.Fragment_Top10;
import com.example.du_an_mau_master.model.ThuThu;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    daoThuThu daoThuThu;
    ArrayList<ThuThu> listTT = new ArrayList<>();
    TextView tenUser;
    View header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        daoThuThu = new daoThuThu(this);
        listTT = daoThuThu.selectAll();
        header = navigationView.getHeaderView(0);
        tenUser = header.findViewById(R.id.username);
        for(ThuThu x : listTT) {
            if(getIntent().getStringExtra("maTT").equals(x.getMaTT())) {
                tenUser.setText("Welcome " + x.getTenTT());
                if (x.getChucVu() == 0) {
                    navigationView.getMenu().findItem(R.id.item_themthuthu).setVisible(true);
                } else {
                    navigationView.getMenu().findItem(R.id.item_themthuthu).setVisible(false);
                }
            }
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quản lý Phiếu Mượn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.menunav);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if(item.getItemId() == R.id.item_qlpm) {
                    toolbar.setTitle(item.getTitle());
                    fragment = new Fragment_PhieuMuon();
                    replaceFrg(fragment);
                } else if (item.getItemId() == R.id.item_qlls) {
                    toolbar.setTitle(item.getTitle());
                    fragment = new Fragment_LoaiSach();
                    replaceFrg(fragment);
                } else if (item.getItemId() == R.id.item_qls) {
                    toolbar.setTitle(item.getTitle());
                    fragment = new Fragment_Sach();
                    replaceFrg(fragment);
                } else if (item.getItemId() == R.id.item_qltv) {
                    toolbar.setTitle(item.getTitle());
                    fragment = new Fragment_ThanhVien();
                    replaceFrg(fragment);
                } else if (item.getItemId() == R.id.item_top10) {
                    toolbar.setTitle(item.getTitle());
                    fragment = new Fragment_Top10();
                    replaceFrg(fragment);
                } else if (item.getItemId() == R.id.item_doanhthu) {
                    toolbar.setTitle(item.getTitle());
                    fragment = new Fragment_DoanhThu();
                    replaceFrg(fragment);
                } else if (item.getItemId() == R.id.item_themthuthu) {
                    OpenDialog_ThemTT();
                } else if (item.getItemId() == R.id.item_doimatkhau) {

                } else if (item.getItemId() == R.id.item_dangxuat) {
                    OpenDialog_DangXuat();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
        Fragment_PhieuMuon fragment_phieuMuon = new Fragment_PhieuMuon();
        replaceFrg(fragment_phieuMuon);
    }
    public void replaceFrg(Fragment frg) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.framelayout, frg).commit();
    }
    public void OpenDialog_DangXuat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.warning);
        builder.setTitle("WARNING");
        builder.setMessage("Bạn có muốn đăng xuất không ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, log_in.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }
    public void OpenDialog_ThemTT() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.layout_them_thuthu, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        EditText txtMaTT = view.findViewById(R.id.txtMaTT);
        EditText txtTenTT = view.findViewById(R.id.txtTenTT);
        EditText txtMatKhau = view.findViewById(R.id.txtMatKhau);

        view.findViewById(R.id.btnThem_TT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maTT = txtMaTT.getText().toString();
                String tenTT = txtTenTT.getText().toString();
                String matKhau = txtMatKhau.getText().toString();

                if(maTT.isEmpty() || tenTT.isEmpty() || matKhau.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if(daoThuThu.insert(new ThuThu(maTT, tenTT, matKhau, 1))) {
                        daoThuThu.selectAll();
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}