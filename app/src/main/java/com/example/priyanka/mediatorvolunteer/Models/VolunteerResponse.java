package com.example.priyanka.mediatorvolunteer.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by priyanka on 1/24/18.
 */

public class VolunteerResponse {

    @SerializedName("Volunteer")
    private Volunteer volunteer;
    private final static long serialVersionUID = 5166597326015072679L;

    @SerializedName("id")
    private int id;

    public int getId() {return id;}

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

}
