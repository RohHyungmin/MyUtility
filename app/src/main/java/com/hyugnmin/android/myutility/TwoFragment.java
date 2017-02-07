package com.hyugnmin.android.myutility;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TwoFragment extends Fragment {

    Button btnLength, btnArea, btnWeight, btnDo;
    LinearLayout layoutLength, layoutArea, layoutWeight;

    Spinner lengthSp1, lengthSp2, areaSp1, areaSp2, weightSp1, weightSp2;
    EditText inputText, resultText;
    TextView tvValue1,tvValue2,tvValue3,tvValue4,tvValue5;

    ArrayList<String> length = new ArrayList<>();
    ArrayList <String>area = new ArrayList<>();
    ArrayList <String>weight = new ArrayList<>();
    ArrayList <String>length2 = new ArrayList<>();
    ArrayList <String>area2 = new ArrayList<>();
    ArrayList <String>weight2 = new ArrayList<>();


    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            layoutLength.setVisibility(View.GONE);
            layoutArea.setVisibility(View.GONE);
            layoutWeight.setVisibility(View.GONE);
            switch(view.getId()) {
                case R.id.btnLength:
                    layoutLength.setVisibility(View.VISIBLE);
                    break;
                case R.id.btnArea:
                    layoutArea.setVisibility(View.VISIBLE);
                    break;
                case R.id.btnWeight:
                    layoutWeight.setVisibility(View.VISIBLE);
                    break;
                case R.id.btnDo :
                    doConvert();
                    break;

            }

        }
    };

    public TwoFragment() {
        // Required empty public constructor
    }

    View view = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view != null)
            return view;
        view =  inflater.inflate(R.layout.fragment_two, container, false);

        btnLength = (Button) view.findViewById(R.id.btnLength);
        btnArea = (Button) view.findViewById(R.id.btnArea);
        btnWeight = (Button) view.findViewById(R.id.btnWeight);
        btnDo = (Button) view.findViewById(R.id.btnDo);


        btnLength.setOnClickListener(clickListener);
        btnArea.setOnClickListener(clickListener);
        btnWeight.setOnClickListener(clickListener);
        btnDo.setOnClickListener(clickListener);


        ////////////////////////////////////////동적 전환 레이아웃

        layoutLength = (LinearLayout) view.findViewById(R.id.layoutLength);
        layoutArea = (LinearLayout) view.findViewById(R.id.layoutArea);
        layoutWeight = (LinearLayout) view.findViewById(R.id.layoutWeight);

        ///////////////////////////////////////// 스피너


        lengthSp1 = (Spinner) view.findViewById(R.id.lengthSp1);
        lengthSp2 = (Spinner) view.findViewById(R.id.lengthSp2);
        areaSp1 = (Spinner) view.findViewById(R.id.areaSp1);
        areaSp2 = (Spinner) view.findViewById(R.id.areaSp2);
        weightSp1 = (Spinner) view.findViewById(R.id.weightSp1);
        weightSp2 = (Spinner) view.findViewById(R.id.weightSp2);

        ///////////////////length
        length.add("mm");
        length.add("cm");
        length.add("m");
        length.add("km");
        length.add("인치(in)");

        ArrayAdapter<String> lengthAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, length);
        lengthSp1.setAdapter(lengthAdapter);
        lengthSp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        length2.add("mm");
        length2.add("cm");
        length2.add("m");
        length2.add("km");
        length2.add("인치(in)");

        ArrayAdapter<String> lengthAdapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, length2);
        lengthSp2.setAdapter(lengthAdapter2);
        lengthSp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        ////////////////////////////////////////////

        ///////////////////weight
        weight.add("mg");
        weight.add("g");
        weight.add("kg");
        weight.add("톤(t)");
        weight.add("킬로톤(kt)");

        ArrayAdapter<String> weightAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, weight);
        weightSp1.setAdapter(weightAdapter);
        weightSp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        weight2.add("mg");
        weight2.add("g");
        weight2.add("kg");
        weight2.add("톤(t)");
        weight2.add("킬로톤(kt)");

        ArrayAdapter<String> weightAdapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, weight2);
        weightSp2.setAdapter(weightAdapter2);
        weightSp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        ///////////////////Area
        area.add("m^2");
        area.add("아르(a)");
        area.add("km^2");
        area.add("제곱피트(ft^2)");
        area.add("제곱야드(yd^2)");

        ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, area);
        areaSp1.setAdapter(areaAdapter);
        areaSp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        area2.add("m^2");
        area2.add("아르(a)");
        area2.add("km^2");
        area2.add("제곱피트(ft^2)");
        area2.add("제곱야드(yd^2)");

        ArrayAdapter<String> areaAdapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, area2);
        areaSp2.setAdapter(areaAdapter2);
        areaSp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ////////////////////////////////////////////

        inputText = (EditText) view.findViewById(R.id.inputText);


        resultText = (EditText) view.findViewById(R.id.resultText);
        resultText.setEnabled(false);




        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable e) {
                Log.i("Input Value", "after=" + e.toString());


            }
        });

        return view;

    }

    public void doConvert() {

        lengthCalculate(Double.parseDouble(inputText.getText().toString()), lengthSp1.getSelectedItemPosition(), lengthSp2.getSelectedItemPosition());

    }

    public void lengthCalculate (double inputNum, int spinnerIndex1, int spinnerIndex2) {

        double mm=0, cm=0, m=0, km=0, in =0, mmCm, mmM, mmKm, mmIn, cmMm, cmM, cmKm, cmIn,mMm, mCm, mKm, mIn,kmMm, kmCm, kmM, kmIn,inMm, inCm, inM, inKm, mmMm, cmCm, mM, kmKm, inIn;

        mm = inputNum;
        cm = inputNum;
        km = inputNum;
        m = inputNum;
        in = inputNum;

        //mm가 선택된 경우
        mmCm = mm/10; mmM= mm/100; mmKm = mm/1000; mmIn = 0.0397*mm; mmMm = mm;
        //cm가 선택된 경우

        cmMm = cm*10; cmM= cm/10; cmKm = cm/100; cmIn = 0.393701*cm; cmCm = cm;
        //m가 선택된 경우
        mMm = m*100; mCm = m*10; mKm = m/10; mIn = 39.370079*m; mM = m;
        //km가 선택된 경우
        kmMm = km*1000; kmCm = km*100; kmM = km*10; kmIn = 39370.0787*km; kmKm = km;
        //in가 선택된 경우
        inMm = in*25.4; inCm = in*2.54; inM = in*0.0254; inKm = 0.000025*in; inIn = in;

        double numResult [][] = new double [5][5];
        double numPreResult [] = {mmMm, mmCm, mmM, mmKm, mmIn, cmMm, cmCm, cmM, cmKm, cmIn, mMm, mCm, mM, mKm, mIn,kmMm, kmCm, kmM, kmKm, kmIn,inMm, inCm, inM, inKm, inIn};
        int count = 0;
        for(int i = 0; i<5; i++) {

            for(int j = 0; j<5; j++) {

                numResult [i][j] = numPreResult[count];
                count++;
            }
        }


        for(int i = 0; i<5; i++) {
            if(i == spinnerIndex1) {

                tvValue1.setText(Double.toString(numResult[i][0]));
                tvValue2.setText(Double.toString(numResult[i][1]));
                tvValue3.setText(Double.toString(numResult[i][2]));
                tvValue4.setText(Double.toString(numResult[i][3]));
                tvValue5.setText(Double.toString(numResult[i][4]));

            }

            for(int j = 0; j<5; j++) {

                if(i == spinnerIndex1 && j == spinnerIndex2) {

                    resultText.setText(Double.toString(numResult[i][j]));
                }
            }
        }


    }



}
