package com.example.du_an_mau_master.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.du_an_mau_master.R;
import com.example.du_an_mau_master.dao.daoPhieuMuon;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Fragment_DoanhThu extends Fragment {

    EditText txtTuNgay, txtDenNgay;
    TextView lblDoanhThu;
    ImageButton btnTuNgay, btnDenNgay;
    Button btnDoanhThu;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
    int ngay, thang, nam;
    public Fragment_DoanhThu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__doanh_thu, container, false);
        //
        txtTuNgay = view.findViewById(R.id.txtTuNgay);
        txtDenNgay = view.findViewById(R.id.txtDenNgay);
        btnTuNgay = view.findViewById(R.id.btnTuNgay);
        lblDoanhThu = view.findViewById(R.id.lblDoanhThu);
        btnDenNgay = view.findViewById(R.id.btnDenNgay);
        btnDoanhThu = view.findViewById(R.id.btnDoanhThu);
        //
        btnTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar lich = Calendar.getInstance();

                ngay = lich.get(Calendar.DAY_OF_MONTH);
                thang = lich.get(Calendar.MONTH);
                nam = lich.get(Calendar.YEAR);
                DatePickerDialog bangLich = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtTuNgay.setText(String.format("%d/%d/%d", dayOfMonth, month+1, year));
                    }
                }, nam, thang, ngay);
                bangLich.show();
            }
        });
        btnDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar lich = Calendar.getInstance();

                ngay = lich.get(Calendar.DAY_OF_MONTH);
                thang = lich.get(Calendar.MONTH);
                nam = lich.get(Calendar.YEAR);

                DatePickerDialog bangLich = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtDenNgay.setText(String.format("%d/%d/%d", dayOfMonth, month+1, year));
                    }
                }, nam, thang, ngay);
                bangLich.show();
            }
        });
        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tuNgay = txtTuNgay.getText().toString();
                String denNgay = txtDenNgay.getText().toString();
                daoPhieuMuon daoPhieuMuon = new daoPhieuMuon(getContext());
                lblDoanhThu.setText("Doanh Thu: " + daoPhieuMuon.getDoanhThu(tuNgay, denNgay) + " VNĐ");
            }
        });
        //
        return view;
    }
    DatePickerDialog.OnDateSetListener dateTuNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            ngay = dayOfMonth;
            thang = month;
            nam = year;
            GregorianCalendar gregorianCalendar = new GregorianCalendar( nam, thang, ngay);
            txtTuNgay.setText(sdf.format(gregorianCalendar.getTime()));
        }
    };
    DatePickerDialog.OnDateSetListener dateDenNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            ngay = dayOfMonth;
            thang = month;
            nam = year;
            GregorianCalendar gregorianCalendar = new GregorianCalendar( nam, thang, ngay);
            txtDenNgay.setText(sdf.format(gregorianCalendar.getTime()));
        }
    };
}