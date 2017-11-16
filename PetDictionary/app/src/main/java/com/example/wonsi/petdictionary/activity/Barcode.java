package com.example.wonsi.petdictionary.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.wonsi.petdictionary.JSONHandler;
import com.example.wonsi.petdictionary.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by wonsi on 2017-11-13.
 */

public class Barcode extends AppCompatActivity {
    HttpURLConnection conn;
    String jsonResult;
    JSONHandler jsonHandler;
    ArrayList<Object> jsonArray = new ArrayList<>();
    TextView tv_barcode;
    TextView tv_foodname;
    TextView tv_prices;
    TextView tv_foodweight;
    TextView tv_origin;
    TextView tv_maker;
    TextView tv_functional;
    TextView tv_whom;
    TextView tv_agefrom;
    TextView tv_ageto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        tv_barcode = (TextView) findViewById(R.id.Barcode_TV_barcode);
        tv_foodname= (TextView) findViewById(R.id.Barcode_TV_foodname);
        tv_prices = (TextView) findViewById(R.id.Barcode_TV_price);
        tv_foodweight = (TextView) findViewById(R.id.Barcode_TV_foodweight);
        tv_origin = (TextView) findViewById(R.id.Barcode_TV_origin);
        tv_maker= (TextView) findViewById(R.id.Barcode_TV_maker);
        tv_functional= (TextView) findViewById(R.id.Barcode_TV_functional);
        tv_whom = (TextView) findViewById(R.id.Barcode_TV_whom);
        tv_agefrom= (TextView) findViewById(R.id.Barcode_TV_agefrom);
        tv_ageto = (TextView) findViewById(R.id.Barcode_TV_ageto);

        Intent intent = getIntent();
        String barcode = intent.getExtras().getString("barcode");

        new showFoodData().execute(barcode);
    }
    private class showFoodData extends AsyncTask<String, String, String> {
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(Barcode.this, "Please Wait", null, true, true);
        }
        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL("http://18.216.142.72/getBarcode.php");
                conn = (HttpURLConnection)url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("barcode", params[0]);
                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

                int responce_code = conn.getResponseCode();

                if (responce_code == HttpURLConnection.HTTP_OK){
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while((line = reader.readLine()) != null){
                        result.append(line);
                    }

                    jsonResult = result.toString();
                    Log.d("Result", jsonResult);
                    return(result.toString());
                }
                else{
                    return("???");
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return "false";
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            loading.dismiss();
            jsonHandler = new JSONHandler(jsonResult);
            jsonArray = jsonHandler.parseJSON("Barcode", "FoodName", "Prices", "FoodWeight",
                    "Origin", "Maker", "Functional", "Whom", "AgeFrom","AgeTo");

            tv_barcode.setText(jsonArray.get(0).toString());
            tv_foodname.setText(jsonArray.get(1).toString());
            tv_prices.setText(jsonArray.get(2).toString());
            tv_foodweight.setText(jsonArray.get(3).toString());
            tv_origin.setText(jsonArray.get(4).toString());
            tv_maker.setText(jsonArray.get(5).toString());
            tv_functional.setText(jsonArray.get(6).toString());
            tv_whom.setText(jsonArray.get(7).toString());
            tv_agefrom.setText(jsonArray.get(8).toString());
            tv_ageto.setText(jsonArray.get(9).toString());


        }
    }

}
