package com.example.du_an_mau_master.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.du_an_mau_master.R;
import com.example.du_an_mau_master.adapter.adapterThanhVien;
import com.example.du_an_mau_master.dao.daoThanhVien;
import com.example.du_an_mau_master.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Fragment_ThanhVien extends Fragment {
    RecyclerView rcvThanhVien;
    FloatingActionButton flt_btn_Them;
    ArrayList<ThanhVien> list = new ArrayList<>();
    ArrayList<ThanhVien> searchList;
    daoThanhVien daoThanhVien;
    adapterThanhVien adapterThanhVien;
    TextView txtTenTV, txtNamSinh;

    public Fragment_ThanhVien() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__thanh_vien, container, false);
        //
        rcvThanhVien = view.findViewById(R.id.rcvThanhVien);
        flt_btn_Them = view.findViewById(R.id.flt_btn_Them);

        daoThanhVien = new daoThanhVien(getContext());
        list = daoThanhVien.selectAll();
        adapterThanhVien = new adapterThanhVien(getContext(), list);
        //
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvThanhVien.setLayoutManager(gridLayoutManager);
        rcvThanhVien.setAdapter(adapterThanhVien);
        adapterThanhVien.notifyDataSetChanged();
        //
        flt_btn_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDialog_Them();
            }
        });



        return view;
    }
    public void OpenDialog_Them() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.layout_them_thanhvien, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        txtTenTV = view.findViewById(R.id.txtTenTV);
        txtNamSinh = view.findViewById(R.id.txtNamSinh);
        view.findViewById(R.id.btnThem_TV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenTV = txtTenTV.getText().toString();
                String namSinh = txtNamSinh.getText().toString();

                if(tenTV.isEmpty() || namSinh.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if(daoThanhVien.insert(new ThanhVien(tenTV, namSinh))) {
                        list.clear();
                        list.addAll(daoThanhVien.selectAll());
                        dialog.dismiss();
                        adapterThanhVien.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        view.findViewById(R.id.btnHuy_TV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}