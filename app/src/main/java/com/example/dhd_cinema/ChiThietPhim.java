package com.example.dhd_cinema;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dhd_cinema.Dao.PhimDao;
import com.example.dhd_cinema.Framgment.Fragment_Phim;
import com.example.dhd_cinema.Model.Phim;

public class ChiThietPhim extends AppCompatActivity {
    private ImageView anh;
    private TextView txtID_Phim_chiTiet;
    private TextView txtID_TL_chiTiet;
    private TextView txtTenPhim_chiTiet;
    private TextView txtDaoDien_chiTiet;
    private TextView txtNgayPhatHanh_chiTiet;
    private TextView txtMoTa_chiTiet;
    private ImageView btnExit_chiTiet;
    private PhimDao phimDao;

    private Phim phim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_thiet_phim);
        anh = findViewById(R.id.imageView2);
        txtID_Phim_chiTiet = findViewById(R.id.txtID_Phim_chiTiet);
        txtID_TL_chiTiet = findViewById(R.id.txtID_TL_chiTiet);
        txtTenPhim_chiTiet = findViewById(R.id.txtTenPhim_chiTiet);
        txtDaoDien_chiTiet = findViewById(R.id.txtDaoDien_chiTiet);
        txtNgayPhatHanh_chiTiet = findViewById(R.id.txtNgayPhatHanh_chiTiet);
        txtMoTa_chiTiet = findViewById(R.id.txtMoTa_chiTiet);
        btnExit_chiTiet = findViewById(R.id.btnExit_chiTiet);

        phimDao = new PhimDao(this);
        // Nhận dữ liệu từ Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int phimId = extras.getInt("ID_Phim", 0); // Sử dụng getInt() thay vì getString() và chuyển giá trị mặc định là 0 nếu không tìm thấy khóa "ID_Phim"

            // Gọi phương thức selectPhimById để lấy thông tin phim từ cơ sở dữ liệu
             phim = phimDao.selectPhimById(phimId);

            if (phim != null) {
                // Đặt dữ liệu vào các thành phần giao diện
                txtID_Phim_chiTiet.setText(String.valueOf(phim.getID_Phim()));
                txtID_TL_chiTiet.setText(String.valueOf(phim.getID_TL()));
                txtTenPhim_chiTiet.setText(phim.getTenPhim());
                txtDaoDien_chiTiet.setText(phim.getDaoDien());
                txtNgayPhatHanh_chiTiet.setText(phim.getNgayPhatHanh());
                txtMoTa_chiTiet.setText(phim.getMota());

            }
        }

        String base64String = phim.getAnh();
        // Giải mã chuỗi Base64 thành mảng byte
        byte[] decodedByteArray = Base64.decode(base64String, Base64.DEFAULT);
        // Chuyển đổi mảng byte thành Bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
        // Hiển thị Bitmap bằng Glide
        Glide.with(this)
                .load(bitmap)
                .into(anh);


        btnExit_chiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChiThietPhim.this, "Thoát!!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChiThietPhim.this, MainActivity.class);

                startActivity(intent);
            }
        });




    }
}