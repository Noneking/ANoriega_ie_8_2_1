package com.example.mantenimiento.anoriega_ie_8_2_1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by nonek on 26/11/2015.
 */
public class FullList {

    private static ArrayList<ItemList> arrayList;

    public FullList()
    {
        arrayList=new ArrayList<ItemList>();
    }

    public FullList(ArrayList<ItemList> arrayList)
    {
        this.arrayList=arrayList;
    }

    public static ArrayList<ItemList> getArrayList()
    {
        return arrayList;
    }

    public static void setArrayList(ArrayList<ItemList> array)
    {
        arrayList=array;
    }

    public static void addItem(ItemList item)
    {
        arrayList.add(item);
    }
}
