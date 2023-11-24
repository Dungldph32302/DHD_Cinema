package com.example.dhd_cinema.Framgment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dhd_cinema.Adapter.AdapterNguoiDung;
import com.example.dhd_cinema.Adapter.AdapterPhimHay;
import com.example.dhd_cinema.Dao.NguoiDungDao;
import com.example.dhd_cinema.Dao.ThongKeDao;
import com.example.dhd_cinema.Model.NguoiDung;
import com.example.dhd_cinema.Model.Phim;
import com.example.dhd_cinema.R;

import java.util.ArrayList;


public class Fragment_NguoiDung extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__nguoi_dung, container, false);
        RecyclerView recyclerTop10 = view.findViewById(R.id.rcvThanhVien);

        NguoiDungDao nguoiDungDao = new NguoiDungDao(getContext());
        ArrayList<NguoiDung> list = nguoiDungDao.selectAllNguoiDUng();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerTop10.setLayoutManager(linearLayoutManager);

        AdapterNguoiDung adapter = new AdapterNguoiDung(getContext(), list);
        recyclerTop10.setAdapter(adapter);
        return view;
    }
}