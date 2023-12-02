package com.example.dhd_cinema.Framgment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.dhd_cinema.Dao.ThongKeDao;
import com.example.dhd_cinema.MainActivity;
import com.example.dhd_cinema.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Fragment_DoanhThu extends Fragment {

    EditText edtNgayBatDau, edtNgayKetThuc;
    ThongKeDao thongKeDao;

    @Override
    public void onResume() {
        super.onResume();
        // Đặt tiêu đề trên Toolbar khi Fragment này được hiển thị
        if (getActivity() != null) {
            ((MainActivity) getActivity()).setActionBarTitle("Doanh thu");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__doanh_thu, container, false);
        edtNgayBatDau = view.findViewById(R.id.edtNgayBatDau);
        edtNgayKetThuc = view.findViewById(R.id.edtNgayKetThuc);
        Button btnThongKe = view.findViewById(R.id.btnThongKe);
        TextView txtKetQua = view.findViewById(R.id.txtKetQua);
        TextView tongDoanhThu = view.findViewById(R.id.txtTongDoanhThu);
        Button btnthang = view.findViewById(R.id.btnThang);
        TextView txtThang = view.findViewById(R.id.txtThang);

        thongKeDao = new ThongKeDao(getContext());
        edtNgayBatDau.setOnClickListener(v -> {
            showDatePickerDialog(edtNgayBatDau);
        });
        edtNgayKetThuc.setOnClickListener(v -> {
            showDatePickerDialog(edtNgayKetThuc);
        });
        btnThongKe.setOnClickListener(v -> {
            String tuNgay = edtNgayBatDau.getText().toString();
            String denNgay = edtNgayKetThuc.getText().toString();

            if (tuNgay.isEmpty() || denNgay.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng chọn ngày bắt đầu và ngày kết thúc", Toast.LENGTH_SHORT).show();
            } else {
                int doanhThu = thongKeDao.DoanhThu(tuNgay, denNgay);
                txtKetQua.setText( doanhThu + " đồng");
            }
        });

        // Hiển thị tổng doanh thu từ trước tới thời điểm hiện tại
        int tongDoanhThuTatCa = thongKeDao.DoanhThuTatCa();
        tongDoanhThu.setText(tongDoanhThuTatCa + " đồng");

        // Hiển thị tổng doanh thu theo tháng
        btnthang.setOnClickListener(v -> {
            showMonthPickerDialog(txtThang);
        });
        return view;
    }

    private void showDatePickerDialog(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, yearSelected, monthOfYear, dayOfMonthSelected) -> {
                    Calendar selectedDateCalendar = Calendar.getInstance();
                    selectedDateCalendar.set(yearSelected, monthOfYear, dayOfMonthSelected);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String selectedDate = sdf.format(selectedDateCalendar.getTimeInMillis());
                    editText.setText(selectedDate);
                },
                year,
                month,
                dayOfMonth
        );
        datePickerDialog.show();
    }


    private void showMonthPickerDialog(TextView textView) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, yearSelected, monthOfYear, dayOfMonthSelected) -> {
                    Calendar selectedDateCalendar = Calendar.getInstance();
                    selectedDateCalendar.set(yearSelected, monthOfYear, 1);
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy", Locale.getDefault());
                    String selectedMonth = sdf.format(selectedDateCalendar.getTimeInMillis());
                    textView.setText("Doanh thu " + selectedMonth);

                    // Tính và hiển thị doanh thu tháng đã chọn
                    String[] parts = selectedMonth.split("/");
                    int selectedMonthValue = Integer.parseInt(parts[0]);
                    int selectedYearValue = Integer.parseInt(parts[1]);
                    int doanhThuThang = thongKeDao.DoanhThuThang(selectedMonthValue, selectedYearValue);

                    // Hiển thị kết quả doanh thu tháng
                    String result = selectedMonth + ": " + doanhThuThang + " đồng";
                    textView.setText(result);
                },
                year,
                month,
                1 // Set ngày là 1 để hiển thị lịch tháng
        );
        datePickerDialog.getDatePicker().setCalendarViewShown(false); // Ẩn chế độ xem lịch
        datePickerDialog.getDatePicker().setSpinnersShown(true); // Hiển thị spinner chọn tháng/năm
        datePickerDialog.show();
    }


}