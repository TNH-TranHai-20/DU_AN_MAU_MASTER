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
import com.example.du_an_mau_master.dao.daoLoaiSach;
import com.example.du_an_mau_master.model.LoaiSach;
import java.util.ArrayList;

public class adapterLoaiSach extends RecyclerView.Adapter<adapterLoaiSach.ViewHolder>{
    private final ArrayList<LoaiSach> list;
    private final Context context;
    daoLoaiSach daoLoaiSach;
    LoaiSach indexLoaiSach;
    TextView txtTenLoai, lblLoaiSach;

    public adapterLoaiSach(ArrayList<LoaiSach> list, Context context) {
        this.list = list;
        this.context = context;
        daoLoaiSach = new daoLoaiSach(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_loaisach, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.lblMaLoai.setText(String.valueOf(list.get(position).getMaLoai()));
        holder.lblTenLoai.setText(list.get(position).getTenLoai());
        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexLoaiSach = list.get(position);
                openDialog_del();
            }
        });
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexLoaiSach = list.get(position);
                OpenDialog_Update();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblMaLoai, lblTenLoai;
        ImageButton btnXoa, btnUpdate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblMaLoai = itemView.findViewById(R.id.lblMaLoai);
            lblTenLoai = itemView.findViewById(R.id.lblTenLoai);
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
                if (daoLoaiSach.delete(indexLoaiSach.getMaLoai())) {
                    list.clear();
                    list.addAll(daoLoaiSach.selectAll());
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
        View view = inflater.inflate(R.layout.layout_sua_loaisach, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        lblLoaiSach = view.findViewById(R.id.lblMaLoai);
        txtTenLoai = view.findViewById(R.id.txtTenLoai_Up);

        txtTenLoai.setText(indexLoaiSach.getTenLoai());
        lblLoaiSach.setText("Mã loại: " + indexLoaiSach.getMaLoai());

        view.findViewById(R.id.btnUpdate_LS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoai = txtTenLoai.getText().toString();

                indexLoaiSach.setTenLoai(tenLoai);

                if(tenLoai.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if(daoLoaiSach.update(indexLoaiSach)) {
                        list.clear();
                        list.addAll(daoLoaiSach.selectAll());
                        dialog.dismiss();
                        notifyDataSetChanged();
                        Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Update thất bại", Toast.LENGTH_SHORT).show();
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
