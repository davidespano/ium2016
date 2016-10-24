package com.example.tutor_ium.esercitazione_2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Tutor_IUM on 24/10/2016.
 */

public class DatePickerFragment extends DialogFragment {

    private Calendar date;

    private DatePickerFragmentListener listener;

    public Dialog onCreateDialog(Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);



        if(date==null){
            date = Calendar.getInstance();
            date.set(Calendar.YEAR, 1995);
            date.set(Calendar.MONTH, Calendar.JANUARY);
            date.set(Calendar.DAY_OF_MONTH, 1);
        }

        final DatePicker datePicker = new DatePicker(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(datePicker);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                date.set(Calendar.YEAR, datePicker.getYear());
                date.set(Calendar.MONTH, datePicker.getMonth());
                date.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());

                if(listener!=null){
                    listener.onDatePickerFragmentOkButton(DatePickerFragment.this, date);
                }
            }
        });

        builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(listener!=null){
                    listener.onDatePickerFragmentCancelButton(DatePickerFragment.this);
                }
            }
        }
        );

        return builder.create();
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setOnDatePickerFragmentChanged(DatePickerFragmentListener l){
        this.listener = l;
    }

    public interface DatePickerFragmentListener{
        public void onDatePickerFragmentOkButton( DialogFragment dialog, Calendar date );
        public void onDatePickerFragmentCancelButton( DialogFragment dialog );
    }
}
