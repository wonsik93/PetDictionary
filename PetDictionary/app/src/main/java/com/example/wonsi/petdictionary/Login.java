package com.example.wonsi.petdictionary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;



import org.apache.http.protocol.HTTP;

import static org.apache.http.protocol.HTTP.UTF_8;


/**
 * Created by wonsi on 2017-11-09.
 */

@SuppressWarnings("deprecation")
public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText editText_userid;
    private EditText editText_userpw;
    private Button button_login;
    private Button button_register;
    ProgressDialog dialog = null;
    List<NameValuePair> params;
    HttpURLConnection conn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //android.app.ActionBar actionBar = getActionBar();
        //actionBar.hide();


        editText_userid = (EditText)findViewById(R.id.Login_ET_ID);
        editText_userpw = (EditText)findViewById(R.id.Login_ET_password);

        button_login = (Button)findViewById(R.id.Login_BTN_login);
        button_register = (Button)findViewById(R.id.Login_BTN_register);
        button_login.setOnClickListener(this);
        button_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Login_BTN_login :

                final String ID = editText_userid.getText().toString();
                final String PW= editText_userpw.getText().toString();
                new Userlogin().execute(ID,PW);
                break;
            case R.id.Login_BTN_register :
                Intent intent = new Intent(this, Register.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private class Userlogin extends AsyncTask<String, String, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Login.this, "Please Wait", null, true, true);
            }
        @Override
            protected String doInBackground(String... params) {

                try {
                    URL url = new URL("http://18.216.142.72/login.php");
                    conn = (HttpURLConnection)url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");

                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("userid", params[0])
                            .appendQueryParameter("userpw", params[1]);
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
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                loading.dismiss();
                if(result.equalsIgnoreCase("success")){
                    Intent intent = new Intent(Login.this, Main.class);
                    startActivity(intent);
                    finish();
                }else if(result.equalsIgnoreCase("false")){
                    Toast.makeText(Login.this,"로그인 실패", Toast.LENGTH_SHORT).show();
                }

            }
        }


}
