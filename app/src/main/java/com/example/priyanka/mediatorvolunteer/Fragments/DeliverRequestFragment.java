package com.example.priyanka.mediatorvolunteer.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.priyanka.mediatorvolunteer.Adaptors.DeliverRequestAdaptor;
import com.example.priyanka.mediatorvolunteer.Adaptors.PickupRequestAdaptor;
import com.example.priyanka.mediatorvolunteer.Models.PickupRequest;
import com.example.priyanka.mediatorvolunteer.Models.PickupRequestResponse;
import com.example.priyanka.mediatorvolunteer.Network.ApiClient;
import com.example.priyanka.mediatorvolunteer.Network.ApiInterface;
import com.example.priyanka.mediatorvolunteer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by priyanka on 1/29/18.
 */

public class DeliverRequestFragment extends Fragment {

    private DeliverRequestAdaptor prAdaptor;
    private Context context;
    private RecyclerView recList;
    private LinearLayout coordinatorLayout;
    private FirebaseAuth mAuth;
    private String email_id;
    public DeliverRequestFragment()
    {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        context = getContext();
        final View view = inflater.inflate(R.layout.deliver_request_fragment, container, false);

        //recList.addItemDecoration(new SimpleListDividerDecorator(ContextCompat.getDrawable(this, R.drawable.list_divider), true));

        mAuth = FirebaseAuth.getInstance();


        FirebaseUser currentUser = mAuth.getCurrentUser();
        email_id = currentUser.getEmail();

        recList = (RecyclerView) view.findViewById(R.id.pickup_request_list);
        coordinatorLayout = view.findViewById(R.id.coordinator_layout);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        recList.setItemAnimator(new DefaultItemAnimator());
        recList.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        // making http call and fetching menu json
        //TODO: Code to call another activity for showing the transaction full details will be called from here
        List<PickupRequest> allDeliverRequests = new ArrayList<>();


        //Calling Server
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<PickupRequestResponse> call = apiService.getDeliverRequests(email_id); //change user id later
        call.enqueue(new Callback<PickupRequestResponse>() {
            @Override
            public void onResponse(Call<PickupRequestResponse> call, Response<PickupRequestResponse> response) {
                if(response.body() != null) {

                    List<PickupRequest> thisa = response.body().getPickupRequestsUser();
                    if(thisa != null) {
                        Log.d("Deliver user",thisa.toString());

                        for (int i = 0; i < thisa.size(); i++) {
                            thisa.get(i).setType("user");
                        }
                        allDeliverRequests.addAll(thisa);
                    }
                    else
                    {
                        Log.d("Deliver user","null");
                    }
                }
            }

            @Override
            public void onFailure(Call<PickupRequestResponse> call, Throwable t) {
            }
        });


        Call<PickupRequestResponse> call2 = apiService.getDeliverOrders(email_id);
        call2.enqueue(new Callback<PickupRequestResponse>() {
            @Override
            public void onResponse(Call<PickupRequestResponse> call, Response<PickupRequestResponse> response) {
                if(response.body()!=null)
                {
                    List<PickupRequest> thisa = response.body().getPickupRequestsNGO();
                    if(thisa != null){
                        Log.d("Deliver ngo",thisa.toString());

                        for(int i = 0;i<thisa.size();i++)
                        {
                            thisa.get(i).setType("ngo");
                        }

                        allDeliverRequests.addAll(thisa);
                    }
                    else
                    {
                        Log.d("Deliver ngo","null");

                    }
                    Log.d("All Deliver",allDeliverRequests.toString());
                    prAdaptor = new DeliverRequestAdaptor(allDeliverRequests);
                    recList.setAdapter(prAdaptor);
                }
            }

            @Override
            public void onFailure(Call<PickupRequestResponse> call, Throwable t) {
            }
        });
        return view;

    }
}
