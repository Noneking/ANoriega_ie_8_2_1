package com.example.mantenimiento.anoriega_ie_8_2_1;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    ListView listView;
    ArrayAdapter<String> listViewAdapter;
    SQLiteDatabase sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        openDatabase();
        loadDatas();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.insertAction) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openDatabase()
    {
        sqlite=openOrCreateDatabase("CineMania", Context.MODE_PRIVATE, null);
        sqlite.execSQL("CREATE TABLE IF NOT EXISTS PELICULA(" +
                "ID INT PRIMARY KEY AUTO_INCREMENT," +
                "NOMBRE VARCHAR UNIQUE NOT NULL," +
                "DESCRIPCION VARCHAR" +
                ");");
    }

    public void loadDatas()
    {
        ArrayList<String> peliculas=new ArrayList<String>();
        Cursor cursor=sqlite.rawQuery("SELECT * FROM PELICULA", null);
        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                peliculas.add(cursor.getString(1));
            }
            listViewAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,peliculas);
            listView.setAdapter(listViewAdapter);
        }
    }

    public void initComponents()
    {
        listView= (ListView) findViewById(R.id.main_listView);
    }

}
