package com.example.databasefirebaseprojectexample.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.databasefirebaseprojectexample.BaordingScreenActivity;
import com.example.databasefirebaseprojectexample.R;
import com.example.databasefirebaseprojectexample.RegistrationComplete;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreenActivity extends AppCompatActivity {


    String Firstname,Lastname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);




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
                            startActivity(new Intent(SplashScreenActivity.this, BaordingScreenActivity.class));
                            finish();
                        } else if (check == true) {
                            if (checkLoginState.equals("UserLogin")) {
                                // Internet Available
                                Intent intent = new Intent(SplashScreenActivity.this, Home_Screen_Activity.class);
                                intent.putExtra("firstname", Firstname);
                                intent.putExtra("lastname", Lastname);
                                startActivity(intent);
                                finish();
                            }
                        } else if (checkLoginState.equals("UserLogout")) {
                            startActivity(new Intent(SplashScreenActivity.this, ChooseScreenActivity.class));
                            finish();
                        }
                    }else {
                        Toast.makeText(SplashScreenActivity.this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
                    }
                } else {
                        Toast.makeText(SplashScreenActivity.this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
                }



            }

        },3000);
    }

}
