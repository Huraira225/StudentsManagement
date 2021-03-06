package com.example.studentmanagement.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.studentmanagement.R;
import com.google.firebase.analytics.FirebaseAnalytics;

public class IntroActivity extends AppCompatActivity  {
    FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAnalytics= FirebaseAnalytics.getInstance(this);
        setContentView(R.layout.activity_choose_screen);

        Button btn=findViewById(R.id.button_login);
        Button btn1=findViewById(R.id.button_signup);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i=new Intent(IntroActivity.this, LoginActivity.class);
                Bundle bundle= new Bundle();
                bundle.putString("loginscreen","Student login screen");
                firebaseAnalytics.logEvent("loginscreen",bundle);
                startActivity(i);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent v=new Intent(IntroActivity.this, SignUpActivity.class);
                Bundle bundle= new Bundle();
                bundle.putString("signupscreen","Student signup screen");
                firebaseAnalytics.logEvent("signupscreen",bundle);
                startActivity(v);
            }
        });
    }
}
