package com.example.lenovo.placepicker;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 06-02-2017.
 */
public class signup extends AppCompatActivity{


    EditText passwordd,mobphone,mail,usrusr;
    TextView login,signup;

    protected String enteredUsername;
    private final String serverUrl = "http://teachlearngrow.esy.es/androidlogin/index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_page);

        usrusr = (EditText) findViewById(R.id.usrusr);
        passwordd = (EditText)findViewById(R.id.passwrd);
        mail = (EditText) findViewById(R.id.mail);
        mobphone = (EditText) findViewById(R.id.mobphone);
        login = (TextView)findViewById(R.id.logiin);
        signup = (TextView)findViewById(R.id.sup);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),"fonts/Lato-Light.ttf");
        signup.setTypeface(custom_font);
        mail.setTypeface(custom_font);
        mobphone.setTypeface(custom_font);
        passwordd.setTypeface(custom_font);
        usrusr.setTypeface(custom_font);
        login.setTypeface(custom_font);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enteredUsername = usrusr.getText().toString();
                String enteredPassword = passwordd.getText().toString();
                String enteredEmail = mail.getText().toString();
                String mobileno=mobphone.getText().toString();


                if(enteredUsername.equals("") || enteredPassword.equals("") || enteredEmail.equals("")||mobileno.equals("")){
                    Toast.makeText(signup.this, "All fields are Mandatory", Toast.LENGTH_LONG).show();
                    return;
                }
                if(enteredUsername.length() <= 4 || enteredPassword.length() <= 4){
                    Toast.makeText(signup.this, "Username or password length must be greater than four", Toast.LENGTH_LONG).show();
                    return;
                }
                // request authentication with remote server4
                AsyncDataClass asyncRequestObject = new AsyncDataClass();
                asyncRequestObject.execute(serverUrl, enteredUsername, enteredPassword, enteredEmail,mobileno);

                Intent intent=new Intent(signup.this,signup.class);
                startActivity(intent);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(signup.this,login.class);
                startActivity(i);
                finish();
            }
        });
    }
    @Override
public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_register, menu);
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
private class AsyncDataClass extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {

        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
        HttpConnectionParams.setSoTimeout(httpParameters, 5000);

        HttpClient httpClient = new DefaultHttpClient(httpParameters);
        HttpPost httpPost = new HttpPost(params[0]);

        String jsonResult = "";
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("username", params[1]));
            nameValuePairs.add(new BasicNameValuePair("password", params[2]));
            nameValuePairs.add(new BasicNameValuePair("email", params[3]));
            nameValuePairs.add(new BasicNameValuePair("mobile",params[4]));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpClient.execute(httpPost);
            jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
            System.out.println("Returned Json object " + jsonResult.toString());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        System.out.println("Resulted Value: " + result);
        if(result.equals("") || result == null){
            Toast.makeText(signup.this, "Server connection failed", Toast.LENGTH_LONG).show();
            return;
        }
        int jsonResult = returnParsedJsonObject(result);
        if(jsonResult == 0){
            Toast.makeText(signup.this, "Invalid username or password or email", Toast.LENGTH_LONG).show();
            return;
        }
        if(jsonResult == 1){
            Intent intent = new Intent(signup.this, loginActivity.class);
            intent.putExtra("USERNAME", enteredUsername);
            intent.putExtra("MESSAGE", "You have been successfully Registered");
            startActivity(intent);
        }
    }
    private StringBuilder inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            while ((rLine = br.readLine()) != null) {
                answer.append(rLine);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return answer;
    }
}
    private int returnParsedJsonObject(String result){

        JSONObject resultObject = null;
        int returnedResult = 0;
        try {
            resultObject = new JSONObject(result);
            returnedResult = resultObject.getInt("success");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedResult;
    }

}
