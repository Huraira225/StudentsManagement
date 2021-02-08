package com.example.databasefirebaseprojectexample.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databasefirebaseprojectexample.R;
import com.example.databasefirebaseprojectexample.GetterSetterActivitys.RegisterUsers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
  String FirstName,LastName,UserName,Cnic,PhoneNo,Email,Password,id,Approval;
  EditText etfirstname,etlastname,etuserName,etcnic,etphoneNo,etEmail,etPassword;
  TextView tvlogin;
  Button register;
  ProgressBar progressBar;
  DatabaseReference  databaseReference;
  FirebaseAuth auth;
  FirebaseAnalytics firebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        //  EditText
        etfirstname=findViewById(R.id.editText_firstname);
        etlastname=findViewById(R.id.editText_lastname);
        etuserName=findViewById(R.id.editText_username);
        etcnic=findViewById(R.id.editText_cnic);
        etphoneNo=findViewById(R.id.editText_phoneNo);
        etPassword=findViewById(R.id.editText_password);
        etEmail=findViewById(R.id.editText_email);
        // TextView as a button
        tvlogin=findViewById(R.id.textView_login);
        //Progressbar
        progressBar=findViewById(R.id.progressBar);
        // Button
        register=findViewById(R.id.button_register);
        databaseReference= FirebaseDatabase.getInstance("https://databaseregisterationuser-default-rtdb.firebaseio.com/").getReference("Students");
        auth=FirebaseAuth.getInstance();
        firebaseAnalytics= FirebaseAnalytics.getInstance(this);


        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        FirstName=etfirstname.getText().toString();
        LastName=etlastname.getText().toString();
        UserName=etuserName.getText().toString();
        Cnic=etcnic.getText().toString();
        PhoneNo =etphoneNo.getText().toString();
        Email=etEmail.getText().toString();
        Password=etPassword.getText().toString();

            boolean error = false;

            if(etfirstname.getText().toString().isEmpty())
            {
                error = true;
                etfirstname.setError("first name is required");
            }
            if(etlastname.getText().toString().isEmpty())
            {
                error = true;
                etlastname.setError("last name is required");
            }
            if(etuserName.getText().toString().isEmpty())
            {
                error = true;
                etuserName.setError("Username is required");
            }
            if(etcnic.getText().toString().isEmpty())
            {
                error = true;
                etcnic.setError("Cnic number is required");
            }
            else if(!(Cnic.matches("^[0-9]{5}-[0-9]{7}-[0-9]{1}$")))
            {
            etcnic.setError("Invalid Cnic");
            error=true;
            }
            if(etphoneNo.getText().toString().length() < 6)
            {
                error = true;
                etphoneNo.setError("phone no is required");
            }

            if(etEmail.getText().toString().isEmpty())
            {
            error = true;
            etEmail.setError("email address is required");
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            error=true;
            etEmail.setError("Please provide valid email");
            }
            if(etPassword.getText().toString().isEmpty())
            {
            error = true;
            etPassword.setError("password is required");
            }
            else if (Password.length() < 6) {
            error=true;
            etPassword.setError("Password too short, enter minimum 6 characters!");
            }
            if(!error)
            {

        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    Bundle bundle= new Bundle();
                    bundle.putString("signup","Student signup");
                    firebaseAnalytics.logEvent("signup",bundle);
                    Toast.makeText(SignUpActivity.this, "Sending your registeration form to Admin Please wait....", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {

                            addInDb();
                        }
                    },3000);
                }
            }
        });
            } else {

            }
    }



    public  void addInDb(){
        FirstName=etfirstname.getText().toString();
        LastName=etlastname.getText().toString();
        UserName=etuserName.getText().toString();
        Cnic=etcnic.getText().toString();
        PhoneNo =etphoneNo.getText().toString();
        Email=etEmail.getText().toString();


        String id=auth.getUid();
        Approval="no";
        RegisterUsers registerUser=new RegisterUsers(FirstName,LastName,UserName,Cnic,PhoneNo,Email,id,Approval);
        databaseReference.child(id).setValue(registerUser);
        startActivity(new Intent(SignUpActivity.this, RegistrationCompleteActivity.class));
    }
}
