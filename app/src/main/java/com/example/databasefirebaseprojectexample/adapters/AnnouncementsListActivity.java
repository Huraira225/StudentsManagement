package com.example.databasefirebaseprojectexample.adapters;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.databasefirebaseprojectexample.classes.AnnouncementsGetterSetter;
import com.example.databasefirebaseprojectexample.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AnnouncementsListActivity extends ArrayAdapter<AnnouncementsGetterSetter> {

    private Activity context;
    //list of users
    List<AnnouncementsGetterSetter> Users;
    public AnnouncementsListActivity(Activity context, List<AnnouncementsGetterSetter> Users) {
        super(context, R.layout.layout_announcements_list_items, Users);
        this.context = context;
        this.Users = Users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_announcements_list_items, null, true);
        //initialize
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textView_title);
        TextView textviewemail = (TextView) listViewItem.findViewById(R.id.textview_accouncements);
        TextView textviewnumber = (TextView) listViewItem.findViewById(R.id.textview_date);

        //getting user at position
        AnnouncementsGetterSetter User = Users.get(position);
        //set user name
        textViewName.setText(User.getTile());
        //set user email
        textviewemail.setText(User.getAnncuncements());
        //set user mobilenumber
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String currentDateandTime = sdf.format(new Date());
        textviewnumber.setText(currentDateandTime);

        return listViewItem;
    }
}