package com.example.du_an_mau_master.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.du_an_mau_master.R;
import com.example.du_an_mau_master.adapter.adapterLoaiSach;
import com.example.du_an_mau_master.dao.daoLoaiSach;
import com.example.du_an_mau_master.model.LoaiSach;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Fragment_LoaiSach extends Fragment {
    RecyclerView rcvLoaiSach;
    FloatingActionButton flt_btn_Them;
    private ArrayList<LoaiSach> list = new ArrayList<>();
    private ArrayList<LoaiSach> searchList;
    daoLoaiSach daoLoaiSach;
    adapterLoaiSach adapterLoaiSach;
    EditText txtTenLoai;

    public Fragment_LoaiSach() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__loai_sach, container, false);
        rcvLoaiSach = view.findViewById(R.id.rcvLoaiSach);
        flt_btn_Them = view.findViewById(R.id.flt_btn_Them);

        //
        daoLoaiSach = new daoLoaiSach(getContext());
        list = daoLoaiSach.selectAll();
        adapterLoaiSach = new adapterLoaiSach(list, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvLoaiSach.setLayoutManager(linearLayoutManager);
        rcvLoaiSach.setAdapter(adapterLoaiSach);
        adapterLoaiSach.notifyDataSetChanged();

        flt_btn_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog_Them();
            }
        });

        return view;
    }
    public void openDialog_Them() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.layout_them_loaisach, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        txtTenLoai = view.findViewById(R.id.txtTenLoai);
        view.findViewById(R.id.btnThem_LS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = txtTenLoai.getText().toString();

                if(ten.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if(daoLoaiSach.insert(new LoaiSach(ten))) {
                        list.clear();
                        list.addAll(daoLoaiSach.selectAll());
                        dialog.dismiss();
                        adapterLoaiSach.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        view.findViewById(R.id.btnHuy_LS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}