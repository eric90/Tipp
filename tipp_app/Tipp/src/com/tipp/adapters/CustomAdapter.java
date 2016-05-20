package com.tipp.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tipp.R;
import com.tipp.group.adt.Group;


public class CustomAdapter extends ArrayAdapter<Group>{

	public CustomAdapter(Context context, ArrayList<Group> group) {
		super(context, 0, group);
	}
	
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position
       Group group = getItem(position);    
       // Check if an existing view is being reused, otherwise inflate the view
       if (convertView == null) {
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_group, parent, false);
       }
       // Lookup view for data population
       //TextView groupName = (TextView) convertView.findViewById(R.id.groupName);
       // Populate the data into the template view using the data object
       //groupName.setText(group.getName());
       // Return the completed view to render on screen
       return convertView;
   }
	

}
