package com.example.dhd_cinema.Framgment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhd_cinema.Adapter.AdapterGhe;
import com.example.dhd_cinema.Adapter.AdapterPhongChieu;
import com.example.dhd_cinema.Dao.GheDao;
import com.example.dhd_cinema.Dao.PhongDao;
import com.example.dhd_cinema.MainActivity;
import com.example.dhd_cinema.Model.GheModel;
import com.example.dhd_cinema.Model.PhongModel;
import com.example.dhd_cinema.R;

import java.util.ArrayList;


public class Fragment_PhongChieu extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
        // Đặt tiêu đề trên Toolbar khi Fragment này được hiển thị
        if (getActivity() != null) {
            ((MainActivity) getActivity()).setActionBarTitle("Quản lý phòng chiếu ");
        }
    }
    public Fragment_PhongChieu() {
        // Required empty public constructor
    }

    PhongDao phongDao;
    AdapterPhongChieu adapter;
    ArrayList<PhongModel> list;
    RecyclerView rcv;
    AppCompatButton add;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view=inflater.inflate(R.layout.fragment_phongchieu, container, false);
         rcv=view.findViewById(R.id.rcvGhe);
         add=view.findViewById(R.id.addGhe);

         phongDao= new PhongDao(getActivity());
         list=phongDao.getAllPhongChieu();
         LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
         rcv.setLayoutManager(layoutManager);
         adapter= new AdapterPhongChieu(getActivity(),list);
         rcv.setAdapter(adapter);
         add.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                 LayoutInflater inflater = getLayoutInflater();
                 View view1 = inflater.inflate(R.layout.add_phong, null);
                 builder.setView(view1);
                 Dialog dialog = builder.create();
                 dialog.show();
                 AppCompatEditText idphong = view1.findViewById(R.id.txtIDP);
                 AppCompatEditText soghe = view1.findViewById(R.id.txtSG);

                 Button them = view1.findViewById(R.id.btnadd);
                 // xử lý khi chọn hủy

                 them.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         if (idphong.getText().toString().trim().isEmpty()) {
                             Toast.makeText(getActivity(), "Vui lòng nhập Tên phòng", Toast.LENGTH_SHORT).show();
                         } else if (soghe.getText().toString().trim().isEmpty()) {
                             Toast.makeText(getActivity(), "Vui lòng nhập Số ghế", Toast.LENGTH_SHORT).show();
                         } else {
                             PhongModel tl = new PhongModel();
                             tl.setLoaiPhong(1);
                             tl.setTenPhong(idphong.getText().toString());
                             int so=Integer.valueOf(soghe.getText().toString());
                             tl.setSoCho(so);
                             if (phongDao.addPhongChieu(tl)) {
                                 list.clear();
                                 list.addAll(phongDao.getAllPhongChieu());
                                 adapter.notifyDataSetChanged();
                                 Toast.makeText(getActivity(), "Bạn đã thêm 1 ghế mới ", Toast.LENGTH_SHORT).show();
                                 dialog.dismiss();
                             }
                         }
                     }
                 });

             }
         });
         return view;
    }
}