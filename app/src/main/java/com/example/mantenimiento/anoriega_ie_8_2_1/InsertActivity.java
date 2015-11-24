package com.example.mantenimiento.anoriega_ie_8_2_1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class InsertActivity extends Activity implements ImageButton.OnClickListener, Date_Picker.OnFechaSeleccionada {

    private static final int SELECT_PICTURE = 1;

    public String selectedImagePath;

    ImageButton imageButton;
    Button buttonDate;
    TextView textViewDate;

    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        initComponents();
        initListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_insert, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initComponents()
    {
        imageButton= (ImageButton) findViewById(R.id.imageButtonInsert);
        buttonDate= (Button) findViewById(R.id.insert_buttonDate);
        textViewDate= (TextView) findViewById(R.id.insert_textViewDate);
    }

    public void initListeners()
    {
        imageButton.setOnClickListener(this);
        buttonDate.setOnClickListener(this);
    }

    public void insertDate(View v)
    {

    }

    public void insert(View v)
    {
        Intent i=new Intent();
        i.putExtra("VALUE", 1);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imageButtonInsert:
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                break;
            case R.id.insert_buttonDate:
                    Date_Picker date=new Date_Picker();
                    date.show(getFragmentManager(),"DIALOGO_FECHA");


                    //DialogFragment mDialog = new DialogFragment();
                    //mDialog.show(new DatePicker(this));
                    //DialogFragment dialogFragment = new DialogFragment();
                    //dialogFragment.show(getFragmentManager(), "start_date_picker");

                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                //selectedImagePath = getPath(selectedImageUri);
                imageButton.setImageURI(selectedImageUri);
            }
        }
    }

    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null )
        {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if(cursor!= null)
        {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

    @Override
    public void onResultadoFecha(GregorianCalendar fecha) {
        textViewDate.setText(fecha.get(Calendar.DAY_OF_MONTH)+"/"+(fecha.get(Calendar.MONTH)+1)+"/"+fecha.get(Calendar.YEAR));
    }
}
