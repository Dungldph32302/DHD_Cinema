package com.example.dhd_cinema.Framgment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dhd_cinema.Adapter.AdapterVe;
import com.example.dhd_cinema.Dao.VeDao;
import com.example.dhd_cinema.Model.VeModel;
import com.example.dhd_cinema.R;

import java.util.ArrayList;


public class FragmentVe extends Fragment {


    public FragmentVe() {
        // Required empty public constructor
    }


RecyclerView rcv;
    AdapterVe adapterVe;
    VeDao veDao;
    ArrayList<VeModel>list;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_ve, container, false);
        rcv=view.findViewById(R.id.rcvve);

        veDao= new VeDao(getActivity());
        list=veDao.getAllVe();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcv.setLayoutManager(layoutManager);
        adapterVe= new AdapterVe(getActivity(),list);
        rcv.setAdapter(adapterVe);
        return view;

    }
}