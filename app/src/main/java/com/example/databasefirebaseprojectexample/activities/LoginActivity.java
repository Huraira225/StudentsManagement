package com.example.databasefirebaseprojectexample.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.databasefirebaseprojectexample.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity  {
     String Email,Password,Approve;
     EditText etEmail, etPassword;
     FirebaseAuth auth;
     DatabaseReference databaseReference;
     ProgressBar progressBar;
     TextView btnReset,btnSignup;
     Button  btnLogin;
     FirebaseAnalytics firebaseAnalytics;
     DatabaseReference profileUserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.editText_email);
        etPassword = (EditText) findViewById(R.id.editText_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnLogin = (Button) findViewById(R.id.button_LogIn);
        btnSignup = (TextView) findViewById(R.id.textView_signup);
        btnReset = (TextView) findViewById(R.id.textView_forgetpassword);

        profileUserRef = FirebaseDatabase.getInstance().getReference().child("Students");
        databaseReference= FirebaseDatabase.getInstance("https://databaseregisterationuser-default-rtdb.firebaseio.com/").getReference("Students");
        firebaseAnalytics= FirebaseAnalytics.getInstance(this);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();


        btnSignup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        btnReset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v == btnLogin) {
                    Email = etEmail.getText().toString().trim();
                    Password = etPassword.getText().toString().trim();

                    boolean error = false;

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
                //authenticate user
                    auth.signInWithEmailAndPassword(Email, Password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    } else {

                                        String uid= FirebaseAuth.getInstance().getUid();

                                        profileUserRef.child(uid).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {

                                                    Approve = dataSnapshot.child("approve").getValue().toString();

                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                if (Approve.equals("yes")) {
                                                    progressBar.setVisibility(View.GONE);
                                                    Bundle bundle= new Bundle();
                                                    bundle.putString("login","Student login");
                                                    firebaseAnalytics.logEvent("login",bundle);

                                                    SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = preferences.edit();
                                                    editor.putString("isUserLogin", "UserLogin");
                                                    editor.commit();

                                                    Toast.makeText(LoginActivity.this, "User login in this Activity", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                    startActivity(intent);
                                                    finish();

                                                }
                                                else{
                                                    Toast.makeText(LoginActivity.this, "Please Approve Your Account From Admin", Toast.LENGTH_LONG).show();

                                                }
                                            }
                                        },5000);

                                    }
                                }
                            });
                    } else {
                        //manage error case here
                    }
                }
            }
        });

    }
}
