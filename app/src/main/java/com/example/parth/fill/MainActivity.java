package com.example.parth.fill;

import android.app.DownloadManager;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Logger;


import android.app.Activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;
import org.json.JSONTokener;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView) findViewById(R.id.textView) ;


        Button callTheAPI = (Button) findViewById(R.id.button);

        callTheAPI.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {

                    SendPostRequest a = new SendPostRequest();
                    // CALL
                    textView.setVisibility(View.VISIBLE);
                  String valueAPI =   a.execute().get();
                    textView.setText(valueAPI);
                    textView.postDelayed(new Runnable() {
                        public void run() {
                            textView.setVisibility(View.INVISIBLE);
                        }
                    }, 3000);
                    a.cancel(true);






                } catch (Exception ex) {
                    // content.setText(" url exeption! " );
                }
            }
        });

    }

    public class SendPostRequest extends AsyncTask<String, Void, String> {



        protected void onPreExecute() {

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://httpbin.org/get?param1=hello");

                //  JSONObject postDataParams = new JSONObject();
                // postDataParams.put("name", "abc");
                //postDataParams.put("email", "abc@gmail.com");
                //Log.e("params",postDataParams.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");



                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(
                                    conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);

                    }

                    in.close();
                    Log.i("as",sb.toString());
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "asdf";

        }

            @Override
            protected void onPostExecute (String result){

            }

    }
}