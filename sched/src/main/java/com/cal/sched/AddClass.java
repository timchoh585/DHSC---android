package com.cal.sched;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Arrays;
import java.util.List;

public class AddClass extends ActionBarActivity {

    public static boolean supAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    public void onClick(View v)
    {
        EditText peb = (EditText) findViewById(R.id.peb);
        if(peb.getText() == null) { peb.setText("none"); }
        EditText p1 = (EditText) findViewById(R.id.p1);
        if(p1.getText() == null) { p1.setText("none"); }
        EditText p2 = (EditText) findViewById(R.id.p2);
        if(p2.getText() == null) { p2.setText("none"); }
        EditText p3 = (EditText) findViewById(R.id.p3);
        if(p3.getText() == null) { p3.setText("none "); }
        EditText p4 = (EditText) findViewById(R.id.p4);
        if(p4.getText() == null) { p4.setText("none"); }
        EditText p5 = (EditText) findViewById(R.id.p5);
        if(p5.getText() == null) { p5.setText("none"); }
        EditText p6 = (EditText) findViewById(R.id.p6);
        if(p6.getText() == null) { p6.setText("none"); }
        EditText p7 = (EditText) findViewById(R.id.p7);
        if(p7.getText() == null) { p7.setText("none"); }
        EditText p8 = (EditText) findViewById(R.id.p8);
        if(p8.getText() == null) { p8.setText("none"); }

        EditText teb = (EditText) findViewById(R.id.teb);
        if(teb.getText() == null) { teb.setText(" "); }
        EditText t1 = (EditText) findViewById(R.id.t1);
        if(p1.getText() == null) { p1.setText(" "); }
        EditText t2 = (EditText) findViewById(R.id.t2);
        if(p2.getText() == null) { p2.setText(" "); }
        EditText t3 = (EditText) findViewById(R.id.t3);
        if(p3.getText() == null) { p3.setText(" "); }
        EditText t4 = (EditText) findViewById(R.id.t4);
        if(p4.getText() == null) { p4.setText(" "); }
        EditText t5 = (EditText) findViewById(R.id.t5);
        if(p5.getText() == null) { p5.setText(" "); }
        EditText t6 = (EditText) findViewById(R.id.t6);
        if(p6.getText() == null) { p6.setText(" "); }
        EditText t7 = (EditText) findViewById(R.id.t7);
        if(p7.getText() == null) { p7.setText(" "); }
        EditText t8 = (EditText) findViewById(R.id.t8);
        if(p8.getText() == null) { p8.setText(" "); }

        EditText reb = (EditText) findViewById(R.id.reb);
        if(reb.getText() == null) { reb.setText(" "); }
        EditText r1 = (EditText) findViewById(R.id.r1);
        if(p1.getText() == null) { p1.setText(" "); }
        EditText r2 = (EditText) findViewById(R.id.r2);
        if(p2.getText() == null) { p2.setText(" "); }
        EditText r3 = (EditText) findViewById(R.id.r3);
        if(p3.getText() == null) { p3.setText(" "); }
        EditText r4 = (EditText) findViewById(R.id.r4);
        if(p4.getText() == null) { p4.setText(" "); }
        EditText r5 = (EditText) findViewById(R.id.r5);
        if(p5.getText() == null) { p5.setText(" "); }
        EditText r6 = (EditText) findViewById(R.id.r6);
        if(p6.getText() == null) { p6.setText(" "); }
        EditText r7 = (EditText) findViewById(R.id.r7);
        if(p7.getText() == null) { p7.setText(" "); }
        EditText r8 = (EditText) findViewById(R.id.r8);
        if(p8.getText() == null) { p8.setText(" "); }

        String sched = peb.getText().toString() + "," + p1.getText().toString() + "," +
                p2.getText().toString() + "," + p3.getText().toString() + "," +
                p4.getText().toString() + "," + p5.getText().toString() + "," +
                p6.getText().toString() + "," + p7.getText().toString() + "," +
                p8.getText().toString() + "," +
                teb.getText().toString() + "," + t1.getText().toString() + "," +
                t2.getText().toString() + "," + t3.getText().toString() + "," +
                t4.getText().toString() + "," + t5.getText().toString() + "," +
                t6.getText().toString() + "," + t7.getText().toString() + "," +
                t8.getText().toString() + "," +
                reb.getText().toString() + "," + r1.getText().toString() + "," +
                r2.getText().toString() + "," + r3.getText().toString() + "," +
                r4.getText().toString() + "," + r5.getText().toString() + "," +
                r6.getText().toString() + "," + r7.getText().toString() + "," +
                r8.getText().toString();


        /*Disp d = new Disp();
        for (int i = 0; i < d.full.size(); i++)
            if (d.full.get(i) == null)
                d.full.set(i, " ");
        supAdd = true;*/

        Disp d = new Disp();
        List<String> listSched = Arrays.asList(sched.split(","));
        for(int i = 0; i < listSched.size(); i++)
            if(listSched.get(i).equals("") || listSched.get(i) == null)
                listSched.set(i, "none");
        supAdd = true;

        sched = "";
        for(int i = 0; i<listSched.size(); i++)
            sched += listSched.get(i) + ",";

        d.full = listSched;

        Intent myIntent = new Intent(AddClass.this, Disp.class);
        myIntent.putExtra("schedu", sched);
        AddClass.this.startActivity(myIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_class, menu);
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
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_add_class, container, false);
            return rootView;
        }
    }
}