package com.example.dhd_cinema.Framgment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dhd_cinema.Adapter.AdapterVe;
import com.example.dhd_cinema.Dao.NguoiDungDao;
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
    NguoiDungDao nguoiDungDao;
    ArrayList<VeModel>list;
    TextView tv;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_ve, container, false);
        rcv=view.findViewById(R.id.rcvve);
        tv=view.findViewById(R.id.tvn);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login",getActivity().MODE_PRIVATE);
        String tendangnhap = sharedPreferences.getString("username", "");
        nguoiDungDao= new NguoiDungDao(getActivity());
        veDao= new VeDao(getActivity());
        int quyen=nguoiDungDao.layQuyenTuDangNhap(tendangnhap);
        if(quyen==1){
            list=veDao.getAllVe();
            tv.setText("Quản Lý Vé");
        }else {
            list=veDao.getVeByTendangnhap(tendangnhap);
            tv.setText(" Vé Của Tôi");
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcv.setLayoutManager(layoutManager);
        adapterVe= new AdapterVe(getActivity(),list);
        rcv.setAdapter(adapterVe);
        return view;

    }
}