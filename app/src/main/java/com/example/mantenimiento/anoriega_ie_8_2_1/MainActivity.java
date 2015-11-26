package com.example.mantenimiento.anoriega_ie_8_2_1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends Activity implements MenuItem.OnMenuItemClickListener {

    ListView listView;
    MenuItem insert_menuItem;
    MenuItem modify_menuItem;
    MenuItem delete_menuItem;

    ArrayAdapter<String> listViewAdapter;
    SQLiteDatabase sqlite;

    FullList fullList;

    Intent intent;

    public static final int VALUE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        initListeners();
        openDatabase();
        loadDatas();
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
                intent = new Intent(this, ModifyActivity.class);
                startActivityForResult(intent, finished);
                break;
            case R.id.deleteAction:

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openDatabase()
    {
        sqlite=openOrCreateDatabase("CineMania", Context.MODE_PRIVATE, null);
        sqlite.execSQL("DROP TABLE PELICULA");
        sqlite.execSQL("CREATE TABLE IF NOT EXISTS PELICULA(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "IMG VARCHAR(100)," +
                "TITULO VARCHAR(30)," +
                "SUBTITULO VARCHAR(30)," +
                "FECHA VARCHAR(9)," +
                "DESCRIPCION VARCHAR(100)" +
                ");");
    }

    public void loadDatas()
    {
        listView.setAdapter(new MyAdapter(this, R.layout.activity_item_list, fullList.getArrayList()) {
            @Override
            public void onEntrada(Object entrada, View view) {
                if(entrada!=null)
                {
                    ImageView img= (ImageView) view.findViewById(R.id.imageViewItem);
                    if(img!=null)
                    {
                        Bitmap myBitmap = BitmapFactory.decodeFile(((ItemList) entrada).getPathImagen());
                        img.setImageBitmap(myBitmap);
                    }
                    TextView titulo= (TextView) view.findViewById(R.id.item_textViewTitle);
                    if(titulo!=null)
                    {
                        titulo.setText(((ItemList) entrada).getTitulo());
                    }
                    TextView subtitulo= (TextView) view.findViewById(R.id.item_textViewSubTitle);
                    if(subtitulo!=null)
                    {
                        subtitulo.setText(((ItemList) entrada).getSubtitulo());
                    }
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemList item= (ItemList) parent.getItemAtPosition(position);
                CharSequence texto = "Seleccionado: " + item.getTitulo();
                Toast toast = Toast.makeText(MainActivity.this, texto, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        //ArrayList<ItemList> peliculas=new ArrayList<ItemList>();
        //Cursor cursor=sqlite.rawQuery("SELECT * FROM PELICULA", null);
        //if(cursor.getCount()>0)
        //{
        //    while(cursor.moveToNext())
        //    {
        //        peliculas.add(cursor.getString(1));
        //    }
            //listViewAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,peliculas);
            //listViewAdapter=new MyAdapter(this, );
            //listView.setAdapter(listViewAdapter);
        //}
    }

    public void insert(String img, String titulo, String subtitulo, String fecha, String descripcion)
    {
        System.out.println(img);
        System.out.println(titulo);
        System.out.println(subtitulo);
        System.out.println(fecha);
        System.out.println(descripcion);
        sqlite.execSQL("INSERT INTO PELICULA (IMG, TITULO, SUBTITULO, FECHA, DESCRIPCION) VALUES ("+img+","+titulo+","+subtitulo+","+fecha+","+descripcion+");");
        loadDatas();
    }

    public void initComponents()
    {
        fullList=new FullList();
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
                //insert(intent.getStringExtra("IMG"), intent.getStringExtra("TITULO"), intent.getStringExtra("SUBTITULO"), intent.getStringExtra("DATE"), intent.getStringExtra("DESCRIPTION"));
                loadDatas();
                Toast.makeText(MainActivity.this, "INSERTED CORRECTLY", Toast.LENGTH_LONG).show();
            }
        }
    }

}
