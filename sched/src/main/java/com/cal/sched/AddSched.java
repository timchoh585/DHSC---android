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
    private String[] currentSched;
    private String[] classes = new String[10];
    private String[] teachers = new String[10];
    private String[] rooms = new String[10];

    //text files
    private EditText peb;
    private EditText p1;
    private EditText p2;
    private EditText p3;
    private EditText p4;
    private EditText p5;
    private EditText p6;
    private EditText p7;
    private EditText p8;

    private EditText teb;
    private EditText t1;
    private EditText t2;
    private EditText t3;
    private EditText t4;
    private EditText t5;
    private EditText t6;
    private EditText t7;
    private EditText t8;
    private EditText hrt;

    private EditText reb;
    private EditText r1;
    private EditText r2;
    private EditText r3;
    private EditText r4;
    private EditText r5;
    private EditText r6;
    private EditText r7;
    private EditText r8;
    private EditText hrr;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sched);

        peb = (EditText) findViewById(R.id.peb);
        p1 = (EditText) findViewById(R.id.p1);
        p2 = (EditText) findViewById(R.id.p2);
        p3 = (EditText) findViewById(R.id.p3);
        p4 = (EditText) findViewById(R.id.p4);
        p5 = (EditText) findViewById(R.id.p5);
        p6 = (EditText) findViewById(R.id.p6);
        p7 = (EditText) findViewById(R.id.p7);
        p8 = (EditText) findViewById(R.id.p8);

        teb = (EditText) findViewById(R.id.teb);
        t1 = (EditText) findViewById(R.id.t1);
        t2 = (EditText) findViewById(R.id.t2);
        t3 = (EditText) findViewById(R.id.t3);
        t4 = (EditText) findViewById(R.id.t4);
        t5 = (EditText) findViewById(R.id.t5);
        t6 = (EditText) findViewById(R.id.t6);
        t7 = (EditText) findViewById(R.id.t7);
        t8 = (EditText) findViewById(R.id.t8);
        t8 = (EditText) findViewById(R.id.t8);
        hrt = (EditText) findViewById(R.id.hrt);

        reb = (EditText) findViewById(R.id.reb);
        r1 = (EditText) findViewById(R.id.r1);
        r2 = (EditText) findViewById(R.id.r2);
        r3 = (EditText) findViewById(R.id.r3);
        r4 = (EditText) findViewById(R.id.r4);
        r5 = (EditText) findViewById(R.id.r5);
        r6 = (EditText) findViewById(R.id.r6);
        r7 = (EditText) findViewById(R.id.r7);
        r8 = (EditText) findViewById(R.id.r8);
        hrr = (EditText) findViewById(R.id.hrr);

        if(editSched())
            edittable();

    }

    /**
     * checks for old schedule and then devides it up into class, teacher, and room
     * @return boolean that checks if there is an old schedule
     */
    public boolean editSched()
    {
        String s = "";
        try
        {
            s = getIntent().getStringExtra("editSched");

            currentSched = s.split(",");
            int indivT = 0;
            int indivR = 0;

            for(int i = 0; i < currentSched.length; i++)
            {
                if (i < 10)
                    classes[i] = currentSched[i];
                else if (i < 20)
                {
                    teachers[indivT] = currentSched[i];
                    indivT++;
                }
                else
                {
                    rooms[indivR] = currentSched[i];
                    indivR++;
                }
            }
            return true;
        } catch (Exception e)
        {
            currentSched = new String[0];
        }
        return false;
    }

    /**
     * if the schedule is editable, it will fill the text edit fields with the previous info
     */
    public void edittable()
    {
        peb.setText(classes[0]);
        p1.setText(classes[1]);
        p2.setText(classes[2]);
        p3.setText(classes[3]);
        p4.setText(classes[4]);
        p5.setText(classes[5]);
        p6.setText(classes[6]);
        p7.setText(classes[7]);
        p8.setText(classes[8]);

        teb.setText(teachers[0]);
        t1.setText(teachers[1]);
        t2.setText(teachers[2]);
        t3.setText(teachers[3]);
        t4.setText(teachers[4]);
        t5.setText(teachers[5]);
        t6.setText(teachers[6]);
        t7.setText(teachers[7]);
        t8.setText(teachers[8]);
        hrt.setText(teachers[9]);

        reb.setText(rooms[0]);
        r1.setText(rooms[1]);
        r2.setText(rooms[2]);
        r3.setText(rooms[3]);
        r4.setText(rooms[4]);
        r5.setText(rooms[5]);
        r6.setText(rooms[6]);
        r7.setText(rooms[7]);
        r8.setText(rooms[8]);
        hrr.setText(rooms[9]);
    }

    /**
     * on click for the button that is pressed to move on to select lunches
     * @param v for the button view
     */
    public void onClick(View v)
    {
        /************************* sets empty entries to be "none" *************************/

        if(peb.getText().toString().equals("")) { peb.setText("none"); }
        if(p1.getText().toString().equals("")) { p1.setText("none"); }
        if(p2.getText().toString().equals("")) { p2.setText("none"); }
        if(p3.getText().toString().equals("")) { p3.setText("none"); }
        if(p4.getText().toString().equals("")) { p4.setText("none"); }
        if(p5.getText().toString().equals("")) { p5.setText("none"); }
        if(p6.getText().toString().equals("")) { p6.setText("none"); }
        if(p7.getText().toString().equals("")) { p7.setText("none"); }
        if(p8.getText().toString().equals("")) { p8.setText("none"); }

        if(teb.getText().toString().equals("")) { teb.setText("none"); }
        if(t1.getText().toString().equals("")) { t1.setText("none"); }
        if(t2.getText().toString().equals("")) { t2.setText("none"); }
        if(t3.getText().toString().equals("")) { t3.setText("none"); }
        if(t4.getText().toString().equals("")) { t4.setText("none"); }
        if(t5.getText().toString().equals("")) { t5.setText("none"); }
        if(t6.getText().toString().equals("")) { t6.setText("none"); }
        if(t7.getText().toString().equals("")) { t7.setText("none"); }
        if(t8.getText().toString().equals("")) { t8.setText("none"); }
        if(hrt.getText().toString().equals("")) { hrt.setText("none"); }

        if(reb.getText().toString().equals("")) { reb.setText("none"); }
        if(r1.getText().toString().equals("")) { r1.setText("none"); }
        if(r2.getText().toString().equals("")) { r2.setText("none"); }
        if(r3.getText().toString().equals("")) { r3.setText("none"); }
        if(r4.getText().toString().equals("")) { r4.setText("none"); }
        if(r5.getText().toString().equals("")) { r5.setText("none"); }
        if(r6.getText().toString().equals("")) { r6.setText("none"); }
        if(r7.getText().toString().equals("")) { r7.setText("none"); }
        if(r8.getText().toString().equals("")) { r8.setText("none"); }
        if(hrr.getText().toString().equals("")) { hrr.setText("none"); }

        /************************* makes all lists into one string *************************/

        String sched = peb.getText().toString() + "," + p1.getText().toString() + "," +
                p2.getText().toString() + "," + p3.getText().toString() + "," +
                p4.getText().toString() + "," + p5.getText().toString() + "," +
                p6.getText().toString() + "," + p7.getText().toString() + "," +
                p8.getText().toString() + "," + "Homeroom" + "," +
                teb.getText().toString() + "," + t1.getText().toString() + "," +
                t2.getText().toString() + "," + t3.getText().toString() + "," +
                t4.getText().toString() + "," + t5.getText().toString() + "," +
                t6.getText().toString() + "," + t7.getText().toString() + "," +
                t8.getText().toString() + "," +hrt.getText().toString() + "," +
                reb.getText().toString() + "," + r1.getText().toString() + "," +
                r2.getText().toString() + "," + r3.getText().toString() + "," +
                r4.getText().toString() + "," + r5.getText().toString() + "," +
                r6.getText().toString() + "," + r7.getText().toString() + "," +
                r8.getText().toString() + "," +hrr.getText().toString();

        /************************* intent the string *************************/

        Intent moveOnIntent = new Intent(AddSched.this, Lunch.class);
        moveOnIntent.putExtra("userSched", sched);
        AddSched.this.startActivity(moveOnIntent);
    }
}
