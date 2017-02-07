package com.hyugnmin.android.myutility;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends Fragment implements View.OnClickListener {

    Button button_one;
    Button button_two;
    Button button_three;
    Button button_four;
    Button button_five;
    Button button_six;
    Button button_seven;
    Button button_eight;
    Button button_nine;
    Button button_zero;
    Button button_plus;
    Button button_minus;
    Button button_multiply;
    Button button_divide;

    Button button_cancel;
    Button button_calculate;

    TextView result;
    TextView preView;

    public OneFragment() {
        // Required empty public constructor
    }

    View view = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //뷰를 재사용하기 위해 Holder형태로 만들어준다
        if(view != null)
            return view;

        view =  inflater.inflate(R.layout.fragment_one, container, false);

        button_one = (Button) view.findViewById(R.id.button_no1);
        button_two = (Button) view.findViewById(R.id.button_no2);
        button_three = (Button) view.findViewById(R.id.button_no3);
        button_four = (Button) view.findViewById(R.id.button_no4);
        button_five = (Button) view.findViewById(R.id.button_no5);
        button_six = (Button) view.findViewById(R.id.button_no6);
        button_seven = (Button) view.findViewById(R.id.button_no7);
        button_eight = (Button) view.findViewById(R.id.button_no8);
        button_nine = (Button) view.findViewById(R.id.button_no9);
        button_zero = (Button) view.findViewById(R.id.button_no0);
        button_plus = (Button) view.findViewById(R.id.button_plus);
        button_minus = (Button) view.findViewById(R.id.button_minus);
        button_multiply = (Button) view.findViewById(R.id.button_multiply);
        button_divide = (Button) view.findViewById(R.id.button_divide);

        button_cancel = (Button) view.findViewById(R.id.button_ce);
        button_calculate = (Button) view.findViewById(R.id.button_calculate);

        result = (TextView) view.findViewById(R.id.result);
        preView = (TextView) view.findViewById(R.id.preView);

        button_one.setOnClickListener(this);
        button_two.setOnClickListener(this);
        button_three.setOnClickListener(this);
        button_four.setOnClickListener(this);
        button_five.setOnClickListener(this);
        button_six.setOnClickListener(this);
        button_seven.setOnClickListener(this);
        button_eight.setOnClickListener(this);
        button_nine.setOnClickListener(this);
        button_zero.setOnClickListener(this);
        button_plus.setOnClickListener(this);
        button_minus.setOnClickListener(this);
        button_multiply.setOnClickListener(this);
        button_divide.setOnClickListener(this);

        button_cancel.setOnClickListener(this);
        button_calculate.setOnClickListener(this);

        return  view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_no1:
                result.setText(result.getText() + "1");
                break;
            case R.id.button_no2:
                result.setText(result.getText() + "2");
                break;
            case R.id.button_no3:
                result.setText(result.getText() + "3");
                break;
            case R.id.button_no4:
                result.setText(result.getText() + "4");
                break;
            case R.id.button_no5:
                result.setText(result.getText() + "5");
                break;
            case R.id.button_no6:
                result.setText(result.getText() + "6");
                break;
            case R.id.button_no7:
                result.setText(result.getText() + "7");
                break;
            case R.id.button_no8:
                result.setText(result.getText() + "8");
                break;
            case R.id.button_no9:
                result.setText(result.getText() + "9");
                break;
            case R.id.button_no0:
                result.setText(result.getText() + "0");
                break;
            case R.id.button_plus:
                result.setText(result.getText() + "+");
                break;
            case R.id.button_minus:
                result.setText(result.getText() + "-");
                break;
            case R.id.button_multiply:
                result.setText(result.getText() + "*");
                break;
            case R.id.button_divide:
                result.setText(result.getText() + "/");
                break;
            case R.id.button_ce:
                result.setText("");
                preView.setText("Result : ");
                break;
            case R.id.button_calculate:
                String num = result.getText().toString();
                String finalResult = Double.toString(calculator(num));
                preView.setText(finalResult);
        }
    }

    public double calculator (String number ) {

        String firstNumber = number.replace(" ", "");
        String [] SecondNumber = firstNumber.split("(?<=[*/+-])|(?=[*/+-])");


        ArrayList<String> list = new ArrayList<>();

        for(String item : SecondNumber) {
            list.add(item);
        }

        int index = 0;
        int repeat = 0;

        for(repeat = 0; repeat<8; repeat++) {

            for(index =0; index < list.size(); index++) {

                if(list.get(index).equals("*")) {

                    double multiNum = Double.parseDouble(list.get(index-1));
                    double multiNumSecond = Double.parseDouble(list.get(index +1));
                    double multiResult = multiNum * multiNumSecond;
                    list.set(index, Double.toString(multiResult));
                    list.remove(index-1);
                    list.remove(index);
                }

            }
        }
        for(repeat = 0; repeat<8; repeat++) {
            for(index = 0;index < list.size(); index++) {

                if(list.get(index).equals("/")) {

                    double multiNum = Double.parseDouble(list.get(index-1));
                    double multiNumSecond = Double.parseDouble(list.get(index +1));
                    double multiResult = multiNum / multiNumSecond;
                    list.set(index, Double.toString(multiResult));
                    list.remove(index-1);
                    list.remove(index);
                }

            }
        }
        for(repeat = 0; repeat<8; repeat++) {
            for(index = 0;index < list.size(); index++) {

                if(list.get(index).equals("+")) {

                    double multiNum = Double.parseDouble(list.get(index-1));
                    double multiNumSecond = Double.parseDouble(list.get(index +1));
                    double multiResult = multiNum + multiNumSecond;
                    list.set(index, Double.toString(multiResult));
                    list.remove(index-1);
                    list.remove(index);

                }

            }
        }

        for(repeat = 0; repeat<8; repeat++) {
            for(index = 0;index < list.size(); index++) {

                if(list.get(index).equals("-")) {

                    double multiNum = Double.parseDouble(list.get(index-1));
                    double multiNumSecond = Double.parseDouble(list.get(index +1));
                    double multiResult = multiNum - multiNumSecond;
                    list.set(index, Double.toString(multiResult));
                    list.remove(index-1);
                    list.remove(index);
                }

            }
        }
        return Double.parseDouble(list.get(0));
    }
}
