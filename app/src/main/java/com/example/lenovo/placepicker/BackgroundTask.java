package com.example.lenovo.placepicker;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Lenovo on 02-04-2017.
 */
public class BackgroundTask extends AsyncTask<String,Void,String> {

    AlertDialog alertDialog;

    Context ctx;
    BackgroundTask(Context ctx){
        this.ctx=ctx;
    }


    @Override
    protected void onPreExecute() {
        alertDialog=new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("PLACE INFORMATION...");
    }

    @Override
    protected String doInBackground(String... params) {

        String reg_url="http://teachlearngrow.esy.es/data_entry/entry.php";
        String retrieve_url="http://teachlearngrow.esy.es/data_entry/retrieve.php";

        String method=params[0];
        if (method.equals("register")){

            String lattitude=params[1];
            String longitude=params[2];
            String feedback=params[3];

            try {
                URL url=new URL(reg_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));

                String data= URLEncoder.encode("lattitude","UTF-8")+"="+URLEncoder.encode(lattitude,"UTF-8")+"&"+
                        URLEncoder.encode("longitude","UTF-8")+"="+URLEncoder.encode(longitude,"UTF-8")+"&"+
                        URLEncoder.encode("feedback","UTF-8")+"="+URLEncoder.encode(feedback,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS=httpURLConnection.getInputStream();
                IS.close();
                return "Registration Success...";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(method.equals("send"))
        {
            String lat1=params[1];
            String lon1=params[2];
            try {
                URL url=new URL(retrieve_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data=URLEncoder.encode("lat1","UTF-8")+"="+URLEncoder.encode(lat1,"UTF-8")+"&"+
                        URLEncoder.encode("lon1","UTF-8")+"="+URLEncoder.encode(lon1,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response="";
                String line="";
                while ((line=bufferedReader.readLine())!=null){
                    response+=line;
                }bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.equals("Registration Success...")) {
            Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
        }else
        {
            alertDialog.setMessage(result);
            alertDialog.show();
        }
    }
}
