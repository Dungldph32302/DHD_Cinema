package com.example.dhd_cinema.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dhd_cinema.Dao.NguoiDungDao;
import com.example.dhd_cinema.MainActivity;
import com.example.dhd_cinema.R;
import com.google.android.material.textfield.TextInputEditText;

public class DangNhap extends AppCompatActivity {

    TextInputEditText name,password;
    AppCompatButton btlogin;
    NguoiDungDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        name=findViewById(R.id.edtUser);
        password=findViewById(R.id.edtPass);
        btlogin=findViewById(R.id.btnlogin);
        dao= new NguoiDungDao(DangNhap.this);
        // xử lý khi click button đang nhập
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=name.getText().toString().trim();
                String pass=password.getText().toString().trim();
                if (validate(user,pass)){
                    if(dao.checkDangNhapSDT(user,pass)||dao.checkDangNhapGmail(user,pass)){
                        Toast.makeText(DangNhap.this, "Đang nhập thành công ", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(DangNhap.this, MainActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(DangNhap.this, "Đang nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public  boolean validate(String name,String pass){
        if(name.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập User", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(pass.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}