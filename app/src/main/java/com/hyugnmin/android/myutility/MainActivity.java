package com.hyugnmin.android.myutility;

import android.*;
import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.media.audiofx.BassBoost;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.preference.DialogPreference;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hyugnmin.android.myutility.dummy.DummyContent;

import java.util.ArrayList;
import java.util.Stack;

/* GPS 사용 순서
1. manifest에 fine, coarse 권한 추가
2. runtime permission 소스코드에 추가
3. gps location manager 정의
4.. gps가 켜져 있는지 확인, 꺼져 있다면 gps화면으로 이동
5. 리스너 등록
5. 리스너 실행
6. 리스너 해제
*/

public class MainActivity extends AppCompatActivity implements FiveFragment.OnListFragmentInteractionListener {

    //탭 및 페이저 속성 정의
    final int TAB_COUNT = 5;
    OneFragment one;
    TwoFragment two;
    ThreeFragment three;
    FourFragment four;
    FiveFragment five;
    private int page_position = 0;
    ViewPager viewPager;
    private final int REQ_CODE = 100;
    private final int REQ_PERMISSION = 100; // 권한요청코드
    FragmentManager fragmentManager;



    Stack<Integer> pageStack = new Stack<>();
    boolean backPress = false;

    //위치정보 관리자
    private LocationManager manager;

    public LocationManager getLocationManager () {
        return  manager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //method traicing 시작
        Debug.startMethodTracing("trace_result");

        //fragmnet init
        one = new OneFragment();
        two = new TwoFragment();
        three = new ThreeFragment();
        four = new FourFragment();
        five = FiveFragment.newInstance(3); //미리 정해진 그리드 가로축 개수


        //tab layout 정의
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        //tab 생성 및 타이틀 입력
        tabLayout.addTab(tabLayout.newTab().setText("계산"));
        tabLayout.addTab(tabLayout.newTab().setText("변환"));
        tabLayout.addTab(tabLayout.newTab().setText("검색"));
        tabLayout.addTab(tabLayout.newTab().setText("위치"));
        tabLayout.addTab(tabLayout.newTab().setText("카메라"));

        //fragment 페이저 작성
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //아답터 세팅
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        //pagerListener - 페이저가 변경되었을 때 탭을 바꿔주는 리스너
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            //페이지 변경사항을 체크한다
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if(!backPress) {
                        pageStack.push(page_position);
                    }else {
                        backPress = false;
                    }
                    page_position = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        //탭이 변경되었을 때 페이저를 바꿔주는 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            checkPermission();
        }else {
            init();
        }
        Debug.stopMethodTracing();
    }




    //1. 권한 체크
    @TargetApi(Build.VERSION_CODES.M) //target 지정 annotation
    private void checkPermission() {
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            if( PermissionControl.checkPermission(this, REQ_PERMISSION) ){
                init();
            }
        }else{
            init();
        }
    }

    //2. 권한 체크 후 call back < 사용자가 확인 후 시스템이 호출하는 함수
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQ_PERMISSION){
            if( PermissionControl.onCheckResult(grantResults)){
                init();
            }else{
                Toast.makeText(this, "권한을 허용하지 않으시면 프로그램을 실행할 수 없습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void init () {
        //location manager 객체를 얻어온다
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //gps 센서가 켜져 있는지 확인
        //꺼져있다면 gps를 켜는 페이지로 이동
        if(!gpsCheck()) {
            //팝업창 만들기
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            //팝업창 제목
            alertDialog.setTitle("GPS 켜기");
            //팝업창 메시지
            alertDialog.setMessage("GPS가 꺼져있습니다. \r\n 설정창으로 이동하시겠습니까?");
                //yes 버튼 생성
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });
                //no 버튼생성
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
            //show 함수로 팝업창을 화면에 띄운다.
            alertDialog.show();
        }
    }

    //gps가 꺼져 있는지 확인
    private boolean gpsCheck() {
        //롤리팝 이상 버전에서는 locationManager로 gps 꺼짐 여부 체크
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            //롤리팝 이하 버전에서는 location providers allowed로 체크
        } else {
            String gps = android.provider.Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if (gps.matches(",*gps.*")) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

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
                case 4 : fragment = five; break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }
    }

    @Override
    public void onBackPressed() {
        switch (page_position) {
            case 2:
                //뒤로가기가 가능하면 아무런 동작을 하지 않는다
                if(three.goBack()) {
            }
            else{
                goBackStack();
            }
                break;
            //위의 조건에 해당되지 않는 모든 케이스는 아래 로직으로 처리한다
            default:
                goBackStack();
                break;
        }
    }

    private void goBackStack() {
        if(pageStack.size() < 1) {
            super.onBackPressed();
        } else {
            backPress = true;
            viewPager.setCurrentItem(pageStack.pop());
        }
    }

}