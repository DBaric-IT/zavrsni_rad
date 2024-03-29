package com.example.demo.Model;

import com.example.demo.CustomModel.CustomMeasurement;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

//mark class as an Entity
@Entity
//defining class name as Table name
@Table
public class Measurement
{
    //mark id as primary key
    @Id
    //defining id as column name
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int Id;
    //defining date as column name
    @Column
    private Date Date;
    //defining Value as column name
    @Column
    private BigDecimal Value;
    //defining RiverRegion Relationship
    @ManyToOne
    @JoinColumn(name="RiverRegionId", nullable=false)
    private RiverRegion RiverRegion;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public java.sql.Date getDate() {
        return Date;
    }

    public void setDate(java.sql.Date date) {
        Date = date;
    }

    public BigDecimal getValue() {
        return Value;
    }

    public void setValue(BigDecimal value) {
        Value = value;
    }

    public com.example.demo.Model.RiverRegion getRiverRegion() {
        return RiverRegion;
    }

    public void setRiverRegion(com.example.demo.Model.RiverRegion riverRegion) {
        RiverRegion = riverRegion;
    }

    public Measurement() {

    }

    public Measurement(CustomMeasurement customMeasurement) {
        this.setId(customMeasurement.getId());
        this.setValue(new BigDecimal(customMeasurement.getValue()));
        try {
            this.setDate(new java.sql.Date(new SimpleDateFormat("dd.MM.yyyy.").parse(customMeasurement.getDate()).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}