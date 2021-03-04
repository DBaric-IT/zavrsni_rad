package com.example.demo.Model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

//mark class as an Entity
@Entity
//defining class name as Table name
@Table
public class RiverRegion
{
    //mark id as primary key
    @Id
    //defining id as column name
    @Column
    private int Id;
    //defining UpperLevel as column name
    @Column
    private BigDecimal UpperLevel;
    //defining Latitude as column name
    @Column
    private BigDecimal Latitude;
    //defining UpperLevel as column name
    @Column
    private BigDecimal Longitude;
    @ManyToOne
    @JoinColumn(name="RiverId")
    private River River;
    //defining Region Relationship
    @ManyToOne
    @JoinColumn(name="RegionId")
    private Region Region;
    //defining Measurement Relationship
    @OneToMany(mappedBy="RiverRegion")
    private List<Measurement> measurements;

    public BigDecimal getLatitude() {
        return Latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        Latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return Longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        Longitude = longitude;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public BigDecimal getUpperLevel() {
        return UpperLevel;
    }

    public void setUpperLevel(BigDecimal upperLevel) {
        UpperLevel = upperLevel;
    }

    public com.example.demo.Model.River getRiver() {
        return River;
    }

    public void setRiver(com.example.demo.Model.River river) {
        River = river;
    }

    public com.example.demo.Model.Region getRegion() {
        return Region;
    }

    public void setRegion(com.example.demo.Model.Region region) {
        Region = region;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }
}