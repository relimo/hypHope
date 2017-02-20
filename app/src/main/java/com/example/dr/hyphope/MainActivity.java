package com.example.dr.hyphope;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.neura.standalonesdk.util.Builder;

import com.neura.resources.authentication.AuthenticateCallback;
import com.neura.resources.authentication.AuthenticateData;
import com.neura.sdk.object.AuthenticationRequest;
import com.neura.sdk.object.Permission;
import com.neura.sdk.service.SubscriptionRequestCallbacks;
import com.neura.standalonesdk.service.NeuraApiClient;
import com.neura.standalonesdk.util.Builder;
import com.neura.standalonesdk.util.SDKUtils;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private NeuraApiClient mNeuraApiClient;
    private ArrayList<Permission> mPermissions;
    private Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btnStart=(Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mPermissions = Permission.list(new String[]{"userStartedRunning", "userStartedWalking", "userFinishedRunning", "userFinishedDriving", "userStartedDriving", "userFinishedWalking", "userArrivedAtRestaurant", "userLeftCafe", "userArrivedAtCafe", "userLeftRestaurant", "userArrivedToGym", "userLeftGym", "userArrivedAtSchoolCampus", "userWokeUp", "userStartedSleeping", "userIsIdle", "userLeftSchoolCampus", "userLeftWork", "userLeftHome", "userArrivedHome", "userArrivedToWork", "userArrivedAtGroceryStore"});
               mPermissions=Permission.list(new String[]{
                               "userStartedSleeping",
                               "userArrivedHome",
                               "userFinishedRunning",
                               "userLeftWork",
                               "userStartedWalking",
                               "userLeftSchoolCampus",
                               "userFinishedCycling",
                               "physicalActivity",
                               "userArrivedAtSchoolCampus",
                               "userLeftGym",
                               "userArrivedAtGroceryStore",
                               "userStartedRunning",
                               "userIsIdle",
                               "userFinishedDriving",
                               "userStartedCycling",
                               "userArrivedToWork",
                               "userLeftCafe",
                               "userLeftRestaurant",
                               "userStartedDriving",
                               "userLeftHome",
                               "userArrivedAtCafe",
                               "userArrivedAtRestaurant",
                               "userWokeUp",
                               "userFinishedWalking",
                               "userArrivedToGym"
                       });
                AuthenticationRequest request = new AuthenticationRequest(mPermissions);
                Builder builder = new Builder(getApplicationContext());
                mNeuraApiClient = builder.build();
                mNeuraApiClient.setAppUid(getResources().getString(R.string.app_uid));
                mNeuraApiClient.setAppSecret(getResources().getString(R.string.app_secret));
                mNeuraApiClient.connect();



                mNeuraApiClient.authenticate(request, new AuthenticateCallback() {
                    @Override
                    public void onSuccess(AuthenticateData authenticateData) {
                        Log.i(getClass().getSimpleName(), "Successfully authenticate with neura. "
                                + "NeuraUserId = " + authenticateData.getNeuraUserId() + " "
                                + "AccessToken = " + authenticateData.getAccessToken());
                    }

                    @Override
                    public void onFailure(int i) {
                        Log.e(getClass().getSimpleName(), "Failed to authenticate with neura. "
                                + "Reason : " + SDKUtils.errorCodeToString(i));


                    }
                });



            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
