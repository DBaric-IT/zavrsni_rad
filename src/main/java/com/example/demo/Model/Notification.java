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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int Id;
    //defining Message as column name
    @Column
    private String Message;
    //defining Subject as column name
    @Column
    private String Subject;
    //defining User Relationship
    @ManyToOne
    @JoinColumn(name="UserId", nullable=false)
    private User User;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public com.example.demo.Model.User getUser() {
        return User;
    }

    public void setUser(com.example.demo.Model.User user) {
        User = user;
    }
}