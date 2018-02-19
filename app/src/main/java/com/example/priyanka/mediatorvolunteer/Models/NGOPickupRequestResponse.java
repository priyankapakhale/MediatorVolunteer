package com.example.priyanka.mediatorvolunteer.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by priyanka on 2/2/18.
 */

public class NGOPickupRequestResponse implements Serializable {

    @SerializedName("ngo_pickup_request_list")
    private List<NGOPickupRequest> requestList;

    public List<NGOPickupRequest> getNGOPickupRequests() {
        return requestList;
    }

    public void setNGOPickupRequests(List<NGOPickupRequest> requestList) {
        this.requestList = requestList;
    }

}
