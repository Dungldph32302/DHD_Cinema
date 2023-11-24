package com.example.dhd_cinema.Framgment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dhd_cinema.Adapter.AdapterPhim;
import com.example.dhd_cinema.Dao.PhimDao;
import com.example.dhd_cinema.Dao.TheLoaiPhimDao;
import com.example.dhd_cinema.MainActivity;
import com.example.dhd_cinema.Model.Phim;
import com.example.dhd_cinema.Model.TheLoaiPhim;
import com.example.dhd_cinema.R;
import com.example.dhd_cinema.Spinner.TheLoaiPhimSpinner;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Fragment_Phim extends Fragment {
    private RecyclerView rcvQLPhim;
    private FloatingActionButton fltAddPhim;

    private ArrayList<Phim> list = new ArrayList<Phim>();

    private PhimDao phimDao;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__phim, container, false);

        rcvQLPhim = view.findViewById(R.id.rcvQLPhim);
        fltAddPhim = view.findViewById(R.id.fltAddPhim);
        loadData();
        fltAddPhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_ThemPhim frg=new Fragment_ThemPhim();
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.replec(frg);
            }
        });
        return view;
    }
    private void loadData(){
        // data
        phimDao = new PhimDao(getContext());
        list = phimDao.selectAllPhim();

//        GridLayoutManager manager= new GridLayoutManager(getActivity(),2);
//        rcvQLPhim.setLayoutManager(manager);

         LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
         rcvQLPhim.setLayoutManager(linearLayoutManager);
        AdapterPhim adapter = new AdapterPhim(getContext(),list);
        rcvQLPhim.setAdapter(adapter);
    }


}