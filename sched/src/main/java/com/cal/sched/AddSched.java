package com.cal.sched;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Tim on 3/23/2014.
 */
public class AddSched extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sched);
    }

    public void onClick(View v)
    {
        /************************* sets empty entries to be "none" *************************/

        EditText peb = (EditText) findViewById(R.id.peb);
        if(peb.getText().toString().equals("")) { peb.setText("none"); }
        EditText p1 = (EditText) findViewById(R.id.p1);
        if(p1.getText().toString().equals("")) { p1.setText("none"); }
        EditText p2 = (EditText) findViewById(R.id.p2);
        if(p2.getText().toString().equals("")) { p2.setText("none"); }
        EditText p3 = (EditText) findViewById(R.id.p3);
        if(p3.getText().toString().equals("")) { p3.setText("none "); }
        EditText p4 = (EditText) findViewById(R.id.p4);
        if(p4.getText().toString().equals("")) { p4.setText("none"); }
        EditText p5 = (EditText) findViewById(R.id.p5);
        if(p5.getText().toString().equals("")) { p5.setText("none"); }
        EditText p6 = (EditText) findViewById(R.id.p6);
        if(p6.getText().toString().equals("")) { p6.setText("none"); }
        EditText p7 = (EditText) findViewById(R.id.p7);
        if(p7.getText().toString().equals("")) { p7.setText("none"); }
        EditText p8 = (EditText) findViewById(R.id.p8);
        if(p8.getText().toString().equals("")) { p8.setText("none"); }

        EditText teb = (EditText) findViewById(R.id.teb);
        if(teb.getText().toString().equals("")) { teb.setText("none"); }
        EditText t1 = (EditText) findViewById(R.id.t1);
        if(t1.getText().toString().equals("")) { t1.setText("none"); }
        EditText t2 = (EditText) findViewById(R.id.t2);
        if(t2.getText().toString().equals("")) { t2.setText("none"); }
        EditText t3 = (EditText) findViewById(R.id.t3);
        if(t3.getText().toString().equals("")) { t3.setText("none"); }
        EditText t4 = (EditText) findViewById(R.id.t4);
        if(t4.getText().toString().equals("")) { t4.setText("none"); }
        EditText t5 = (EditText) findViewById(R.id.t5);
        if(t5.getText().toString().equals("")) { t5.setText("none"); }
        EditText t6 = (EditText) findViewById(R.id.t6);
        if(t6.getText().toString().equals("")) { t6.setText("none"); }
        EditText t7 = (EditText) findViewById(R.id.t7);
        if(t7.getText().toString().equals("")) { t7.setText("none"); }
        EditText t8 = (EditText) findViewById(R.id.t8);
        if(t8.getText().toString().equals("")) { t8.setText("none"); }

        EditText reb = (EditText) findViewById(R.id.reb);
        if(reb.getText().toString().equals("")) { reb.setText("none"); }
        EditText r1 = (EditText) findViewById(R.id.r1);
        if(r1.getText().toString().equals("")) { r1.setText("none"); }
        EditText r2 = (EditText) findViewById(R.id.r2);
        if(r2.getText().toString().equals("")) { r2.setText("none"); }
        EditText r3 = (EditText) findViewById(R.id.r3);
        if(r3.getText().toString().equals("")) { r3.setText("none"); }
        EditText r4 = (EditText) findViewById(R.id.r4);
        if(r4.getText().toString().equals("")) { r4.setText("none"); }
        EditText r5 = (EditText) findViewById(R.id.r5);
        if(r5.getText().toString().equals("")) { r5.setText("none"); }
        EditText r6 = (EditText) findViewById(R.id.r6);
        if(r6.getText().toString().equals("")) { r6.setText("none"); }
        EditText r7 = (EditText) findViewById(R.id.r7);
        if(r7.getText().toString().equals("")) { r7.setText("none"); }
        EditText r8 = (EditText) findViewById(R.id.r8);
        if(r8.getText().toString().equals("")) { r8.setText("none"); }

        /************************* makes all lists into one string *************************/

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

        /************************* intent the string *************************/

        Intent myIntent = new Intent(AddSched.this, Main.class);
        myIntent.putExtra("userSched", sched);
        AddSched.this.startActivity(myIntent);
    }
}
