package com.example.priyanka.mediatorvolunteer.Adaptors;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.priyanka.mediatorvolunteer.Models.NGO;
import com.example.priyanka.mediatorvolunteer.Models.NGOPickupRequest;
import com.example.priyanka.mediatorvolunteer.Network.ApiClient;
import com.example.priyanka.mediatorvolunteer.Network.ApiInterface;
import com.example.priyanka.mediatorvolunteer.R;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by priyanka on 2/2/18.
 */

public class NGOPickupRequestAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NGOPickupRequest> requests;
    private NGO ngo;
    private EditText _countText;
    private int order_id;

    public NGOPickupRequestAdaptor()
    {}

    public NGOPickupRequestAdaptor(List<NGOPickupRequest> requests)
    {
        this.requests = requests;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pickup_request_card,
                parent, false);
        NGOPickupRequestAdaptor.PRViewHolder vh = new NGOPickupRequestAdaptor.PRViewHolder(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(parent.getContext(),"Clicked "+vh.name.getText().toString(),Toast.LENGTH_SHORT).show();

                _countText = new EditText(parent.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                _countText.setLayoutParams(lp);

                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                builder.setTitle("Enter medicine count")
                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Assign volunteer to current pickup request
                                int count = Integer.parseInt(_countText.getText().toString());
                                updateRequestStatus(count);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        })
                        .setView(_countText)
                ;
                // Create the AlertDialog object and return it
                builder.create();
                builder.show();
            }
        });
        return vh;
    }

    public void updateRequestStatus(int count)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.d("order id",order_id+"");
        Call<ResponseBody> call = apiService.updatePickupRequest(order_id,count);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        if (holder1 instanceof NGOPickupRequestAdaptor.PRViewHolder ) {

            final NGOPickupRequestAdaptor.PRViewHolder holder = (NGOPickupRequestAdaptor.PRViewHolder) holder1;
            Log.d("requests",requests.toString());

            if(requests.size() > 0 && position < requests.size()) {

                NGOPickupRequest t = requests.get(position);
                Log.d("pickuprequest",t.toString());

                // Fetch user information from server using user_id
                int user_id = t.getUserId();
                order_id = t.getId();
                Log.d("id here",t.getId()+"");
                holder.name.setText(t.getName());
                holder.address.setText(t.getAddress());
                holder.contact_no.setText(t.getContactNo());
                holder.status.setText(t.getStatus()+" @ "+t.getStartDate());


            }

        }


    }



    public class PRViewHolder extends RecyclerView.ViewHolder {

        protected TextView name;
        protected TextView status;
        protected TextView address;
        protected TextView contact_no;

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
