package com.cal.sched;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Tim on 2/12/14.
 */
public class myAdapter extends BaseAdapter
{
    Context context;
    String[] classes;
    String[] teachers;
    String[] rooms;
    String[] times;
    private static LayoutInflater inflater = null;

    public myAdapter(Context context, String[] classes, String[] teachers, String[] rooms,
                     String[] times)
    {
        this.context = context;
        this.classes = classes;
        this.teachers = teachers;
        this.rooms = rooms;
        this.times = times;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return classes.length;
    }

    @Override
    public Object getItem(int i) { return classes[i] + " " + teachers[i]; }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup)
    {
        View myView = convertView;
        if(myView == null)
            myView = inflater.inflate(R.layout.custom_adapter, null);
        TextView tclass = (TextView) myView.findViewById(R.id.aclass);
        TextView tteacher = (TextView) myView.findViewById(R.id.ateacher);
        TextView troom = (TextView) myView.findViewById(R.id.aroom);
        TextView ttime = (TextView) myView.findViewById(R.id.time);
        tclass.setText(classes[i]);
        tteacher.setText(teachers[i]);
        troom.setText(rooms[i]);
        ttime.setText(times[i]);
        return myView;
    }
}