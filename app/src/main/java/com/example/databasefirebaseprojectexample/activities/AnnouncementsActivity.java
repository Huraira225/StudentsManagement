package com.example.databasefirebaseprojectexample.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.databasefirebaseprojectexample.classes.AnnouncementsGetterSetter;
import com.example.databasefirebaseprojectexample.adapters.AnnouncementsListActivity;
import com.example.databasefirebaseprojectexample.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementsActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;
    ListView listViewUsers;
    List<AnnouncementsGetterSetter> Users;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_Announcement);
        listViewUsers = (ListView) findViewById(R.id.listView_announcements);
        TextView textView = findViewById(R.id.textView_classfellow_back);

        databaseReference = FirebaseDatabase.getInstance().getReference("Announcements");
        textView.setOnClickListener(this);
        Users = new ArrayList<>();

    }


    @Override
    protected void onStart() {
        super.onStart();
            progressBar.setVisibility(View.VISIBLE);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //clearing the previous User list
                    Users.clear();
                    //getting all nodes
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        //getting User from firebase console
                        AnnouncementsGetterSetter User = postSnapshot.getValue(AnnouncementsGetterSetter.class);
                        //adding User to the list
                        Users.add(User);
                    }
                    //creating Userlist adapter
                    AnnouncementsListActivity UserAdapter = new AnnouncementsListActivity(AnnouncementsActivity.this, Users);
                    //attaching adapter to the listview
                    listViewUsers.setAdapter(UserAdapter);
                    progressBar.setVisibility(View.GONE);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(AnnouncementsActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}

