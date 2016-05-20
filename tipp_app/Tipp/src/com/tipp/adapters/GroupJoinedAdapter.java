package com.tipp.adapters;


import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tipp.R;
import com.tipp.group.adt.GroupJoined;

public class GroupJoinedAdapter extends ArrayAdapter<GroupJoined> {
  private final Context context;
  private final ArrayList<GroupJoined> values;

  public GroupJoinedAdapter(Context context, ArrayList<GroupJoined> values) {
    super(context, R.layout.profile_group_ratings_list, values);
    this.context = context;
    this.values = values;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.profile_group_ratings_list, parent, false);
    TextView reviewText = (TextView) rowView.findViewById(R.id.textView1);
    ImageView ratingImg = (ImageView) rowView.findViewById(R.id.gRatingText1);
    GroupJoined group = values.get(position);
    int ratingValue = group.getGroupRating();
    
    reviewText.setText(group.getName());
    //Log.d("BEFORE GETGROUPRATING","BEFORE GROUPRATING");
    if(ratingValue >= 0 && ratingValue <1)
    {
    	ratingImg.setImageResource(R.drawable.ratingempty);
    }else if(ratingValue >=1 && ratingValue < 2)
    {
    	ratingImg.setImageResource(R.drawable.ratingone);

    }else if(ratingValue >=2 && ratingValue < 3)
    {
    	ratingImg.setImageResource(R.drawable.ratingtwo);

    }else if(ratingValue >=3 && ratingValue < 4)
    {
    	ratingImg.setImageResource(R.drawable.ratingthree);

    }else if(ratingValue >=4 && ratingValue < 5)
    {
    	ratingImg.setImageResource(R.drawable.ratingfour);

    } else
    {
    	ratingImg.setImageResource(R.drawable.ratingfull);

    }
    //Log.d("AFTER GETGROUPRATING", "AFTER GROUPRATING");
    //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
    //textView.setText(values[position]);
    // change the icon for Windows and iPhone
    //String s = values[position];
    //if (s.startsWith("iPhone")) {
      //imageView.setImageResource(R.drawable.no);
    //} else {
      //imageView.setImageResource(R.drawable.ok);
    //}

    return rowView;
  }
} 