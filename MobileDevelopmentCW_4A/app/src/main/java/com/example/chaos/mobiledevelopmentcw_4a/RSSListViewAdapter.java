package com.example.chaos.mobiledevelopmentcw_4a;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by chaos on 06/12/2016.
 */

public class RSSListViewAdapter extends BaseAdapter {

    /*** Declarations (Lists) ***/
    public static ArrayList<String> titleLst = new ArrayList<>();
    public static ArrayList<String> descLst = new ArrayList<>();

    /*** Declarations (Variables) ***/
    Activity context;
    String title[];
    String desc[];

    public RSSListViewAdapter(Activity context, String[] title, String[] desc, ArrayList<String> titleLst, ArrayList<String> descLst)
    {
        super();
        this.context = context;
        this.title = title;
        this.desc = desc;
        this.titleLst = titleLst;
        this.descLst = descLst;
    }

    public int getCount()
    {
        return title.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }


    private class ViewHolder
    {
        /*** Declarations (UI) ***/
        TextView txtTitle, txtDescription;
    }

    /*** Handles Display ***/
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final RSSListViewAdapter.ViewHolder holder;
        LayoutInflater inflater = context.getLayoutInflater();

        /*** Sets up all UI components ***/
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_rss, null);
            holder = new RSSListViewAdapter.ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.txtVwTitle);
            holder.txtDescription= (TextView) convertView.findViewById(R.id.txtVwDesc);
            convertView.setTag(holder);
        } else {
            holder = (RSSListViewAdapter.ViewHolder) convertView.getTag();
        }

        ActualAdd(holder, position);
        return convertView;
    }

    public void ActualAdd(RSSListViewAdapter.ViewHolder holder, int position)
    {
        holder.txtTitle.setText(title[position]);
        holder.txtDescription.setText(desc[position]);
    }

}
