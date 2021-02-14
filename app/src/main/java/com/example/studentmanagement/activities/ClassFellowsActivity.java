package com.example.studentmanagement.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.studentmanagement.adapters.ClassfellowItemsListActivity;
import com.example.studentmanagement.R;
import com.example.studentmanagement.classes.RegisterUsers;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClassFellowsActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listView;
    DatabaseReference databaseReference;
    List<RegisterUsers> Users;
    FirebaseAnalytics firebaseAnalytics;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classfellows);

        firebaseAnalytics= FirebaseAnalytics.getInstance(this);
        databaseReference= FirebaseDatabase.getInstance().getReference("Students");

        listView=findViewById(R.id.listViewUsers);


        TextView textView=findViewById(R.id.textView_back);
        textView.setOnClickListener(this);

        Users = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RegisterUsers User = Users.get(i);
                Call( User.getFirstname(),User.getLastname(),User.getUsername(),User.getCnic(),User.getPhoneno(),User.getEmail(),User.getApprove(),User.getId());
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.orderByChild("approve").equalTo("yes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous User list
                Users.clear();
                //getting all nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting User from firebase console
                    RegisterUsers User = postSnapshot.getValue(RegisterUsers.class);
                    //adding User to the list
                    Users.add(User);
                }
                //creating Userlist adapter
                ClassfellowItemsListActivity UserAdapter = new ClassfellowItemsListActivity(ClassFellowsActivity.this, Users);
                //attaching adapter to the listview
                listView.setAdapter(UserAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void Call(final String firstname, final String lastname, final String username, final String cnic , final String phoneno, final String email, final String approve , final String id) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_dialog_classfellows, null);
        dialogBuilder.setView(dialogView);
        //Access Dialog Views
        final TextView updateTextFirstname =  dialogView.findViewById(R.id.editText_firstname);
        final TextView updateTextLastname =  dialogView.findViewById(R.id.editText_lastname);
        final TextView updateTextUsername =  dialogView.findViewById(R.id.editText_username);
        final TextView updateTextCnic =  dialogView.findViewById(R.id.editText_cnic);
        final TextView updateTextPhoneno = dialogView.findViewById(R.id.editText_phoneNo);
        final TextView updateTextEmail =  dialogView.findViewById(R.id.editText_email);

        updateTextFirstname.setText(firstname);
        updateTextLastname.setText(lastname);
        updateTextUsername.setText(username);
        updateTextCnic.setText(cnic);
        updateTextPhoneno.setText(phoneno);
        updateTextEmail.setText(email);

        final Button buttonEmail = (Button) dialogView.findViewById(R.id.button_Email);
        final Button buttonPhone = (Button) dialogView.findViewById(R.id.button_Phone);
        final Button buttonMessage = (Button) dialogView.findViewById(R.id.button_Message);
        //username for set dialog title
        final AlertDialog b = dialogBuilder.create();
        b.show();
        b.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Click listener for Update data
        buttonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailReceiverList= updateTextEmail.getText().toString();
                Intent intent= new Intent(Intent.ACTION_SEND);
                intent.setType("vnd.android.cursor.dir/email");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{ emailReceiverList}); ;
                startActivity(Intent.createChooser(intent,
                        "To complete action choose:"));
                Bundle bundle= new Bundle();
                bundle.putString("email","Student email");
                firebaseAnalytics.logEvent("email",bundle);
                b.dismiss();
            }

        });
        // Click listener for Delete data
        buttonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myPhoneNumberUri = "tel:"+updateTextPhoneno.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse(myPhoneNumberUri));
                startActivity(intent);
                Bundle bundle= new Bundle();
                bundle.putString("phone","Student phone");
                firebaseAnalytics.logEvent("phone",bundle);
                b.dismiss();
            }
        });
        buttonMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myPhoneNumberUri = updateTextPhoneno.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + myPhoneNumberUri));
                intent.putExtra("" ,"");
                startActivity(intent);
                Bundle bundle= new Bundle();
                bundle.putString("message","Student message");
                firebaseAnalytics.logEvent("message",bundle);
                b.dismiss();
            }
        });
    }
    @Override
    public void onClick(View view) {
        finish();
    }
}

