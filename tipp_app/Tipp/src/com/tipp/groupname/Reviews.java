package com.tipp.groupname;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.tipp.user.adt.User;

public class Reviews implements Parcelable{
	private String timestamp;
	private String review;
	private int rating;
	
	public Reviews(String timestamp, String review, int rating)
	{
		this.timestamp = timestamp;
		this.review = review;
		this.rating = rating;
		
	}
	
	public String getTimeStamp()
	{
		return timestamp;
	}
	public String getReview()
	{
		return review;
	}
	public int getRating()
	{
		return rating;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}

}
