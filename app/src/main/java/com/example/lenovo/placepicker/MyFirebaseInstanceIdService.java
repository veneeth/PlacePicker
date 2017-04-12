package com.example.lenovo.placepicker;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Lenovo on 08-04-2017.
 */
public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService{

    //private static final String REG_TOKEN = "REG_TOKEN";
    private static final String TAG="MyFirebaseInsIDService";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String recent_token= FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"New Token: "+recent_token);
    }
}
