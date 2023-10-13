package com.example.du_an_mau_master.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.du_an_mau_master.R;
import com.example.du_an_mau_master.adapter.adapterPhieuMuon;
import com.example.du_an_mau_master.dao.daoPhieuMuon;
import com.example.du_an_mau_master.dao.daoSach;
import com.example.du_an_mau_master.dao.daoThanhVien;
import com.example.du_an_mau_master.model.PhieuMuon;
import com.example.du_an_mau_master.model.Sach;
import com.example.du_an_mau_master.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class Fragment_PhieuMuon extends Fragment {
    RecyclerView rcvPhieuMuon;
    FloatingActionButton flt_btn_Them;
    ArrayList<PhieuMuon> list = new ArrayList<>();
    ArrayList<PhieuMuon> searchList;
    daoPhieuMuon daoPhieuMuon;
    adapterPhieuMuon adapterPhieuMuon;
    Spinner spnThanhVien, spnSach;
    EditText txtNgayThue, txtTienThue;
    daoSach daoSach;
    daoThanhVien daoThanhVien;
    int indexS, indexTV;
    SearchView searchView;
    public Fragment_PhieuMuon() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__phieu_muon, container, false);
        rcvPhieuMuon = view.findViewById(R.id.rcvPhieuMuon);
        flt_btn_Them = view.findViewById(R.id.flt_btn_Them);

        //
        daoPhieuMuon = new daoPhieuMuon(getContext());
        list = daoPhieuMuon.selectAll();
        //
        adapterPhieuMuon = new adapterPhieuMuon(getContext(), list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvPhieuMuon.setLayoutManager(linearLayoutManager);
        rcvPhieuMuon.setAdapter(adapterPhieuMuon);
        adapterPhieuMuon.notifyDataSetChanged();
        //
        flt_btn_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDialog_Them();
            }
        });
        //

        return view;
    }
    public void OpenDialog_Them() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.layout_them_phieumuon, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        txtNgayThue = view.findViewById(R.id.txtNgayThue);
        txtTienThue = view.findViewById(R.id.txtTienThue);
        spnThanhVien = view.findViewById(R.id.spnThanhVien);
        spnSach = view.findViewById(R.id.spnSach);

        daoSach = new daoSach(getContext());
        daoThanhVien = new daoThanhVien(getContext());

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        ArrayList<Sach> listS = new ArrayList<>();
        listS = daoSach.selectAll();
        ArrayList<String> sachArr = new ArrayList<>();
        ArrayList<String> tienThueArr = new ArrayList<>();
        for (Sach x: listS) {
            sachArr.add(x.getTenSach());
            tienThueArr.add(String.valueOf(x.getGiaThue()));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, sachArr);
        spnSach.setAdapter(adapter);
        spnSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                indexS = daoSach.getMaS(sachArr.get(position).toString());
                txtTienThue.setText(String.valueOf(daoSach.getTienThue(sachArr.get(position).toString())));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //
        ArrayList<ThanhVien> listTV = new ArrayList<>();
        listTV = daoThanhVien.selectAll();
        ArrayList<String> thanhVienArr = new ArrayList<>();
        for (ThanhVien x: listTV) {
            thanhVienArr.add(x.getTenTV());
        }
        ArrayAdapter<String> adaptertv = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, thanhVienArr);
        spnThanhVien.setAdapter(adaptertv);
        spnThanhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                indexTV = daoThanhVien.getMaTV(thanhVienArr.get(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        txtNgayThue.setText(String.format("%d/%d/%d", day, month + 1, year));
        view.findViewById(R.id.btnThem_PM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ngayThue = txtNgayThue.getText().toString();
                String tienThue = txtTienThue.getText().toString();
                if (thanhVienArr.isEmpty() || sachArr.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if(daoPhieuMuon.insert(new PhieuMuon("TT01", indexTV, indexS, Integer.valueOf(tienThue), ngayThue, 1))) {
                        list.clear();
                        list.addAll(daoPhieuMuon.selectAll());
                        dialog.dismiss();
                        adapterPhieuMuon.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        view.findViewById(R.id.btnHuy_PM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}