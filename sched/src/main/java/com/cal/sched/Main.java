package com.cal.sched;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Tim on 3/23/2014.
 */
public class Main extends ActionBarActivity
{

    private String sched = "";
    private boolean schedAdd = false;
    private myAdapter adapt;

    //set schedule
    private String[] classes = new String[10];
    private String[] teachers = new String[10];
    private String[] rooms = new String[10];

    //regular schedule
    private String[] classes100 = new String[10];
    private String[] teachers100 = new String[10];
    private String[] rooms100 = new String[10];

    //cycle
    private String[] cycleClass = new String[9];
    private String[] cycleTeacher = new String[9];
    private String[] cycleRoom = new String[9];

    private String[] lateClass = new String[4];
    private String[] lateTeacher = new String[4];
    private String[] lateRoom = new String[4];

    //times
    private String[] day100 = new String[]{"7:22-8:05", "8:10-8:52", "8:57-9:39", "9:44-10:26",
        "10:31-11:17", "11:22-12:08", "12:13-12:53", " 12:58-1:40", "12:07-12:53", "1:45-2:27",
            "2:32-3:14"};
    private String[] cycleDay = new String[]{"7:22-8:05", "8:10-9:07", "9:12-9:24", "9:29-10:26",
            "10:31-11:28", "11:37-12:34", "12:39-1:10", " 1:15-2:12", "2:17-3:14"};
    private String[] lateStart = new String[]{"11:15-12:11", "12:16-1:12", "1:17-2:13",
            "2:18-3:14"};
    private String[] psae = new String[]{"", "12:30-1:22", "1:27-2:18", "2:23-3:14"};
    private String currentTime = "00:00";

    //current class
    private int[] c100ClassesImages = new int[]{R.drawable.gray, R.drawable.gray,
            R.drawable.gray,
            R.drawable.gray, R.drawable.gray, R.drawable.gray, R.drawable.gray,
            R.drawable.gray, R.drawable.gray,R.drawable.gray, R.drawable.gray};
    private int[] cycleClassesImages = new int[]{R.drawable.gray, R.drawable.gray,
            R.drawable.gray,
            R.drawable.gray, R.drawable.gray, R.drawable.gray, R.drawable.gray,
            R.drawable.gray, R.drawable.gray};
    private int[] lateStartsImages = new int[]{R.drawable.gray, R.drawable.gray, R.drawable.gray,
            R.drawable.gray};

    //lunch
    private Boolean[] bLunchBool = new Boolean[5];
    private String bLunches = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        currentTime = getCurrentTime();

        mainAct();
    }

    public void onResume()
    {
        super.onPause();
        currentTime = getCurrentTime();
        setClassImages(currentTime);

        mainAct();
    }

    /**
     * used as main process that handles all of the processes on the main page
     */
    public void mainAct()
    {
        /********** sets date and cycle **********/
        getDate();
        getCycle();

        try
        {
            day100[5] = getIntent().getStringExtra("day1005");
            day100[6] = getIntent().getStringExtra("day1006");
            cycleDay[5] = getIntent().getStringExtra("cycleDay4");
            cycleDay[6] = getIntent().getStringExtra("cycleDay5");
            bLunches = getIntent().getStringExtra("bLunch");
            sched = getIntent().getStringExtra("userSched");
            if(sched != null)
            {
                schedAdd = true;

                String[] full = bLunches.split(",");
                for(int i = 0; i < 5; i++)
                {
                    if (full[i].equals("true"))
                        bLunchBool[i] = true;
                    else
                        bLunchBool[i] = false;
                }
            }
            else
            {
                try
                {
                    sched = readSched("schedule");
                    bLunches = readSched("bLunch");

                    String[] full = bLunches.split(",");
                    for(int i = 0; i < 5; i++)
                    {
                        if (full[i].equals("true"))
                            bLunchBool[i] = true;
                        else
                            bLunchBool[i] = false;
                    }

                    if(sched != null)
                    {
                        schedAdd = true;
                        for(int i = 0; i < 5; i++)
                        {
                            if(i == 0 && bLunchBool[0])
                            {
                                day100[5] = "11:22-12:02";
                            }
                            else if(bLunchBool[i])
                            {
                                cycleDay[5] = "11:33-12:08";
                                cycleDay[6] = "12:13-1:10";
                            }

                            /********** preset values of D lunch **********/
                            else
                            {
                                day100[5] = "12:07-12:53";
                                day100[6] = "11:22-12:02";
                                cycleDay[6] = "12:13-1:10";
                                cycleDay[5] = "11:33-12:08";
                            }
                        }
                    }

                } catch(Exception e)
                {
                    sched = "";
                }
            }
        } catch(NullPointerException e)
        {
            sched = "";
        }

        if(schedAdd)
        {
            /********** makes the schedule **********/
            splitSched(sched);

            ListView lists = (ListView) findViewById(R.id.listView);
            if(getCycle().equals("100"))
            { setCycleArray(100); adapt = new myAdapter(this, classes100, teachers100, rooms100,
                    day100, c100ClassesImages); }
            else
            {
                if(getCycle().equals("78"))
                {
                    setCycleArray(78);
                    adapt = new myAdapter(this, Get("classes"), Get("teachers"), Get("rooms"),
                            Get("cycleDay"), cycleClassesImages);
                }
                else if(getCycle().equals("56"))
                {
                    setCycleArray(56);
                    adapt = new myAdapter(this, Get("classes"), Get("teachers"), Get("rooms"),
                            Get("cycleDay"), cycleClassesImages);
                }
                else if(getCycle().equals("34"))
                {
                    setCycleArray(34);
                    adapt = new myAdapter(this, Get("classes"), Get("teachers"), Get("rooms"),
                            Get("cycleDay"), cycleClassesImages);
                }
                else if(getCycle().equals("12"))
                {
                    setCycleArray(12);
                    adapt = new myAdapter(this, Get("classes"), Get("teachers"), Get("rooms"),
                            Get("cycleDay"), cycleClassesImages);
                }
                else if(getCycle().equals("eb123"))
                {
                    setCycleArray(0123);
                    adapt = new myAdapter(this, Get("lclasses"), Get("lteachers"), Get("lrooms"),
                            Get("lateStart"), lateStartsImages);
                }
                else if(getCycle().equals("478"))
                {
                    setCycleArray(478);
                    adapt = new myAdapter(this, Get("lclasses"), Get("lteachers"), Get("lrooms"),
                            Get("lateStart"), lateStartsImages);
                }
                else if(getCycle().equals("eb125"))
                {
                    setCycleArray(0125);
                    adapt = new myAdapter(this, Get("lclasses"), Get("lteachers"), Get("lrooms"),
                            Get("lateStart"), lateStartsImages);
                }
                else if(getCycle().equals("678"))
                {
                    setCycleArray(678);
                    adapt = new myAdapter(this, Get("lclasses"), Get("lteachers"), Get("lrooms"),
                            Get("psae"), lateStartsImages);
                }
                else
                {
                    setCycleArray(000);
                    adapt = new myAdapter(this, Get("classes"), Get("teachers"), Get("rooms"),
                            Get("cycleDay"), lateStartsImages);
                }
                currentTime = getCurrentTime();
                setClassImages(currentTime);
            }

            Button edit = (Button) findViewById(R.id.edit);
            try
            {
                /********** makes edit schedule button visible **********/
                edit.requestFocus();
                edit.setFocusable(true);
                if(edit.getVisibility() == View.INVISIBLE || edit.getVisibility() == View.GONE)
                    edit.setVisibility(View.VISIBLE);
            }catch (Exception e)
            {
                Log.e("SCHEDULE", e.getMessage() + " error!");
            }

            lists.setAdapter(adapt);
        }
        else
        {
            if(sched.equals(""))
            {
                Button add = (Button) findViewById(R.id.add);
                try
                {
                    /********** makes add schedule button visible **********/
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
            {
                splitSched(sched);
            }
        }
    }

    /**
     * in the button click, it starts the new activity for the enterance of all of the classes
     * @param v for the view of the View
     */
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.add:
                Intent addd = new Intent(Main.this, AddSched.class);
                Main.this.startActivity(addd);
                break;
            case R.id.edit:
                Intent editt = new Intent(Main.this, AddSched.class);
                editt.putExtra("editSched", sched);
                Main.this.startActivity(editt);
                break;
        }
    }

    /**
     * splits the string into ArrayLists of classes, teachers, and rooms
     * @param s string to be split
     */
    public void splitSched(String s)
    {
        saveSched(s);

        String[] full = s.split(",");
        int indivT = 0;
        int indivR = 0;

        for(int i = 0; i < full.length; i++)
        {
            if (i < 10)
                classes[i] = full[i];
            else if (i < 20)
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
     * set the image array for the current class based on time
     * @param s
     */
    public void setClassImages(String s)
    {
        if(readCal().equals(" 100"))
        {
            for(int i = 0; i < 11; i++)
            {
                String[] times = day100[i].split("-");
                if(s.length() == 4)
                    s = "0" + s;
                if(times[0].length() == 4)
                    times[0] = "0" + times[0];
                if(times[1].length() == 4)
                    times[1] = "0" + times[1];
                if(s.compareTo(times[0]) >= 0 && s.compareTo(times[1]) <= 0)
                {
                    for(int j = 0; j < 11; j++)
                        c100ClassesImages[j] = R.drawable.gray;
                    c100ClassesImages[i] = R.drawable.arrow;
                    break;
                }
                else if(i == 6)
                {
                    if((s.compareTo(times[0]) >= 0 && s.compareTo("12:59") <= 0) || (s.compareTo
                            ("01:00") >= 0 && s.compareTo(times[1]) <= 0))
                    {
                        for(int j = 0; j < 9; j++)
                            c100ClassesImages[j] = R.drawable.gray;
                        c100ClassesImages[i] = R.drawable.arrow;
                    }
                    break;
                }
            }
        }
        else if(readCal().equals(" 78") || readCal().equals(" 56") || readCal().equals(" 34") ||
                readCal().equals(" 12"))
        {
            for(int i = 0; i < 9; i++)
            {
                String[] times = cycleDay[i].split("-");
                if(s.length() == 4)
                    s = "0" + s;
                if(times[0].length() == 4)
                    times[0] = "0" + times[0];
                if(times[1].length() == 4)
                    times[1] = "0" + times[1];
                if(s.compareTo(times[0]) > 0 && s.compareTo(times[1]) < 0)
                {
                    for(int j = 0; j < 9; j++)
                        cycleClassesImages[j] = R.drawable.gray;
                    cycleClassesImages[i] = R.drawable.arrow;
                    break;
                }
                else if(i == 6)
                {
                    if((s.compareTo(times[0]) >= 0 && s.compareTo("12:59") <= 0) || (s.compareTo
                        ("01:00") >= 0 && s.compareTo(times[1]) <= 0))
                {
                    for(int j = 0; j < 9; j++)
                        cycleClassesImages[j] = R.drawable.gray;
                    cycleClassesImages[i] = R.drawable.arrow;
                }
                    break;
                }
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
        SimpleDateFormat todayForm = new SimpleDateFormat("MMM dd");
        Date today = new Date();

        /********** check to see if day is accessible **********/
        try {
            day.setText(Html.fromHtml("<b><h1>" + dayForm.format(today) + "</h1></b>"));
            date.setText(Html.fromHtml("<fontsize=\"10\">" + dateForm.format(today) + "</font>"));
        } catch (Exception e) {
            Log.e("DATE", e.getMessage() + " Error!");
        }

        return todayForm.format(today).toString();
    }

    public String getCurrentTime()
    {
        Time time = new Time(Time.getCurrentTimezone());
        time.setToNow();
        SimpleDateFormat sdfTime = new SimpleDateFormat("h:mm");
        return sdfTime.format(Calendar.getInstance().getTime());
    }

    /**
     * gets the cycle based on the day
     */
    public String getCycle()
    {
        TextView cycle = (TextView) findViewById(R.id.cycle);
        String cyclee = "";
        /********** sets cycle **********/

        if(readCal().equals(" 100"))
        { cycle.setText(Html.fromHtml("<h3> 100 Day </h3>")); cyclee = "100"; }
        else if(readCal().equals(" 78"))
        { cycle.setText(Html.fromHtml("<h3> 78 Day </h3>")); cyclee = "78"; }
        else if(readCal().equals(" 56"))
        { cycle.setText(Html.fromHtml("<h3> 56 Day </h3>")); cyclee = "56"; }
        else if(readCal().equals(" 34"))
        { cycle.setText(Html.fromHtml("<h3> 34 Day </h3>")); cyclee = "34"; }
        else if(readCal().equals(" 12"))
        { cycle.setText(Html.fromHtml("<h3> 12 Day </h3>")); cyclee = "12"; }
        else if(readCal().equals(" 0123"))
        { cycle.setText(Html.fromHtml("<h3> EB123 Day </h3>")); cyclee = "eb123"; }
        else if(readCal().equals(" 478"))
        { cycle.setText(Html.fromHtml("<h3> 478 Day </h3>")); cyclee = "478"; }
        else if(readCal().equals(" 0125"))
        { cycle.setText(Html.fromHtml("<h3> EB125 Day </h3>")); cyclee = "eb125"; }
        else if(readCal().equals(" 678"))
        { cycle.setText(Html.fromHtml("<h3> 678 Day </h3>")); cyclee = "678"; }
        else
        { cycle.setText(Html.fromHtml("<h3> No\nSchool </h3>")); cyclee = "No School"; }

        return cyclee;
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
        else if(s.equals("rooms"))
            return cycleRoom;
        else if(s.equals("lclasses"))
            return lateClass;
        else if(s.equals("lteachers"))
            return lateTeacher;
        else if(s.equals("lrooms"))
            return lateRoom;
        else if(s.equals("100day"))
            return day100;
        else if(s.equals("cycleDay"))
            return cycleDay;
        else if(s.equals("lateStart"))
            return lateStart;
        else
            return psae;
    }

    public void setCycleArray(int a)
    {
        if(a == 100)
        {
            classes100[0] = classes[0];
            teachers100[0] = teachers[0];
            rooms100[0] = rooms[0];
            classes100[1] = classes[1];
            teachers100[1] = teachers[1];
            rooms100[1] = rooms[1];
            classes100[2] = classes[2];
            teachers100[2] = teachers[2];
            rooms100[2] = rooms[2];
            classes100[3] = classes[3];
            teachers100[3] = teachers[3];
            rooms100[3] = rooms[3];
            classes100[4] = classes[4];
            teachers100[4] = teachers[4];
            rooms100[4] = rooms[4];
            if(bLunchBool[0])
            {
                classes100[5] = "Lunch";
                teachers100[5] = "-----";
                rooms100[5] = "-----";
                classes100[6] = classes[5];
                teachers100[6] = teachers[5];
                rooms100[6] = rooms[5];
            }
            else
            {
                classes100[5] = classes[5];
                teachers100[5] = teachers[5];
                rooms100[5] = rooms[5];
                classes100[6] = "Lunch";
                teachers100[6] = "-----";
                rooms100[6] = "-----";
            }
            classes100[7] = classes[6];
            teachers100[7] = teachers[6];
            rooms100[7] = rooms[6];
            classes100[8] = classes[7];
            teachers100[8] = teachers[7];
            rooms100[8] = rooms[7];
            classes100[9] = classes[8];
            teachers100[9] = teachers[8];
            rooms100[9] = rooms[8];
        }
        else if(a == 78)
        {
            cycleClass[0] = classes[0];
            cycleTeacher[0] = teachers[0];
            cycleRoom[0] = rooms[0];
            cycleClass[1] = classes[1];
            cycleTeacher[1] = teachers[1];
            cycleRoom[1] = rooms[1];
            cycleClass[2] = classes[9];
            cycleTeacher[2] = teachers[9];
            cycleRoom[2] = rooms[9];
            cycleClass[3] = classes[2];
            cycleTeacher[3] = teachers[2];
            cycleRoom[3] = rooms[2];
            cycleClass[4] = classes[3];
            cycleTeacher[4] = teachers[3];
            cycleRoom[4] = rooms[3];
            if(bLunchBool[1])
            {
                cycleClass[5] = "Lunch";
                cycleTeacher[5] = "-----";
                cycleRoom[5] = "-----";
                cycleClass[6] = classes[4];
                cycleTeacher[6] = teachers[4];
                cycleRoom[6] = rooms[4];
            }
            else
            {
                cycleRoom[6] = rooms[4];
                cycleClass[5] = classes[4];
                cycleTeacher[5] = teachers[4];
                cycleRoom[5] = rooms[4];
                cycleClass[6] = "Lunch";
                cycleTeacher[6] = "-----";
                cycleRoom[6] = "-----";
            }
            cycleClass[7] = classes[5];
            cycleTeacher[7] = teachers[5];
            cycleRoom[7] = rooms[5];
            cycleClass[8] = classes[6];
            cycleTeacher[8] = teachers[6];
            cycleRoom[8] = rooms[6];
        }
        else if(a == 56)
        {
            cycleClass[0] = classes[0];
            cycleTeacher[0] = teachers[0];
            cycleRoom[0] = rooms[0];
            cycleClass[1] = classes[1];
            cycleTeacher[1] = teachers[1];
            cycleRoom[1] = rooms[1];
            cycleClass[2] = classes[9];
            cycleTeacher[2] = teachers[9];
            cycleRoom[2] = rooms[9];
            cycleClass[3] = classes[2];
            cycleTeacher[3] = teachers[2];
            cycleRoom[3] = rooms[2];
            cycleClass[4] = classes[3];
            cycleTeacher[4] = teachers[3];
            cycleRoom[4] = rooms[3];
            if(bLunchBool[2]) {
                cycleClass[5] = "Lunch";
                cycleTeacher[5] = "-----";
                cycleRoom[5] = "-----";
                cycleClass[6] = classes[4];
                cycleTeacher[6] = teachers[4];
                cycleRoom[6] = rooms[4];
            }
            else
            {
                cycleClass[5] = classes[4];
                cycleTeacher[5] = teachers[4];
                cycleRoom[5] = rooms[4];
                cycleClass[6] = "Lunch";
                cycleTeacher[6] = "-----";
                cycleRoom[6] = "-----";
            }
            cycleClass[7] = classes[7];
            cycleTeacher[7] = teachers[7];
            cycleRoom[7] = rooms[7];
            cycleClass[8] = classes[8];
            cycleTeacher[8] = teachers[8];
            cycleRoom[8] = rooms[8];
        }
        else if(a == 34)
        {
            cycleClass[0] = classes[0];
            cycleTeacher[0] = teachers[0];
            cycleRoom[0] = rooms[0];
            cycleClass[1] = classes[1];
            cycleTeacher[1] = teachers[1];
            cycleRoom[1] = rooms[1];
            cycleClass[2] = classes[9];
            cycleTeacher[2] = teachers[9];
            cycleRoom[2] = rooms[9];
            cycleClass[3] = classes[2];
            cycleTeacher[3] = teachers[2];
            cycleRoom[3] = rooms[2];
            cycleClass[4] = classes[5];
            cycleTeacher[4] = teachers[5];
            cycleRoom[4] = rooms[5];
            if(bLunchBool[3])
            {
                cycleClass[5] = "Lunch";
                cycleTeacher[5] = "-----";
                cycleRoom[5] = "-----";
                cycleClass[6] = classes[6];
                cycleTeacher[6] = teachers[6];
                cycleRoom[6] = rooms[6];
            }
            else
            {
                cycleClass[5] = classes[6];
                cycleTeacher[5] = teachers[6];
                cycleRoom[5] = rooms[6];
                cycleClass[6] = "Lunch";
                cycleTeacher[6] = "-----";
                cycleRoom[6] = "-----";
            }
            cycleClass[7] = classes[7];
            cycleTeacher[7] = teachers[7];
            cycleRoom[7] = rooms[7];
            cycleClass[8] = classes[8];
            cycleTeacher[8] = teachers[8];
            cycleRoom[8] = rooms[8];
        }
        else if(a == 12)
        {
            cycleClass[0] = classes[0];
            cycleTeacher[0] = teachers[0];
            cycleRoom[0] = rooms[0];
            cycleClass[1] = classes[3];
            cycleTeacher[1] = teachers[3];
            cycleRoom[1] = rooms[3];
            cycleClass[2] = classes[9];
            cycleTeacher[2] = teachers[9];
            cycleRoom[2] = rooms[9];
            cycleClass[3] = classes[4];
            cycleTeacher[3] = teachers[4];
            cycleRoom[3] = rooms[4];
            cycleClass[4] = rooms[5];
            cycleTeacher[4] = teachers[5];
            cycleRoom[4] = rooms[5];
            if(bLunchBool[4])
            {
                cycleClass[5] = "Lunch";
                cycleTeacher[5] = "-----";
                cycleRoom[5] = "-----";
                cycleClass[6] = classes[6];
                cycleTeacher[6] = teachers[6];
                cycleRoom[6] = rooms[6];
            }
            else
            {
                cycleClass[5] = classes[6];
                cycleTeacher[5] = teachers[6];
                cycleRoom[5] = rooms[6];
                cycleClass[6] = "Lunch";
                cycleTeacher[6] = "-----";
                cycleRoom[6] = "-----";
            }
            cycleClass[7] = classes[7];
            cycleTeacher[7] = teachers[7];
            cycleRoom[7] = rooms[7];
            cycleClass[8] = classes[8];
            cycleTeacher[8] = teachers[8];
            cycleRoom[8] = rooms[8];
        }
        else if(a == 0123)
        {
            lateClass[0] = classes[0];
            lateTeacher[0] = teachers[0];
            lateRoom[0] = rooms[0];
            lateClass[1] = classes[1];
            lateTeacher[1] = teachers[1];
            lateRoom[1] = rooms[1];
            lateClass[2] = classes[2];
            lateTeacher[2] = teachers[2];
            lateRoom[2] = rooms[2];
            lateClass[3] = classes[3];
            lateTeacher[3] = teachers[3];
            lateRoom[3] = rooms[3];
        }
        else if(a == 478)
        {
            lateClass[0] = "3-Sci";
            lateTeacher[0] = "";
            lateRoom[0] = "";
            lateClass[1] = classes[4];
            lateTeacher[1] = teachers[4];
            lateRoom[1] = rooms[4];
            lateClass[2] = classes[7];
            lateTeacher[2] = teachers[7];
            lateRoom[2] = rooms[7];
            lateClass[3] = classes[8];
            lateTeacher[3] = teachers[8];
            lateRoom[3] = rooms[8];
        }
        else if(a == 0125)
        {
            lateClass[0] = classes[0];
            lateTeacher[0] = teachers[0];
            lateRoom[0] = rooms[0];
            lateClass[1] = classes[1];
            lateTeacher[1] = teachers[1];
            lateRoom[1] = rooms[1];
            lateClass[2] = classes[2];
            lateTeacher[2] = teachers[2];
            lateRoom[2] = rooms[2];
            lateClass[3] = classes[5];
            lateTeacher[3] = teachers[5];
            lateRoom[3] = rooms[5];
        }
        else if(a == 678)
        {
            lateClass[0] = "5-Sci";
            lateTeacher[0] = "";
            lateRoom[0] = "";
            lateClass[1] = classes[6];
            lateTeacher[1] = teachers[6];
            lateRoom[1] = rooms[6];
            lateClass[2] = classes[7];
            lateTeacher[2] = teachers[7];
            lateRoom[2] = rooms[7];
            lateClass[3] = classes[8];
            lateTeacher[3] = teachers[8];
            lateRoom[3] = rooms[8];
        }
        else if(a == 000)
        {
            for(int i = 0; i < 9; i++)
            {
                cycleClass[i] = "none";
                cycleTeacher[i] = "none";
                cycleRoom[i] = "none";
            }
        }
    }

    /**
     * saves strings into StudentSched
     * saves two strings right now: schedule and lunches
     * @param s takes in a string to save
     */
    public void saveSched(String s)
    {
        SharedPreferences sharedPref = getSharedPreferences("StudentSched", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("schedule", s);
        editor.putString("bLunch", bLunches);
        editor.commit();
    }

    /**
     * reads from StudentSched
     * reads the string from the saved strings file
     * @param s for passing by reference
     * @return String
     */
    public String readSched(String s)
    {
        SharedPreferences sharedPref = getSharedPreferences("StudentSched", Context.MODE_PRIVATE);
        if(s.equals("schedule"))
            return sharedPref.getString("schedule", "");
        else
            return sharedPref.getString("bLunch", "");
    }

    /**
     * reads from a .txt file that will have date(Abr), (cycle number).
     * the file is first split by the '.' then split by the ','
     * @return cycle day of the given day taken from the getDate()
     */
    public String readCal()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        String s = "";
        DefaultHttpClient httpclient = new DefaultHttpClient();

        try {
            HttpGet httppost = new HttpGet("http://www.gamershut.net/TimChoh/calendar.txt");
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity ht = response.getEntity();

            BufferedHttpEntity buf = new BufferedHttpEntity(ht);

            InputStream is = buf.getContent();

            BufferedReader r = new BufferedReader(new InputStreamReader(is));

            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null)
            {
                total.append(line + "\n");
            }
            s = total.toString();
        } catch(IOException e)
        {
            s = "";
        }

        s = s.replaceAll("\\r|\\n","");

        String[] cal = s.split(",");
        String date = "";

        String asjaf = getDate();

        for (int i = 0; i < cal.length; i+=2)
            if(cal[i].equals(asjaf))
                date = cal[i+1];

        return date;
    }
}