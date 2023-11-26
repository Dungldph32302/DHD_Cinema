package com.example.dhd_cinema;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhd_cinema.Dao.DanhgiaDao;
import com.example.dhd_cinema.Model.danhgiamodel;

public class DanhGiaPhim extends AppCompatActivity {
    ImageView anh,sao1,sao2,sao3,sao4,sao5;
    private int sao=0;
    TextView tvtl,tvten;
    AppCompatButton danhgia;
    AppCompatEditText binhluan;
    DanhgiaDao dao;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_gia_phim);
        sao1=findViewById(R.id.sao1);
        sao2=findViewById(R.id.sao2);
        sao3=findViewById(R.id.sao3);
        sao4=findViewById(R.id.sao4);
        sao5=findViewById(R.id.sao5);
        anh=findViewById(R.id.imageView11);
        binhluan=findViewById(R.id.txtbinhluan);
        danhgia=findViewById(R.id.btndanhgia);
        tvten=findViewById(R.id.textView);
        dao= new DanhgiaDao(DanhGiaPhim.this);
        Drawable drawable1= ContextCompat.getDrawable(DanhGiaPhim.this, R.drawable.img_27);
        Drawable drawable2= ContextCompat.getDrawable(DanhGiaPhim.this, R.drawable.img_1sao);
        Intent intent = getIntent();
        int idsc = intent.getIntExtra("idsc", -1);
        String ngdung = intent.getStringExtra("ng");
        String tenphim=intent.getStringExtra("tenphim");

        tvten.setText(tenphim);
        sao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sao=1;
                sao1.setBackground(drawable2);
                sao2.setBackground(drawable1);
                sao3.setBackground(drawable1);
                sao4.setBackground(drawable1);
                sao5.setBackground(drawable1);
            }
        });
        sao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sao=2;
                sao1.setBackground(drawable2);
                sao2.setBackground(drawable2);
                sao3.setBackground(drawable1);
                sao4.setBackground(drawable1);
                sao5.setBackground(drawable1);
            }
        });
        sao3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sao=3;
                sao1.setBackground(drawable2);
                sao2.setBackground(drawable2);
                sao3.setBackground(drawable2);
                sao4.setBackground(drawable1);
                sao5.setBackground(drawable1);
            }
        });
        sao4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sao=4;
                sao1.setBackground(drawable2);
                sao2.setBackground(drawable2);
                sao3.setBackground(drawable2);
                sao4.setBackground(drawable2);
                sao5.setBackground(drawable1);
            }
        });
        sao5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sao=5;
                sao1.setBackground(drawable2);
                sao2.setBackground(drawable2);
                sao3.setBackground(drawable2);
                sao4.setBackground(drawable2);
                sao5.setBackground(drawable2);
            }
        });

        danhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dg = danhgia.getText().toString();
                if (dg.isEmpty()) {
                    Toast.makeText(DanhGiaPhim.this, "Vui lòng nhập nội dung ", Toast.LENGTH_SHORT).show();
                } else {



                    int idp = dao.getPhimIdBySuatChieuId(idsc);
                    danhgiamodel danhgiamodel = new danhgiamodel();
                    danhgiamodel.setIdphim(idp);
                    danhgiamodel.setNoidung(dg);
                    danhgiamodel.setSao(sao);
                    danhgiamodel.setTen(ngdung);
                    if (dao.adđg(danhgiamodel)) {
                        Toast.makeText(DanhGiaPhim.this, "Đánh giá thành công ", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(DanhGiaPhim.this, MainActivity.class);
                        startActivity(intent1);
                    }
                }
            }
        });


    }
}