package com.tipp.group.adt;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.tipp.user.adt.User;

public class GroupJoined implements Parcelable{
	
	private String name = "";
	private int groupId;
	private List<User>members;
	private int avgGroupRating;
	
	public GroupJoined(String name){ 
		setName(name);
		setMembers(null);
	}
	
	public void setName(String groupName) {
		this.name = groupName;
	}

	public String getName() {
		return name;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getGroupRating(){
		return this.avgGroupRating;
	}
	public void setGroupRating(int groupRating)
	{
		this.avgGroupRating = groupRating;
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
