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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Date;

public class EditGasStationFragment extends DialogFragment {
    interface EditGasStationDialogListener{
        void editGasStation();
    }

    private EditGasStationFragment.EditGasStationDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof EditGasStationFragment.EditGasStationDialogListener) {
            listener = (EditGasStationFragment.EditGasStationDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditGasStationDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_edit_gas_station, null);

        //to get main activity object, so we can access GasStation object
        MainActivity mainActivity = (MainActivity) getActivity();
        assert mainActivity != null;

        //getting that specific GasStation object from main activity
        GasStation gasStation = mainActivity.getGasStationObj();

        EditText gasStationName = view.findViewById(R.id.edit_text_add_gas_station_name);
        DatePicker gasStationDate = view.findViewById(R.id.edit_text_add_gas_station_date);
        RadioGroup gasStationFuelType = view.findViewById(R.id.edit_text_add_gas_station_fuel_type);
        EditText gasStationAil = view.findViewById(R.id.edit_text_add_gas_station_ail);
        EditText gasStationPpl = view.findViewById(R.id.edit_text_add_gas_station_ppl);

        gasStationName.setText(gasStation.getName());

        //need to keep either of the radiobuttons checked
        if (gasStation.getFuelType().equals("Diesel")){
            RadioButton gasolineRadioButton = view.findViewById(R.id.edit_text_add_gas_station_gasoline);
            gasolineRadioButton.setChecked(true);
        }else if(gasStation.getFuelType().equals("Gasoline")){
            RadioButton dieselRadioButton = view.findViewById(R.id.edit_text_add_gas_station_diesel);
            dieselRadioButton.setChecked(true);
        }
        gasStationAil.setText(String.valueOf(gasStation.getAmount()));
        gasStationPpl.setText(String.valueOf(gasStation.getPricePerLiter()));


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Edit Gas Station Visit")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Edit", (dialog, which) -> {
                    //all values are initially set to either 0 or none, to prevent null pointer exception from getText()

                    String name = "None";
                    try{
                        name = gasStationName.getText().toString();
                    }catch(Exception e){
                        System.out.println(e);
                    }

                    //getYear() provided wrong year if not subtracted by 1900
                    Date date = (Date) new Date(gasStationDate.getYear()-1900, gasStationDate.getMonth(), gasStationDate.getDayOfMonth());

                    //code for radiobutton
                    String type = "None";
                    int optionId = gasStationFuelType.getCheckedRadioButtonId();
                    RadioButton gasStationRadioButton = (RadioButton) view.findViewById(optionId);
                    try{
                        type = gasStationRadioButton.getText().toString();
                    }catch(Exception e){
                        System.out.println(e);
                    }

                    int ail = 0;
                    try{
                        ail = Integer.parseInt(gasStationAil.getText().toString());
                    }catch(Exception e){
                        System.out.println(e);
                    }

                    float ppl = 0.0f;
                    try{
                        ppl = Float.parseFloat(gasStationPpl.getText().toString());
                    }catch(Exception e){
                        System.out.println(e);
                    }

                    gasStation.setName(name);
                    gasStation.setDate(date);
                    gasStation.setFuelType(type);
                    gasStation.setAmount(ail);
                    gasStation.setPricePerLiter(Float.parseFloat(String.format("%.2f" , ppl)));

                    listener.editGasStation();
                })
                .create();
    }
}
