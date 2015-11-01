package com.example.ladivaar.ladivaarapp;

import android.app.ListFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

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
        JSONArray m_jArry = null;
        String[] _tatkra = null;

        try {
            m_jArry = new JSONArray(loadJSONFromAsset("tatkara.json"));
            _tatkra = new String[m_jArry.length()];
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jsonobject = m_jArry.getJSONObject(i);
                _tatkra[i] = jsonobject.getString("name");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<String> lst = new ArrayList<String>(Arrays.asList(_tatkra));
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lst);
        adapter.setNotifyOnChange(true);
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
        Toast.makeText(getActivity(), "Item  " + position, Toast.LENGTH_SHORT).show();
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
