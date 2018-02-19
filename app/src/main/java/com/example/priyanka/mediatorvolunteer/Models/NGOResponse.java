package com.example.priyanka.mediatorvolunteer.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by priyanka on 1/31/18.
 */

public class NGOResponse {

    @SerializedName("NGO")
    private NGO ngo;

    @SerializedName("ngo_id")
    private int id;

    public int getId() {return id;}

    public NGO getNGO() {
        return ngo;
    }

    public void setNGO(NGO ngo) {
        this.ngo = ngo;
    }
}
