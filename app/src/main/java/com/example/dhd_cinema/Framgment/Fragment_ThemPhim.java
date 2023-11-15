package com.example.dhd_cinema.Framgment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dhd_cinema.Dao.PhimDao;
import com.example.dhd_cinema.Model.Phim;
import com.example.dhd_cinema.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Fragment_ThemPhim extends Fragment {

    PhimDao dao;
    ArrayList<Phim> danhSachPhim;
    String duongDanAnh;
    AppCompatEditText tenPhim, theLoai, ngayPhatHanh, daoDien, moTa;
    private static final int YeuCauQuyen = 10;
    ImageView anhPhim;
    AppCompatButton themPhim;
    private final ActivityResultLauncher<Intent> moGalleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    xuLyKetQuaChonAnh(result);
                }
            }
    );

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__them_phim, container, false);
        tenPhim=view.findViewById(R.id.txtName);
        theLoai=view.findViewById(R.id.txtTL);
        ngayPhatHanh=view.findViewById(R.id.txtDat);
        daoDien=view.findViewById(R.id.txtDD);
        moTa=view.findViewById(R.id.txtMT);
        anhPhim=view.findViewById(R.id.anhPhim);
        themPhim=view.findViewById(R.id.addPhim);

        dao = new PhimDao(getActivity());

        anhPhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonAnhTuGallery();
            }
        });

        themPhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (duongDanAnh != null && !duongDanAnh.isEmpty()) {
                    String theLoaiPhim = String.valueOf(daoDien.getText());
                    Phim phim = new Phim();
                    phim.setAnh(duongDanAnh);
                    phim.setMota(moTa.getText().toString());
                    phim.setTenPhim(tenPhim.getText().toString());
                    phim.setDaoDien(daoDien.getText().toString());
                    phim.setNgayPhatHanh(ngayPhatHanh.getText().toString());
                    phim.setID_Phim(1);

                    if (dao.insert(phim)) {
                        Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Chưa chọn ảnh", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void moGallery() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        if (kiemTraQuyen(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            chonAnhTuGallery();
        } else {
            String[] quyen = {Manifest.permission.READ_EXTERNAL_STORAGE};
            yeuCauQuyen(quyen, YeuCauQuyen);
        }
    }

    private void chonAnhTuGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        moGalleryLauncher.launch(Intent.createChooser(intent, "Chọn ảnh"));
    }

    private void xuLyKetQuaChonAnh(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            if (data != null) {
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uri);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();

                    // Chuyển đổi mảng byte thành chuỗi base64
                    duongDanAnh = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    anhPhim.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int kiemTraQuyen(Activity activity, String quyen) {
        return ContextCompat.checkSelfPermission(activity, quyen);
    }

    private void yeuCauQuyen(String[] quyen, int maYeuCau) {
        requestPermissions(quyen, maYeuCau);
    }
}
