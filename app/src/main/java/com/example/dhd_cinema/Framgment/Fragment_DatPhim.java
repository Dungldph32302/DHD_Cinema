package com.example.dhd_cinema.Framgment;

import static android.content.Intent.getIntent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dhd_cinema.Adapter.AdapterGhe_in_Dat;
import com.example.dhd_cinema.Adapter.AdapterSuatChieu;
import com.example.dhd_cinema.Dao.GheDao;
import com.example.dhd_cinema.MainActivity;
import com.example.dhd_cinema.Model.GheModel;
import com.example.dhd_cinema.R;

import java.util.ArrayList;


public class Fragment_DatPhim extends Fragment {

    public Fragment_DatPhim() {
        // Required empty public constructor
    }

    RecyclerView rcvghe;
    AdapterGhe_in_Dat adapterGheInDat;
    GheDao gheDao;
    ArrayList<GheModel>list;

    TextView tvtongtien,tvsoghe,tvgia,tvngay,tvtenphim;
    ImageView img;
    private int giaa=0, idsc=-1;
    Button btt;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment__dat_phim, container, false);
        rcvghe=view.findViewById(R.id.rcyghe);
        tvtongtien=view.findViewById(R.id.tvtongtien);
        tvsoghe=view.findViewById(R.id.tvsoghe);
        img=view.findViewById(R.id.imageView5);
        tvgia=view.findViewById(R.id.tvgiave);
        tvngay=view.findViewById(R.id.tvchieu);
        tvtenphim=view.findViewById(R.id.tvtenphim);
        btt=view.findViewById(R.id.bttt);
        gheDao= new GheDao(getActivity());


        list=gheDao.getAllGhe();
         int spanCount = 10; // Số cột hoặc hàng trong lưới
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
        rcvghe.setLayoutManager(layoutManager);
        adapterGheInDat= new AdapterGhe_in_Dat(getActivity(),list);
        rcvghe.setAdapter(adapterGheInDat);

        Bundle bundle = getArguments();
        if (bundle != null) {
             idsc= bundle.getInt("scid");
            giaa= bundle.getInt("gia");
            String ngaygio=bundle.getString("ngaygio");
            String tenPhim=bundle.getString("ten");
            tvtenphim.setText(tenPhim);
            tvgia.setText(String.valueOf("Giá vé :"+giaa));
            tvngay.setText("Chiếu vào: "+ngaygio);
            String linkanh=gheDao.getPhimAnhBySuatChieuId(idsc);

// Giải mã chuỗi Base64 thành mảng byte
            byte[] decodedByteArray = Base64.decode(linkanh, Base64.DEFAULT);

// Chuyển đổi mảng byte thành Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);

// Hiển thị Bitmap bằng Glide
            Glide.with(getActivity())
                    .load(bitmap)
                    .into(img);
            // Xử lý dữ liệu itemId ở đây
        }

        btt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<GheModel>list2=adapterGheInDat.getSelectedListGhe();
                if(list2.size()==0){
                    Toast.makeText(getActivity(), "Vui lòng chọn ghế", Toast.LENGTH_SHORT).show();
                }else {
                    ArrayList<GheModel>list3=adapterGheInDat.getSelectedListGhe();

                    Fragment_HoaDon frg= new Fragment_HoaDon();
                    Bundle bundle1= new Bundle();
                    bundle1.putInt("sll",adapterGheInDat.getTongTatCa());
                    bundle1.putInt("giahh",giaa);

                    bundle1.putInt("idschh",idsc);
                    bundle1.putSerializable("list",list3);
                    frg.setArguments(bundle1);
                    MainActivity mainActivity = (MainActivity)getActivity();
                    mainActivity.replec(frg);
                }

            }
        });
        adapterGheInDat.setOnQuantityChangeListener(new AdapterGhe_in_Dat.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged() {
                ArrayList<GheModel>list2=adapterGheInDat.getSelectedListGhe();

                int soghe=adapterGheInDat.getTongTatCa();
                tvtongtien.setText(String.valueOf(soghe*giaa));
                StringBuilder so= new StringBuilder();
                for(GheModel gheModel: list2){
                    so.append(gheModel.getSoGhe()+",");
                }
                tvsoghe.setText(so);
            }
        });
        return view;
    }
}