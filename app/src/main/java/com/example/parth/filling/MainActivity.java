package com.example.parth.filling;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    SharedPreferences mPrefs;
    final String welcomeScreenShownPref = "welcomeScreenShown";
    final String userIdentifyPref = "identifyUser";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // second argument is the default to use if the preference can't be found
        Boolean welcomeScreenShown = mPrefs.getBoolean(welcomeScreenShownPref, false);

        if (!welcomeScreenShown) {
            // here you can launch another activity if you like
            // the code below will display a popup

            String whatsNewTitle = getResources().getString(R.string.whatsNewTitle);
            String whatsNewText = getResources().getString(R.string.whatsNewText);
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(whatsNewTitle).setMessage(whatsNewText).setPositiveButton(
                    R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean(welcomeScreenShownPref, true);
            editor.commit(); // Very important to save the preference
        }
        Button userOne = (Button) findViewById(R.id.button);
        Button userTwo = (Button) findViewById(R.id.button2);
        Button userThree = (Button) findViewById(R.id.button3);

        if(mPrefs.getAll().containsKey("identifyUser")){
            Log.d("Value of Pref " ,"Value    "+mPrefs.getInt("identifyUser",0));

            Intent myIntent = new Intent(MainActivity.this, DataActivity.class);
            myIntent.putExtra("userValue",mPrefs.getInt("identifyUser",0));


            MainActivity.this.startActivity(myIntent);

        }

        userOne.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    setSharePreferences(1);

                } catch (Exception ex) {
                    // content.setText(" url exeption! " );
                }
            }
        });


        userTwo.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    // Intent myIntent = new Intent(MainActivity.this, DataActivity.class);
                    // myIntent.putExtra("key", "Haha"); //Optional parameters
                    //MainActivity.this.startActivity(myIntent);
                    setSharePreferences(2);

                } catch (Exception ex) {
                    // content.setText(" url exeption! " );
                }
            }
        });

        userThree.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    // Intent myIntent = new Intent(MainActivity.this, DataActivity.class);
                    // myIntent.putExtra("key", "Haha"); //Optional parameters
                    //MainActivity.this.startActivity(myIntent);
                    setSharePreferences(3);

                } catch (Exception ex) {
                    // content.setText(" url exeption! " );
                }
            }
        });


    }

    public void setSharePreferences(int valueToBeSet){

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putInt(userIdentifyPref,valueToBeSet);
        editor.commit(); // Very important t



    }
}
