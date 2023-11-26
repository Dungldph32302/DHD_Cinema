package com.example.dhd_cinema.Login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhd_cinema.Dao.NguoiDungDao;
import com.example.dhd_cinema.MainActivity;
import com.example.dhd_cinema.Model.NguoiDung;
import com.example.dhd_cinema.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;

public class DangNhap extends AppCompatActivity {

    TextInputEditText tenDangNhap,matKhau;
    TextView singup, quenMK;
    CheckBox chkLuuMatKhau;
    AppCompatButton btlogin;
    NguoiDungDao nguoiDungDao;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        tenDangNhap=findViewById(R.id.edtUser);
        matKhau=findViewById(R.id.edtPass);
        btlogin=findViewById(R.id.btnlogin);
        singup = findViewById(R.id.txtSign);
        chkLuuMatKhau = findViewById(R.id.chkLuuMatKhau);
        quenMK = findViewById(R.id.txtQuenMK);
        nguoiDungDao= new NguoiDungDao(DangNhap.this);

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangNhap.this, DangKy.class);
                startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        // Kiểm tra nếu đã lưu mật khẩu trước đó
        if (sharedPreferences.getBoolean("rememberPassword", false)) {
            String savedUsername = sharedPreferences.getString("username", "");
            String savedPassword = sharedPreferences.getString("password", "");

            tenDangNhap.setText(savedUsername);
            matKhau.setText(savedPassword);
            chkLuuMatKhau.setChecked(true);
        }


        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = tenDangNhap.getText().toString();
                String pass = matKhau.getText().toString();

                // checkbook
                // Kiểm tra nút checkbook đã được chọn hay không
                if (chkLuuMatKhau.isChecked()) {
                    // Lưu thông tin đăng nhập nếu đã chọn
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", user);
                    editor.putString("password", pass);
                    editor.putBoolean("rememberPassword", true);
                    editor.apply();
                } else {
                    // Xóa thông tin đăng nhập nếu không chọn
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("username");
                    editor.remove("password");
                    editor.remove("rememberPassword");
                    editor.apply();
                }


                // login
                if(user.isEmpty() || pass.isEmpty()){
                    Toast.makeText(DangNhap.this, "Không Đươc Để Trống", Toast.LENGTH_SHORT).show();
                }
                else {

                    if (nguoiDungDao.checkDangNhap(user, pass)) {
                        //
                        Toast.makeText(DangNhap.this, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DangNhap.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(DangNhap.this, "Sai pass hoặc mk", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        // quenmk
        quenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DangNhap.this);
                builder.setTitle("Quên mật khẩu");
                builder.setMessage("Nhập email và số điện thoại để lấy lại mật khẩu");

                LinearLayout layout = new LinearLayout(DangNhap.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText edtEmail = new EditText(DangNhap.this);
                edtEmail.setHint("Email");
                layout.addView(edtEmail);

                final EditText edtSDT = new EditText(DangNhap.this);
                edtSDT.setHint("Số điện thoại");
                layout.addView(edtSDT);

                builder.setView(layout);

                builder.setPositiveButton("Lấy lại mật khẩu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String email = edtEmail.getText().toString();
                        String sdt = edtSDT.getText().toString();

                        String matKhau = nguoiDungDao.layMatKhau(email, sdt);
                        if (matKhau != null) {
                            Toast.makeText(DangNhap.this, "Mật khẩu của bạn là: " + matKhau, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(DangNhap.this, "Email và số điện thoại không khớp", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Hủy", null);

                builder.create().show();
            }
        });
    }



}