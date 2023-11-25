package com.example.dhd_cinema.Framgment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhd_cinema.Adapter.AdapterGhe_in_Dat;
import com.example.dhd_cinema.Booked_tickets_successfully;
import com.example.dhd_cinema.Dao.GheDao;
import com.example.dhd_cinema.Dao.HoaDonDao;
import com.example.dhd_cinema.Dao.SuatChieuDao;
import com.example.dhd_cinema.MainActivity;
import com.example.dhd_cinema.Model.GheModel;
import com.example.dhd_cinema.Model.HoaDonModel;
import com.example.dhd_cinema.Model.SuatChieuModel;
import com.example.dhd_cinema.R;

import java.util.ArrayList;


public class Fragment_HoaDon extends Fragment {


    public Fragment_HoaDon() {

    }



    LinearLayout tienmat,chuyenkhoan;

    int index=0;
    TextView ngay,gio,gia,sl,tong,phong,tenp;
    AdapterGhe_in_Dat adapterGheInDat;
    HoaDonDao hoaDonDao; GheDao gheDao;
    ArrayList<GheModel>list;
    ArrayList<GheModel>list1;
    int idsc=-1,idphim=-1,idv=-1,tongtien=0,phuongthuc=-1,soluong=0,giave=0,idmax;
    SuatChieuDao suatChieuDao;
    String tenphim;
    AppCompatButton thanhtoan;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment__hoa_don, container, false);
        tienmat=view.findViewById(R.id.tienmat);
        chuyenkhoan=view.findViewById(R.id.chuyenkhoan);
        ngay=view.findViewById(R.id.tvngaychieu);
        gio=view.findViewById(R.id.tvgiochieu);
        gia=view.findViewById(R.id.tvgia);
        sl=view.findViewById(R.id.tvsl);
        tong=view.findViewById(R.id.tvtong);
        phong=view.findViewById(R.id.tvphongchieu);
        tenp=view.findViewById(R.id.tvtenp);
        thanhtoan=view.findViewById(R.id.btnthanhtoan);
        gheDao= new GheDao(getActivity());
        suatChieuDao= new SuatChieuDao(getActivity());
        hoaDonDao= new HoaDonDao(getActivity());

        list=gheDao.getAllGhe();
        adapterGheInDat=new AdapterGhe_in_Dat(getActivity(),list);
       list1=adapterGheInDat.getSelectedListGhe();

        Bundle bundle = getArguments();
        if (bundle != null) {
             idsc = bundle.getInt("idschh");
             giave = bundle.getInt("giahh");
             soluong = bundle.getInt("sll");
             list1= (ArrayList<GheModel>)bundle.getSerializable("list");
            Toast.makeText(getActivity(), " so luong"+list1.size(), Toast.LENGTH_SHORT).show();
            sl.setText(String.valueOf(soluong));
            gia.setText(String.valueOf(giave) +"đ");
            tong.setText(String.valueOf(giave*soluong)+"đ");
        }
        SuatChieuModel sc= suatChieuDao.getSuatChieuById(idsc);
        gio.setText(String.valueOf(sc.getGioChieu()));
        ngay.setText(sc.getNgayChieu());
        tenp.setText(sc.getTenPhim());
        phong.setText(sc.getTenPhong());





        Drawable drawable= ContextCompat.getDrawable(getContext(), R.drawable.phuongthuc);
        Drawable drawable1= ContextCompat.getDrawable(getContext(), R.drawable.phuongthuc1);

        tienmat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phuongthuc=0;
               index=0;
                if(index==0){
                    tienmat.setBackground(drawable);
                    chuyenkhoan.setBackground(drawable1);
                } else if (index==1) {
                    chuyenkhoan.setBackground(drawable);
                    tienmat.setBackground(drawable1);
                }else {
                    chuyenkhoan.setBackground(drawable1);
                    tienmat.setBackground(drawable1);
                }
                Toast.makeText(getActivity(), " index"+index, Toast.LENGTH_SHORT).show();
            }
        });

        chuyenkhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index=1;
                phuongthuc=1;
                if(index==0){
                    tienmat.setBackground(drawable);
                    chuyenkhoan.setBackground(drawable1);
                } else if (index==1) {
                    chuyenkhoan.setBackground(drawable);
                    tienmat.setBackground(drawable1);
                }else {
                    chuyenkhoan.setBackground(drawable1);
                    tienmat.setBackground(drawable1);
                }
                Toast.makeText(getActivity(), " index"+index, Toast.LENGTH_SHORT).show();
            }
        });

        idmax=hoaDonDao.getMaxHoaDonId();
        thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", getActivity().MODE_PRIVATE);
                String savedUsername = sharedPreferences.getString("username", "");
                @SuppressLint({"NewApi", "LocalSuppress"})
                String ngay = String.valueOf(java.time.LocalDate.now());
                HoaDonModel hoaDonModel= new HoaDonModel();
                hoaDonModel.setId(idmax);
                hoaDonModel.setIdsc(idsc);
                hoaDonModel.setTennguoidung("admin");
                hoaDonModel.setGia(giave);
                hoaDonModel.setPhuongthuc(phuongthuc);
                hoaDonModel.setSl(soluong);
                hoaDonModel.setThoigian(ngay);
                hoaDonModel.setTongtien(giave*soluong);
                hoaDonModel.setTrangthai(phuongthuc);
                hoaDonModel.setTennguoidung(savedUsername);
                if(phuongthuc==-1){
                    Toast.makeText(getActivity(), "Vui lòng chọn phương thức thanh toán ", Toast.LENGTH_SHORT).show();
                }else {
                    if(hoaDonDao.addHoaDonandve(hoaDonModel,list1)){
                        Toast.makeText(getActivity(), "Tạo thành công ", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(getActivity(), Booked_tickets_successfully.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getActivity(), "Tạo không thành công ", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        return view;
    }
}