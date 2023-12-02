package com.example.dhd_cinema.Framgment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dhd_cinema.Adapter.AdapterGhe_in_Dat;
import com.example.dhd_cinema.Adapter.AdapterGioChieu;
import com.example.dhd_cinema.Adapter.AdapterNgayChieu;
import com.example.dhd_cinema.Dao.PhimDao;
import com.example.dhd_cinema.Dao.SuatChieuDao;
import com.example.dhd_cinema.MainActivity;
import com.example.dhd_cinema.Model.GheModel;
import com.example.dhd_cinema.Model.Phim;
import com.example.dhd_cinema.Model.SuatChieuModel;
import com.example.dhd_cinema.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment_SuatChieu_2 extends Fragment {


    public Fragment_SuatChieu_2() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        // Đặt tiêu đề trên Toolbar khi Fragment này được hiển thị
        if (getActivity() != null) {
            ((MainActivity) getActivity()).setActionBarTitle("Đặt vé ");
        }
    }
    SuatChieuDao suatChieuDao;
    AdapterNgayChieu adapterNgayChieu;
    AdapterGioChieu adapterGioChieu;
    ArrayList<SuatChieuModel> listngay,listgio;
    RecyclerView rcvn,rcvg;
    private int idphim=-1;
    TextView tvtongtien,tvsoghe,tvgia,tvngay,tvtenphim;
    ImageView img;
    PhimDao phimDao;
    AppCompatButton btn;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__suat_chieu_2, container, false);
        rcvn=view.findViewById(R.id.rcyNgaychieu);
        rcvg=view.findViewById(R.id.rcyGiochieu);

        tvtongtien=view.findViewById(R.id.tvtongtien);
        tvsoghe=view.findViewById(R.id.tvsoghe);
        img=view.findViewById(R.id.imageView5);
        tvgia=view.findViewById(R.id.tvgiave);
        tvngay=view.findViewById(R.id.tvchieu);
        tvtenphim=view.findViewById(R.id.tvtenphim);
        btn=view.findViewById(R.id.appCompatButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity=(MainActivity)getActivity();
                Fragment_SuatChieu frg= new Fragment_SuatChieu();
                mainActivity.replec(frg);
            }
        });

// Tạo LinearLayoutManager với hướng là HORIZONTAL cho rcvg
        LinearLayoutManager layoutManagerGioChieu = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rcvg.setLayoutManager(layoutManagerGioChieu);

// Tạo LinearLayoutManager với hướng là HORIZONTAL cho rcvn
        LinearLayoutManager layoutManagerNgayChieu = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rcvn.setLayoutManager(layoutManagerNgayChieu);

        Bundle bundle = getArguments();
        if (bundle != null) {
            idphim = bundle.getInt("idphim");
        }

        // xét ảnh
        phimDao= new PhimDao(getActivity());
        Phim phim= phimDao.selectPhimById(idphim);
        tvtenphim.setText(phim.getTenPhim());
        // Giải mã chuỗi Base64 thành mảng byte
        byte[] decodedByteArray = Base64.decode(phim.getAnh(), Base64.DEFAULT);

// Chuyển đổi mảng byte thành Bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);

// Hiển thị Bitmap bằng Glide
        Glide.with(getActivity())
                .load(bitmap)
                .into(img);
        // Xử lý dữ liệu itemId ở đây


        suatChieuDao= new SuatChieuDao(getActivity());
        @SuppressLint({"NewApi", "LocalSuppress"})
        String ngay = String.valueOf(java.time.LocalDate.now());
        listngay=suatChieuDao.getSuatChieuByPhimId(idphim,ngay);
        adapterNgayChieu=new AdapterNgayChieu(getActivity(),listngay);
        rcvn.setAdapter(adapterNgayChieu);
        adapterNgayChieu.setOnQuantityChangeListener(new AdapterNgayChieu.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged() {
                SuatChieuModel sc=adapterNgayChieu.getlist();
                listgio=suatChieuDao.getSuatChieuByPhimIdAndDate(sc.getIdPhim(),sc.getNgayChieu());
                adapterGioChieu=new AdapterGioChieu(getActivity(),listgio);
                rcvg.setAdapter(adapterGioChieu);

            }
        });
        return view;
    }
}