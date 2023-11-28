package com.example.dhd_cinema;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.dhd_cinema.Framgment.FragmentVe;
import com.example.dhd_cinema.Login.DangNhap;

public class Booked_tickets_successfully extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_tickets_successfully);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Booked_tickets_successfully.this, MainActivity.class);
                startActivity(intent);
            }
        },800);
    }
}