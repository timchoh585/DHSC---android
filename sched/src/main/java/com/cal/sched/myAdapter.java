package com.cal.sched;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tim on 2/12/14.
 */
public class myAdapter extends BaseAdapter
{
    Context context;
<<<<<<< HEAD
<<<<<<< HEAD
    HashMap<String, String> listers = new HashMap<>();
    private static LayoutInflater inflater = null;

    public myAdapter(Context context, HashMap<String, String> listers)
=======
=======
>>>>>>> master
    String[] classes;
    String[] teachers;
    private static LayoutInflater inflater = null;

    public myAdapter(Context context, String[] classes, String[] teachers)
<<<<<<< HEAD
>>>>>>> revised_main
=======
>>>>>>> master
    {
        this.context = context;
        this.classes = classes;
        this.teachers = teachers;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
<<<<<<< HEAD
<<<<<<< HEAD
        return listers.size();
    }

    @Override
    public Object getItem(int i) {
        return listers.get(i);
    }
=======
=======
>>>>>>> master
        return classes.length;
    }

    @Override
    public Object getItem(int i) { return classes[i] + " " + teachers[i]; }
<<<<<<< HEAD
>>>>>>> revised_main
=======
>>>>>>> master

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup)
    {
        View myView = convertView;
<<<<<<< HEAD
<<<<<<< HEAD
        TextView classes = (TextView) findViewById(R.id.aclass);
        TextView teachers = (TextView) findViewById(R.id.ateacher);
        classes = listers.get(i);
        teachers = listers.get(i);
=======
=======
>>>>>>> master
        if(myView == null)
            myView = inflater.inflate(R.layout.lin_class, null);
        TextView tclass = (TextView) myView.findViewById(R.id.aclass);
        TextView tteacher = (TextView) myView.findViewById(R.id.ateacher);
        tclass.setText(classes[i]);
        tteacher.setText(teachers[i]);

<<<<<<< HEAD
>>>>>>> revised_main
=======
>>>>>>> master
        return myView;
    }
}