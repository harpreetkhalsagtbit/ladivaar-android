package com.example.ladivaar.ladivaarapp;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by Harpreet on 25-10-2015.
 */
public class BaaniPage extends Fragment implements View.OnTouchListener{
    int index;
    MyTextView text;
    View _v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.baanipage, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d("Else","OnViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("index", index);
    }

    public void createScreen() {
        ScrollView baaniContentLayout = (ScrollView) getView().findViewById(R.id.baaniContentLayout);
        if(baaniContentLayout.getChildCount() > 0) {
            baaniContentLayout.removeAllViews();
        }
        FlowLayout _container = new FlowLayout(getActivity());
        _container.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        _container.setPadding(30,30,0,0);
        baaniContentLayout.addView(_container);
        String[] descriptions=getResources().getStringArray(R.array.gurbani);
        String data = descriptions[index];
        String[] textArray = data.split("\\s+");
        for( int i = 0; i < textArray.length; i++ )
        {
            MyTextView textView = new MyTextView(getActivity());
            textView.setCustomFontTypeFace("NotoSansGurmukhi-Regular.ttf");
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
            textView.setText(textArray[i]);
            _container.addView(textView);
            textView.setOnTouchListener((View.OnTouchListener) this);
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d("Activity Created","Created");
        if(savedInstanceState == null) {
            Log.d("New Create","New Create");
        } else {
            Log.d("Got index","New Create");
            index = savedInstanceState.getInt("index",0);
            Log.d("Got index", String.valueOf(index));
        }
        super.onActivityCreated(savedInstanceState);
        createScreen();
    }

    public void changeData(int index) {
        this.index = index;
        Log.d("CHange Data", String.valueOf(index));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(_v != null) {
            _v.setBackgroundColor(0x00000000);
        }
        v.setBackgroundColor(Color.BLUE);
        _v = v;
        return false;
    }
}
