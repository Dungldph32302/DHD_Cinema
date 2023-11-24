package com.example.dhd_cinema.Framgment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhd_cinema.Login.DangNhap;
import com.example.dhd_cinema.MainActivity;
import com.example.dhd_cinema.R;

public class Fragment_Khac extends Fragment {


LinearLayout trangCaNhan, doiMK, phimNhieuView, phimDanhGiaCao, hoaDon;
Button btn_dangXuat;

TextView ten, email, sdt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__khac, container, false);
        doiMK = view.findViewById(R.id.ln_DoiMK);
        phimNhieuView = view.findViewById(R.id.ln_PhimHot);
        phimDanhGiaCao = view.findViewById(R.id.ln_phimNhieuSao);
        hoaDon = view.findViewById(R.id.ln_HoaDon);
        btn_dangXuat = view.findViewById(R.id.btn_DangXuat);
        ten = view.findViewById(R.id.txt_ten_TCN);
        email = view.findViewById(R.id.txt_email_TCN);
        sdt = view.findViewById(R.id.txt_SDT_TCN);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("ThongTin", Context.MODE_PRIVATE);
        String hoten = sharedPreferences.getString("HoTen", "");
        String Email = sharedPreferences.getString("Email", "");
        String SDT = sharedPreferences.getString("SDT", "");
        ten.setText(""+ hoten);
        email.setText(Email);
        sdt.setText(SDT);
        doiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_DoiMK frg=new Fragment_DoiMK();
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.replec(frg);
            }
        });

        btn_dangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DangNhap.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(getContext(), "Đăng Xuất!!!", Toast.LENGTH_SHORT).show();
            }
        });

        phimNhieuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_PhimHot frg=new Fragment_PhimHot();
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.replec(frg);
            }
        });
        return view;
    }
}