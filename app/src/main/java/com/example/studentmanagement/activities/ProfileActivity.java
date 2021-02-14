package com.example.studentmanagement.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentmanagement.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.media.CamcorderProfile.get;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    TextView firstName, lastName, userName, cnic, phoneNo,email,back ;
    Button button;
    DatabaseReference profileUserRef;
    FirebaseAuth auth;
    Context context = this;
    String currrentUserId;
    FirebaseAnalytics firebaseAnalytics;
    String FirstName,LastName,UserName,Cnic,PhoneNo,Email, approve ="yes";
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        firebaseAnalytics= FirebaseAnalytics.getInstance(this);
        auth = FirebaseAuth.getInstance();
        currrentUserId = auth.getCurrentUser().getUid();
        profileUserRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currrentUserId);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_profile);
        userName = (TextView) findViewById(R.id.TextView_username);
        email = (TextView) findViewById(R.id.TextView_email);
        phoneNo = (TextView) findViewById(R.id.TextView_phoneNo);
        firstName = (TextView) findViewById(R.id.TextView_firstname);
        lastName = (TextView) findViewById(R.id.TextView_lastname);
        cnic = (TextView) findViewById(R.id.TextView_cnic);
        button = findViewById(R.id.button_Edit_profile);
        back = findViewById(R.id.textView_profile_back);

        back.setOnClickListener(this);
        button.setOnClickListener(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        profileUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                     FirstName = dataSnapshot.child("firstname").getValue().toString().trim();
                     LastName = dataSnapshot.child("lastname").getValue().toString().trim();
                     UserName = dataSnapshot.child("username").getValue().toString().trim();
                     Cnic = dataSnapshot.child("cnic").getValue().toString().trim();
                     PhoneNo = dataSnapshot.child("phoneno").getValue().toString().trim();
                     Email = dataSnapshot.child("email").getValue().toString().trim();

                    firstName.setText(FirstName);
                    lastName.setText(LastName);
                    userName.setText(UserName);
                    cnic.setText(Cnic);
                    phoneNo.setText(PhoneNo);
                    email.setText(Email);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id==R.id.textView_profile_back)
        {
            finish();

        }else if (id == R.id.button_Edit_profile) {
            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProfileActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.layout_dialog_profile, null);
            mBuilder.setView(mView);
             final AlertDialog dialog = mBuilder.create();
            dialog.show();
            final EditText updateFirstname = dialog.findViewById(R.id.editText_firstname);
            final EditText updateLastname = dialog.findViewById(R.id.editText_lastname);
            final EditText updateUsername = dialog.findViewById(R.id.editText_username);
            final EditText updateCnic = dialog.findViewById(R.id.editText_cnic);
            final EditText updatePhoneno = dialog.findViewById(R.id.editText_phoneNo);
            final EditText updateEmail = dialog.findViewById(R.id.editText_email);
             Button buttonUpdate = dialog.findViewById(R.id.button_Update);

             updateEmail.setEnabled(false);
             updateFirstname.setText(FirstName);
             updateLastname.setText(LastName);
             updateUsername.setText(UserName);
             updateCnic.setText(Cnic);
             updatePhoneno.setText(PhoneNo);
             updateEmail.setText(Email);
             dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String FirstName = updateFirstname.getText().toString();
                    String LastName = updateLastname.getText().toString();
                    String UserName = updateUsername.getText().toString();
                    String Cnic = updateCnic.getText().toString();
                    String PhoneNo = updatePhoneno.getText().toString();
                    String Email = updateEmail.getText().toString();
                    String Id=auth.getUid();

                    profileUserRef.child("firstname").setValue(FirstName);
                    profileUserRef.child("lastname").setValue(LastName);
                    profileUserRef.child("username").setValue(UserName);
                    profileUserRef.child("cnic").setValue(Cnic);
                    profileUserRef.child("phoneno").setValue(PhoneNo);
                    profileUserRef.child("email").setValue(Email);

                                            Toast.makeText(ProfileActivity.this, "User Updated", Toast.LENGTH_LONG).show();
                                            Bundle bundle= new Bundle();
                                            bundle.putString("profile","Student profile");
                                            firebaseAnalytics.logEvent("profile",bundle);
                                            dialog.dismiss();
                }
            });
       }
    }
}





