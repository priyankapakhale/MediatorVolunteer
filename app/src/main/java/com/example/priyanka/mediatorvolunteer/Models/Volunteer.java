package com.example.priyanka.mediatorvolunteer.Models;

/**
 * Created by priyanka on 1/22/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Volunteer implements Serializable{

    @SerializedName("volunteer_id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("contact_no")
    @Expose
    private String contact_no;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("email_id")
    @Expose
    private String email_id;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("area")
    @Expose
    private String area;

    public Volunteer()
    {}

    public Volunteer(String name, String contact_no, String address, String area, String email_id, String password)
    {
        this.name = name;
        this.contact_no = contact_no;
        this.address = address;
        this.email_id = email_id;
        this.password = password;
        this.area = area;
    }

    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setContactNo(String contact_no) {this.contact_no = contact_no;}
    public void setAddress(String address) {this.address = address;}
    public void setEmailId(String email_id) {this.email_id = email_id;}
    public void setPassword(String password){this.password = password;}
    public void setArea(String area) {this.area = area;}

    public int getId() {return id;}
    public String getName() {return name;}
    public String getContactNo() {return contact_no;}
    public String getAddress() {return address;}
    public String getEmailId() {return email_id;}
    public String getPassword() {return password;}
    public String getArea() {return area;}

}
