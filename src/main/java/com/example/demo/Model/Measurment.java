package com.example.demo.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Date;

//mark class as an Entity
@Entity
//defining class name as Table name
@Table
public class Measurment
{
    //mark id as primary key
    @Id
    //defining id as column name
    @Column
    private int Id;
    //defining date as column name
    @Column
    private Date Date;
    //defining Value as column name
    @Column
    private BigDecimal Value;
    //defining RiverRegionId as column name
    @Column
    private int RiverRegionId;

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

    public int getRiverRegionId() {
        return RiverRegionId;
    }

    public void setRiverRegionId(int riverRegionId) {
        RiverRegionId = riverRegionId;
    }
}