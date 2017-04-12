package com.example.lenovo.placepicker;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public class MainActivity extends AppCompatActivity {

    TextView get_place;
    TextView sndsms;
    TextView addressView,feedBackView;
    int PLACE_PICKER_REQUEST = 1;
    Double lattitude;
    Double longitude;
    String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        get_place = (TextView) findViewById(R.id.textView);
        addressView=(TextView)findViewById(R.id.address);
        feedBackView=(TextView)findViewById(R.id.review);



        get_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent intent;
                try {
                    intent = builder.build(MainActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this ,"ERRORRRRR",Toast.LENGTH_SHORT).show();
                }
            }
        });

        sndsms=(TextView)findViewById(R.id.sendsms);
        sndsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent=new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,address);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        feedBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lat=String.valueOf(lattitude);
                String lon=String.valueOf(longitude);

                //Toast.makeText(MainActivity.this,lat+"\n"+lon,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,sendFeedback.class);

                Bundle bundle=new Bundle();
                bundle.putString("lattitude_key",lat);
                bundle.putString("longitude_key",lon);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }


    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        if(requestCode==PLACE_PICKER_REQUEST){
            if(resultCode==RESULT_OK){
                Place place=PlacePicker.getPlace(data,this);
                lattitude=place.getLatLng().latitude;
                longitude=place.getLatLng().longitude;
                address=String.format("  Place:%s\n\nAddress: %s\n  Lattitude:%f\n  Longitude:%f",place.getName(),place.getAddress(),lattitude,longitude);
                //place.getName();
                addressView.setText(address);
                addressView.setTextSize(20);
                addressView.setPadding(5,5,5,5);

            }
        }
    }

}
