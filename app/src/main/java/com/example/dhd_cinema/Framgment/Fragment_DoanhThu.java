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
import com.example.dhd_cinema.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Fragment_DoanhThu extends Fragment {

    EditText edtNgayBatDau, edtNgayKetThuc;
    ThongKeDao thongKeDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__doanh_thu, container, false);
        edtNgayBatDau = view.findViewById(R.id.edtNgayBatDau);
        edtNgayKetThuc = view.findViewById(R.id.edtNgayKetThuc);
        Button btnThongKe = view.findViewById(R.id.btnThongKe);
        TextView txtKetQua = view.findViewById(R.id.txtKetQua);
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
}