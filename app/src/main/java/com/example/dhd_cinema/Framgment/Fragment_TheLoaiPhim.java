package com.example.dhd_cinema.Framgment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dhd_cinema.Adapter.TheLoaiPhimAdapter;
import com.example.dhd_cinema.Dao.TheLoaiPhimDao;
import com.example.dhd_cinema.Model.TheLoaiPhim;
import com.example.dhd_cinema.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class Fragment_TheLoaiPhim extends Fragment {
    private RecyclerView rcvQLTheLoaiPhim;
    private FloatingActionButton fltAddTheLoaiPhim;

    private ArrayList<TheLoaiPhim> list = new ArrayList<TheLoaiPhim>();

    private TheLoaiPhimDao theLoaiPhimDao;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__the_loai_phim, container, false);
        rcvQLTheLoaiPhim = view.findViewById(R.id.rcvQLTheLoaiPhim);
        fltAddTheLoaiPhim = view.findViewById(R.id.fltAddTheLoaiPhim);

        loadData();
        fltAddTheLoaiPhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemTheLoaiPhim();
            }
        });
        return view;
    }

    private void loadData(){
        // data
        theLoaiPhimDao = new TheLoaiPhimDao(getContext());
        list = theLoaiPhimDao.selectAllTheLoaiPhim();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvQLTheLoaiPhim.setLayoutManager(linearLayoutManager);
        TheLoaiPhimAdapter adapter = new TheLoaiPhimAdapter(getContext(),list);
        rcvQLTheLoaiPhim.setAdapter(adapter);
    }
    private Dialog dialog;
    private void ThemTheLoaiPhim(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //gan layout, tao view
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.them_theloaiphim, null);
        builder.setView(view);

        EditText edt_tenTheLoai_Them =view.findViewById(R.id.edt_tenTheLoai_them);
        EditText edt_ID_TL_them = view.findViewById(R.id.edt_ID_TL_Them);
        edt_ID_TL_them.setEnabled(false);
        Button btnLuu_them = view.findViewById(R.id.btnLuu_them);
        Button btnHuy_them = view.findViewById(R.id.btnHuy_them);

        btnLuu_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lay ma thanh vien
                TheLoaiPhimAdapter adapter = new TheLoaiPhimAdapter(getContext(),list);
                String themTheLoaiPhim = edt_tenTheLoai_Them.getText().toString();
                TheLoaiPhim theLoaiPhim =new TheLoaiPhim(themTheLoaiPhim);// tao doi tuong
                if(themTheLoaiPhim.isEmpty() ){
                    Toast.makeText(getContext(), "Không Được Để Trống", Toast.LENGTH_SHORT).show();
                }else {
                    if (theLoaiPhimDao.insert(theLoaiPhim)){
                        //load lai du lieu tren danh sach
                        list.clear();
                        list.addAll(theLoaiPhimDao.selectAllTheLoaiPhim());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        loadData();
                    }
                    else {
                        Toast.makeText(getContext(), "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnHuy_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Không Thêm", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog= builder.create(); //tao hop thoai
        dialog.show();


    }
}