package com.example.dhd_cinema;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dhd_cinema.Dao.PhimDao;
import com.example.dhd_cinema.Dao.TheLoaiPhimDao;
import com.example.dhd_cinema.Framgment.Fragment_Phim;
import com.example.dhd_cinema.Model.Phim;
import com.example.dhd_cinema.Model.TheLoaiPhim;
import com.example.dhd_cinema.Spinner.TheLoaiPhimSpinner;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class SuaPhim extends AppCompatActivity {
ImageView anhphim, back;
Button btnLuu_sua_P;
    EditText edtID_Phim_sua_P,edtTenPhim_sua_p, edtDaoDien_sua_P, edtNgayPhatHanh_sua_P, edtMota_sua_P;
    Spinner spn_TheLoaiPhim;
    private PhimDao phimDao;

    private Phim phim;
    String duongDanAnh;
    private ActivityResultLauncher<Intent> launcherChonAnh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_phim);

         edtID_Phim_sua_P = findViewById(R.id.edtID_Phim_sua1);
         spn_TheLoaiPhim = findViewById(R.id.spn_TheLoaiPhim1);
         edtTenPhim_sua_p = findViewById(R.id.edtTenPhim_sua1);
         edtDaoDien_sua_P = findViewById(R.id.edtDaoDien_sua1);
         edtNgayPhatHanh_sua_P = findViewById(R.id.edtNgayPhatHanh_sua1);
         edtMota_sua_P = findViewById(R.id.edtMota_sua1);
         btnLuu_sua_P = findViewById(R.id.btnLuu_sua1);
        anhphim = findViewById(R.id.anhPhim_Sua1);
         back = findViewById(R.id.btnExit_suaPhim1);
        edtID_Phim_sua_P.setEnabled(false);

        phimDao = new PhimDao(this);
        // Nhận dữ liệu từ Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int phimId = extras.getInt("ID_Phim", 0); // Sử dụng getInt() thay vì getString() và chuyển giá trị mặc định là 0 nếu không tìm thấy khóa "ID_Phim"

            // Gọi phương thức selectPhimById để lấy thông tin phim từ cơ sở dữ liệu
            phim = phimDao.selectPhimById(phimId);

            if (phim != null) {
                // Đặt dữ liệu vào các thành phần giao diện
                edtID_Phim_sua_P.setText(String.valueOf(phim.getID_Phim()));
                edtTenPhim_sua_p.setText(phim.getTenPhim());
                edtDaoDien_sua_P.setText(phim.getDaoDien());
                edtNgayPhatHanh_sua_P.setText(phim.getNgayPhatHanh());
                edtMota_sua_P.setText(phim.getMota());

                // Setup Spinner
                TheLoaiPhimDao theLoaiPhimDao = new TheLoaiPhimDao(this);
                ArrayList<TheLoaiPhim> listTheLoaiPhim = theLoaiPhimDao.selectAllTheLoaiPhim();
                TheLoaiPhimSpinner adapter = new TheLoaiPhimSpinner(this, listTheLoaiPhim);
                spn_TheLoaiPhim.setAdapter(adapter);

                // Chọn mục hiện tại trong Spinner
                int theLoaiPhimPosition = getTheLoaiPhimPosition(phim.getID_TL(), listTheLoaiPhim);
                spn_TheLoaiPhim.setSelection(theLoaiPhimPosition);

                String base64String = phim.getAnh();
                // Giải mã chuỗi Base64 thành mảng byte
                byte[] decodedByteArray = Base64.decode(base64String, Base64.DEFAULT);
                // Chuyển đổi mảng byte thành Bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
                // Hiển thị Bitmap bằng Glide
                Glide.with(this)
                        .load(bitmap)
                        .into(anhphim);
            }
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SuaPhim.this, "Thoát!!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SuaPhim.this, Fragment_Phim.class);
                startActivity(intent);
            }
        });

        // lay anh
        launcherChonAnh = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        xuLyKetQuaChonAnh(data);
                    }
                }
            }
        });

        anhphim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonAnhTuGallery();
            }
        });
        anhphim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonAnhTuGallery();
            }
        });

        Calendar calendar = Calendar.getInstance();
        edtNgayPhatHanh_sua_P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        SuaPhim.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar selectedDate = Calendar.getInstance();
                                selectedDate.set(year, month, dayOfMonth);

                                Calendar currentDate = Calendar.getInstance();
                                if (selectedDate.after(currentDate)) {
                                    // Ngày phát hành không được lớn hơn ngày hiện tại
                                    Toast.makeText(SuaPhim.this, "Ngày phát hành không được lớn hơn ngày hiện tại", Toast.LENGTH_SHORT).show();
                                } else {
                                    String ngay = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                                    String thang = ((month + 1) < 10) ? "0" + (month + 1) : String.valueOf(month + 1);
                                    edtNgayPhatHanh_sua_P.setText(ngay + "/" + thang + "/" + year);
                                }
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

                // Hạn chế ngày tối thiểu và tối đa
                // datePickerDialog.setMinDate(minDate.getTimeInMillis());
                // datePickerDialog.setMaxDate(maxDate.getTimeInMillis());

                datePickerDialog.show();
            }
        });

        btnLuu_sua_P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy dữ liệu đã sửa
                String tenPhim = edtTenPhim_sua_p.getText().toString();
                String daoDien = edtDaoDien_sua_P.getText().toString();
                String ngayPhatHanh = edtNgayPhatHanh_sua_P.getText().toString();
                String moTa = edtMota_sua_P.getText().toString();
                int theLoaiPhimID = ((TheLoaiPhim) spn_TheLoaiPhim.getSelectedItem()).getID_TL();

                // Cập nhật thông tin của phim
                phim.setTenPhim(tenPhim);
                phim.setDaoDien(daoDien);
                phim.setNgayPhatHanh(ngayPhatHanh);
                phim.setMota(moTa);
                phim.setID_TL(theLoaiPhimID);
                phim.setAnh(duongDanAnh); // Cập nhật đường dẫn ảnh (base64)

                // Kiểm tra xem có trường nào trống không
                if (tenPhim.isEmpty() || daoDien.isEmpty() || ngayPhatHanh.isEmpty() || moTa.isEmpty()) {
                    Toast.makeText(SuaPhim.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (phimDao.update(phim)) {
                        Toast.makeText(SuaPhim.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SuaPhim.this, Fragment_Phim.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SuaPhim.this, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    // Phương thức để lấy vị trí của TheLoaiPhim trong Spinner
    private int getTheLoaiPhimPosition(int theLoaiPhimID, ArrayList<TheLoaiPhim> listTheLoaiPhim) {
        for (int i = 0; i < listTheLoaiPhim.size(); i++) {
            if (listTheLoaiPhim.get(i).getID_TL() == theLoaiPhimID) {
                return i;
            }
        }
        return -1;
    }

    private void chonAnhTuGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        launcherChonAnh.launch(intent);
    }

    private void xuLyKetQuaChonAnh(Intent data) {
        Uri uri = data.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            // Chuyển đổi mảng byte thành chuỗi base64
            duongDanAnh = Base64.encodeToString(byteArray, Base64.DEFAULT);

            anhphim.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}