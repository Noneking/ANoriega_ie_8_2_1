package com.example.mantenimiento.anoriega_ie_8_2_1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity implements MenuItem.OnMenuItemClickListener {

    ListView listView;
    MenuItem insert_menuItem;
    MenuItem modify_menuItem;
    MenuItem delete_menuItem;

    ArrayAdapter<String> listViewAdapter;
    SQLiteDatabase sqlite;

    Intent intent;

    public static final int VALUE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        initListeners();
        //openDatabase();
        //loadDatas();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        insert_menuItem= (MenuItem) menu.findItem(R.id.insertAction);
        modify_menuItem= (MenuItem) menu.findItem(R.id.modifyAction);
        delete_menuItem= (MenuItem) menu.findItem(R.id.deleteAction);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.insertAction) {
        //    return true;
        //}

        int finished=0;
        intent=new Intent();

        switch (item.getItemId())
        {
            case R.id.insertAction:
                intent = new Intent(this, InsertActivity.class);
                startActivityForResult(intent, finished);
                break;
            case R.id.modifyAction:

                break;
            case R.id.deleteAction:

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openDatabase()
    {
        sqlite=openOrCreateDatabase("CineMania", Context.MODE_PRIVATE, null);
        sqlite.execSQL("CREATE TABLE IF NOT EXISTS PELICULA(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "TITULO VARCHAR(30) NOT NULL," +
                "SUBTITULO VARCHAR(30)," +
                "FECHA DATE" +
                "DESCRIPCION VARCHAR(100)" +
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

    public void initListeners()
    {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == VALUE) {
            if (resultCode == RESULT_OK) {
                //loadDatas();
                Toast.makeText(MainActivity.this, "INSERTED CORRECTLY", Toast.LENGTH_LONG).show();
            }
        }
    }

}
