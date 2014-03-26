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
import java.util.Date;

/**
 * Created by Tim on 3/23/2014.
 */
public class Main extends ActionBarActivity
{

    private String sched = "";
    private boolean schedAdd = false;
    private myAdapter adapt;

    //set arrays too big fix later

    //regular
    private String[] classes = new String[9];
    private String[] teachers = new String[9];
    private String[] rooms = new String[9];
    public String[] day100 = new String[]{"7:22-8:05", "8:10-8:52", "8:57-9:39", "9:44-10:26",
            "10:31-11:17", "11:22-12:08", " 12:58-1:40", "12:07-12:53", "1:45-2:27", "2:32-3:14"};
    public String[] cycleDay = new String[]{"7:22-8:05", "8:10-9:07", "9:29-10:26",
            "10:31-11:28", "11:37-12:34", " 1:15-2:12", "2:17-3:14"};

    //cycle
    private String[] cycleClass = new String[7];
    private String[] cycleTeacher = new String[7];
    private String[] cycleRoom = new String[7];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

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
            if(getDate().equals("Monday\n\n"))
                adapt = new myAdapter(this, classes, teachers, rooms, day100);
            else
            {
                if(getDate().equals("Tuesday\n\n"))
                    setCycleArray(78);
                else if(getDate().equals("Wednesday\n\n"))
                    setCycleArray(56);
                else if(getDate().equals("Thursday\n\n"))
                    setCycleArray(34);
                else if(getDate().equals("Friday\n\n"))
                    setCycleArray(12);
                adapt = new myAdapter(this, Get("classes"), Get("teachers"), Get("rooms"), cycleDay);
            }
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

        return day.getText().toString();
    }

    /**
     * gets the cycle based on the day
     */
    public void getCycle()
    {
        TextView cycle = (TextView) findViewById(R.id.cycle);
        String dc = getDate();
        /********** sets cycle **********/
        if(dc.equals("Monday\n\n"))
            cycle.setText(Html.fromHtml("h3> 100 Day </h3>"));
        else if(dc.equals("Tuesday\n\n"))
            cycle.setText(Html.fromHtml("<h3> 78 Day </h3>"));
        else if(dc.equals("Wednesday\n\n"))
            cycle.setText(Html.fromHtml("<h3> 56 Day </h3>"));
        else if(dc.equals("Thursday\n\n"))
            cycle.setText(Html.fromHtml("<h3> 34 Day </h3>"));
        else if(dc.equals("Friday\n\n"))
            cycle.setText(Html.fromHtml("<h3> 12 Day </h3>"));
        else
            cycle.setText(Html.fromHtml("<h3> 100 Day </h3>"));
    }

    /**
     *
     * @param s takes in String of what wants to be gotten
     * @return array of what ever the programmer wants
     */
    public String[] Get(String s)
    {
        if(s.equals("classes"))
            return cycleClass;
        else if(s.equals("teachers"))
            return cycleTeacher;
        else
            return cycleRoom;
    }

    public void setCycleArray(int a)
    {
        if(a == 78)
        {
            for(int i = 0; i < 7; i++)
            {
                cycleClass[i] = classes[i];
                cycleTeacher[i] = teachers[i];
                cycleRoom[i] = rooms[i];
            }
        }
        else if(a == 56)
        {
            cycleClass[0] = classes[0];
            cycleTeacher[0] = teachers[0];
            cycleRoom[0] = rooms[0];
            cycleClass[1] = classes[1];
            cycleTeacher[1] = teachers[1];
            cycleRoom[1] = rooms[1];
            cycleClass[2] = classes[2];
            cycleTeacher[2] = teachers[2];
            cycleRoom[2] = rooms[2];
            cycleClass[3] = classes[3];
            cycleTeacher[3] = teachers[3];
            cycleRoom[3] = rooms[3];
            cycleClass[4] = classes[4];
            cycleTeacher[4] = teachers[4];
            cycleRoom[4] = rooms[4];
            cycleClass[5] = classes[7];
            cycleTeacher[5] = teachers[7];
            cycleRoom[5] = rooms[7];
            cycleClass[6] = classes[8];
            cycleTeacher[6] = teachers[8];
            cycleRoom[6] = rooms[8];
        }
        else if(a == 34)
        {
            cycleClass[0] = classes[0];
            cycleTeacher[0] = teachers[0];
            cycleRoom[0] = rooms[0];
            cycleClass[1] = classes[1];
            cycleTeacher[1] = teachers[1];
            cycleRoom[1] = rooms[1];
            cycleClass[2] = classes[2];
            cycleTeacher[2] = teachers[2];
            cycleRoom[2] = rooms[2];
            cycleClass[3] = classes[5];
            cycleTeacher[3] = teachers[5];
            cycleRoom[3] = rooms[5];
            cycleClass[4] = classes[6];
            cycleTeacher[4] = teachers[6];
            cycleRoom[4] = rooms[6];
            cycleClass[5] = classes[7];
            cycleTeacher[5] = teachers[7];
            cycleRoom[5] = rooms[7];
            cycleClass[6] = classes[8];
            cycleTeacher[6] = teachers[8];
            cycleRoom[6] = rooms[8];
        }
        else if(a == 12)
        {
            for(int i = 2; i < 9; i++)
            {
                cycleClass[i] = classes[i];
                cycleTeacher[i] = teachers[i];
                cycleRoom[i] = rooms[i];
            }
        }
    }
}