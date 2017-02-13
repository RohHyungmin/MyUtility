package com.hyugnmin.android.myutility;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ZoomIn extends AppCompatActivity {

    ImageView imageViewZoomIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_in);

        imageViewZoomIn = (ImageView) findViewById(R.id.imageViewZoomIn);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String str = bundle.getString("position");
        int pos = Integer.parseInt(str);
//        imageViewZoomIn.setImageURI(Uri.parse(MyItemRecyclerViewAdapter.datas.get(pos)));
//

    }
}
