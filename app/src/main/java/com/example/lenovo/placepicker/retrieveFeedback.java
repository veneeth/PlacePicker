package com.example.lenovo.placepicker;

import android.app.Activity;
import android.content.EntityIterator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Lenovo on 02-04-2017.
 */
public class retrieveFeedback extends Activity {

    EditText lat,lon;
    String lattitude,longitude;
    Button back,viewb;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrievefeedback);

        lat=(EditText)findViewById(R.id.lattitude);
        lon=(EditText)findViewById(R.id.longitude);
        back=(Button)findViewById(R.id.btn_back);
        viewb=(Button)findViewById(R.id.view_review);

        Bundle bundle=getIntent().getExtras();
        lattitude=bundle.getString("lattitude_key");
        longitude=bundle.getString("longitude_key");

        lat.setText(lattitude);
        lon.setText(longitude);
       // lat.setEnabled(false);
       // lon.setEnabled(false);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(retrieveFeedback.this,MainActivity.class);
                startActivity(intent);
            }
        });
        viewb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(retrieveFeedback.this,lattitude+"\n"+longitude,Toast.LENGTH_SHORT).show();
                String method="send";
                BackgroundTask backgroundTask=new BackgroundTask(retrieveFeedback.this);
                backgroundTask.execute(method,lattitude,longitude);
            }
        });

    }

    /*public void retrieveData(View view){
        Toast.makeText(retrieveFeedback.this,lattitude+"\n"+longitude,Toast.LENGTH_SHORT).show();
        String method="send";
        BackgroundTask backgroundTask=new BackgroundTask(retrieveFeedback.this);
        backgroundTask.execute(method,lattitude,longitude);
    }*/

    /*public void addData(View view){

        Intent intent=new Intent(retrieveFeedback.this,sendFeedback.class);
        startActivity(intent);
    }*/

}
