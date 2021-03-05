package com.example.demo.CustomModel;

import com.example.demo.Model.Measurement;
import com.example.demo.Model.RiverRegion;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CustomMeasurement {
    private int Id;
    private String Date;
    private String Value;
    private Boolean Danger;

    public CustomMeasurement() {}

    public CustomMeasurement(Measurement measurement, DateTimeFormatter myFormatObj) {
        this.setId(measurement.getId());
        this.setDate(measurement.getDate().toLocalDate().format(myFormatObj));
        this.setValue(measurement.getValue() + " cm");
    }

    public static List<CustomMeasurement> getMeasurementList(RiverRegion riverRegion){
        List<CustomMeasurement> measurementList = new ArrayList<CustomMeasurement>();

        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

        for (Measurement measurement : riverRegion.getMeasurements()){
            CustomMeasurement currentMeasurement = new CustomMeasurement(measurement, myFormatObj);

            currentMeasurement.setDanger((riverRegion.getUpperLevel().compareTo(measurement.getValue())) != 1);

            measurementList.add(currentMeasurement);
        }

        return measurementList;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public Boolean getDanger() {
        return Danger;
    }

    public void setDanger(Boolean danger) {
        Danger = danger;
    }
}
