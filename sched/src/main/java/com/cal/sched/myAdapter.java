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
    ArrayList<String> classes = null;
    ArrayList<String> teachers = null;
    private static LayoutInflater inflater = null;

    public myAdapter(Context context, ArrayList<String> classes, ArrayList<String> teachers)
    {
        this.context = context;
        this.classes = classes;
        this.teachers = teachers;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return classes.size() + teachers.size();
    }

    @Override
    public Object getItem(int i) {
        return classes.get(i) + " " + teachers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup)
    {
        View myView = convertView;
        if(myView == null)
            myView = inflater.inflate(R.layout.lin_class, null);
        TextView classer = (TextView) myView.findViewById(R.id.aclass);
        TextView teacher = (TextView) myView.findViewById(R.id.ateacher);
        classer.setText(classes.get(i));
        teacher.setText(teachers.get(i));
        return myView;
    }
}