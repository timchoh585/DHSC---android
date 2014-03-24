package com.cal.sched;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;

public class Disp extends ActionBarActivity {

    private String sched = "";
    public List<String> full = new  ArrayList<>();
    private ArrayList<String> aclass = new ArrayList<>();
    private ArrayList<String> ateacher = new ArrayList<>();
    private ArrayList<String> aroom = new ArrayList<>();
    private String sclass = "";
    private String steacher = "";
    private String sroom = "";
    public static boolean supAdd;
    private boolean canSetDay = true;

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

        Intent intent = new Intent();

        try
        {
            sched = intent.getStringExtra("schedu");
        } catch(Exception e)
        {
            Log.e("SCHEDULE", e.getMessage() + " no saved/intended string.");
            canSetDay = false;
            sched = "";
        }

        getDay();
        setCycle();
        readSched();

        if(supAdd == true)
        {
            saveSched(sched);
            createList();

            //HashMap<String, String> hash= new HashMap<>();

            setCycle();

            ListView lists = (ListView) findViewById(R.id.listView);
            lists.setAdapter(new myAdapter(this,));
        }
        else
        {
            supAdd = true;
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
            {
                try
                {
                    saveSched(sched); createList();
                }catch(Exception e)
                {
                    Log.e("GET_DAY", e.getMessage() + " cannot get days bc list never saved.");
                }
            }
            try
            {
                saveSched(sched); createList();
            }catch(Exception e)
            {
                Log.e("GET_DAY", e.getMessage() + " cannot get days bc list never saved.");
            }
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
     * it will also set the arraylists that are needed for the adapter right here to that we can
     * find out which day we should output to the user when the user is running the app.
     */
    public String getDay()
    {
        TextView day = (TextView) findViewById(R.id.day);
        TextView date = (TextView) findViewById(R.id.date);
        SimpleDateFormat dayForm = new SimpleDateFormat("EEEE");
        SimpleDateFormat dateForm = new SimpleDateFormat("MMM dd, yyyy");
        Date today = new Date();

        try
        {
            day.setText(Html.fromHtml("<b><h1>" + dayForm.format(today) + "</h1></b>"));
            date.setText(Html.fromHtml("<fontsize=\"10\">" + dateForm.format(today) + "</font>"));
        }catch(Exception e)
        {
            Log.e("DATE", e.getMessage() + " Error getting date!");
        }

        return day.getText().toString();
    }

    public void setCycle()
    {
        TextView cycle = (TextView) findViewById(R.id.cycle);
        if(canSetDay)
        {
            if(getDay().equals("Monday"))
            {
                //tentative
                cycle.setText(Html.fromHtml("<fontsize='6'> 100 Day </font>"));
                set100();
            }
            else if(getDay().equals("Tuesday"))
            {
                //tentative
                cycle.setText(Html.fromHtml("<fontsize='6'> 78 Day </font>"));
                set78();
            }
            else if(getDay().equals("Wednesday"))
            {
                //tentative
                cycle.setText(Html.fromHtml("<fontsize='6'> 56 Day </font>"));
                set56();
            }
            else if(getDay().equals("Thursday"))
            {
                //tentative
                cycle.setText(Html.fromHtml("<fontsize='6'> 34 Day </font>"));
                set34();
            }
            else if(getDay().equals("Friday"))
            {
                //tentative
                cycle.setText(Html.fromHtml("<fontsize='6'> 12 Day </font>"));
                set12();
            }
            else {
                cycle.setText(Html.fromHtml("<fontsize='16'>" + "100 Day" + "</font>"));
                set100();
            }
        }
    }

    /**
     * creates the seperate arraylists that the programmer can use for ease
     * split up by size of 9 as there are 9 "strings" in each case i.e. class, teacher, room
     *
     * goes from 0 to the length of arraylist full
     * goes by 9 at a time starting from 0 - 8 (class), 9 - 17 (teacher), 18 - 27 (room)
     */
    private void createList()
    {
        for(int i = 0; i > full.size(); i--)
        {
            if(i < 9)
                aclass.add(full.get(i));
            else if(i >= 9 && i <= 17)
                ateacher.add(full.get(i));
            else
                aroom.add(full.get(i));
        }
    }

    /**
     * for 100 days
     * show all the periods
     */
    private void set100()
    {
    }

    /**
     * for 78 days
     * show all but period 7 and 8
     */
    private void set78()
    {
        aclass.remove(8);
        ateacher.remove(8);
        aroom.remove(8);
        aclass.remove(7);
        ateacher.remove(7);
        aroom.remove(7);
    }

    /**
     * for 56 days
     */
    private void set56()
    {
        aclass.remove(6);
        ateacher.remove(6);
        aroom.remove(6);
        aclass.remove(5);
        ateacher.remove(5);
        aroom.remove(5);
    }

    /**
     * for 34 days
     */
    private void set34()
    {
        aclass.remove(4);
        ateacher.remove(4);
        aroom.remove(4);
        aclass.remove(3);
        ateacher.remove(3);
        aroom.remove(3);
    }

    /**
     * for 12 days
     */
    private void set12()
    {
        aclass.remove(2);
        ateacher.remove(2);
        aroom.remove(2);
        aclass.remove(1);
        ateacher.remove(1);
        aroom.remove(1);
    }

    /**
     * remove earlybird classses
     */
    public void removeEB()
    {
        aclass.remove(0);
        ateacher.remove(0);
        aroom.remove(0);
    }

    /**
     * 56 late start day
     *
     * 4,7,8
     */
    public void first56()
    {
        set56();
        aclass.remove(3);
        ateacher.remove(3);
        aroom.remove(3);
        set12();
    }
    /**
     * 56 late start day
     *
     * 1,2,3
     */
    public void second56()
    {
        set78();
        set56();
        aclass.remove(4);
        ateacher.remove(4);
        aroom.remove(4);
    }

    /**
     * 34 late start
     *
     * 6,7,8
     */
    public void first34()
    {
        aclass.remove(5);
        ateacher.remove(5);
        aroom.remove(5);
        set34();
        set12();
    }

    /**
     * 34 late start
     *
     * 1,2,5
     */
    public void second34()
    {
        set78();
        aclass.remove(6);
        ateacher.remove(6);
        aroom.remove(6);
        set34();
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