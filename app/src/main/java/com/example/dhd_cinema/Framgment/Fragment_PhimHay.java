package com.example.dhd_cinema.Framgment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dhd_cinema.Adapter.AdapterPhimHay;
import com.example.dhd_cinema.Adapter.AdapterPhimHot;
import com.example.dhd_cinema.Dao.ThongKeDao;
import com.example.dhd_cinema.Model.Phim;
import com.example.dhd_cinema.R;

import java.util.ArrayList;


public class Fragment_PhimHay extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__phim_hay, container, false);
        RecyclerView recyclerTop10 = view.findViewById(R.id.rcvTop10PhimHay);

        ThongKeDao thongKeDao = new ThongKeDao(getContext());
        ArrayList<Phim> list = thongKeDao.selectTopPhimDanhGiaCao();

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerTop10.setLayoutManager(linearLayoutManager);

        AdapterPhimHay adapter = new AdapterPhimHay(getContext(), list);
        recyclerTop10.setAdapter(adapter);
        return view;
    }
}