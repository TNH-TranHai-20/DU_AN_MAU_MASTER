package com.example.du_an_mau_master.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.du_an_mau_master.R;
import com.example.du_an_mau_master.dao.daoLoaiSach;
import com.example.du_an_mau_master.dao.daoPhieuMuon;
import com.example.du_an_mau_master.dao.daoSach;
import com.example.du_an_mau_master.model.LoaiSach;
import com.example.du_an_mau_master.model.Sach;


import java.util.ArrayList;

public class adapterSach extends RecyclerView.Adapter<adapterSach.ViewHolder>{
    private final Context context;
    private final ArrayList<Sach> list;
    private ArrayList<LoaiSach> listLS;
    daoSach daoSach;
    daoLoaiSach daoLoaiSach;
    daoPhieuMuon daoPhieuMuon;
    Sach indexSach;
    int index;
    EditText txtTenSach, txtGiaThue;
    TextView lblMaSach;
    Spinner spnLoaiSach;
    public adapterSach(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
        daoSach = new daoSach(context);
        daoLoaiSach = new daoLoaiSach(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_sach, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.lblMaSach.setText(String.valueOf(list.get(position).getMaSach()));
        holder.lblTenSach.setText(list.get(position).getTenSach());
        holder.lblGiaThue.setText(String.valueOf(list.get(position).getGiaThue()));
        holder.lblLoaiSach.setText(String.valueOf(list.get(position).getTenLoai()));
        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexSach = list.get(position);
                openDialog_del();
            }
        });
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexSach = list.get(position);
                OpenDialog_Update();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblMaSach, lblTenSach, lblGiaThue, lblLoaiSach;
        ImageButton btnXoa, btnUpdate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblMaSach = itemView.findViewById(R.id.lblMaSach);
            lblTenSach = itemView.findViewById(R.id.lblTenSach);
            lblGiaThue = itemView.findViewById(R.id.lblGiaThue);
            lblLoaiSach = itemView.findViewById(R.id.lblLoaiSach);
            btnXoa = itemView.findViewById(R.id.btnXoa);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
        }
    }
    public void openDialog_del() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.warning);
        builder.setTitle("Warning");
        builder.setMessage("Bạn có chắc chắn muốn xóa không ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (daoSach.delete(indexSach.getMaSach())) {
                    list.clear();
                    list.addAll(daoSach.selectAll());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "XÓa thất bại", Toast.LENGTH_SHORT).show();
                }
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
    public void OpenDialog_Update() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_sua_sach, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        lblMaSach = view.findViewById(R.id.lblMaSach);
        txtTenSach = view.findViewById(R.id.txtTenSach_Up);
        txtGiaThue = view.findViewById(R.id.txtGiaThue_Up);
        spnLoaiSach = view.findViewById(R.id.spnLoaiSach_Up);

        lblMaSach.setText("Mã sách: " + indexSach.getMaSach());

        ArrayList<LoaiSach> listLS = new ArrayList<>();
        listLS = daoLoaiSach.selectAll();
        ArrayList<String> loaiSachArr = new ArrayList<>();
        for (LoaiSach x: listLS) {
            loaiSachArr.add(x.getTenLoai());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, loaiSachArr);
        spnLoaiSach.setAdapter(adapter);

        for (int i = 0; i < loaiSachArr.size(); i++) {
            if (loaiSachArr.get(i).toString().equals(indexSach.getTenLoai())) {
                spnLoaiSach.setSelection(i);
            }
        }
        txtTenSach.setText(String.valueOf(indexSach.getTenSach()));
        txtGiaThue.setText(String.valueOf(indexSach.getGiaThue()));
        spnLoaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index = daoLoaiSach.getMaLoai(loaiSachArr.get(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        view.findViewById(R.id.btnUpdate_S).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = txtTenSach.getText().toString();
                String giaThue = txtGiaThue.getText().toString();

                indexSach.setTenSach(tenSach);
                indexSach.setGiaThue(Integer.valueOf(giaThue));
                indexSach.setMaLoai(index);

                if(tenSach.isEmpty() || giaThue.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if(giaThue.matches("\\d+") == false) {
                        Toast.makeText(context, "Giá thuê sai định dạng", Toast.LENGTH_SHORT).show();
                    } else if(Integer.valueOf(giaThue) < 0) {
                        Toast.makeText(context, "Giá thuê phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    }
                    else if(daoSach.update(indexSach)) {
                        list.clear();
                        list.addAll(daoSach.selectAll());
                        daoPhieuMuon = new daoPhieuMuon(context);
                        daoPhieuMuon.selectAll();
                        dialog.dismiss();
                        notifyDataSetChanged();
                        Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Update thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        view.findViewById(R.id.btnHuy_S).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
