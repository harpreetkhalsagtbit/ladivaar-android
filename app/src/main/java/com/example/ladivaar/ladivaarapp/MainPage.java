package com.example.ladivaar.ladivaarapp;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Harpreet on 25-10-2015.
 */
public class MainPage extends ListFragment implements AdapterView.OnItemClickListener{
    Communicater comm;
    int index;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mainpage, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.listOfBaanis, R.layout.simple_list_item);
        setListAdapter(adapter);
        comm = (Communicater) getActivity();
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("index", index);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.index = position;
        Toast.makeText(getActivity(), "Loading...", Toast.LENGTH_SHORT).show();
        comm.respond(position);
    }

    public String loadJSONFromAsset(String file) {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
