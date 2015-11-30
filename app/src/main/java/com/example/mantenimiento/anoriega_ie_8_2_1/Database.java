package com.example.mantenimiento.anoriega_ie_8_2_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by nonek on 30/11/2015.
 */
public class Database {

    static SQLiteDatabase database;

    public Database()
    {

    }

    public static SQLiteDatabase getDatabase()
    {
        return database;
    }

    public static void setDatabase(SQLiteDatabase db)
    {
        database=db;
    }

}
