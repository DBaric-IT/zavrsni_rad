package com.example.demo.Model;

import javax.persistence.*;
import java.math.BigDecimal;

//mark class as an Entity
@Entity
//defining class name as Table name
@Table
public class Notification
{
    //mark id as primary key
    @Id
    //defining Id as column name
    @Column
    private int Id;
    //defining Description as column name
    @Column
    private String Description;
    //defining Seen as column name
    @Column
    private Boolean Seen;
    //defining UserId as column name
    @Column
    private int UserId;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Boolean getSeen() {
        return Seen;
    }

    public void setSeen(Boolean seen) {
        Seen = seen;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}