package com.example.demo.CustomModel;

import com.example.demo.Model.Measurement;
import com.example.demo.Model.RiverRegion;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CustomMeasurement {
    private int Id;
    private String Date;
    private String Value;
    private Boolean Danger;
    private int RiverRegionId;

    public CustomMeasurement() {}

    public CustomMeasurement(Measurement measurement, DateTimeFormatter myFormatObj) {
        this.setId(measurement.getId());
        this.setDate(measurement.getDate().toLocalDate().format(myFormatObj));
        this.setValue(measurement.getValue() + " cm");
        this.setRiverRegionId(measurement.getRiverRegion().getId());
    }

    public static List<CustomMeasurement> getMeasurementList(RiverRegion riverRegion){
        List<CustomMeasurement> measurementList = new ArrayList<CustomMeasurement>();

        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

        List<Measurement> measurements = riverRegion.getMeasurements();

        Collections.sort(measurements, new Comparator<Measurement>() {
            public int compare(Measurement m1, Measurement m2) {
                return m2.getDate().compareTo(m1.getDate());
            }
        });

        for (Measurement measurement : measurements){
            CustomMeasurement currentMeasurement = new CustomMeasurement(measurement, myFormatObj);

            currentMeasurement.setDanger((riverRegion.getUpperLevel().compareTo(measurement.getValue())) != 1);
            currentMeasurement.setRiverRegionId(riverRegion.getId());

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

    public int getRiverRegionId() {
        return RiverRegionId;
    }

    public void setRiverRegionId(int riverRegionId) {
        RiverRegionId = riverRegionId;
    }
}
