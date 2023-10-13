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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.du_an_mau_master.R;
import com.example.du_an_mau_master.dao.daoThanhVien;
import com.example.du_an_mau_master.model.ThanhVien;


import java.util.ArrayList;

public class adapterThanhVien extends RecyclerView.Adapter<adapterThanhVien.ViewHolder> {
    private final Context context;
    private final ArrayList<ThanhVien> list;
    daoThanhVien daoThanhVien;
    ThanhVien tv;
    TextView txtTenTV, txtNamSinh, lblMaTV;

    public adapterThanhVien(Context context, ArrayList<ThanhVien> list) {
        this.context = context;
        this.list = list;
        daoThanhVien = new daoThanhVien(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_thanhvien, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.lblMaTV.setText("Mã thành viên: " + String.valueOf(list.get(position).getMaTV()));
        holder.lblTenTV.setText(list.get(position).getTenTV());
        holder.lblNamSinh.setText("Ngày sinh: " + list.get(position).getNamSinh());
        holder.btnMenuContext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv = list.get(position);
                openDialog_Menu();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblMaTV, lblTenTV, lblNamSinh;
        ImageButton btnMenuContext;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblMaTV = itemView.findViewById(R.id.lblMaTV);
            lblTenTV = itemView.findViewById(R.id.lblTenTV);
            lblNamSinh = itemView.findViewById(R.id.lblNamSinh);
            btnMenuContext = itemView.findViewById(R.id.btnMenuContext);

        }
    }
    public void openDialog_Menu() {
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        String [] menu ={"Chỉnh sửa","Xóa"};
        builder.setItems( menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    OpenDialog_Update();
                } else if (i == 1) {
                    openDialog_del();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void openDialog_del() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.warning);
        builder.setTitle("Warning");
        builder.setMessage("Bạn có chắc chắn muốn xóa không ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (daoThanhVien.delete(tv.getMaTV())) {
                    list.clear();
                    list.addAll(daoThanhVien.selectAll());
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
        View view = inflater.inflate(R.layout.layout_sua_thanhvien, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        txtTenTV = view.findViewById(R.id.txtTenTV_Up);
        txtNamSinh = view.findViewById(R.id.txtNamSinh_Up);
        lblMaTV = view.findViewById(R.id.lblMaTV);

        txtTenTV.setText(tv.getTenTV());
        txtNamSinh.setText(tv.getNamSinh());
        lblMaTV.setText("Mã thành viên: " + tv.getMaTV());
        view.findViewById(R.id.btnThem_TV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenTV = txtTenTV.getText().toString();
                String namSinh = txtNamSinh.getText().toString();

                if(tenTV.isEmpty() || namSinh.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    tv.setTenTV(tenTV);
                    tv.setNamSinh(namSinh);
                    if(daoThanhVien.update(tv)) {
                        list.clear();
                        list.addAll(daoThanhVien.selectAll());
                        dialog.dismiss();
                        notifyDataSetChanged();
                        Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Update thất bại", Toast.LENGTH_SHORT).show();
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
