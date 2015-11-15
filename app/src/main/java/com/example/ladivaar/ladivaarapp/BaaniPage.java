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

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Harpreet on 25-10-2015.
 */
public class BaaniPage extends Fragment implements View.OnTouchListener{
    int index;
    MyTextView text;
    View _v;
    String[] textArray = new String[0];
    String[] descriptions = new String[0];
    MyTextView textView = null;
    MyTextView tempTextView = null;
    BaaniPage _this = this;
    FlowLayout _container = null;
    String data = null;
    String tempData = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.baanipage, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d("Else", "OnViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("index", index);
    }

    public void createScreen() {
        ScrollView baaniContentLayout = (ScrollView) getView().findViewById(R.id.baaniContentLayout);
        // This code will always run on the UI thread, therefore is safe to modify UI elements.
        _container = (FlowLayout) getView().findViewById(R.id.flow);
        if (_container.getChildCount() > 0) {
            _container.removeAllViews();
        }
        descriptions = getResources().getStringArray(R.array.gurbani);
        data = descriptions[index];
        tempData  = data.replaceAll("[\\s\\n]+", "");
        tempTextView = new MyTextView(getActivity());
        tempTextView.setCustomFontTypeFace("NotoSansGurmukhi-Regular.ttf");
        tempTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
        tempTextView.setText(tempData);
        _container.addView(tempTextView);

//        baaniContentLayout.addView(_container);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // When you need to modify a UI element, do so on the UI thread.
                // 'getActivity()' is required as this is being ran from a Fragment.
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tempTextView = null;
                        if (_container != null && _container.getChildCount() > 0) {
                            _container.removeAllViews();
                            textArray = data.split("\\s+");
                            Log.d("ds", data);
                            for (int i = 0; i < textArray.length; i++) {
                                textView = new MyTextView(getActivity());
                                textView.setCustomFontTypeFace("NotoSansGurmukhi-Regular.ttf");
                                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
                                textView.setId(i);
                                textView.setText(textArray[i]);
                                _container.addView(textView);
                            }
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    // this code will be executed after 2 seconds
                                    for (int i = 0; i < textArray.length; i++) {
                                        textView = (MyTextView) getActivity().findViewById(i);
                                        if (textView != null) {
                                            textView.setOnTouchListener((View.OnTouchListener) _this);
                                        }
                                    }
                                }
                            }, 500);
                        }
                    }
                });
            }
        }, 800);
        baaniContentLayout.fullScroll(ScrollView.FOCUS_UP);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if(savedInstanceState == null) {
            Log.d("New Create","New Create");
        } else {
            index = savedInstanceState.getInt("index",0);
        }
        super.onActivityCreated(savedInstanceState);
        createScreen();
    }

    @Override
    public void onStop() {
        for (int i = 0; i < textArray.length; i++) {
            textView = (MyTextView) getActivity().findViewById(i);
            textView.setOnTouchListener(null);
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void changeData(int index) {
        this.index = index;
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
