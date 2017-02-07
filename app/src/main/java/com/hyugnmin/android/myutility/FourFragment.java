package com.hyugnmin.android.myutility;


import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class FourFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    SupportMapFragment mapFragment;
    MainActivity activity;
    LocationManager manager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //메인액티비티를 받아서 처리
        activity = (MainActivity) context;
        manager = activity.getLocationManager();
    }

    public FourFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        //리스너 등록
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
        }
        //gps 제공자의 정보가 바뀌면 콜백하도록 리스너 등록
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, locationListener);
                            //등록할 위치 제공자 //업데이트 통지사이의 최소 간격(milisecond) //업데이트 통지사이의 최소 변경거리(m)
        //정보제공자로 네트워크 프로바이더등록
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 10, locationListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        //리스너 해제
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        manager.removeUpdates(locationListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_four, container, false);

        //fragment에서 맵뷰를 호출하는 방법
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        return  view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng seoul = new LatLng(37.516006, 127.019361);
        map.addMarker(new MarkerOptions().position(seoul).title("Sinsadong in Seoul"));
        map.moveCamera(CameraUpdateFactory.newLatLng(seoul));
    }

    android.location.LocationListener locationListener = new android.location.LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double longitude = location.getLongitude(); //경도
            double latitude = location.getLatitude(); //위도
            double altitude = location.getAltitude(); //고도
            float accuracy = location.getAccuracy();  //정확도
            String provider = location.getProvider(); //위치제공자

            //내 위치
            LatLng myPosition = new LatLng(latitude, longitude);
            //내 위치에 마커 찍기
            map.addMarker(new MarkerOptions().position(myPosition).title("Here I am"));
            //화면을 내 위치로 이동시키는 함수
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 18)); //내위치,  줌레벨
        }

        @Override //provider의 상태변경시 호출(gps 센서..네트워크..)
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override//gps를 사용할 수 없었다가 사용 가능해졌을 때
        public void onProviderEnabled(String provider) {

        }

        @Override//gps를 사용할 수 없을 때
        public void onProviderDisabled(String provider) {

        }
    };
}
