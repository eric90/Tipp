package com.tipp.group.adt;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class GroupNotJoined implements Parcelable{
	
	private String name = "";
	private int groupId;
	private List<String>members;
	
	public GroupNotJoined(String name){
		this.name = name;
		//members = new ArrayList();
	}

	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
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
