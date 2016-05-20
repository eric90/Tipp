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
import com.tipp.groupname.Reviews;

public class ReviewsAdapter extends ArrayAdapter<Reviews> {
  private final Context context;
  private final ArrayList<Reviews> values;

  public ReviewsAdapter(Context context, ArrayList<Reviews> values) {
    super(context, R.layout.review, values);
    this.context = context;
    this.values = values;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.review, parent, false);
    TextView reviewText = (TextView) rowView.findViewById(R.id.gsearchtitle);
    TextView timeText = (TextView) rowView.findViewById(R.id.gsearchtitle1);
    ImageView ratingImg = (ImageView) rowView.findViewById(R.id.review_rating);
    Reviews reviewObj = values.get(position);
    reviewText.setText(reviewObj.getReview());
    timeText.setText(reviewObj.getTimeStamp());
    double ratingValue = reviewObj.getRating();
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
    
    
    return rowView;
  }
} 