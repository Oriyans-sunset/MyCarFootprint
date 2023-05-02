package com.example.mycarfootprint;

import java.util.Date;

public class GasStation {

    private String name;
    private Date date;
    private String fuelType;
    private int amount;
    private float pricePerLiter;

    public GasStation(String name, Date date, String gasType, int amount, Float pricePerLiter) {
        this.name = name;
        this.date = date;
        this.fuelType = gasType;
        this.amount = amount;
        this.pricePerLiter = pricePerLiter;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getFuelType() {
        return fuelType;
    }

    public int getAmount() {
        return amount;
    }

    public float getPricePerLiter() {
        return pricePerLiter;
    }

    public float getCarbonFootprint(){

        //gasoline and diesel have diffrent carbon cofficients, therefore amount calculated is differnt for both
        if(fuelType.equals("Gasoline")){
            return Math.round(((float)(2.32 * amount)));
        }else if(fuelType.equals("Diesel")){
            return Math.round(((float)(2.69 * amount)));
        }else{
            return 0.0f;
        }
    }

    public float getFuelCost(){
        float fuel_cost = (float) (amount * pricePerLiter);

        //rounding to 2 decimals
        fuel_cost = fuel_cost*100;
        fuel_cost = Math.round(fuel_cost);
        fuel_cost = fuel_cost /100;

        return fuel_cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPricePerLiter(float pricePerLiter) {
        this.pricePerLiter = pricePerLiter;
    }
}
