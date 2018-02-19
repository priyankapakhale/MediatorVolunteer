package com.example.priyanka.mediatorvolunteer.Adaptors;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.priyanka.mediatorvolunteer.Activities.HomeActivity;
import com.example.priyanka.mediatorvolunteer.Models.PickupRequest;
import com.example.priyanka.mediatorvolunteer.Models.User;
import com.example.priyanka.mediatorvolunteer.Network.ApiClient;
import com.example.priyanka.mediatorvolunteer.Network.ApiInterface;
import com.example.priyanka.mediatorvolunteer.R;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by priyanka on 1/29/18.
 */

public class DeliverRequestAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PickupRequest> requests;
    private User user;
    private EditText _countText;
    private int donation_id;
    private int order_id;
    private String type;
    private View view1;
    private Context context;

    public DeliverRequestAdaptor()
    {}

    public DeliverRequestAdaptor(List<PickupRequest> requests)
    {
        this.requests = requests;
        Log.d("received req",requests.toString());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.deliver_request_card,
                parent, false);
        DeliverRequestAdaptor.PRViewHolder vh = new DeliverRequestAdaptor.PRViewHolder(itemView);
        context = parent.getContext();

        view1 = itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(parent.getContext(),"Clicked "+vh.name.getText().toString(),Toast.LENGTH_SHORT).show();
                _countText = new EditText(parent.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                _countText.setLayoutParams(lp);

                AlertDialog.Builder builder1 = new AlertDialog.Builder(parent.getContext());
                builder1.setTitle("Delivered ?")
                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Assign volunteer to current pickup request
                                updateRequestStatus();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        })
                ;
                // Create the AlertDialog object and return it
                builder1.create();
                AlertDialog.Builder builder2 = new AlertDialog.Builder(parent.getContext());
                builder2.setTitle("Delivered ? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Assign volunteer to current pickup request
                                updateOrderStatus();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        })
                ;
                // Create the AlertDialog object and return it
                builder2.create();

                if(vh.type.equalsIgnoreCase("user"))
                {
                    builder1.show();
                }
                else
                {
                    builder2.show();
                }

            }
        });
        return vh;
    }

    public void updateOrderStatus()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.d("order id",order_id+"");
        Call<ResponseBody> call = apiService.updateOrderDelivered(order_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    public void updateRequestStatus()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.d("donation id",donation_id+"");
        Call<ResponseBody> call = apiService.updateRequestDelivered(donation_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        if (holder1 instanceof DeliverRequestAdaptor.PRViewHolder ) {

            final DeliverRequestAdaptor.PRViewHolder holder = (DeliverRequestAdaptor.PRViewHolder) holder1;
            Log.d("requests",requests.toString());

            if(requests.size() > 0 && position < requests.size()) {

                PickupRequest t = requests.get(position);
                Log.d("deliverrequest",t.toString());

                // Fetch user information from server using user_id
                type = t.getType();
                int user_id = t.getUserId();
                donation_id = t.getId();
                order_id = t.getOrderId();
                Log.d("id here",t.getId()+"");
                holder.name.setText(t.getName());
                holder.address.setText(t.getAddress());
                holder.contact_no.setText(t.getContactNo());
                holder.status.setText(t.getStatus()+" @ "+t.getStartDate());
                holder.type = type;

                if(type.equalsIgnoreCase("ngo"))
                {
                    view1.setBackgroundColor(Color.GRAY);

                }


            }

        }


    }



    public class PRViewHolder extends RecyclerView.ViewHolder {

        protected TextView name;
        protected TextView status;
        protected TextView address;
        protected TextView contact_no;
        protected String type;

        public PRViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            status = itemView.findViewById(R.id.status);
            address = itemView.findViewById(R.id.address);
            contact_no = itemView.findViewById(R.id.contact_no);

        }
    }

    @Override
    public int getItemCount() {
        if (requests == null) {
            return 0;
        }

        if (requests.size() == 0) {
            return 0;
        }

        // Add extra view to show the footer view
        return requests.size();
    }
}
