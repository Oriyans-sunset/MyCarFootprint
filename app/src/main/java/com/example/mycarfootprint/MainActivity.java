package com.example.mycarfootprint;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddGasStationFragment.AddGasStationDialogListener, EditGasStationFragment.EditGasStationDialogListener {

    private ArrayList<GasStation> gasStationList;
    private ListView gasStationListView;
    private GasStationArrayAdapter gasStationAdapter;
    private GasStation gasStation;

    private TextView totalCarbonFootprint;
    private TextView totalFuelCost;
    int total_carbon_footprint = 0;
    float total_fuel_cost = 0.0f;

    /**
     * Adds gasStation instance to the array adapter
     * @param gasStation The instance of GasStation class.
     * @return None.
     */
    @Override
    public void addGasStation(GasStation gasStation){
        gasStationAdapter.add(gasStation);
        gasStationAdapter.notifyDataSetChanged();

        totalCarbonFootprint = findViewById(R.id.total_carbon_footprint);
        totalFuelCost = findViewById(R.id.total_fuel_cost);

        totalCarbonFootprint.setText(String.valueOf(updateTotalCarbon()));
        totalFuelCost.setText(String.format("%.2f", updateTotalFuelCost()));
    }

    /**
     * Notifies adapter to the changes in the instance
     * @return None.
     */
    @Override
    public void editGasStation(){
        gasStationAdapter.notifyDataSetChanged();

        totalCarbonFootprint.setText(String.valueOf(updateTotalCarbon()));
        totalFuelCost.setText(String.format("%.2f", updateTotalFuelCost()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gasStationList = new ArrayList<GasStation>();
        gasStationListView = findViewById(R.id.gas_station_list_view);
        gasStationAdapter = new GasStationArrayAdapter(this, gasStationList);
        gasStationListView.setAdapter(gasStationAdapter);

        //calls add fragment on fab click
        FloatingActionButton fab = findViewById(R.id.add_gas_station_btn);
        fab.setOnClickListener(v -> {
            new AddGasStationFragment().show(getSupportFragmentManager(), "Add Gas Station");
        });

        //calls edit fragment on list item click
        gasStationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gasStation = gasStationList.get(i);
                new EditGasStationFragment().show(getSupportFragmentManager(), "Edit Gas Station");
            }
        });

        //deletes items from list on long click,
        //long press to delete seems intutive from UX standpoint,
        //showing dialog box before deleting to gives user the option to change their decision
        //code collaboration for this part: samrathj(CCID)
        gasStationListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int itemToDelete = position;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_menu_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this trip?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                gasStationList.remove(itemToDelete);
                                gasStationAdapter.notifyDataSetChanged();
                                totalCarbonFootprint = findViewById(R.id.total_carbon_footprint);
                                totalFuelCost = findViewById(R.id.total_fuel_cost);

                                totalCarbonFootprint.setText(String.valueOf(updateTotalCarbon()));
                                totalFuelCost.setText(String.format("%.2f", updateTotalFuelCost()));
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });

    }

    /**
     * Updates total carbon footprint using data from all trips
     * @return total carbon footprint
     */
    public int updateTotalCarbon(){
        int carbon_footprint_sum = 0;
        for(int j = 0; j < gasStationList.size(); j++){
            carbon_footprint_sum += gasStationList.get(j).getCarbonFootprint();
        }

        return carbon_footprint_sum;
    }

    /**
     * Updates total fuel cost using data from all trips
     * @return total fuel cost
     */
    public float updateTotalFuelCost(){
        float fuel_cost_sum = 0.0f;
        for(int j = 0; j < gasStationList.size(); j++){
            fuel_cost_sum += gasStationList.get(j).getFuelCost();
        }

        return fuel_cost_sum;
    }

    /**
     * Returns a specific GasStation object, to allow fragment access to the object.
     * @return a GasStation object
     */
    public GasStation getGasStationObj(){
        return gasStation;
    }
}