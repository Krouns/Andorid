package com.example.parth.filling;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class DataActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);


        Button btnApiCall = (Button) findViewById(R.id.button4);
        final TextView textView = (TextView) findViewById(R.id.textView3) ;

        btnApiCall.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {

                    SendPostRequest a = new SendPostRequest();

                    String valueAPI =   a.execute().get();
                    textView.setText(valueAPI);




                } catch (Exception ex) {
                    // content.setText(" url exeption! " );
                }
            }
        });




    }

    public class SendPostRequest extends AsyncTask<String, Void, String>   {



        protected void onPreExecute() {

        }

        protected String doInBackground(String... arg0) {

            try {
                Intent i = getIntent();
                final int valueOfUser = i.getIntExtra("userValue",0);

                Log.d("Share Value","Value  "+valueOfUser);

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("column", valueOfUser);

                URL url = new URL("http://httpbin.org/get?param1=hello");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();


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
            }catch (Exception e){
                e.printStackTrace();
            }

            return "What";

        }

        @Override
        protected void onPostExecute (String result){

        }

    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

}
