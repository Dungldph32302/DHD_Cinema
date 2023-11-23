package com.example.dhd_cinema.Framgment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dhd_cinema.Adapter.AdapterHoaDon;
import com.example.dhd_cinema.Dao.HoaDonDao;
import com.example.dhd_cinema.Model.HoaDonModel;
import com.example.dhd_cinema.R;

import java.util.ArrayList;

public class FragmentAllHoaDon extends Fragment {

    public FragmentAllHoaDon() {
        // Required empty public constructor
    }

    RecyclerView rcv;
    ArrayList<HoaDonModel>list;
    HoaDonDao hoaDonDao;
    AdapterHoaDon adapterHoaDon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_all_hoa_don, container, false);
        rcv=view.findViewById(R.id.rcvhd);

        hoaDonDao= new HoaDonDao(getActivity());
        list=hoaDonDao.getAllHoaDon();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcv.setLayoutManager(layoutManager);
        adapterHoaDon= new AdapterHoaDon(getActivity(),list);
        rcv.setAdapter(adapterHoaDon);
        return view;
    }
}