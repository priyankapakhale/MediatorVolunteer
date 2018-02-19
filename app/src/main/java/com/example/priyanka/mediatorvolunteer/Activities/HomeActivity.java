package com.example.priyanka.mediatorvolunteer.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.priyanka.mediatorvolunteer.Fragments.DeliverRequestFragment;
import com.example.priyanka.mediatorvolunteer.Fragments.PickupRequestFragment;
import com.example.priyanka.mediatorvolunteer.Helpers.CircleTransform;
import com.example.priyanka.mediatorvolunteer.Models.Volunteer;
import com.example.priyanka.mediatorvolunteer.Models.VolunteerResponse;
import com.example.priyanka.mediatorvolunteer.Network.ApiClient;
import com.example.priyanka.mediatorvolunteer.Network.ApiInterface;
import com.example.priyanka.mediatorvolunteer.Network.NotificationHandler;
import com.example.priyanka.mediatorvolunteer.R;
import com.example.priyanka.mediatorvolunteer.SendNotificationService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by priyanka on 1/24/18.
 */

public class HomeActivity extends AppCompatActivity implements Runnable{
    private static final String TAG = "HomeActivity";
    private FirebaseAuth mAuth;
    private ViewPager viewPager;
    Handler timerHandler;
    private TabLayout tabLayout;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private NavigationView navigationView;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;

    private static final String urlNavHeaderBg = "https://api.androidhive.info/images/nav-menu-header-bg.jpg";
    private static final String urlProfileImg = "https://pbs.twimg.com/profile_images/3766735565/6e0118f54f242cf5978fa990c9c838df.jpeg";

    private Volunteer v;
    TextView TVname, TVemail;
    String email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        timerHandler = new Handler();
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        email = currentUser.getEmail();
        Log.d("current user",currentUser.toString());
        Log.d("Email",email);
        getUser(email);

        TVname = (TextView)findViewById(R.id.user_name);
        TVemail = (TextView)findViewById(R.id.email);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.openDrawer,R.string.closeDrawer );
        mToolbar = (Toolbar)findViewById(R.id.nav_action_bar);
        setSupportActionBar(mToolbar);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        TVname = (TextView) navHeader.findViewById(R.id.user_name);
        TVemail = (TextView) navHeader.findViewById(R.id.email);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
        // load nav menu header data
        loadNavHeader();
        // initializing navigation menu
        setUpNavigationView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void settings()
    {
        Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
        startActivity(intent);
    }


    private void loadNavHeader() {
        // name, website
        TVname.setText("Prajakta Pakhale");
        TVemail.setText("prajakta.pakhale13@gmail.com");

        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);


    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_activity_home:
                        mDrawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_settings:
                        settings();
                        break;
                    case R.id.nav_logout:
                        break;

                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);


                return true;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        Fragment mrFragment = new PickupRequestFragment();
        adapter.addFragment(mrFragment, "Pickup");
        viewPager.setAdapter(adapter);
        Fragment drFragment = new DeliverRequestFragment();
        adapter.addFragment(drFragment,"Deliver");
        viewPager.setAdapter(adapter);

    }

    @Override
    public void run() {
        // Here you can update your adapter data
        //yourAdapter.notifyDataSetChanged();
        //this.run(); WTF!!!! INCEPTION ?
        getNewRequests();
        timerHandler.postDelayed(this, 20000);
    }

    public void getNewRequests()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        Log.d("reached","here");
        String msg = SendNotificationService.message;
        String rid = SendNotificationService.request_id;
        String type = SendNotificationService.type;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New pickup request");
        builder.setMessage(msg)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Assign volunteer to current pickup request
                        int request_id = Integer.parseInt(rid);
                        if(type.equalsIgnoreCase("user"))
                            acceptRequest(request_id);
                        else
                            acceptOrder(request_id);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                })
                ;
        // Create the AlertDialog object and return it
        builder.create();
        if(!msg.equalsIgnoreCase(""))
            builder.show();

    }

    public void acceptRequest(int request_id)
    {
        SendNotificationService.message = "";
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.d("email here",email);
        Call<ResponseBody> call = apiService.assignVolunteerToPickupRequest(request_id, email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    public void acceptOrder(int request_id)
    {
        SendNotificationService.message = "";
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.d("email here",email);
        Call<ResponseBody> call = apiService.assignVolunteerToOrderRequest(request_id, email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



    public void onClick(View v)
    {
        switch(v.getId())
        {
        }
    }


    public void getUser(String email)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<VolunteerResponse> call = apiService.getVolunteer(email);
        call.enqueue(new Callback<VolunteerResponse>() {
            @Override
            public void onResponse(Call<VolunteerResponse> call, Response<VolunteerResponse> response) {
                Log.d("response",response.message());
                v = response.body().getVolunteer();
                int id = response.body().getId();
                v.setId(id);
                Log.d("Id fetched", ""+id);

                TVname.setText(v.getName());
                TVemail.setText(v.getEmailId());

            }

            @Override
            public void onFailure(Call<VolunteerResponse> call, Throwable t) {
                Log.d("Failure",t.getMessage());
            }
        });

        NotificationHandler nd = new NotificationHandler();
        String token = nd.getToken();

        Call<ResponseBody> call1 = apiService.addDevice(email,token,email);
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        Log.d("token received", token);

        Call<ResponseBody> call2 = apiService.sendRegistrationTokenToServer(token,email);
        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        this.run();

    }

}
