package com.example.databasefirebaseprojectexample.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.databasefirebaseprojectexample.R;

public class SplashActivity extends AppCompatActivity {


    String Firstname,Lastname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);




        final SharedPreferences pref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        final SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        final String checkLoginState=preferences.getString("isUserLogin","UserLogin");

        final Boolean check = pref.getBoolean("isIntroOpnend", false);
        final Handler[] handler = {new Handler()};
        handler[0].postDelayed(new Runnable() {
            @Override
            public void run() {

                ConnectivityManager mgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = mgr.getActiveNetworkInfo();

                if (netInfo != null) {
                    if (netInfo.isConnected()) {
                        if (check == false) {
                            startActivity(new Intent(SplashActivity.this, OnBaordingScreenActivity.class));
                            finish();
                        } else if (check == true) {
                            if (checkLoginState.equals("UserLogin")) {
                                // Internet Available
                                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                                intent.putExtra("firstname", Firstname);
                                intent.putExtra("lastname", Lastname);
                                startActivity(intent);
                                finish();
                            }
                        } else if (checkLoginState.equals("UserLogout")) {
                            startActivity(new Intent(SplashActivity.this, ChooseScreenActivity.class));
                            finish();
                        }
                    }else {
                        Toast.makeText(SplashActivity.this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
                    }
                } else {
                        Toast.makeText(SplashActivity.this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
                }



            }

        },3000);
    }

}
