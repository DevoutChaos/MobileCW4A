package com.example.chaos.mobiledevelopmentcw_4a;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
/**
 * Created by chaos on 06/12/2016.
 */

public class DBListViewAdapter extends BaseAdapter {

    /*** Declarations (UI) ***/
    Button but14, but15;

    /*** Declarations (Lists) ***/
    public static ArrayList<String> nameLst = new ArrayList<>();
    public static ArrayList<Integer> initLst = new ArrayList<>();

    /*** Declarations (Variables) ***/
    Activity context;
    String name[];
    Integer init[];

    public DBListViewAdapter(Activity context, String[] name, Integer[] init, ArrayList<String> nameLst, ArrayList<Integer> initLst)
    {
        super();
        this.context = context;
        this.name = name;
        this.init = init;
        this.nameLst = nameLst;
        this.initLst = initLst;
    }

    public int getCount()
    {
        return name.length;
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
        TextView txtName, txtInitiative;
    }

    /*** Handles Display ***/
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final DBListViewAdapter.ViewHolder holder;
        LayoutInflater inflater = context.getLayoutInflater();

        /*** Sets up all UI components ***/
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_db, null);
            holder = new DBListViewAdapter.ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.txtNameDB);
            holder.txtInitiative= (TextView) convertView.findViewById(R.id.txtInitiativeDB);
            convertView.setTag(holder);
        } else {
            holder = (DBListViewAdapter.ViewHolder) convertView.getTag();
        }

        but14 = (Button) convertView.findViewById(R.id.butDeleteDB);
        but15 = (Button) convertView.findViewById(R.id.butAddToTracker);


        /*** Delete item ***/
        but14.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DeleteCombatant(position);
                notifyDataSetChanged();
            }
        });

        /*** Add combatant to Initiative Tracker ***/
        but15.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainMenu.nameLst.add(holder.txtName.getText().toString());
                int initMod = (int) (20 * Math.random()) + 1;
                MainMenu.initLst.add((Integer.parseInt(holder.txtInitiative.getText().toString())) + initMod);
                MainMenu.dbAdd = true;
            }
        });

        ActualAdd(holder, position);
        return convertView;
    }

    public void ActualAdd(DBListViewAdapter.ViewHolder holder, int position)
    {
        holder.txtName.setText(name[position]);
        holder.txtInitiative.setText(init[position].toString());
    }

    public void DeleteCombatant(int position)
    {
        nameLst.remove(position);
        initLst.remove(position);

        name = nameLst.toArray(new String[0]);
        init = initLst.toArray(new Integer[0]);

        MainMenu.nameArr = name;
        MainMenu.initArr = init;

        MainMenu.toBeDeleted.add(position);
    }
}
