package com.tipp.adapters;


import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.tipp.R;
import com.tipp.group.adt.GroupJoined;
import com.tipp.group.adt.GroupNotJoined;

public class GroupNotJoinedAdapter extends ArrayAdapter<GroupNotJoined> implements Filterable{
  private final Context context;
  private ArrayList<GroupNotJoined> values;
  //private int count;
  private ArrayList<GroupNotJoined> filteredData;

  public GroupNotJoinedAdapter(Context context, ArrayList<GroupNotJoined> values) {
    super(context, R.layout.light_blue, values);
    this.context = context;
    this.values = values;
    this.filteredData = values;
  }
  
  @Override
  public GroupNotJoined getItem(int position)
  {
	GroupNotJoined gnj = filteredData.get(position);
	return gnj;
	  
  }
  
  @Override
  public void remove(GroupNotJoined gnj)
  {
	  filteredData.remove(gnj);
	  notifyDataSetChanged();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.light_blue, parent, false);
    TextView nameText = (TextView) rowView.findViewById(R.id.gsearchtitle);
    
    GroupNotJoined group = filteredData.get(position);
    nameText.setText(group.getName());
    //Log.d("BEFORE GETGROUPRATING","BEFORE GROUPRATING");

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
  @Override
  public int getCount()
  {
	  return filteredData.size();
  }
  @Override
  public Filter getFilter()
  {
	  return new Filter()
	  {

		@Override
		protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();

            //If there's nothing to filter on, return the original data for your list
            if(charSequence == null || charSequence.length() == 0)
            {
                results.values = values;
                results.count = values.size();
            }
            else
            {
                ArrayList<GroupNotJoined> filterResultsData = new ArrayList<GroupNotJoined>();

                for(int i = 0; i < values.size(); i++)
                {
                    //In this loop, you'll filter through originalData and compare each item to charSequence.
                    //If you find a match, add it to your new ArrayList
                    //I'm not sure how you're going to do comparison, so you'll need to fill out this conditional
                    GroupNotJoined data = values.get(i);
                	String mainString = data.getName();
                	if(mainString.toLowerCase().contains(charSequence.toString().toLowerCase()))
                    {
                        filterResultsData.add(data);
                    }
                }            

                results.values = filterResultsData;
                results.count = filterResultsData.size();
            }

            return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			filteredData = (ArrayList<GroupNotJoined>)results.values;
			//count = results.count;
			// TODO Auto-generated method stub
			notifyDataSetChanged();
			
		}

	  };
  }
  
} 