package com.cal.sched;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;


public class Lunch extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lunch_info);

        ToggleButton c100 = (ToggleButton) findViewById(R.id.b_lunch100);
        c100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isToggled) {
                if (isToggled) {
                    Log.i("info", "Button2 is on!");
                } else {
                    Log.i("info", "Button2 is off!");
                }
            }
        });
    }



    public void onClick(View v)
    {
    }
}