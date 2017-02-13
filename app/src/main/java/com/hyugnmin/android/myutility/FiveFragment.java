package com.hyugnmin.android.myutility;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyugnmin.android.myutility.dummy.DummyContent;
import com.hyugnmin.android.myutility.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class FiveFragment extends Fragment {

    private final int REQ_CAMERA = 101; // 카메라 요청코드
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 2;
    List<String> datas = new ArrayList<>();
    Button btnGallery, btnCamera;
    ImageView imageView2;
    RecyclerView recyclerView;
    FiveFragment five;

    Uri fileUri = null;
    private static View view = null;
    MyItemRecyclerViewAdapter adapter;



    public FiveFragment() {
    }


    @SuppressWarnings("unused")
    public static FiveFragment newInstance(int columnCount) {
        FiveFragment fragment = new FiveFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        if(view != null)
//            return  view;

        Context context = getContext();
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        btnCamera = (Button) view.findViewById(R.id.btnCamera);
        btnGallery = (Button) view.findViewById(R.id.btnGallery);
        imageView2 = (ImageView) view.findViewById(R.id.imageView2);
        imageView2.setVisibility(View.GONE);

        btnCamera.setOnClickListener(clickListener);
        btnGallery.setOnClickListener(clickListener);

        datas = loadData(); //datas에 data 세팅

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        // Set the adapter
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            adapter = new MyItemRecyclerViewAdapter(getContext(), datas);
            recyclerView.setAdapter(adapter);


        return view;
    }

    public List<String> loadData(){

        List<String> datas = new ArrayList<>();

        // 폰에서 이미지를 가져온후 datas 에 세팅한다
        ContentResolver resolver = getContext().getContentResolver();
        // 1. 데이터 Uri 정의
        Uri target = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        // 2. projection 정의
        String projection[] = { MediaStore.Images.ImageColumns.DATA }; // 이미지 경로
        // 정렬 추가 - 날짜순 역순 정렬
        String order = MediaStore.Images.ImageColumns.DATE_ADDED +" DESC";

        // 3. 데이터 가져오기
        Cursor cursor = resolver.query(target, projection, null, null, order);
        if(cursor != null) {
            while (cursor.moveToNext()) {
                String uriString = cursor.getString(0);
                datas.add(uriString);
            }
            cursor.close();
        }
        return datas;
    }


    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            imageView2.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            switch (v.getId()) {
                case R.id.btnCamera :
                    imageView2.setVisibility(View.VISIBLE);
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
                        // 저장할 미디어 속성을 정의하는 클래스
                        ContentValues values = new ContentValues(1);
                        // 속성중에 파일의 종류를 정의
                        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
                        // 전역변수로 정의한 fileUri에 외부저장소 컨텐츠가 있는 Uri 를 임시로 생성해서 넣어준다.
                        fileUri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        // 위에서 생성한 fileUri를 사진저장공간으로 사용하겠다고 설정
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        // Uri에 읽기와 쓰기 권한을 시스템에 요청
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    }

                    startActivityForResult(intent, REQ_CAMERA);
                    break;

                case R.id.btnGallery :
                    datas = loadData();
                    adapter = new MyItemRecyclerViewAdapter(getContext(), datas);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    //intent = new Intent(getContext(), FiveFragment.class);
                    recyclerView.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case REQ_CAMERA :
                if (resultCode == RESULT_OK && fileUri != null) {
                    datas = loadData();
//                    adapter = new MyItemRecyclerViewAdapter(getContext(), datas);
//                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    Glide.with(this).load(fileUri).into(imageView2);
                }
                 else {
                    Toast.makeText(getContext(), "사진파일이 없습니다", Toast.LENGTH_LONG).show();
                }
                break;

        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {

        void onListFragmentInteraction(DummyItem item);
    }
}
