package com.example.mantenimiento.anoriega_ie_8_2_1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Date_Picker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    OnFechaSeleccionada f;
    @Override
    public void onAttach(Activity activity) {
        f=(OnFechaSeleccionada)activity;
        super.onAttach(activity);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar c=Calendar.getInstance();
        int año=c.get(Calendar.YEAR);
        int mes=c.get(Calendar.MONTH);
        int dia=c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),this,año,mes,dia);
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int i, int i2, int i3) {
        GregorianCalendar g=new GregorianCalendar(i,i2,i3);
        f.onResultadoFecha(g);
    }

    public interface OnFechaSeleccionada{
        public void onResultadoFecha(GregorianCalendar fecha);
    }

}
