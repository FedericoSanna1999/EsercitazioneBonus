// Federico Sanna (65614)

package com.example.esercitazionebonus;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    public static DatePickerFragment newInstance() {
        DatePickerFragment datePickerfragment = new DatePickerFragment();
        datePickerfragment.setArguments(new Bundle());

        return datePickerfragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        FragmentActivity activity = getActivity();
        DatePicker datePicker = new DatePicker(activity);
        Calendar calendar = Calendar.getInstance();

        return new AlertDialog.Builder(activity).setView(datePicker)
            .setPositiveButton(R.string.alert_dialog_ok, (dialog, whichButton) -> {
                calendar.set(Calendar.YEAR, datePicker.getYear());
                calendar.set(Calendar.MONTH, datePicker.getMonth());
                calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());

                if (activity != null)
                    ((RegistrazioneActivity) activity).doPositiveClick(calendar);
            }).setNegativeButton(R.string.alert_dialog_cancel, (dialog, whichButton) -> {
                if (activity != null)
                    ((RegistrazioneActivity) activity).doNegativeClick();
            }).create();
    }
}