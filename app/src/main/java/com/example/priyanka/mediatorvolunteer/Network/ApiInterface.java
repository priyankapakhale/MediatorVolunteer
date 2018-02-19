package com.example.priyanka.mediatorvolunteer.Network;

import com.example.priyanka.mediatorvolunteer.Models.NGOPickupRequestResponse;
import com.example.priyanka.mediatorvolunteer.Models.PickupRequestResponse;
import com.example.priyanka.mediatorvolunteer.Models.UserResponse;
import com.example.priyanka.mediatorvolunteer.Models.VolunteerResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by priyanka on 1/23/18.
 */

public interface ApiInterface {

    @POST("add_volunteer/")
    @FormUrlEncoded
    Call<ResponseBody> addUser(@Field("name") String name, @Field("contact_no") String contact_no,
                               @Field("address") String address, @Field("area") String area, @Field("email_id") String email_id, @Field("password") String password);

    @POST("get_volunteer/")
    @FormUrlEncoded
    Call<VolunteerResponse> getVolunteer(@Field("email_id") String email_id);

    @POST("get_pickup_requests/")
    @FormUrlEncoded
    Call<PickupRequestResponse> getPickupRequests(@Field("email_id") String email_id);

    @POST("get_order_requests/")
    @FormUrlEncoded
    Call<PickupRequestResponse> getOrderRequests(@Field("email_id") String email_id);

    @POST("get_deliver_requests/")
    @FormUrlEncoded
    Call<PickupRequestResponse> getDeliverRequests(@Field("email_id") String email_id);

    @POST("get_deliver_orders/")
    @FormUrlEncoded
    Call<PickupRequestResponse> getDeliverOrders(@Field("email_id") String email_id);

    @POST("get_user_from_userid/")
    @FormUrlEncoded
    Call<UserResponse> getUserFromUserId(@Field("user_id") int user_id);

    @POST("send_registration_token_to_server/")
    @FormUrlEncoded
    Call<ResponseBody> sendRegistrationTokenToServer(@Field("token") String token, @Field("email_id") String email_id);

    @POST("fcm/v1/devices/")
    @FormUrlEncoded
    Call<ResponseBody> addDevice(@Field("dev_id") String device_id, @Field("reg_id") String reg_id,@Field("name") String name);

    @POST("assign_volunteer_to_pickup_request/")
    @FormUrlEncoded
    Call<ResponseBody> assignVolunteerToPickupRequest(@Field("donation_id") int request_id , @Field("email_id") String email_id);

    @POST("update_pickup_request/")
    @FormUrlEncoded
    Call<ResponseBody> updatePickupRequest(@Field("donation_id") int request_id, @Field("count") int count);

    @POST("update_order_request/")
    @FormUrlEncoded
    Call<ResponseBody> updateOrderRequest(@Field("order_id") int request_id);


    @POST("assign_volunteer_to_order_request/")
    @FormUrlEncoded
    Call<ResponseBody> assignVolunteerToOrderRequest(@Field("order_id") int request_id , @Field("email_id") String email_id);

    @POST("get_all_pickup_requests/")
    @FormUrlEncoded
    Call<PickupRequestResponse> getAllPickupRequests(@Field("email_id") String email_id);

    @POST("update_request_delivered/")
    @FormUrlEncoded
    Call<ResponseBody> updateRequestDelivered(@Field("donation_id") int donation_id);

    @POST("update_order_delivered/")
    @FormUrlEncoded
    Call<ResponseBody> updateOrderDelivered(@Field("order_id") int order_id);



}
