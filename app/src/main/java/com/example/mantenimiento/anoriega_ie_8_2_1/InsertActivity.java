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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class InsertActivity extends Activity implements ImageButton.OnClickListener, Date_Picker.OnFechaSeleccionada {

    private static final int SELECT_PICTURE = 1;

    public String selectedImagePath;

    ImageButton imageButton;
    EditText editTextTitulo;
    EditText editTextSubTitulo;
    Button buttonDate;
    TextView textViewDate;
    EditText editTextDescripcion;

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
        editTextTitulo= (EditText) findViewById(R.id.insert_editTextTitle);
        editTextSubTitulo= (EditText) findViewById(R.id.insert_editTextSubTitle);
        buttonDate= (Button) findViewById(R.id.insert_buttonDate);
        textViewDate= (TextView) findViewById(R.id.insert_textViewDate);
        editTextDescripcion= (EditText) findViewById(R.id.insert_editTextDescripcion);
    }

    public void initListeners()
    {
        imageButton.setOnClickListener(this);
        buttonDate.setOnClickListener(this);
    }

    public void insert(View v)
    {
        if(checkDatas())
        {
            System.out.println(selectedImagePath);
            System.out.println(editTextTitulo.getText());
            System.out.println(editTextSubTitulo.getText());
            System.out.println(textViewDate.getText());
            System.out.println(editTextDescripcion.getText());
            System.out.println("TERMINA LOS SYSTEM DE INSERT");
            Intent i = new Intent();
            i.putExtra("IMG", selectedImagePath);
            i.putExtra("TITULO", editTextTitulo.getText());
            i.putExtra("SUBTITULO", editTextSubTitulo.getText());
            i.putExtra("DATE", textViewDate.getText());
            i.putExtra("DESCRIPTION", editTextDescripcion.getText());
            setResult(RESULT_OK);
            finish();
        }
        else
        {
            Toast.makeText(InsertActivity.this, "DATA ERRORS", Toast.LENGTH_LONG).show();
        }
    }

    public boolean checkDatas()
    {
        boolean correcto=false;
        boolean img=true;
        boolean titulo=false;
        boolean subtitulo=false;
        boolean fecha=false;
        boolean descripcion=false;

        if(editTextTitulo.getText().length()>0 && editTextTitulo.getText().length()<=30)
        {
            titulo=true;
        }
        if(editTextSubTitulo.getText().length()>0 && editTextSubTitulo.getText().length()<=30)
        {
            subtitulo=true;
        }
        if(textViewDate.getText().length()>0 && textViewDate.getText().length()<=10)
        {
            fecha=true;
        }
        if(editTextDescripcion.getText().length()>0 && editTextDescripcion.getText().length()<=100)
        {
            descripcion=true;
        }
        if(titulo==true && subtitulo==true && fecha==true && descripcion==true)
        {
            correcto=true;
        }
        return correcto;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imageButtonInsert:
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "RESULT_OK"), SELECT_PICTURE);
                break;
            case R.id.insert_buttonDate:
                    Date_Picker date=new Date_Picker();
                    date.show(getFragmentManager(),"DIALOGO_FECHA");
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                //selectedImagePath = getPath(selectedImageUri);
                //System.out.println("Path del uri: "+selectedImageUri.getPath());
                selectedImagePath = selectedImageUri.getPath();
                imageButton.setImageURI(selectedImageUri);
            }
        }
    }

    public String getPath(Uri uri) {
        if( uri == null )
        {
            System.out.println("Ha entrado en el uri=null");
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if(cursor!= null)
        {
            System.out.println("Ha entrado en el cursor!=null");
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
