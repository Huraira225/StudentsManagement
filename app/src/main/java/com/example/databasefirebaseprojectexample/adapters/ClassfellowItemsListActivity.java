package com.example.databasefirebaseprojectexample.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.databasefirebaseprojectexample.classes.RegisterUsers;
import com.example.databasefirebaseprojectexample.R;

import java.util.List;

public class ClassfellowItemsListActivity extends ArrayAdapter<RegisterUsers> {


    private Activity context;
    //list of users
    List<RegisterUsers> Users;

    public ClassfellowItemsListActivity(Activity context, List<RegisterUsers> Users) {
        super(context, R.layout.layout_classfellows_list_items, Users);
        this.context = context;
        this.Users = Users;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_classfellows_list_items, null, true);
        //initialize
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textview_Name);
        TextView textviewemail = (TextView) listViewItem.findViewById(R.id.textview_Email);
        //getting user at position
        RegisterUsers User = Users.get(position);
        //set user name
        textViewName.setText(User.getUsername());
        //set user email
        textviewemail.setText(User.getEmail ());
        //set user mobilenumber
        return listViewItem;
    }
}
