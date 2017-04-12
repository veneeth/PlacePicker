package com.example.lenovo.placepicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Lenovo on 02-04-2017.
 */
public class sendFeedback extends Activity {
    EditText et_lattitude,et_longitude,et_feedback;
    String lattitude,longitude,feedback;
    Button submitFeedback,btn_view_review;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendfeedback);



        et_lattitude=(EditText)findViewById(R.id.lattitude);
        et_longitude=(EditText)findViewById(R.id.longitude);
        et_feedback=(EditText)findViewById(R.id.feedback);
        submitFeedback=(Button)findViewById(R.id.btn_submit);
        btn_view_review=(Button)findViewById(R.id.view_btn);

        Bundle bundle=getIntent().getExtras();
        lattitude=bundle.getString("lattitude_key");
        longitude=bundle.getString("longitude_key");

        et_lattitude.setText(lattitude);
        et_longitude.setText(longitude);


        btn_view_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(sendFeedback.this,retrieveFeedback.class);

                Bundle bundle=new Bundle();
                bundle.putString("lattitude_key",lattitude);
                bundle.putString("longitude_key",longitude);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        submitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                lattitude=et_lattitude.getText().toString();
  //              longitude=et_longitude.getText().toString();
                feedback=et_feedback.getText().toString();
                String method="register";
                BackgroundTask backgroundTask=new BackgroundTask(sendFeedback.this);
                backgroundTask.execute(method,lattitude,longitude,feedback);
                finish();
            }
        });
    }
}
