package com.example.priyanka.mediatorvolunteer.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by priyanka on 1/28/18.
 */

public class PickupRequestResponse implements Serializable {

    private String type = "";

    @SerializedName("pickup_request_list_user")
    private List<PickupRequest> requestList1;

    @SerializedName("pickup_request_list_ngo")
    private List<PickupRequest> requestList2;

    @SerializedName("pickup_request_list")
    private List<PickupRequest> requestList;

    public List<PickupRequest> getPickupRequestsUser() {
        if(requestList1!=null)
        {
            for(int i = 0;i<requestList1.size();i++)
            {
                requestList1.get(i).setType("user");
            }
        }
        return requestList1;
    }

    public List<PickupRequest> getPickupRequestsNGO() {
        if(requestList2!=null)
        {
            for(int i = 0;i<requestList2.size();i++)
            {
                requestList2.get(i).setType("ngo");
            }
        }
        return requestList2;
    }

    public List<PickupRequest> getPickupRequests() {return  requestList;}

    public void setType(String type){this.type = type;}
    public String getType() {return type;}

}
