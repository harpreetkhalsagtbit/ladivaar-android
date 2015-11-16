package com.example.ladivaar.ladivaarapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Communicater{

    MainPage _mainPage;
    BaaniPage _baaniPage;
    FragmentManager manager;
    int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        ImageView _back = (ImageView)findViewById(R.id.imageView1);
        ImageView _next = (ImageView)findViewById(R.id.imageView2);
        _back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("before Index is", String.valueOf(index));
                if(index > 0) {
                    index--;
                    _baaniPage.changeData(index);
                    _baaniPage.createScreen();
                }
                Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_SHORT).show();
                Log.d("Index is", String.valueOf(index));
            }
        });

        _next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("before Index is", String.valueOf(index));
                Log.d("Index is", String.valueOf(index));
                index++;
                _baaniPage.changeData(index);
                Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_SHORT).show();
                _baaniPage.createScreen();

            }
        });

        final EditText edit_txt = (EditText) findViewById(R.id.searchbox);
        edit_txt.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.i("Submit", String.valueOf(edit_txt.getText()));
                    String s = String.valueOf(edit_txt.getText());
                    Integer k = Integer.valueOf(s);
                    _baaniPage.changeData(k - 1);
                    Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_SHORT).show();
                    _baaniPage.createScreen();
                    return true;
                }
                return false;
            }
        });

        manager = getFragmentManager();
        if(_baaniPage == null) {
//            _baaniPage = null;
            _baaniPage = new BaaniPage();
        }
        if(savedInstanceState == null) {
            _mainPage = new MainPage();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.my_layout, _mainPage, "MainFragment");
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();
        } else {
            Log.d("here","here");
            if(_baaniPage == null) {
                Log.d("yes null here","here");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void respond(int index) {
        Log.d("INside respond", "working");
        this.index = index;
        if(manager != null && _baaniPage != null) {
            Log.d("2 INside respond", "working");
            _baaniPage.changeData(index);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.my_layout, _baaniPage, "BaaniFragment");
            transaction.addToBackStack("BackToMainPage");
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();
        } else {
            Log.d("else respod", "working");
        }
    }
}
