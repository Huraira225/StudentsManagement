package com.example.databasefirebaseprojectexample.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.databasefirebaseprojectexample.R;

public class RegistrationCompleteActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_complete);

        Button button= findViewById(R.id.button_LogIn);
         button.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(RegistrationCompleteActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}