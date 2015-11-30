package com.example.mantenimiento.anoriega_ie_8_2_1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewActivity extends Activity {

    ImageView imageView;
    TextView textViewTitulo;
    TextView textViewSubTitulo;
    TextView textViewFecha;
    TextView textViewDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        imageView= (ImageView) findViewById(R.id.imageViewView);
        textViewTitulo= (TextView) findViewById(R.id.view_textViewTitle);
        textViewSubTitulo= (TextView) findViewById(R.id.view_textViewSubTitle);
        textViewFecha= (TextView) findViewById(R.id.view_textViewDate);
        textViewDescripcion= (TextView) findViewById(R.id.view_textViewDescripcion);

        Intent intent=getIntent();
        //imageView.setImageBitmap(new Bitmap());
        textViewTitulo.setText(intent.getStringExtra("TITULO"));
        textViewSubTitulo.setText(intent.getStringExtra("SUBTITULO"));
        textViewFecha.setText(intent.getStringExtra("FECHA"));
        textViewDescripcion.setText(intent.getStringExtra("DESCRIPCION"));

    }
}
