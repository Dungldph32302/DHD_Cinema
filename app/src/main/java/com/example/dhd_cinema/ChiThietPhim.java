package com.example.dhd_cinema;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ChiThietPhim extends AppCompatActivity {
    private ImageView anh;
    private TextView txtID_Phim_chiTiet;
    private TextView txtID_TL_chiTiet;
    private TextView txtTenPhim_chiTiet;
    private TextView txtDaoDien_chiTiet;
    private TextView txtNgayPhatHanh_chiTiet;
    private TextView txtMoTa_chiTiet;
    private ImageView btnExit_chiTiet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_thiet_phim);
        anh = findViewById(R.id.imageView2);
        txtID_Phim_chiTiet = findViewById(R.id.txtID_Phim_chiTiet);
        txtID_TL_chiTiet = findViewById(R.id.txtID_TL_chiTiet);
        txtTenPhim_chiTiet = findViewById(R.id.txtTenPhim_chiTiet);
        txtDaoDien_chiTiet = findViewById(R.id.txtDaoDien_chiTiet);
        txtNgayPhatHanh_chiTiet = findViewById(R.id.txtNgayPhatHanh_chiTiet);
        txtMoTa_chiTiet = findViewById(R.id.txtMoTa_chiTiet);
        btnExit_chiTiet = findViewById(R.id.btnExit_chiTiet);
    }
}