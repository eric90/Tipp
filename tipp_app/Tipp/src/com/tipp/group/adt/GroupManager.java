package com.tipp.group.adt;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

public class GroupManager {

	private ArrayList<GroupJoined> gj;
	private ArrayList<GroupNotJoined> gnj;
	private String user_ID;
	private Double user_Rating;
	
	public GroupManager(JSONObject obj){
		gj = new ArrayList<GroupJoined>();
		gnj = new ArrayList<GroupNotJoined>();
		user_ID = "";

		
		try {
			JSONArray groupNotJoinedJSON = obj.getJSONArray("groupsNotJoined");
	        JSONArray groupJoinedJSON = obj.getJSONArray("groupsJoined");
	        JSONObject userRatingJSON = obj.getJSONObject("rating");
	        //Log.d("ONPOSTEXECUTE", "starting post execute");
	        
	        user_Rating = userRatingJSON.getDouble("avg");
	        
	        for(int i = 0; i < groupNotJoinedJSON.length(); i++)
	        {
	        	JSONObject childGroupJSON = groupNotJoinedJSON.getJSONObject(i);
	        	GroupNotJoined group = new GroupNotJoined (childGroupJSON.getString("name"));
                group.setGroupId(childGroupJSON.getInt("id"));
                gnj.add(group);
        }
	        //Log.d("GROUPS_LIST", tempArray);
	        for(int i = 0; i < groupJoinedJSON.length(); i++)
	        {
	                JSONObject childGroupJSON = groupJoinedJSON.getJSONObject(i);
		        	GroupJoined group = new GroupJoined (childGroupJSON.getString("name"));
	                group.setGroupId(childGroupJSON.getInt("id"));
	                group.setGroupRating(childGroupJSON.getInt("avg"));
	                Log.d("GROUP MANAGER", childGroupJSON.getInt("id") + "");
	                gj.add(group);                         
	        }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getGroupJoinedName(){
		ArrayList<String> groupNames = new ArrayList<String>();
		for(int i = 0; i < gj.size(); i++)
			groupNames.add(((GroupJoined) gj.toArray()[i]).getName());
		return groupNames;
	}
	
	public ArrayList<String> getGroupNotJoinedName(){
		ArrayList<String> groupNames = new ArrayList<String>();
		for(int i = 0; i < gnj.size(); i++)
			groupNames.add(((GroupNotJoined) gnj.toArray()[i]).getName());
		return groupNames;
	}
	
	public ArrayList<Integer> getGroupJoinedId(){
		ArrayList<Integer> groupNames = new ArrayList<Integer>();
		for(int i = 0; i < gj.size(); i++)
			groupNames.add(((GroupJoined) gj.toArray()[i]).getGroupId());
		return groupNames;
	}
	
	public ArrayList<Integer> getGroupNotJoinedId(){
		ArrayList<Integer> groupNames = new ArrayList<Integer>();
		for(int i = 0; i < gnj.size(); i++)
			groupNames.add(((GroupNotJoined) gnj.toArray()[i]).getGroupId());
		return groupNames;
	}
	public ArrayList<Integer> getGroupJoinedRating(){
		ArrayList<Integer> groupRatings = new ArrayList<Integer>();
		for(int i = 0; i < gj.size(); i++)
			groupRatings.add(((GroupJoined) gj.toArray()[i]).getGroupRating());
		return groupRatings;
	}	
	public Bundle getGroupJoinedBundle(){
		Bundle bundle = new Bundle();
		bundle.putStringArrayList("groupMemberStringArray", getGroupJoinedName());
		bundle.putIntegerArrayList("groupIds", getGroupJoinedId());
		bundle.putIntegerArrayList("groupRatings",getGroupJoinedRating());
		bundle.putParcelableArrayList("groupJoined", gj);
		bundle.putString("user_ID", user_ID);
		bundle.putDouble("user_Rating", user_Rating);
		//Log.d("groupmanager userid=", user_ID);
		return bundle;
	}
	public Bundle getGroupNotJoinedBundle(){
		Bundle bundle = new Bundle();
		bundle.putStringArrayList("groupStringArray", getGroupNotJoinedName());
		bundle.putIntegerArrayList("groupIds", getGroupNotJoinedId());
		bundle.putParcelableArrayList("groupNotJoined",gnj);
		bundle.putString("user_ID", user_ID);
		return bundle;
	}

	public void setUserID(String user_ID) {
		this.user_ID = user_ID;
	}
}
