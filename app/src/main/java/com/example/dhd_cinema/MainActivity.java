package com.example.dhd_cinema;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhd_cinema.Dao.NguoiDungDao;
import com.example.dhd_cinema.Framgment.FragmentAllHoaDon;
import com.example.dhd_cinema.Framgment.FragmentVe;
import com.example.dhd_cinema.Framgment.Fragment_DanhThuTheoPhim;
import com.example.dhd_cinema.Framgment.Fragment_DatPhim;
import com.example.dhd_cinema.Framgment.Fragment_DoanhThu;
import com.example.dhd_cinema.Framgment.Fragment_HoaDon;
import com.example.dhd_cinema.Framgment.Fragment_Khac;
import com.example.dhd_cinema.Framgment.Fragment_NguoiDung;
import com.example.dhd_cinema.Framgment.Fragment_Phim;
import com.example.dhd_cinema.Framgment.Fragment_PhongChieu;
import com.example.dhd_cinema.Framgment.Fragment_SuatChieu;
import com.example.dhd_cinema.Framgment.Fragment_SuatChieu_2;
import com.example.dhd_cinema.Framgment.Fragment_TheLoaiPhim;
import com.example.dhd_cinema.Framgment.Fragment_ThemPhim;
import com.example.dhd_cinema.Framgment.Fragment_ThemSuatChieu;
import com.example.dhd_cinema.Framgment.Fragment_TrangCaNhan;
import com.example.dhd_cinema.Framgment.Fragment_ghe;
import com.example.dhd_cinema.Login.DangNhap;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawable;
    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    NguoiDungDao nguoiDungDao;
    int quyen=-1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        drawable=findViewById(R.id.drawerLayout);
        bottomNavigationView=findViewById(R.id.btnNavigation);
        navigationView=findViewById(R.id.navigationView);
        View v=navigationView.getHeaderView(0);
        TextView tvname=v.findViewById(R.id.welcomeName);
        nguoiDungDao=new NguoiDungDao(MainActivity.this);

        ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(this,drawable,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawable.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        // lấy mã người dùng
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        String tendangnhap = sharedPreferences.getString("username", "");
        quyen=nguoiDungDao.layQuyenTuDangNhap(tendangnhap);

        if(quyen==0){
            toolbar.setVisibility(View.GONE);
        }else {
            toolbar.setVisibility(View.VISIBLE);
        }
        // xửa lý khi chọn bottomNavigation
        Fragment_SuatChieu frf = new Fragment_SuatChieu();
        replec(frf);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.home){
                    Fragment_SuatChieu frf = new Fragment_SuatChieu();
                     replec(frf);
                } else if (item.getItemId()==R.id.phim) {
                   Fragment_Phim frg= new Fragment_Phim();
                    replec(frg);
                } else if (item.getItemId()==R.id.ve) {
                    FragmentVe frg= new FragmentVe();
                    replec(frg);
                 }else if (item.getItemId()==R.id.khac) {
                    Fragment_Khac frg= new Fragment_Khac();
                    replec(frg);
                }
                // doi ten titel
                getSupportActionBar().setTitle(item.getTitle());
                return true;
            }
        });
        // bam vao item no ao ra

        // xử lý khi chọn item navigation
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.suatChieu){
                    Fragment_SuatChieu frf = new Fragment_SuatChieu();
                    replec(frf);
                } else if (item.getItemId()==R.id.LoaiPhim) {
                    Fragment_TheLoaiPhim fragment_theLoaiPhim =new Fragment_TheLoaiPhim(); //tao doi tuong
                    replec(fragment_theLoaiPhim);
                } else if (item.getItemId()==R.id.Phim) {
                    Fragment_Phim fragment_phim =new Fragment_Phim(); //tao doi tuong
                    replec(fragment_phim);
                } else if (item.getItemId()==R.id.Phong) {
                    Fragment_PhongChieu frg= new Fragment_PhongChieu();
                    replec(frg);
                } else if (item.getItemId()==R.id.ghe) {
                    Fragment_ghe frg= new Fragment_ghe();
                    replec(frg);
                }else if (item.getItemId()==R.id.TKDT) {
                    Fragment_DoanhThu frg= new Fragment_DoanhThu();
                    replec(frg);
                }else if (item.getItemId()==R.id.TKDTP){
                    Fragment_DanhThuTheoPhim frg= new Fragment_DanhThuTheoPhim();
                    replec(frg);
                }
                else if (item.getItemId()==R.id.ThanhVien) {
                    Fragment_NguoiDung frg= new Fragment_NguoiDung();
                    replec(frg);
                } else if (item.getItemId()==R.id.ve) {
                    FragmentVe frg= new FragmentVe();
                    replec(frg);
                }
                else if (item.getItemId()==R.id.hoaDon) {
                    FragmentAllHoaDon frg= new FragmentAllHoaDon();
                    replec(frg);
                }else if (item.getItemId()==R.id.checkout) {
                    Intent intent = new Intent(MainActivity.this, DangNhap.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Đăng Xuất!!!", Toast.LENGTH_SHORT).show();
                }
                // doi ten titel
                getSupportActionBar().setTitle(item.getTitle());
//                // bam vao item no ao ra
                drawable.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }
    public void replec(Fragment fragment){
        FragmentManager frg=getSupportFragmentManager();
        frg.beginTransaction().replace(R.id.frmHienthi,fragment).commit();
    }

    public void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}