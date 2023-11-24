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
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.dhd_cinema.Framgment.FragmentAllHoaDon;
import com.example.dhd_cinema.Framgment.FragmentVe;
import com.example.dhd_cinema.Framgment.Fragment_DatPhim;
import com.example.dhd_cinema.Framgment.Fragment_Khac;
import com.example.dhd_cinema.Framgment.Fragment_Phim;
import com.example.dhd_cinema.Framgment.Fragment_PhongChieu;
import com.example.dhd_cinema.Framgment.Fragment_SuatChieu;
import com.example.dhd_cinema.Framgment.Fragment_TheLoaiPhim;
import com.example.dhd_cinema.Framgment.Fragment_ThemPhim;
import com.example.dhd_cinema.Framgment.Fragment_ThemSuatChieu;
import com.example.dhd_cinema.Framgment.Fragment_TrangCaNhan;
import com.example.dhd_cinema.Framgment.Fragment_ghe;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawable;
    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
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

        ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(this,drawable,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawable.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        // xửa lý khi chọn bottomNavigation
        Fragment_ghe frg= new Fragment_ghe();
        Fragment_PhongChieu fr= new Fragment_PhongChieu();
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
                return true;
            }
        });
        // bam vao item no ao ra

        // xử lý khi chọn item navigation
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.suatChieu){

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
                }else if (item.getItemId()==R.id.TKNhap) {

                }else if (item.getItemId()==R.id.tk) {

                }else if (item.getItemId()==R.id.doimk) {

                }else if (item.getItemId()==R.id.checkout) {
                    Fragment_ThemPhim frg= new Fragment_ThemPhim();
                    replec(frg);
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
}