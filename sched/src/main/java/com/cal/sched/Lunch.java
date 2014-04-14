package com.cal.sched;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;


public class Lunch extends ActionBarActivity
{
    private String[] day100 = new String[]{"7:22-8:05", "8:10-8:52", "8:57-9:39", "9:44-10:26",
            "10:31-11:17", "11:22-12:08", " 12:58-1:40", "12:07-12:53", "1:45-2:27", "2:32-3:14"};
    private String[] cycleDay = new String[]{"7:22-8:05", "8:10-9:07", "9:12-9:24", "9:29-10:26",
            "10:31-11:28", "11:37-12:34", " 1:15-2:12", "2:17-3:14"};
    private String[] bLunchArray = new String[]{"false", "false", "false", "false", "false"};
    private String bLunch = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lunch_info);
    }

    /**
     * intents a bunch of strings that will be used in the main file
     * @param v move to the main.java for finishing the list
     */
    public void onClick(View v)
    {
        //B LUNCH
        /********** 100 **********/
        ToggleButton b100 = (ToggleButton) findViewById(R.id.b_lunch100);
        if (b100.getText().toString().equals("ON"))
        {
            day100[5] = "11:22-12:02";
            day100[6] = "12:07-12:53";
            bLunchArray[0] = "true";
        }
        else
        {
            day100[5] = "12:13-12:53";
            day100[6] = "11:22-12:08";
            bLunchArray[0] = "false";
        }

        /********** 78 **********/
        ToggleButton b78 = (ToggleButton) findViewById(R.id.b_lunch78);
        if (b78.getText().toString().equals("ON"))
        {
            cycleDay[5] = "11:33-12:08";
            cycleDay[6] = "12:13-1:10";
            bLunchArray[1] = "true";
        }
        else
        {
            cycleDay[6] = "12:39-1:10";
            cycleDay[5] = "11:37-12:34";
            bLunchArray[1] = "false";
        }

        /********** 56 **********/
        ToggleButton b56 = (ToggleButton) findViewById(R.id.b_lunch56);
        if (b56.getText().toString().equals("ON"))
        {
            cycleDay[5] = "11:33-12:08";
            cycleDay[6] = "12:13-1:10";
            bLunchArray[2] = "true";
        }
        else
        {
            cycleDay[6] = "12:39-1:10";
            cycleDay[5] = "11:37-12:34";
            bLunchArray[2] = "false";
        }

        /********** 34 **********/
        ToggleButton b34 = (ToggleButton) findViewById(R.id.b_lunch34);
        if (b34.getText().toString().equals("ON"))
        {
            cycleDay[5] = "11:33-12:08";
            cycleDay[6] = "12:13-1:10";
            bLunchArray[3] = "true";
        }
        else
        {
            cycleDay[6] = "12:39-1:10";
            cycleDay[5] = "11:37-12:34";
            bLunchArray[3] = "false";
        }

        /********** 12 **********/
        ToggleButton b12 = (ToggleButton) findViewById(R.id.b_lunch12);
        if (b12.getText().toString().equals("ON"))
        {
            cycleDay[5] = "11:33-12:08";
            cycleDay[6] = "12:13-1:10";
            bLunchArray[4] = "true";
        }
        else
        {
            cycleDay[6] = "12:39-1:10";
            cycleDay[5] = "11:37-12:34";
            bLunchArray[4] = "false";
        }

        for(int i = 0; i < 5; i++)
            bLunch += bLunchArray[i] + ",";

        String sched = getIntent().getStringExtra("userSched");
        Intent moveOnIntent = new Intent(Lunch.this, Main.class);
        moveOnIntent.putExtra("day1005", day100[5].toString());
        moveOnIntent.putExtra("day1006", day100[6].toString());
        moveOnIntent.putExtra("cycleDay4", cycleDay[5].toString());
        moveOnIntent.putExtra("cycleDay5", cycleDay[6].toString());
        moveOnIntent.putExtra("bLunch", bLunch);
        moveOnIntent.putExtra("userSched", sched);
        Lunch.this.startActivity(moveOnIntent);
    }
}