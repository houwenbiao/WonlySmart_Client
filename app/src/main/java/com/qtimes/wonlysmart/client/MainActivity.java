package com.qtimes.wonlysmart.client;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.qtimes.service.wonly.client.PersonStatusData;
import com.qtimes.service.wonly.client.QtimesServiceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        QtimesServiceManager.instance().connect(getApplicationContext());
        /*注册变化监听*/
        QtimesServiceManager.instance().setListener(new QtimesServiceManager.QtimesDoorServiceListener() {
            @Override
            public void onPersonNumberChange(int inNum, int outNum) {
                Log.i(TAG, "onPersonNumberChange, inNum:" + inNum + ", outNum:" + outNum);
            }

            @Override
            public void onPersonExistChange(boolean exist) {
                Log.i(TAG, "onPersonExistChange:" + exist);
            }

            @Override
            public void onServiceConnect() {

            }

            @Override
            public void onServiceDisconnect() {

            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        /*查询demo*/
        Log.e(TAG, "当前是否有人===" + QtimesServiceManager.instance().queryPersonExist());
        PersonStatusData data = QtimesServiceManager.instance().queryPersonStatus();
        Log.e(TAG, "当前人员数据===" + data.inNum + ", " + data.outNum);
        return super.dispatchTouchEvent(ev);
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
}
