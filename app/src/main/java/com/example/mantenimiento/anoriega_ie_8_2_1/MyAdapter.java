package com.example.mantenimiento.anoriega_ie_8_2_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nonek on 24/11/2015.
 */
public abstract class MyAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<?> entradas;
    private int R_layout_idView;
    //private static LayoutInflater inflater = null;

    public MyAdapter(Context context, int R_layout_idView, ArrayList<ItemList> entradas)
    {
        super();
        this.context = context;
        this.entradas=entradas;
        this.R_layout_idView=R_layout_idView;
        //inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return entradas.size();
    }

    @Override
    public Object getItem(int position) {
        return entradas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        //View view = convertView;
        if (view == null)
        {
            LayoutInflater li= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R_layout_idView, null);
        }
        onEntrada(entradas.get(position), view);
        //ImageView imageView= (ImageView) view.findViewById(R.id.imageViewItem);
        //TextView textViewTitulo = (TextView) view.findViewById(R.id.item_textViewTitle);
        //TextView textViewSubTitulo = (TextView) view.findViewById(R.id.item_textViewSubTitle);
        return view;
    }

    public abstract void onEntrada(Object entrada, View view);

}
