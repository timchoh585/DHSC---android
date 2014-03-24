package com.cal.sched;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tim on 2/12/14.
 */
public class myAdapter extends BaseAdapter
{
    Context context;
    HashMap<String, String> listers = new HashMap<>();
    private static LayoutInflater inflater = null;

    public myAdapter(Context context, HashMap<String, String> listers)
    {
        this.context = context;
        this.listers = listers;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listers.size();
    }

    @Override
    public Object getItem(int i) {
        return listers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup)
    {
        View myView = convertView;
        TextView classes = (TextView) findViewById(R.id.aclass);
        TextView teachers = (TextView) findViewById(R.id.ateacher);
        classes = listers.get(i);
        teachers = listers.get(i);
        return myView;
    }
}