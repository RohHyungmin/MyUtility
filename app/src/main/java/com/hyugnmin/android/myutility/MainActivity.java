package com.hyugnmin.android.myutility;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //탭 및 페이저 속성 정의
    final int TAB_COUNT = 4;
    OneFragment one;
    TwoFragment two;
    ThreeFragment three;
    FourFragment four;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fragmnet init
        one = new OneFragment();
        two = new TwoFragment();
        three = new ThreeFragment();
        four = new FourFragment();

        //tab layout 정의
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        //tab 생성 및 타이틀 입력
        tabLayout.addTab(tabLayout.newTab().setText("Calculate"));
        tabLayout.addTab(tabLayout.newTab().setText("Convert"));
        tabLayout.addTab(tabLayout.newTab().setText("Search"));
        tabLayout.addTab(tabLayout.newTab().setText("Locate"));

        //fragment 페이저 작성
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        //아답터 세팅
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        //pagerListener - 페이저가 변경되었을 때 탭을 바꿔주는 리스너
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //탭이 변경되었을 때 페이저를 바꿔주는 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

    }

    //adapter 생성(fragment를 넣어야해서 기존과 좀 다르다)
    class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;

            switch (position){
                case 0 : fragment = one; break;
                case 1 : fragment = two; break;
                case 2 : fragment = three; break;
                case 3 : fragment = four; break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }
    }

}
