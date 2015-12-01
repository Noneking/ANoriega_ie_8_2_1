package com.example.mantenimiento.anoriega_ie_8_2_1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends Activity implements MenuItem.OnMenuItemClickListener, ListView.OnItemClickListener, ListView.OnItemLongClickListener {

    ListView listView;
    MenuItem insert_menuItem;
    MenuItem modify_menuItem;
    MenuItem delete_menuItem;

    ArrayAdapter<String> listViewAdapter;
    SQLiteDatabase sqlite;

    Database database;
    FullList fullList;

    Intent intent;

    int finished=0;

    public static final int VALUE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database=new Database();

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
        //sqlite.execSQL("DROP TABLE PELICULA");
        sqlite.execSQL("CREATE TABLE IF NOT EXISTS PELICULA(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "IMG VARCHAR(100)," +
                "TITULO VARCHAR(30) UNIQUE," +
                "SUBTITULO VARCHAR(30)," +
                "FECHA VARCHAR(9)," +
                "DESCRIPCION VARCHAR(100)" +
                ");");
        database.setDatabase(sqlite);
    }

    public void loadDatas()
    {
        Cursor cursor=database.getDatabase().rawQuery("SELECT * FROM PELICULA;", null);
        ArrayList<ItemList> arrayList=new ArrayList<ItemList>();
        while(cursor.moveToNext())
        {
            String img=cursor.getString(cursor.getColumnIndex("IMG"));
            String titulo=cursor.getString(cursor.getColumnIndex("TITULO"));
            String subtitulo=cursor.getString(cursor.getColumnIndex("SUBTITULO"));
            String fecha=cursor.getString(cursor.getColumnIndex("FECHA"));
            String descripcion=cursor.getString(cursor.getColumnIndex("DESCRIPCION"));
            ItemList item=new ItemList(img, titulo, subtitulo, fecha, descripcion);
            arrayList.add(item);
        }
        fullList.setArrayList(arrayList);
        listView.setAdapter(new MyAdapter(this, R.layout.activity_item_list, fullList.getArrayList()) {
            @Override
            public void onEntrada(Object entrada, View view) {
                if(entrada!=null)
                {
                    ImageView img= (ImageView) view.findViewById(R.id.imageViewItem);
                    if(img!=null)
                    {
                        try {

                            //FileInputStream fis=new FileInputStream("/data/data/com.example.mantenimiento.anoriega_ie_8_2_1/files/"+img);
                            FileInputStream fis=new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"imageFolder"+ File.separator+img);
                            img.setImageBitmap(BitmapFactory.decodeStream(fis));
                            //String photoPath = Environment.getExternalStorageDirectory()+"/data/data/com.example.mantenimiento.anoriega_ie_8_2_1/files/"+img;
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        //Bitmap myBitmap = BitmapFactory.decodeFile(((ItemList) entrada).getPathImagen());
                        //img.setImageBitmap(myBitmap);

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

        /**
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemList item= (ItemList) parent.getItemAtPosition(position);
                CharSequence texto = "Seleccionado: " + item.getTitulo();
                Toast toast = Toast.makeText(MainActivity.this, texto, Toast.LENGTH_SHORT);
                toast.show();

            }
        });*/

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
        database.getDatabase().execSQL("INSERT INTO PELICULA (IMG, TITULO, SUBTITULO, FECHA, DESCRIPCION) VALUES (" + img + "," + titulo + "," + subtitulo + "," + fecha + "," + descripcion + ");");
        loadDatas();
    }

    public void initComponents()
    {
        fullList=new FullList();
        listView= (ListView) findViewById(R.id.main_listView);
    }

    public void initListeners()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemList itemSelected = (ItemList) parent.getItemAtPosition(position);
                itemSelected.getTitulo();

                Cursor cursor = database.getDatabase().rawQuery("SELECT * FROM PELICULA WHERE TITULO='" + itemSelected.getTitulo() + "';", null);
                cursor.moveToFirst();

                itemClick(cursor);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Entra en el item long click listener");

                ItemList itemSelected= (ItemList) parent.getItemAtPosition(position);
                itemSelected.getTitulo();

                database.getDatabase().execSQL("DELETE FROM PELICULA WHERE TITULO='" + itemSelected.getTitulo() + "';");
                Toast toast = Toast.makeText(MainActivity.this, "DELETE CORRECTLY", Toast.LENGTH_SHORT);
                toast.show();
                loadDatas();
                return false;
            }
        });
    }

    public void itemClick(Cursor cursor)
    {
        String img=cursor.getString(cursor.getColumnIndex("IMG"));
        String titulo=cursor.getString(cursor.getColumnIndex("TITULO"));
        String subtitulo=cursor.getString(cursor.getColumnIndex("SUBTITULO"));
        String fecha=cursor.getString(cursor.getColumnIndex("FECHA"));
        String descripcion=cursor.getString(cursor.getColumnIndex("DESCRIPCION"));

        Intent i = new Intent(this, ViewActivity.class);
        i.putExtra("IMG", img);
        i.putExtra("TITULO", titulo);
        i.putExtra("SUBTITULO", subtitulo);
        i.putExtra("FECHA", fecha);
        i.putExtra("DESCRIPCION", descripcion);
        startActivityForResult(i, finished);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        System.out.println("ESTAS PINCHANDO");
        ItemList itemSelected= (ItemList) item;
        itemSelected.getTitulo();

        Cursor cursor=database.getDatabase().rawQuery("SELECT * FROM PELICULA WHERE TITULO='" + itemSelected.getTitulo() + "';", null);
        cursor.moveToFirst();

        String img=cursor.getString(cursor.getColumnIndex("IMG"));
        String titulo=cursor.getString(cursor.getColumnIndex("TITULO"));
        String subtitulo=cursor.getString(cursor.getColumnIndex("SUBTITULO"));
        String fecha=cursor.getString(cursor.getColumnIndex("FECHA"));
        String descripcion=cursor.getString(cursor.getColumnIndex("DESCRIPCION"));

        intent = new Intent(this, ViewActivity.class);
        intent.putExtra("IMG", img);
        intent.putExtra("TITULO", titulo);
        intent.putExtra("SUBTITULO", subtitulo);
        intent.putExtra("FECHA", fecha);
        intent.putExtra("DESCRIPCION", descripcion);
        startActivityForResult(intent, finished);


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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        return false;
    }
}
