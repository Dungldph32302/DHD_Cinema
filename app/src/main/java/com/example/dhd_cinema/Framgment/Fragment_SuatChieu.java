package com.example.dhd_cinema.Framgment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhd_cinema.Adapter.AdapterPhongChieu;
import com.example.dhd_cinema.Adapter.AdapterSuatChieu;
import com.example.dhd_cinema.Dao.PhongDao;
import com.example.dhd_cinema.Dao.SuatChieuDao;
import com.example.dhd_cinema.Model.PhongModel;
import com.example.dhd_cinema.Model.SuatChieuModel;
import com.example.dhd_cinema.R;

import java.util.ArrayList;


public class Fragment_SuatChieu extends Fragment {


    public Fragment_SuatChieu() {
        // Required empty public constructor
    }

    SuatChieuDao suatChieuDao;
    AdapterSuatChieu adapter;
    ArrayList<SuatChieuModel> list;
    RecyclerView rcv;
    AppCompatButton add;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view=inflater.inflate(R.layout.fragment_suatchieu, container, false);
         rcv=view.findViewById(R.id.rcvGhe);
         add=view.findViewById(R.id.addGhe);

         suatChieuDao= new SuatChieuDao(getActivity());
         list=suatChieuDao.getAllSuatChieuWithInfo();
        int spanCount = 3; // Số cột hoặc hàng trong lưới
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
        rcv.setLayoutManager(layoutManager);
         adapter= new AdapterSuatChieu(getActivity(),list);
         rcv.setAdapter(adapter);
//         add.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//                 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                 LayoutInflater inflater = getLayoutInflater();
//                 View view1 = inflater.inflate(R.layout.add_ghe, null);
//                 builder.setView(view1);
//                 Dialog dialog = builder.create();
//                 dialog.show();
//                 AppCompatEditText idphong = view1.findViewById(R.id.txtIDP);
//                 AppCompatEditText soghe = view1.findViewById(R.id.txtSG);
//
//                 Button them = view1.findViewById(R.id.btnadd);
//                 // xử lý khi chọn hủy
//
//                 them.setOnClickListener(new View.OnClickListener() {
//                     @Override
//                     public void onClick(View v) {
//                         if (idphong.getText().toString().trim().isEmpty()) {
//                             Toast.makeText(getActivity(), "Vui lòng nhập ID phòng", Toast.LENGTH_SHORT).show();
//                         } else if (soghe.getText().toString().trim().isEmpty()) {
//                             Toast.makeText(getActivity(), "Vui lòng nhập Số ghế", Toast.LENGTH_SHORT).show();
//                         } else {
//                             GheModel tl = new GheModel();
//                             tl.setTrangThai(0);
//                             tl.setIdPhong(Integer.parseInt(idphong.getText().toString()));
//                             tl.setSoGhe(soghe.getText().toString());
//                             if (gheDao.addGhe(tl)) {
//                                 list.clear();
//                                 list.addAll(gheDao.getAllGhe());
//                                 adapterGhe.notifyDataSetChanged();
//                                 Toast.makeText(getActivity(), "Bạn đã thêm 1 ghế mới ", Toast.LENGTH_SHORT).show();
//                                 dialog.dismiss();
//                             }
//                         }
//                     }
//                 });
//
//             }
//         });
         return view;
    }
}