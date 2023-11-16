package com.example.dhd_cinema.Framgment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import com.example.dhd_cinema.Dao.PhimDao;
import com.example.dhd_cinema.Model.Phim;
import com.example.dhd_cinema.R;
import com.example.dhd_cinema.Spinner.AdapterSpinnerPhim;

import java.util.ArrayList;

public class Fragment_ThemSuatChieu extends Fragment {

    PhimDao dao;
    ArrayList<Phim> danhSachPhim;
    String duongDanAnh;
    AppCompatEditText tenPhim, theLoai, ngayPhatHanh, daoDien, moTa;
    private static final int YeuCauQuyen = 10;
    ImageView anhPhim;
    AppCompatButton themPhim;
    Spinner phim;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__them_suat_chieu, container, false);
//        tenPhim=view.findViewById(R.id.txtName);
//        theLoai=view.findViewById(R.id.txtTL);
//        ngayPhatHanh=view.findViewById(R.id.txtDat);
//        daoDien=view.findViewById(R.id.txtDD);
//        moTa=view.findViewById(R.id.txtMT);
//        anhPhim=view.findViewById(R.id.anhPhim);
//        themPhim=view.findViewById(R.id.addPhim);
        phim = view.findViewById(R.id.spinnerPhim);

        dao = new PhimDao(getActivity());

        PhimDao tv = new PhimDao(getActivity());
        ArrayList<Phim> list1 = tv.selectAllPhim();
        AdapterSpinnerPhim adapter1 = new AdapterSpinnerPhim(getActivity(), list1);
        phim.setAdapter(adapter1);

        return view;
    }
}



