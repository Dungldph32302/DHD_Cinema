package com.example.dhd_cinema.Framgment;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.dhd_cinema.Model.PhongModel;
import com.example.dhd_cinema.Model.SuatChieuModel;
import com.example.dhd_cinema.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class Fragment_HoaDon extends Fragment {


    public Fragment_HoaDon() {

    }
    @Override
    public void onResume() {
        super.onResume();
        // Đặt tiêu đề trên Toolbar khi Fragment này được hiển thị
        if (getActivity() != null) {
            ((MainActivity) getActivity()).setActionBarTitle("Đặt vé ");
        }
    }
    private static final int YeuCauQuyen = 10;
    private final ActivityResultLauncher<Intent> moGalleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    xuLyKetQuaChonAnh(result);
                }
            }
    );

    LinearLayout tienmat,chuyenkhoan;

    int index=0;
    ImageView anh;
    TextView ngay,gio,gia,sl,tong,phong,tenp;
    AdapterGhe_in_Dat adapterGheInDat;
    HoaDonDao hoaDonDao; GheDao gheDao;
    ArrayList<GheModel>list;
    ArrayList<GheModel>list1;
    int idsc=-1,idphim=-1,idv=-1,tongtien=0,phuongthuc=-1,soluong=0,giave=0,idmax;
    SuatChieuDao suatChieuDao;
    String tenphim;
    AppCompatButton thanhtoan;
    private String linkanh;

    HoaDonModel hoaDonModel;

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

        Bundle bundle = getArguments();
        if (bundle != null) {
            idsc = bundle.getInt("idschh");
            giave = bundle.getInt("giahh");
            soluong = bundle.getInt("sll");
            list1= (ArrayList<GheModel>)bundle.getSerializable("list");
            sl.setText(String.valueOf(soluong));
            gia.setText(String.valueOf(giave) +"đ");
            tong.setText(String.valueOf(giave*soluong)+"đ");
        }

        list=gheDao.getAllGhe();
        adapterGheInDat=new AdapterGhe_in_Dat(getActivity(),list,idsc);
      // list1=adapterGheInDat.getSelectedListGhe();

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



                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", getActivity().MODE_PRIVATE);
                String savedUsername = sharedPreferences.getString("username", "");
                @SuppressLint({"NewApi", "LocalSuppress"})
                String ngay = String.valueOf(java.time.LocalDate.now());
                HoaDonModel hoaDonModel= new HoaDonModel();
                hoaDonModel.setId(idmax);
                hoaDonModel.setIdsc(idsc);
                hoaDonModel.setGia(giave);
                hoaDonModel.setPhuongthuc(index);
                hoaDonModel.setSl(soluong);
                hoaDonModel.setThoigian(ngay);
                hoaDonModel.setTongtien(giave*soluong);
                hoaDonModel.setTrangthai(0);
                hoaDonModel.setTennguoidung(savedUsername);
                hoaDonModel.setAnh(linkanh);

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
            }
        });

        chuyenkhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogchuyenkhoan();
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
                hoaDonModel.setGia(giave);
                hoaDonModel.setPhuongthuc(index);
                hoaDonModel.setSl(soluong);
                hoaDonModel.setThoigian(ngay);
                hoaDonModel.setTongtien(giave*soluong);
                hoaDonModel.setTrangthai(0);
                hoaDonModel.setTennguoidung(savedUsername);
                hoaDonModel.setAnh(linkanh);
                if(phuongthuc==-1){
                    Toast.makeText(getActivity(), "Vui lòng chọn phương thức thanh toán ", Toast.LENGTH_SHORT).show();
                }else {
                    if(hoaDonDao.addHoaDonandve(hoaDonModel,list1)){
                        Toast.makeText(getActivity(), "Tạo thành công ", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), "Ảnh"+linkanh, Toast.LENGTH_SHORT).show();
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
    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    public void openDialogchuyenkhoan () {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater =getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.gui_anh,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
       anh =  (ImageView) view.findViewById(R.id.anhthanhtoan);
        TextView tvtien=view.findViewById(R.id.tvtien);
        AppCompatButton huy, gui;
        huy=view.findViewById(R.id.btnhuy);
        gui=view.findViewById(R.id.btnxacnhan);
        tvtien.setText("Số tiền thanh toán : "+String.valueOf(giave*soluong));
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // xử lý chọn ảnh
        anh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonAnhTuGallery();
            }
        });
        gui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linkanh != null && !linkanh.isEmpty()) {
                index=1;
                phuongthuc=1;
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", getActivity().MODE_PRIVATE);
                String savedUsername = sharedPreferences.getString("username", "");
                @SuppressLint({"NewApi", "LocalSuppress"})
                String ngay = String.valueOf(java.time.LocalDate.now());
                HoaDonModel hoaDonModel= new HoaDonModel();
                hoaDonModel.setId(idmax);
                hoaDonModel.setIdsc(idsc);
                hoaDonModel.setTennguoidung("admin");
                hoaDonModel.setGia(giave);
                hoaDonModel.setPhuongthuc(1);
                hoaDonModel.setSl(soluong);
                hoaDonModel.setThoigian(ngay);
                hoaDonModel.setTongtien(giave*soluong);
                hoaDonModel.setTrangthai(0);
                hoaDonModel.setTennguoidung(savedUsername);
                hoaDonModel.setAnh(linkanh);
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
            }else {
                    Toast.makeText(getActivity(), "Chưa chọn ảnh ", Toast.LENGTH_SHORT).show();
                }
            }


        });

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
                    linkanh = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    anh.setImageBitmap(bitmap);
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