package com.example.dr.hyphope;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.neura.resources.authentication.AuthenticateCallback;
import com.neura.resources.authentication.AuthenticateData;
import com.neura.sdk.object.AuthenticationRequest;
import com.neura.sdk.object.Permission;
import com.neura.sdk.service.SubscriptionRequestCallbacks;
import com.neura.standalonesdk.service.NeuraApiClient;
import com.neura.standalonesdk.util.Builder;
import com.neura.standalonesdk.util.SDKUtils;

import java.util.ArrayList;

/**
 * Created by d&r on 09/11/2016.
 */
public class NeuraReceiver extends BroadcastReceiver {
    private NeuraApiClient mNeuraApiClient;
    private ArrayList<Permission> mPermissions;
    public NeuraReceiver(){




    }

    @Override
    public void onReceive(Context context, Intent intent) {

//
    }
}
