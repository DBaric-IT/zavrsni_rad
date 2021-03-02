package com.example.demo.Model;

import javax.persistence.*;

//mark class as an Entity
@Entity
//defining class name as Table name
@Table
public class User
{
    //mark id as primary key
    @Id
    //defining id as column name
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    //@SequenceGenerator(name = "id_Sequence", sequenceName = "ORACLE_DB_SEQ_ID")
    private int Id;
    //defining name as column name
    @Column
    private String Name;
    //defining age as column name
    @Column
    private String Surname;
    //defining email as column name
    @Column
    private String Email;
    //defining address as column name
    @Column
    private String Address;
    //defining password as column name
    @Column
    private String Password;
    //defining role as column name
    @Column
    private Integer RoleId;
    //defining Region as column name
    @Column
    private Integer RegionId;
    //defining Newsletter as column name
    @Column
    private boolean Newsletter;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Integer getRoleId() {
        return RoleId;
    }

    public void setRoleId(Integer roleId) {
        RoleId = roleId;
    }

    public Integer getRegionId() {
        return RegionId;
    }

    public void setRegionId(Integer regionId) {
        RegionId = regionId;
    }

    public boolean isNewsletter() {
        return Newsletter;
    }

    public void setNewsletter(boolean newsletter) {
        Newsletter = newsletter;
    }

}