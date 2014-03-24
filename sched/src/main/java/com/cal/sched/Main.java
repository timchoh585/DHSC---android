package com.cal.sched;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tim on 3/23/2014.
 */
public class Main extends ActionBarActivity
{

    private String sched = "";
    private boolean schedAdd = false;
    private String[] classes = new String[9];
    private String[] teachers = new String[9];
    private String[] rooms = new String[9];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp);

        mainAct();
    }

    /**
     * used as main process that handles all of the processes on the main page
     */
    private void mainAct()
    {
        /********** sets date and cycle **********/
        getDate();
        getCycle();

        try
        {
            sched = getIntent().getStringExtra("userSched");
            if(!sched.equals(null))
                schedAdd = true;
        } catch(NullPointerException e)
        {
            sched = "";
        }

        if(schedAdd)
        {
            /********** makes the schedule **********/
            splitSched(sched);

            ListView lists = (ListView) findViewById(R.id.listView);
            myAdapter adapt = new myAdapter(this, classes, teachers);
            lists.setAdapter(adapt);
        }
        else
        {
            if(sched.equals(""))
            {
                Button add = (Button) findViewById(R.id.finish);
                try
                {
                    /********** makes button visible **********/
                    add.requestFocus();
                    add.setFocusable(true);
                    if(add.getVisibility() == View.INVISIBLE || add.getVisibility() == View.GONE)
                        add.setVisibility(View.VISIBLE);
                    /********** makes toast for no schedule **********/
                    Toast.makeText(this, "No Current Schedule", Toast.LENGTH_LONG).show();
                }catch (Exception e)
                {
                    Log.e("SCHEDULE", e.getMessage() + " error!");
                }
            }
            else
                splitSched(sched);
        }
    }

    /**
     * in the button click, it starts the new activity for the enterance of all of the classes
     * @param v for the view of the View
     */
    public void onClick(View v)
    {
        Intent myIntent = new Intent(Main.this, AddSched.class);
        Main.this.startActivity(myIntent);
    }

    /**
     * splits the string into ArrayLists of classes, teachers, and rooms
     * @param s string to be split
     */
    public void splitSched(String s)
    {
        String[] full = s.split(",");
        int indivT = 0;
        int indivR = 0;

        for(int i = 0; i < full.length; i++)
        {
            if (i < 9)
                classes[i] = full[i];
            else if (i < 18)
            {
                teachers[indivT] = full[i];
                indivT++;
            }
            else
            {
                rooms[indivR] = full[i];
                indivR++;
            }
        }
    }

    /**
     * returns today's date
     * @return string of day
     */
    public String getDate()
    {
        TextView day = (TextView) findViewById(R.id.day);
        TextView date = (TextView) findViewById(R.id.date);
        SimpleDateFormat dayForm = new SimpleDateFormat("EEEE");
        SimpleDateFormat dateForm = new SimpleDateFormat("MMM dd, yyyy");
        Date today = new Date();

        /********** check to see if day is accessible **********/
        try {
            day.setText(Html.fromHtml("<b><h1>" + dayForm.format(today) + "</h1></b>"));
            date.setText(Html.fromHtml("<fontsize=\"10\">" + dateForm.format(today) + "</font>"));
        } catch (Exception e) {
            Log.e("DATE", e.getMessage() + " Error!");
        }

        return day.toString();
    }

    /**
     * gets the cycle based on the day
     */
    public void getCycle()
    {
        TextView cycle = (TextView) findViewById(R.id.cycle);
        /********** sets cycle **********/
        if(getDate().equals("Monday"))
            cycle.setText(Html.fromHtml("<fontsize=\"6\"> 100 Day </font>"));
        else if(getDate().equals("Tuesday"))
            cycle.setText(Html.fromHtml("<fontsize=\"6\"> 78 Day </font>"));
        else if(getDate().equals("Wednesday"))
            cycle.setText(Html.fromHtml("<fontsize=\"6\"> 56 Day </font>"));
        else if(getDate().equals("Thursday"))
            cycle.setText(Html.fromHtml("<fontsize=\"6\"> 34 Day </font>"));
        else if(getDate().equals("Friday"))
            cycle.setText(Html.fromHtml("<fontsize=\"6\"> 12 Day </font>"));
        else
            cycle.setText(Html.fromHtml("<h1> 100 Day </h1>"));
    }
}