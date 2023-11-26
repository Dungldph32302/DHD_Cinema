package com.example.dhd_cinema.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dhd_cinema.ChiThietPhim;
import com.example.dhd_cinema.Dao.NguoiDungDao;
import com.example.dhd_cinema.Dao.PhimDao;
import com.example.dhd_cinema.Dao.TheLoaiPhimDao;
import com.example.dhd_cinema.HoaDon_chitiet;
import com.example.dhd_cinema.MainActivity;
import com.example.dhd_cinema.Model.Phim;
import com.example.dhd_cinema.Model.TheLoaiPhim;
import com.example.dhd_cinema.R;
import com.example.dhd_cinema.Spinner.TheLoaiPhimSpinner;

import java.util.ArrayList;

public class AdapterPhim extends RecyclerView.Adapter<AdapterPhim.ViewHolder>{
    private Context context;
    private ArrayList<Phim> list;
    PhimDao phimDao;

    public AdapterPhim(Context context, ArrayList<Phim> list) {
        this.context = context;
        this.list = list;
        phimDao = new PhimDao(context);
    }
    @NonNull
    @Override
    public AdapterPhim.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_phim, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPhim.ViewHolder holder,int position) {
        int quyen=-1;
        SharedPreferences sharedPreferences = context.getSharedPreferences("login", context.MODE_PRIVATE);
        String tendangnhap = sharedPreferences.getString("username", "");

        NguoiDungDao nguoiDungDao= new NguoiDungDao(context);
        quyen=nguoiDungDao.layQuyenTuDangNhap(tendangnhap);
        if(quyen==0){
            holder.chon.setVisibility(View.INVISIBLE);
        }

        holder.txtTenPhim_item.setText("" + (list.get(position).getTenPhim()));
        holder.txtDaoDien_item.setText("Đạo Diễn: " + list.get(position).getDaoDien());
        holder.txtNgayPhatHanh_item.setText("" + (list.get(position).getNgayPhatHanh()));

        String base64String = list.get(position).getAnh();

        // Giải mã chuỗi Base64 thành mảng byte
        byte[] decodedByteArray = Base64.decode(base64String, Base64.DEFAULT);

        // Chuyển đổi mảng byte thành Bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);

        // Hiển thị Bitmap bằng Glide
        Glide.with(context)
                .load(bitmap)
                .into(holder.anh);



        holder.chon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.getMenuInflater().inflate(R.menu.popumenu, popupMenu.getMenu());

                // Bắt sự kiện khi một mục trong menu được chọn
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();

                        if (itemId == R.id.action_custom){
                            // Xử lý khi chọn tùy chỉnh
                            showDialogSua(list.get(position));
                            return true;
                        } else if (itemId == R.id.action_delete) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Cảnh Báo"); //set tieu de cho hop thoai
                            builder.setIcon(R.drawable.baseline_warning_24); //icon cho hop thoai
                            builder.setMessage("Bạn Có Muốn Xóa Phim Này Không?"); //chuoi thong bao
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    phimDao = new PhimDao(context);
                                    int check = phimDao.delete(list.get(holder.getAdapterPosition()).getID_Phim());
                                    switch (check) {
                                        case 1:
                                            list.clear();
                                            list = phimDao.selectAllPhim();
                                            notifyDataSetChanged();
                                            Toast.makeText(context, "Xóa thành công!!!", Toast.LENGTH_SHORT).show();
                                            break;
                                        case -1:
                                            Toast.makeText(context, "Không Thể Xóa vì Phim Đang Có Trong Suất CHiếu!!!", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 0:
                                            Toast.makeText(context, "Xóa không thành công!!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            //nut no
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(context, "Không Xóa", Toast.LENGTH_SHORT).show();
                                }
                            });
                            // tao va hien thi hop thoai
                            AlertDialog dialog = builder.create();// tao hop thoai
                            dialog.show();//hien thi hop thoai
                        }
                        return  false;
                    }
                });

                popupMenu.show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent= new Intent(context, ChiThietPhim.class);
                intent.putExtra("ID_Phim",list.get(position).getID_Phim());
                context.startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenPhim_item, txtDaoDien_item, txtNgayPhatHanh_item, txtAnh;
        ImageView chon;
        LinearLayout dsPhim;
        ImageView anh;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenPhim_item = itemView.findViewById(R.id.txtTenPhim_item);
            txtDaoDien_item = itemView.findViewById(R.id.txtDaoDien_item);
            txtNgayPhatHanh_item = itemView.findViewById(R.id.txtNgayPhatHanh_item);
            chon = itemView.findViewById(R.id.img_chon);
            dsPhim = itemView.findViewById(R.id.DSPhim);
            anh=itemView.findViewById(R.id.imageView);
        }
    }

    private Dialog dialog;


    private Uri selectedImageUri;
    ImageView anhphim;
    public void showDialogSua(Phim phim) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);// tao doi tuong cua altertdialog
        // gan layout,tao view
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.sua_phim, null);
        builder.setView(view); //gan view cho hop thoai

        //anh xa cac thanh phan widget
        EditText edtID_Phim_sua_P = view.findViewById(R.id.edtID_Phim_sua_P);
        Spinner spn_TheLoaiPhim = view.findViewById(R.id.spn_TheLoaiPhim);
        EditText edtTenPhim_sua_p = view.findViewById(R.id.edtTenPhim_sua_p);
        EditText edtDaoDien_sua_P = view.findViewById(R.id.edtDaoDien_sua_P);
        EditText edtNgayPhatHanh_sua_P = view.findViewById(R.id.edtNgayPhatHanh_sua_P);
        EditText edtMota_sua_P = view.findViewById(R.id.edtMota_sua_P);
        Button btnLuu_sua_P = view.findViewById(R.id.btnLuu_sua_P);
        anhphim = view.findViewById(R.id.anhPhim_Sua);
        ImageView back = view.findViewById(R.id.btnExit_suaPhim);
        edtID_Phim_sua_P.setEnabled(false);

        // Hiển thị dữ liệu hiện tại của phim trong các trường sửa
        edtID_Phim_sua_P.setText(String.valueOf(phim.getID_Phim()));
        edtTenPhim_sua_p.setText(phim.getTenPhim());
        edtDaoDien_sua_P.setText(phim.getDaoDien());
        edtNgayPhatHanh_sua_P.setText(phim.getNgayPhatHanh());
        edtMota_sua_P.setText(phim.getMota());
        String base64String = phim.getAnh();

        // Giải mã chuỗi Base64 thành mảng byte
        byte[] decodedByteArray = Base64.decode(base64String, Base64.DEFAULT);

        // Chuyển đổi mảng byte thành Bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);

        // Hiển thị Bitmap bằng Glide
        Glide.with(context)
                .load(bitmap)
                .into(anhphim);


        // Xử lý khi người dùng click vào ảnh để chọn hình ảnh từ thiết bị
        anhphim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonAnh();
            }
        });
        // Setup Spinner
        TheLoaiPhimDao theLoaiPhimDao = new TheLoaiPhimDao(context);
        ArrayList<TheLoaiPhim> listTheLoaiPhim = theLoaiPhimDao.selectAllTheLoaiPhim();
        TheLoaiPhimSpinner adapter = new TheLoaiPhimSpinner(context, listTheLoaiPhim);
        spn_TheLoaiPhim.setAdapter(adapter);

        // Chọn mục hiện tại trong Spinner
        int theLoaiPhimPosition = getTheLoaiPhimPosition(phim.getID_TL(), listTheLoaiPhim);
        spn_TheLoaiPhim.setSelection(theLoaiPhimPosition);

        // Xử lý sự kiện khi nhấn nút "Lưu"
        btnLuu_sua_P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


                // Kiểm tra xem có trường nào trống không
                if (tenPhim.isEmpty() || daoDien.isEmpty() || ngayPhatHanh.isEmpty() || moTa.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();

                } else {
                    if (phimDao.update(phim)) {
                        list.clear();
                        list.addAll(phimDao.selectAllPhim());
                        notifyDataSetChanged();
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Xử lý sự kiện khi nhấn nút "Hủy"
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Thoát", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


        dialog = builder.create();
        dialog.show();
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

    // Phương thức để chọn hình ảnh từ thiết bị
    private void chonAnh() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        int PICK_IMAGE_REQUEST = 1;
        ((Activity) context).startActivityForResult(gallery, PICK_IMAGE_REQUEST);
    }

    // Phương thức để cập nhật ImageView 'anhphim' với hình ảnh được chọn từ thiết bị
//    public void updateSelectedImage(Uri selectedImage, Bitmap bitmap) {
//        if (selectedImage != null && bitmap != null) {
//            Glide.with(context)
//                    .load(selectedImage)
//                    .into(anh);
//        }
//
//    }


}