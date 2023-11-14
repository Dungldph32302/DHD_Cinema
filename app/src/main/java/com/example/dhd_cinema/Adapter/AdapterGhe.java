package com.example.dhd_cinema.Adapter;

import static android.app.ProgressDialog.show;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhd_cinema.Dao.GheDao;
import com.example.dhd_cinema.Model.GheModel;
import com.example.dhd_cinema.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class AdapterGhe extends RecyclerView.Adapter<AdapterGhe.ViewHolder>{
    private final Context context;
    private final ArrayList<GheModel> list;
 GheDao dao;

    public AdapterGhe(Context context, ArrayList<GheModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        View view=inflater.inflate(R.layout.item_ghe,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.name.setText(list.get(position).getSoGhe());
            holder.id.setText("ID : "+String.valueOf(list.get(position).getId()));
            holder.phong.setText("ID Phòng : "+String.valueOf(list.get(position).getIdPhong()));
            dao= new GheDao(context);
            GheModel ghe=list.get(position);
            if(list.get(position).getTrangThai()==0){
                holder.trangThai.setText("Ghế trống ");
            }else {
                holder.trangThai.setText("Ghế đã đặt");
            }

            holder.cham.setOnClickListener(new View.OnClickListener() {
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
                              openDialogUpdate(ghe);
                                return true;
                            } else if (itemId == R.id.action_delete) {
                                dao= new GheDao(context);
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Cảnh báo!");
                                builder.setIcon(R.drawable.baseline_warning_24);
                                builder.setMessage("Bạn có muốn xoá?");
                                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        if (dao.deleteGhe(list.get(position).getId())) {
                                            list.clear();
                                            list.addAll(dao.getAllGhe());
                                            notifyDataSetChanged();
                                            Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                                            // Sau khi xóa thành công sản phẩm
                                            //  NotificationHelper.showNotification(context.getApplicationContext(), "Bạn đã xóa một sản phẩm.");

                                        } else {
                                            Toast.makeText(context, "Xoá thất bại", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        Toast.makeText(context, "Huỷ xoá", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                                // Xử lý khi chọn xóa
                                return true;
                            }
                            return  false;
                        }
                    });

                    popupMenu.show();
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ghe,cham;
        TextView id,phong,trangThai,name;
        public ViewHolder(@NonNull View view) {
            super(view);
            ghe=view.findViewById(R.id.imageView);
            name=view.findViewById(R.id.tvNameGhe);
            cham=view.findViewById(R.id.tvc);
            id=view.findViewById(R.id.tvid);
            phong=view.findViewById(R.id.tvphong);
            trangThai=view.findViewById(R.id.tvtt);
        }
    }
    public void openDialogUpdate (GheModel dv) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.update_ghe,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        TextView id=view.findViewById(R.id.TVmaGhe);
        AppCompatEditText idp,soghe;
        idp=view.findViewById(R.id.txtIDP);
        soghe=view.findViewById(R.id.txtSG);
        Button update,canel;
        update=view.findViewById(R.id.btnUpdateTV);
        canel=view.findViewById(R.id.btncanelTV);

        id.setText("Mã: "+String.valueOf(dv.getId()));
        idp.setText(String.valueOf(dv.getIdPhong()));
        soghe.setText(dv.getSoGhe());
        // xử lý khi ấn hủy
        canel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idphong=idp.getText().toString().trim();
                String ten = soghe.getText().toString().trim();
                if(idphong.isEmpty()){
                    Toast.makeText(context, "Không để trống ID Phòng", Toast.LENGTH_SHORT).show();
                } else if (ten.isEmpty()) {
                    Toast.makeText(context, "Không để trống Số ghế", Toast.LENGTH_SHORT).show();
                } else {
                    int idghe=dv.getId();
                    try {
                        dv.setId(idghe);
                        dv.setIdPhong(Integer.parseInt(idphong));
                        dv.setSoGhe(ten);
                        dv.setTrangThai(dv.getTrangThai());

                        if (dao.updateghe(dv)) {
                            list.clear();
                            list.addAll(dao.getAllGhe());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        });

    }

}
