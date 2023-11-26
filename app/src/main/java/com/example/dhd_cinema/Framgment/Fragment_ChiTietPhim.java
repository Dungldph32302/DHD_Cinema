package com.example.dhd_cinema.Framgment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dhd_cinema.Dao.PhimDao;
import com.example.dhd_cinema.MainActivity;
import com.example.dhd_cinema.Model.Phim;
import com.example.dhd_cinema.R;


public class Fragment_ChiTietPhim extends Fragment {

    private ImageView anh;
    private TextView txtID_Phim_chiTiet;
    private TextView txtID_TL_chiTiet;
    private TextView txtTenPhim_chiTiet;
    private TextView txtDaoDien_chiTiet;
    private TextView txtNgayPhatHanh_chiTiet;
    private TextView txtMoTa_chiTiet;
    private ImageView btnExit_chiTiet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__chi_tiet_phim, container, false);
        // Ánh xạ các thành phần widget
        anh = view.findViewById(R.id.imageView2);
        txtID_Phim_chiTiet = view.findViewById(R.id.txtID_Phim_chiTiet);
        txtID_TL_chiTiet = view.findViewById(R.id.txtID_TL_chiTiet);
        txtTenPhim_chiTiet = view.findViewById(R.id.txtTenPhim_chiTiet);
        txtDaoDien_chiTiet = view.findViewById(R.id.txtDaoDien_chiTiet);
        txtNgayPhatHanh_chiTiet = view.findViewById(R.id.txtNgayPhatHanh_chiTiet);
        txtMoTa_chiTiet = view.findViewById(R.id.txtMoTa_chiTiet);
        btnExit_chiTiet = view.findViewById(R.id.btnExit_chiTiet);

        // Nhận dữ liệu từ Intent
        Bundle bundle = getArguments();
        if (bundle != null) {
            int ID_Phim = bundle.getInt("ID_Phim");
            PhimDao dao= new PhimDao(getActivity());
            Phim phim = dao.selectPhimById(ID_Phim);

            // Hiển thị thông tin phim
            txtID_Phim_chiTiet.setText(phim.getID_Phim());
            txtID_TL_chiTiet.setText(phim.getID_TL());
            txtTenPhim_chiTiet.setText(phim.getTenPhim());
            txtDaoDien_chiTiet.setText(phim.getDaoDien());
            txtNgayPhatHanh_chiTiet.setText(phim.getNgayPhatHanh());
            txtMoTa_chiTiet.setText(phim.getMota());

            String base64String = phim.getAnh();

            // Giải mã chuỗi Base64 thành mảng byte
            byte[] decodedByteArray = Base64.decode(base64String, Base64.DEFAULT);

            // Chuyển đổi mảng byte thành Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);

            // Hiển thị Bitmap bằng Glide
            Glide.with(getActivity())
                    .load(bitmap)
                    .into(anh);
        }

        // Xử lý sự kiện nút Exit
        btnExit_chiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Thoát!!!", Toast.LENGTH_SHORT).show();
                Fragment_Phim frg=new Fragment_Phim();
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.replec(frg);
            }
        });

        return view;
    }
}