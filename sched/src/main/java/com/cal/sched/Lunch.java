package com.cal.sched;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;


public class Lunch extends ActionBarActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lunch_info);

        //B LUNCH
        ToggleButton b100 = (ToggleButton) findViewById(R.id.b_lunch100);
        b100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isToggled)
            {
                if (isToggled)
                {
                    Log.i("info", "Button2 is on!");
                }
                else
                {
                    Log.i("info", "Button2 is off!");
                }
            }
        });

        ToggleButton b78 = (ToggleButton) findViewById(R.id.b_lunch78);
        b78.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isToggled)
            {
                if (isToggled)
                {
                    Log.i("info", "Button2 is on!");
                }
                else
                {
                    Log.i("info", "Button2 is off!");
                }
            }
        });

        ToggleButton b56 = (ToggleButton) findViewById(R.id.b_lunch56);
        b56.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isToggled)
            {
                if (isToggled)
                {
                    Log.i("info", "Button2 is on!");
                }
                else
                {
                    Log.i("info", "Button2 is off!");
                }
            }
        });

        ToggleButton b34 = (ToggleButton) findViewById(R.id.b_lunch34);
        b34.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isToggled)
            {
                if (isToggled)
                {
                    Log.i("info", "Button2 is on!");
                }
                else
                {
                    Log.i("info", "Button2 is off!");
                }
            }
        });

        ToggleButton b12 = (ToggleButton) findViewById(R.id.b_lunch12);
        b12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isToggled)
            {
                if (isToggled)
                {
                    Log.i("info", "Button2 is on!");
                }
                else
                {
                    Log.i("info", "Button2 is off!");
                }
            }
        });

        //B LUNCH
        ToggleButton d100 = (ToggleButton) findViewById(R.id.d_lunch100);
        d100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isToggled)
            {
                if (isToggled)
                {
                    Log.i("info", "Button2 is on!");
                }
                else
                {
                    Log.i("info", "Button2 is off!");
                }
            }
        });

        ToggleButton d78 = (ToggleButton) findViewById(R.id.d_lunch78);
        d78.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isToggled)
            {
                if (isToggled)
                {
                    Log.i("info", "Button2 is on!");
                }
                else
                {
                    Log.i("info", "Button2 is off!");
                }
            }
        });

        ToggleButton d56 = (ToggleButton) findViewById(R.id.d_lunch56);
        d56.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isToggled)
            {
                if (isToggled)
                {
                    Log.i("info", "Button2 is on!");
                }
                else
                {
                    Log.i("info", "Button2 is off!");
                }
            }
        });

        ToggleButton d34 = (ToggleButton) findViewById(R.id.d_lunch34);
        d34.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isToggled)
            {
                if (isToggled)
                {
                    Log.i("info", "Button2 is on!");
                }
                else
                {
                    Log.i("info", "Button2 is off!");
                }
            }
        });

        ToggleButton d12 = (ToggleButton) findViewById(R.id.d_lunch12);
        d12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isToggled)
            {
                if (isToggled)
                {
                    Log.i("info", "Button2 is on!");
                }
                else
                {
                    Log.i("info", "Button2 is off!");
                }
            }
        });
    }



    public void onClick(View v)
    {
    }
}