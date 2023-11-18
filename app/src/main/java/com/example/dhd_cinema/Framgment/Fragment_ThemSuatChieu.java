package com.example.dhd_cinema.Framgment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import com.example.dhd_cinema.Dao.PhimDao;
import com.example.dhd_cinema.Dao.PhongDao;
import com.example.dhd_cinema.Dao.SuatChieuDao;
import com.example.dhd_cinema.Model.Phim;
import com.example.dhd_cinema.Model.PhongModel;
import com.example.dhd_cinema.Model.SuatChieuModel;
import com.example.dhd_cinema.R;
import com.example.dhd_cinema.Spinner.AdapterSpinnerPhim;
import com.example.dhd_cinema.Spinner.AdapterSpinnerPhong;

import java.util.ArrayList;
import java.util.Calendar;

public class Fragment_ThemSuatChieu extends Fragment {

    PhimDao dao;
    ArrayList<Phim> danhSachPhim;
    String duongDanAnh;
    AppCompatEditText gia;
    private static final int YeuCauQuyen = 10;
    SuatChieuDao suatChieuDao;
    AppCompatButton tao;
    Spinner phim,phong;

    EditText ngay,gio;
    private int selecidPhim=0,selecidPhong=0;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__them_suat_chieu, container, false);
//        tenPhim=view.findViewById(R.id.txtName);
//        theLoai=view.findViewById(R.id.txtTL);
//        ngayPhatHanh=view.findViewById(R.id.txtDat);
        gia=view.findViewById(R.id.txtgia);
        tao=view.findViewById(R.id.btnTao);
        ngay=view.findViewById(R.id.ngay);
        gio=view.findViewById(R.id.gio);
        phim = view.findViewById(R.id.spinnerPhim);
        phong=view.findViewById(R.id.spinnerphong);
        dao = new PhimDao(getActivity());
        suatChieuDao= new SuatChieuDao(getActivity());

        PhimDao tv = new PhimDao(getActivity());
        ArrayList<Phim> list1 = tv.selectAllPhim();
        AdapterSpinnerPhim adapter1 = new AdapterSpinnerPhim(getActivity(), list1);
        phim.setAdapter(adapter1);

         PhongDao dao1 = new PhongDao(getActivity());
        ArrayList<PhongModel> list2 = dao1.getAllPhongChieu();
        AdapterSpinnerPhong adapter2 = new AdapterSpinnerPhong(getActivity(), list2);
        phong.setAdapter(adapter2);

        // xử lý khio chọn giờn
        gio.setKeyListener(null);
        ngay.setKeyListener(null);
        gio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });
        ngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdate();
            }
        });

        phim.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selecidPhim = ((Phim) phim.getItemAtPosition(position)).getID_Phim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // lấy string từ spinner Phong
        phong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selecidPhong = ((PhongModel) phong.getItemAtPosition(position)).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // xử lý khi chọn tạo
        tao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String n=ngay.getText().toString().trim();
               String d=gio.getText().toString().trim();
               String g=gia.getText().toString().trim();

                // lấy string từ spinner Thành viên

               if(validate(g,n,g)){
                   SuatChieuModel suatChieuModel= new SuatChieuModel();
                   suatChieuModel.setIdPhim(selecidPhim);
                   suatChieuModel.setIdPhong(selecidPhong);
                   suatChieuModel.setGioChieu(d);
                   suatChieuModel.setGia(Integer.parseInt(g));

                   Toast.makeText(getActivity(), "maphim"+selecidPhim, Toast.LENGTH_SHORT).show();
                   Toast.makeText(getActivity(), "maphong"+selecidPhong, Toast.LENGTH_SHORT).show();
                   Toast.makeText(getActivity(), "gio chiếu"+d, Toast.LENGTH_SHORT).show();

                   if(suatChieuDao.addSuatChieu(suatChieuModel)){
                       Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                   }
               }
            }
        });
        return view;




    }
    public boolean validate(String gia,String date,String gio){
        if(gia.isEmpty()){
            Toast.makeText(getActivity(), "Vui lòng nhập giá", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(date.isEmpty()){
            Toast.makeText(getActivity(), "Vui lòng chọn ngày chiếu ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(gio.isEmpty()){
            Toast.makeText(getActivity(), "Vui lòng chọn giờ chiếu ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void showdate(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String ngayy = "";
                        String thang = "";
                        if (dayOfMonth < 10) {
                            ngayy = "0" + dayOfMonth;
                        } else {
                            ngayy = String.valueOf(dayOfMonth);
                        }

                        if ((month + 1) < 10) {
                            thang = "0" + (month + 1);
                        } else {
                            thang = String.valueOf((month + 1));
                        }
                        ngay.setText(ngayy+ "/" + thang + "/" + year);

                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
    private void showTimePickerDialog() {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        // Format the selected time and set it to the EditText
                        String formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                        gio.setText(formattedTime);
                    }
                }, hour, minute, true);

        // Show the dialog
        timePickerDialog.show();
    }
}



