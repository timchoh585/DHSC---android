package com.cal.sched;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Disp extends ActionBarActivity {

    private String sched;
    public List<String> full = new  ArrayList<>();
    private ArrayList<String> aclass = new ArrayList<>();
    private ArrayList<String> ateacher = new ArrayList<>();
    private ArrayList<String> sroom = new ArrayList<>();
    public static boolean supAdd = false;
    private boolean hasSaved = false;
    private File dir = getFilesDir();
    private FileOutputStream fos;
//    public static final String FILE_NAME = "sched";
//    private File file = new File(dir + "/DHSC/", FILE_NAME);

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

        if(!(sched = getIntent().getStringExtra("schedu")).equals(null))
            supAdd = true;

        /************************* setting up the layout *************************/
        getDay();
        //readSched();

        if(supAdd)
        {
            //saveSched(sched);
            createList(sched);

            ListView lists = (ListView) findViewById(R.id.listView);
            //myAdapter adapt = new myAdapter(this, aclass, ateacher);
            //lists.setAdapter(adapt);
        }
        else
        {
            supAdd = true;
            if(sched.length() == 0)
            {
                Button add = (Button) findViewById(R.id.finish);
                try
                {
                    /************************* makes button visible *************************/
                    add.requestFocus();
                    add.setFocusable(true);
                    if(add.getVisibility() == View.INVISIBLE || add.getVisibility() == View.GONE)
                        add.setVisibility(View.VISIBLE);
                    /************************* makes toast for no schedule *************************/
                    Toast.makeText(this, "No Current Schedule", Toast.LENGTH_LONG).show();
                }catch (Exception e)
                {
                    Log.e("SCHEDULE", e.getMessage() + " error!");
                }
            }
            else
            {
                //saveSched(sched);
                createList(sched);
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

        /************************* check to see if day is accessible *************************/

        try
        {
            day.setText(Html.fromHtml("<b><h1>" + dayForm.format(today) + "</h1></b>"));
            date.setText(Html.fromHtml("<fontsize=\"10\">" + dateForm.format(today) + "</font>"));
        }catch(Exception e)
        {
            Log.e("DATE", e.getMessage() + " Error!");
        }

        /************************* sets cycle *************************/

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
        /************************* parses string *************************/

        for(String f : schedule.split(","))
            full.add(f);

        /************************* splits up string *************************/

        for(int i = 0; i <= full.size(); i++)
        {
            /************************* sets classes *************************/
            if(i < 9)
                aclass.add(full.remove(0));

            /************************* sets teachers *************************/
            else if(i > 9 && i < 18)
                ateacher.add(full.remove(0));

            /************************* sets room *************************/
            else
                sroom.add(full.remove(0));
        }
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.activity_disp, aclass);

        //lv.setAdapter(adapter);
    }

    /**
     * saving the sched as the user would put it in
     */
    public void saveSched(String s)
    {

        /************************* save to "sched.txt" *************************/

//        boolean save = (new File(getApplicationContext().getFilesDir()+"/DHSC/")).mkdir();
//        if (!save)
//            Log.w("directory not created", "directory not created");
//
//        File internal = getFilesDir();
//        String path = internal.getPath();
//        File file = new File(path + "sched.txt");


        try
        {
            File file = new File(dir + "/DHSC/", "sched.txt");
            if(file.getParentFile().mkdir())
            {
                file.createNewFile();
                fos = new FileOutputStream(file);

                fos.write(s.getBytes());
                fos.flush();
                fos.close();
            }

//            fos = openFileOutput("sched.txt", MODE_PRIVATE);
//            fos.write(s.getBytes());
//            FileWriter fw = new FileWriter(file);
//            BufferedWriter bw = new BufferedWriter(fw);
//            FileOutputStream fos = openFileOutput("sched.txt", MODE_PRIVATE);
//            try
//            {
//            BufferedWriter bw = new BufferedWriter(new FileWriter(new
//                    File(getFilesDir()+File.separator+"sched.txt")));
//            bw.write(s);
//            bw.close();
//            File file = new File(dir + "/text/", FILE_NAME);
//
//            fos.flush();

//                fos.write(s.getBytes());
//                fos.close();
            hasSaved = true;
//            } catch (IOException e)
//            {
//                Log.e("FILEWRITE", e.getMessage() + " Error!");
//            }
        } catch (Exception e)
        {
            e.printStackTrace();
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
        StringBuffer sc = new StringBuffer();

        /************************* read file "sched.txt" *************************/

        try
        {
            File file = new File(dir + "/DHSC/", "sched.txt");
            InputStream is = new BufferedInputStream(new FileInputStream(file));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s;
            while((s = br.readLine()) != null)
            {
                sc.append(s);
            }
            br.close();
            is.close();
            //Log.d("File", "File contents: ", sch);

//            FileInputStream fis = openFileInput("sched.txt");
//            try
//            {
//                while((ch = fis.read()) != -1)
//                    sc.append((char)ch);
//            BufferedReader br = new BufferedReader(new FileReader(new
//                    File(getFilesDir()+File.separator+"sched.txt")));
//            while((sched = br.readLine()) != null)
//            {
//                sch.append(sched);
//            }
//            Log.d("Output", sch.toString());
//            br.close();

//            File file = new File(dir + "/text/", FILE_NAME);
//
//            InputStream is = new BufferedInputStream(new FileInputStream(file));
//            BufferedReader r = new BufferedReader((new InputStreamReader(is)));
//            String line;
//            while((line = r.readLine()) != null)
//                sch.append(line + ", ");

//            int c = fis.read();
//            while((c != -1))
//            {
//                sch.append((char) c);
//                supAdd = true;
//            }
//            } catch (Exception e) {
//                Log.e("FILEREAD", e.getMessage() + " No Saved File!");
//            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        sched = sch.toString();
    }
}