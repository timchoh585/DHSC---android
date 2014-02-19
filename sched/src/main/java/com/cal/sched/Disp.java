package com.cal.sched;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.SimpleTimeZone;
import java.util.Date;

public class Disp extends ActionBarActivity {

    private String sched;
    public List<String> full = new  ArrayList<>();
    private ArrayList<String> sclass = new ArrayList<>();
    private ArrayList<String> steacher = new ArrayList<>();
    private ArrayList<String> sroom = new ArrayList<>();

    /**
     * checks bool supAdd if true, then rusn savedSched to save sched and then it runs create List
     * so that I can use them to create the ListView
     *
     * if the supAdd is false, checks if the sched is empty, then it checks for the sched length
     *       has a safety catch in case the first if statement doesn't work properly
     * creates the button and then allows the user to enter in a valid schedule
     * as well as creates a toast for the user to see that the sched is empty
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp);

        AddClass classes = new AddClass();

        sched = getIntent().getStringExtra("schedu");

        getDay();
        readSched();

        if(classes.supAdd)
        {
            saveSched(sched);
            createList(sched);

            ListView lists = (ListView) findViewById(R.id.listView);
            lists.setAdapter(new myAdapter(this, sclass, steacher));
        }
        else
        {
            classes.supAdd = true;
            if(sched == null || sched.length() == 0)
            {
                Button add = (Button) findViewById(R.id.finish);
                try
                {
                    add.requestFocus();
                    add.setFocusable(true);
                    if(add.getVisibility() == View.INVISIBLE || add.getVisibility() == View.GONE)
                        add.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "No Current Schedule", Toast.LENGTH_LONG).show();
                }catch (Exception e)
                {
                    Log.e("SCHEDULE", e.getMessage() + " error!");
                }
            }
            else
                saveSched(sched); createList(sched);
        }

        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new TakeSchedFragment())
                    .commit();
        }
    }

    /**
     * in the button click, it starts the new activity for the enterance of all of the classes
     * @param v for the view of the View
     */
    public void onClick(View v)
    {
        //Toast.makeText(this, "Clicked on Button", Toast.LENGTH_SHORT).show();
        Intent myIntent = new Intent(Disp.this, AddClass.class);
        //myIntent.putExtra("key", value); //Optional parameters
        Disp.this.startActivity(myIntent);
//        startActivity(new Intent("com.cal.sched.AddClass"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.disp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class TakeSchedFragment extends Fragment {

        public TakeSchedFragment() {
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_disp, container, false);
            return rootView;
        }
    }

    /**
     * used to return the day of the week that is used to determine what cycle day it is
     */
    public void getDay()
    {
        TextView day = (TextView) findViewById(R.id.day);
        TextView date = (TextView) findViewById(R.id.date);
        TextView cycle = (TextView) findViewById(R.id.cycle);
        SimpleDateFormat dayForm = new SimpleDateFormat("EEEE");
        SimpleDateFormat dateForm = new SimpleDateFormat("MMM dd, yyyy");
        Date today = new Date();

        try
        {
            day.setText(Html.fromHtml("<b><h1>" + dayForm.format(today) + "</h1></b>"));
            date.setText(Html.fromHtml("<fontsize=\"10\">" + dateForm.format(today) + "</font>"));
        }catch(Exception e)
        {
            Log.e("DATE", e.getMessage() + " Error!");
        }

        if(dayForm.format(today).equals("Monday"))
            cycle.setText(Html.fromHtml("<fontsize=\"6\"> 100 Day </font>"));
        else if(dayForm.format(today).equals("Tuesday"))
            cycle.setText(Html.fromHtml("<fontsize=\"6\"> 78 Day </font>"));
        else if(dayForm.format(today).equals("Wednesday"))
            cycle.setText(Html.fromHtml("<fontsize=\"6\"> 56 Day </font>"));
        else if(dayForm.format(today).equals("Thursday"))
            cycle.setText(Html.fromHtml("<fontsize=\"6\"> 34 Day </font>"));
        else if(dayForm.format(today).equals("Friday"))
            cycle.setText(Html.fromHtml("<fontsize=\"6\"> 12 Day </font>"));
        else
            cycle.setText(Html.fromHtml("<h1> 100 Day </h1>"));
    }

    /**
     * creates the seperate arraylists that the programmer can use for ease
     * split up by size of 9 as there are 9 "strings" in each case i.e. class, teacher, room
     * @param schedule user's schedule that the user puts in
     */
    private void createList(String schedule)
    {
        //ListView lv = (ListView) findViewById(R.id.listView);

        //ArrayList<String> sclass = new ArrayList<>();
        //ArrayList<String> steacher = new ArrayList<>();
        //ArrayList<String> sroom = new ArrayList<>();

        for(String f : schedule.split(","))
            full.add(f);

        for(int i = 0; i <= full.size(); i++)
        {
            if(i < 9)
                sclass.add(full.remove(0));
            else if(i > 9 && i < 18)
                steacher.add(full.remove(0));
            else
                sroom.add(full.remove(0));
        }
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.activity_disp, sclass);

        //lv.setAdapter(adapter);
    }

    /**
     * saving the sched as the user would put it in
     */
    public void saveSched(String s)
    {
        try
        {
            FileOutputStream fos = openFileOutput("sched.txt", MODE_PRIVATE);
            for(int i = 0; i < s.length(); i++)
                fos.write((byte)s.charAt(i));
        }catch(Exception e)
        {
            Log.e("FILEWRITE", e.getMessage() + " Error!");
        }
    }

    /**
     * reads through the string that would be called up during runtime that a file is saved
     * in the internal/external memory depending on which one i choose to do it with
     *
     * update: I chose to do interal to make it easier for the user
     */
    public void readSched()
    {
        StringBuilder sch = new StringBuilder();
        try
        {
            FileInputStream fis = this.openFileInput("sched.txt");
            int c;
            while((c = fis.read()) != -1)
                sch.append((char)c);
        }catch(Exception e)
        {
            Log.e("FILEREAD", e.getMessage() + " Error!");
        }
        sched = sch.toString();
    }
}