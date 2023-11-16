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
               // Intent intent = new Intent(Fragment_Phim.this, Fragment_ThemPhim.class);
                //showDialogThem();
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

    private Dialog dialog;
    private void showDialogThem(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.them_phim,null);
        builder.setView(view);

        EditText edtThemID = view.findViewById(R.id.edtID_Phim_them_P);
        EditText edtThemTenPhim = view.findViewById(R.id.edtTenPhim_them_p);
        EditText edtThemDaoDien = view.findViewById(R.id.edtDaoDien_them_P);
        EditText edtThemNgayPhatHanh = view.findViewById(R.id.edtNgayPhatHanh_them_P);
        EditText edtThemMota = view.findViewById(R.id.edtMota_them_P);
        EditText edtThemAnh = view.findViewById(R.id.edtAnh_them_P);
        Spinner spnLoaiPhim = view.findViewById(R.id.spn_TheLoaiPhim);
        Button btnLuu_Them_P = view.findViewById(R.id.btnLuu_Them_P);
        Button btnHuy_Them_P = view.findViewById(R.id.btnHuy_Them_P);
        edtThemID.setEnabled(false);

        //set adapte
        TheLoaiPhimDao theLoaiPhimDao = new TheLoaiPhimDao(getActivity());
        ArrayList<TheLoaiPhim>list1=theLoaiPhimDao.selectAllTheLoaiPhim();
        TheLoaiPhimSpinner adapter1 = new TheLoaiPhimSpinner(getActivity(), list1);
        spnLoaiPhim.setAdapter(adapter1);
        // xủa lý khi chọn spinner
        int[] selectedItemSpinner = {0};
        // lấy string từ spinner
        spnLoaiPhim.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemSpinner[0] = ((TheLoaiPhim) spnLoaiPhim.getItemAtPosition(position)).getID_TL();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnHuy_Them_P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Không Thêm", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btnLuu_Them_P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdapterPhim adapter = new AdapterPhim(getContext(),list);

                int selectedTheLoaiPhimID = selectedItemSpinner[0];
                String tenPhim = edtThemTenPhim.getText().toString();
                String daoDien = edtThemDaoDien.getText().toString();
                String ngayPhatHanh = edtThemNgayPhatHanh.getText().toString();
                String moTa = edtThemMota.getText().toString();
                String anh = edtThemAnh.getText().toString();
                // Tạo đối tượng Phim mới
                Phim newPhim = new Phim(selectedTheLoaiPhimID, tenPhim, daoDien, ngayPhatHanh, moTa,anh);
                // Lưu vào CSDL
                phimDao = new PhimDao(getContext());

                if (tenPhim.isEmpty() || daoDien.isEmpty() || ngayPhatHanh.isEmpty() || moTa.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    if (phimDao.insert(newPhim)) {
                        list.clear();
                        list.addAll(phimDao.selectAllPhim());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Thêm phim thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        loadData();
                    } else {
                        Toast.makeText(getContext(), "Thêm phim thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        dialog= builder.create(); //tao hop thoai
        dialog.show();
    }
}