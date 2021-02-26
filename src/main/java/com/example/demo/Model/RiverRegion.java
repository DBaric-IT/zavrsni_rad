package com.example.demo.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

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
    //defining RiverId as column name
    @Column
    private int RiverId;

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

    public int getRiverId() {
        return RiverId;
    }

    public void setRiverId(int riverId) {
        RiverId = riverId;
    }

    public int getRegionId() {
        return RegionId;
    }

    public void setRegionId(int regionId) {
        RegionId = regionId;
    }

    //defining RegionId as column name
    @Column
    private int RegionId;
}