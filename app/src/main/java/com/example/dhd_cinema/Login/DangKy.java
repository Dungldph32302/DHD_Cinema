package com.example.dhd_cinema.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dhd_cinema.Dao.NguoiDungDao;
import com.example.dhd_cinema.Model.NguoiDung;
import com.example.dhd_cinema.R;
import com.google.android.material.textfield.TextInputEditText;

public class DangKy extends AppCompatActivity {
    Button btnDangKy;
    TextInputEditText edtTenDangNhap, hoTen, edtEmail, edtSDT, edtPass, edtEndPass;
    private NguoiDungDao nguoiDungDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        edtTenDangNhap = findViewById(R.id.edtTenDangNhap);
        hoTen = findViewById(R.id.edtHoTen);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        edtSDT = findViewById(R.id.edtSDT);
        edtEndPass = findViewById(R.id.edtEndPass);
        btnDangKy = findViewById(R.id.btnDangKy);

        nguoiDungDao = new NguoiDungDao(this);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDangNhap= edtTenDangNhap.getText().toString();
                String hoten= hoTen.getText().toString();
                String email = edtEmail.getText().toString();
                String sdt = edtSDT.getText().toString();
                String pass = edtPass.getText().toString();
                String endPass = edtEndPass.getText().toString();

                // Kiểm tra tên đăng nhập không trùng
                if (nguoiDungDao.checkUsernameExists(tenDangNhap)) {
                    Toast.makeText(DangKy.this, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Kiểm tra Email
                if (nguoiDungDao.checkEmail(email)) {
                    Toast.makeText(DangKy.this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Kiểm tra SDT
                if (nguoiDungDao.checkSDT(sdt)) {
                    Toast.makeText(DangKy.this, "SĐT đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }

                NguoiDung nguoiDung = new NguoiDung(tenDangNhap, hoten, email, sdt, pass);
                nguoiDung.setQuyen(0);
                if (tenDangNhap.isEmpty() || hoten.isEmpty() || email.isEmpty() || sdt.isEmpty() || pass.isEmpty() || endPass.isEmpty()){
                    Toast.makeText(DangKy.this, "Không Được Để Trống", Toast.LENGTH_SHORT).show();
                } else if (!checkEmail(email)) {
                    Toast.makeText(DangKy.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                } else if (!checkPhoneNumber(sdt)) {
                    Toast.makeText(DangKy.this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                } else if (!pass.equals(endPass)){
                    Toast.makeText(DangKy.this, "Nhập Lại Mật Khẩu Sai", Toast.LENGTH_SHORT).show();
                } else {
                    if (nguoiDungDao.insert(nguoiDung)){
                        Toast.makeText(DangKy.this, "Đăng Ký Thành Công", Toast.LENGTH_SHORT).show();
                         Intent intent = new Intent(DangKy.this, DangNhap.class);
                         startActivity(intent);
                    }else {
                        Toast.makeText(DangKy.this, "Đăng Kí Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private boolean checkEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@gmail\\.com$";
        return email.matches(emailRegex);
    }
    private boolean checkPhoneNumber(String phoneNumber) {
        String phoneRegex = "^0[0-9]{8,9}$";
        return phoneNumber.matches(phoneRegex);
    }
}