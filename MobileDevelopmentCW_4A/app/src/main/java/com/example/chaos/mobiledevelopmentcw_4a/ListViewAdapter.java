package com.example.chaos.mobiledevelopmentcw_4a;

import java.lang.Object;
import java.util.ArrayList;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
/**
 * Created by chaos_000 on 25/07/2016.
 */

public class ListViewAdapter extends BaseAdapter{

    /*** Declarations (UI) ***/
    Button but3, but4, but7;

    /*** Declarations (Lists) ***/
    public static ArrayList<String> nameLst = new ArrayList<>();
    public static ArrayList<Integer> initLst = new ArrayList<>();

    /*** Declarations (Variables) ***/
    Activity context;
    String name[];
    Integer init[];
    int posPlus, posMinus;
    boolean plus, minus;

    /*** Get and Set passed values ***/
    public ListViewAdapter(Activity context, String[] name, Integer[] init, ArrayList<String> nameLst, ArrayList<Integer> initLst)
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
        TextView txtName;
        EditText txtInitiative;
    }

    /*** Handles Display ***/
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        LayoutInflater inflater = context.getLayoutInflater();

        /*** Sets up all UI components ***/
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.txtInitiative= (EditText) convertView.findViewById(R.id.txtInitiative);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        but3 = (Button) convertView.findViewById(R.id.butUp);
        but4 = (Button) convertView.findViewById(R.id.butDown);
        but7 = (Button) convertView.findViewById(R.id.butDelete);

        /*** Move item up by one ***/
        but3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                posMinus = position-1;
                if (position > 0)
                {
                    minus = true;
                    SwitcherooMinus(position, posMinus);
                    ActualAdd(holder, position);
                }
                notifyDataSetChanged();
            }
        });

        /*** Move item down by one ***/
        but4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                posPlus = (position+1);
                if (position != (name.length - 1))
                {
                    plus = true;
                    SwitcherooPlus(position, posPlus);
                    ActualAdd(holder, position);
                }
                notifyDataSetChanged();
            }
        });

        but7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DeleteCombatant(position);
                notifyDataSetChanged();
            }
        });

        ActualAdd(holder, position);
        return convertView;
    }

    public void ActualAdd(ViewHolder holder, int position)
    {
        holder.txtName.setText(name[position]);
        holder.txtInitiative.setText(init[position].toString());
        if (minus)
        {
            holder.txtName.setText(name[posMinus]);
            holder.txtInitiative.setText(init[posMinus].toString());
            minus = false;
        }
        if (plus)
        {
            holder.txtName.setText(name[posPlus]);
            holder.txtInitiative.setText(init[posPlus].toString());
            plus = false;
        }
    }

    public void SwitcherooMinus(int position, int posMinus)
    {
        String temp1;
        int temp2;

        temp1 = name[position];
        name[position] = name[posMinus];
        name[posMinus] = temp1;

        temp2 = init[position];
        init[position] = init[posMinus];
        init[posMinus] = temp2;

    }

    public void SwitcherooPlus(int position, int posPlus)
    {
        String temp1;
        int temp2;

        temp1 = name[position];
        name[position] = name[posPlus];
        name[posPlus] = temp1;

        temp2 = init[position];
        init[position] = init[posPlus];
        init[posPlus] = temp2;
    }

    public void DeleteCombatant(int position)
    {
        nameLst.remove(position);
        initLst.remove(position);

        name = nameLst.toArray(new String[0]);
        init = initLst.toArray(new Integer[0]);

        MainMenu.nameArr = name;
        MainMenu.initArr = init;
    }
}
