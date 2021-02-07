package com.example.databasefirebaseprojectexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.databasefirebaseprojectexample.Activities.Home_Screen_Activity;
import com.example.databasefirebaseprojectexample.Activities.LoginScreenActivity;

public class RegistrationComplete extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_complete);

        Button button= findViewById(R.id.button_LogIn);

         button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(RegistrationComplete.this, LoginScreenActivity.class);
        startActivity(intent);
        finish();
    }
}