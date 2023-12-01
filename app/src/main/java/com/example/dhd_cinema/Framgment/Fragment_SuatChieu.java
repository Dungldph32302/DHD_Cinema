package com.example.dhd_cinema.Framgment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dhd_cinema.Adapter.AdapterPhongChieu;
import com.example.dhd_cinema.Adapter.AdapterSuatChieu;
import com.example.dhd_cinema.Adapter.ImageAdapter;
import com.example.dhd_cinema.Dao.NguoiDungDao;
import com.example.dhd_cinema.Dao.PhongDao;
import com.example.dhd_cinema.Dao.SuatChieuDao;
import com.example.dhd_cinema.MainActivity;
import com.example.dhd_cinema.Model.PhongModel;
import com.example.dhd_cinema.Model.SuatChieuModel;
import com.example.dhd_cinema.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class Fragment_SuatChieu extends Fragment {


    public Fragment_SuatChieu() {
        // Required empty public constructor
    }

    SuatChieuDao suatChieuDao;
    AdapterSuatChieu adapter;
    ArrayList<SuatChieuModel> list;
    RecyclerView rcv;
    AppCompatButton add;
    private ViewPager2 viewPager;
    private Handler sliderHandler = new Handler();
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view=inflater.inflate(R.layout.fragment_suatchieu, container, false);
         rcv=view.findViewById(R.id.rcvGhe);
         add=view.findViewById(R.id.addsc);
        viewPager =view.findViewById(R.id.viewPager);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TabLayout tabLayout =view.findViewById(R.id.tabLayout);

        // xử lý phân quyền
        int quyen=-1;
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", getActivity().MODE_PRIVATE);
        String tendangnhap = sharedPreferences.getString("username", "");
        NguoiDungDao nguoiDungDao= new NguoiDungDao(getActivity());
        quyen=nguoiDungDao.layQuyenTuDangNhap(tendangnhap);
        if(quyen==0){
           add.setVisibility(View.GONE);
        }
        // sinh tablayout
        TabLayout.Tab tab1 = tabLayout.newTab().setText(" CHIẾU THEO PHIM ");
        tabLayout.addTab(tab1);
        TabLayout.Tab tab3 = tabLayout.newTab().setText(" ĐÃ CHIẾU ");
        tabLayout.addTab(tab3);

        // xử lý slide
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.img_23);
        imageList.add(R.drawable.img_22);
        imageList.add(R.drawable.img_24);


        ImageAdapter adapterimg = new ImageAdapter(imageList);
        viewPager.setAdapter(adapterimg);
        autoSlide();

         suatChieuDao= new SuatChieuDao(getActivity());
         list=suatChieuDao.getOneSuatChieuForEachPhim();
        int spanCount = 2; // Số cột hoặc hàng trong lưới
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
        rcv.setLayoutManager(layoutManager);
         adapter= new AdapterSuatChieu(getActivity(),list);
         rcv.setAdapter(adapter);


         // xửa lý khi chọn tablayout
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int selectedTabIndex = tab.getPosition();
                if (selectedTabIndex == 0) {
                    ArrayList<SuatChieuModel> list1 = suatChieuDao.getOneSuatChieuForEachPhim();
                    adapter = new AdapterSuatChieu(getActivity(), list1);
                    rcv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else if (selectedTabIndex == 1) {
                    ArrayList<SuatChieuModel> list1 = suatChieuDao.dachieu();
                    adapter = new AdapterSuatChieu(getActivity(), list1);
                    rcv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
         add.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Fragment_ThemSuatChieu frg=new Fragment_ThemSuatChieu();
                 MainActivity mainActivity = (MainActivity) getActivity();
                 mainActivity.replec(frg);
             }
         });

         return view;
    }
    private boolean isAutoForward = true;
    private void autoSlide() {

        sliderHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int currentPosition = viewPager.getCurrentItem();
                int nextPosition;

                if (isAutoForward) {
                    nextPosition = currentPosition + 1;
                } else {
                    nextPosition = currentPosition - 1;
                }

                if (nextPosition >= viewPager.getAdapter().getItemCount()) {
                    isAutoForward = false;
                    nextPosition = viewPager.getAdapter().getItemCount() - 2;
                } else if (nextPosition < 0) {
                    isAutoForward = true;
                    nextPosition = 1;
                }

                viewPager.setCurrentItem(nextPosition);
                autoSlide();
            }
        }, 5000);
    }
}