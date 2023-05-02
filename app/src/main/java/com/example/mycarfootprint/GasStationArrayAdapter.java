package com.example.mycarfootprint;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class GasStationArrayAdapter extends ArrayAdapter<GasStation>{

    public GasStationArrayAdapter(Context context, ArrayList<GasStation> gasStationList) {
        super(context, 0, gasStationList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;

        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.content, parent, false);
        }else{
            view = convertView;
        }

        GasStation gasStation = getItem(position);
        TextView gasStationName = view.findViewById(R.id.gas_station_name);
        TextView gasStationDate = view.findViewById(R.id.gas_station_date);
        TextView gasStationFuelType = view.findViewById(R.id.gas_station_fuel_type);
        TextView gasStationAil = view.findViewById(R.id.gas_station_Ail);
        TextView gasStationPpl = view.findViewById(R.id.gas_station_Ppl);
        TextView gasStationFuelCost = view.findViewById(R.id.gas_station_fuel_cost);
        TextView gasStationCarbonFootprint = view.findViewById(R.id.gas_station_carbon_footprint);

        gasStationName.setText(gasStation.getName());

        //formatting date to the required format
        Date date = gasStation.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fDate = sdf.format(date);
        gasStationDate.setText(fDate);

        gasStationFuelType.setText(gasStation.getFuelType());
        gasStationAil.setText(String.valueOf(gasStation.getAmount()));
        gasStationPpl.setText(String.format("%.2f", gasStation.getPricePerLiter()));

        float fuel_cost = gasStation.getFuelCost();
        gasStationFuelCost.setText(String.format("%.2f", fuel_cost));

        //formatted using "%.0f" because carbon footprint had to be single integer without decimals behind
        gasStationCarbonFootprint.setText(String.format("%.0f", gasStation.getCarbonFootprint()));


        return view;

    }
}
