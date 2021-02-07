package com.example.databasefirebaseprojectexample.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databasefirebaseprojectexample.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home_Screen_Activity extends AppCompatActivity implements View.OnClickListener {

    TextView firstName,email;
    FirebaseUser currentUser;
    Button btn1 ,btn2;
    DrawerLayout drawer;
    NavigationView navigationView;
    FirebaseAuth auth;
    ActionBarDrawerToggle toggle;
    String FirstName,LastName;
    DatabaseReference profileUserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        auth= FirebaseAuth.getInstance();
        currentUser=auth.getCurrentUser();

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid=currentUser.getUid();
            profileUserRef = FirebaseDatabase.getInstance().getReference().child("Students").child(uid);            //User is Logged in
        }else{
            startActivity(new Intent(Home_Screen_Activity.this,LoginScreenActivity.class));
            finish();
        }

        navigationView = findViewById(R.id.nav_view);
        View headerView =navigationView.getHeaderView(0);
        firstName = (TextView)headerView.findViewById(R.id.textview_header_name);
        email = (TextView)headerView.findViewById(R.id.textview_header_email);

        btn1=findViewById(R.id.button_profile);
        btn2=findViewById(R.id.button_classfellow);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

        drawer = findViewById(R.id.home_screen);



        
        toggle =new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId()){
                    case R.id.home_scr:
                        startActivity(new Intent(Home_Screen_Activity.this, AnnouncementsActivity.class));
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.porfile_scr:
                        startActivity(new Intent(Home_Screen_Activity.this, Profile_Screen_Activity.class));
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.classfellow_scr:
                        startActivity(new Intent(Home_Screen_Activity.this, ClassFellowsActivity.class));
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.logout_scr:
                        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("isUserLogin","UserLogout");
                        editor.commit();

                        auth.getInstance().signOut();
                        Intent intent = new Intent(Home_Screen_Activity.this,ChooseScreenActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.button_profile){
            startActivity(new Intent(Home_Screen_Activity.this, Profile_Screen_Activity.class));
        }
        else if (id == R.id.button_classfellow){
            startActivity(new Intent(Home_Screen_Activity.this, ClassFellowsActivity.class));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        profileUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    FirstName = dataSnapshot.child("firstname").getValue().toString().trim();
                    LastName = dataSnapshot.child("lastname").getValue().toString().trim();
                    FirstName = FirstName.substring(0,1).toUpperCase() + FirstName.substring(1).toLowerCase();
                    LastName = LastName.substring(0,1).toUpperCase() + LastName.substring(1).toLowerCase();

                    firstName.setText(FirstName+" "+LastName);
                    email.setText(currentUser.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
