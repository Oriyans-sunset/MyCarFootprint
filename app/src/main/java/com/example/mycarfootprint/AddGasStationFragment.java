package com.example.mycarfootprint;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddGasStationFragment extends DialogFragment {

    interface AddGasStationDialogListener{
        void addGasStation(GasStation gasStation);
    }

    private AddGasStationDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AddGasStationDialogListener) {
            listener = (AddGasStationDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddGasStationDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_gas_station, null);

        EditText gasStationName = view.findViewById(R.id.edit_text_add_gas_station_name);
        DatePicker gasStationDate = view.findViewById(R.id.edit_text_add_gas_station_date);
        RadioGroup gasStationFuelType = view.findViewById(R.id.edit_text_add_gas_station_fuel_type);
        EditText gasStationAil = view.findViewById(R.id.edit_text_add_gas_station_ail);
        EditText gasStationPpl = view.findViewById(R.id.edit_text_add_gas_station_ppl);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Add a Gas Station Visit")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", (dialog, which) -> {
                    String name = gasStationName.getText().toString();
                    if(name.equals("")) {name = "None";} //to prevent empty string from crashing the app

                    Date date = (Date) new Date(gasStationDate.getYear()-1900, gasStationDate.getMonth(), gasStationDate.getDayOfMonth());

                    //code to see which radiobutton is checked, and store its value
                    //if none of the radiobuttons are checked, a default None value is stored
                    String type = "None";
                    int optionId = gasStationFuelType.getCheckedRadioButtonId();
                    RadioButton gasStationRadioButton = (RadioButton) view.findViewById(optionId);
                    try{
                        type = gasStationRadioButton.getText().toString();
                    }catch(Exception e){
                        System.out.println(e);
                    }

                    //enclosed in try-catch block to prevent null pointer exception from getText()
                    int ail = 0;
                    try{
                        ail = Integer.parseInt(gasStationAil.getText().toString());
                    }catch(Exception e){
                        System.out.println(e);
                    }

                    //enclosed in try-catch block to prevent null pointer exception from getText()
                    float ppl = 0.0f;
                    try{
                        //string has been formatted to 2 decimal places
                        ppl = Float.parseFloat(String.format("%.2f" , Float.parseFloat(gasStationPpl.getText().toString())));
                    }catch(Exception e){
                        System.out.println(e);
                    }


                    listener.addGasStation(new GasStation(name, date, type, ail, ppl));
                })
                .create();
    }
}
