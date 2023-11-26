package com.example.dhd_cinema.Framgment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dhd_cinema.Adapter.AdapterDoanhThuPhim;
import com.example.dhd_cinema.Adapter.AdapterNguoiDung;
import com.example.dhd_cinema.Dao.NguoiDungDao;
import com.example.dhd_cinema.Dao.PhimDao;
import com.example.dhd_cinema.Dao.ThongKeDao;
import com.example.dhd_cinema.Model.NguoiDung;
import com.example.dhd_cinema.Model.Phim;
import com.example.dhd_cinema.R;

import java.util.ArrayList;


public class Fragment_DanhThuTheoPhim extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__danh_thu_theo_phim, container, false);
        RecyclerView recyclerDT = view.findViewById(R.id.rcvDoanhThuPHim);

        ThongKeDao thongKeDao = new ThongKeDao(getContext());
        ArrayList<Phim> list = thongKeDao.DoanhThuTheoPhim();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerDT.setLayoutManager(linearLayoutManager);

        AdapterDoanhThuPhim adapter = new AdapterDoanhThuPhim(getContext(), list);
        recyclerDT.setAdapter(adapter);
        return view;
    }
}