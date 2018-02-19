package com.example.priyanka.mediatorvolunteer.Network;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by priyanka on 1/28/18.
 */

public class NotificationHandler extends FirebaseInstanceIdService {

    private FirebaseAuth mAuth;
    private String token;
    private String email;
    public NotificationHandler()
    {
        onTokenRefresh();
    }

    @Override
    public void onTokenRefresh() {
        mAuth = FirebaseAuth.getInstance();
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        token = refreshedToken;

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        if(refreshedToken != null && mAuth.getCurrentUser() != null)
            sendRegistrationToServer(refreshedToken);
    }

    public String getToken()
    {
        return token;
    }

    public void sendRegistrationToServer(String token)
    {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        email = currentUser.getEmail();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.sendRegistrationTokenToServer(token, email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
