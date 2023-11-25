package com.example.dhd_cinema.Framgment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhd_cinema.Adapter.AdapterPhim;
import com.example.dhd_cinema.Dao.NguoiDungDao;
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
    ArrayList<Phim> tempListPhim = new ArrayList<>();

    AdapterPhim adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__phim, container, false);

        rcvQLPhim = view.findViewById(R.id.rcvQLPhim);
        fltAddPhim = view.findViewById(R.id.fltAddPhim);
        // phân quyền
        int quyen=-1;
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", getActivity().MODE_PRIVATE);
        String tendangnhap = sharedPreferences.getString("username", "");
        NguoiDungDao nguoiDungDao= new NguoiDungDao(getActivity());
        quyen=nguoiDungDao.layQuyenTuDangNhap(tendangnhap);
        if(quyen==0){
            fltAddPhim.setVisibility(View.GONE);
        }

        // data
        phimDao = new PhimDao(getContext());
        list = phimDao.selectAllPhim();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvQLPhim.setLayoutManager(linearLayoutManager);

        adapter = new AdapterPhim(getContext(),list);
        rcvQLPhim.setAdapter(adapter);

        fltAddPhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_ThemPhim frg=new Fragment_ThemPhim();
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.replec(frg);
            }
        });


        tempListPhim = phimDao.selectAllPhim();
        EditText edt_timKiem = view.findViewById(R.id.edtSeach);
        edt_timKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.clear();
                for (Phim pm:tempListPhim) {
                    if(pm.getTenPhim().contains(charSequence.toString())){
                        list.add(pm);
                    }}adapter.notifyDataSetChanged();}
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }

}