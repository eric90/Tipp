package com.tipp.groupname;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tipp.R;

public class GroupMemberFragment extends ListFragment {
	private int groupId;
	private String currentUserId = "";
	private ArrayAdapter adapter;
	private ArrayList<String> memberList = new ArrayList<String>();
	private ArrayList<String> memberIDList = new ArrayList<String>();
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_group_member, container, false);
		currentUserId = getArguments().getString("user_ID");
		groupId = getArguments().getInt("groupid");
		//Log.d("groupID: ",""+groupId);
		//currentUserId = getArguments().getInt("userId");
		//Log.d("userID: ",""+currentUserId);
   	 	new obtainReviews().execute(new String[] {"http://ec2-54-191-237-123.us-west-2.compute.amazonaws.com/obtainGroupInfo.php"});
        
   	 	View header = GroupNameFragment.change_header_View;
   	 	TextView t = (TextView) header.findViewById(R.id.textView1);
		String currentGroupName = getArguments().getString("group_name");
		t.setText(currentGroupName);
   	 	
		return view;
    }
	
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
     	  super.onListItemClick(l,  v,  position, id);
     	  Log.d("CREATE", "ENTERED");
     	  Bundle bundle = new Bundle();
     	  String memberId = (String) memberIDList.toArray()[position];
     	  String memberName = (String) memberList.toArray()[position];
     	  bundle.putString("reviewType", "personal");
     	  bundle.putString("memberId",memberId ); //Who receives the message
     	  bundle.putString("user_name",memberName ); //Name of who receives the message
     	  bundle.putInt("groupId", groupId); // The group that this review is a part of
     	  bundle.putString("user_ID", currentUserId); // The one sending the message
     	  CreateReviewFragment crf = new CreateReviewFragment();
     	  FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
     	  fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down, R.anim.slide_in_up,  R.anim.slide_out_down);
     	  crf.setArguments(bundle);
     	  fragmentTransaction.add(R.id.main_container, crf);
     	  fragmentTransaction.addToBackStack(null);
     	  fragmentTransaction.commit();
    }
	
	
    private class obtainReviews extends AsyncTask<String,Integer,JSONObject> {
    String data = "";
    String Content = "";
    String searchtext = "";
   
    protected void onPreExecute() {
        // NOTE: You can call UI Element here.
         
        //Start Progress Dialog (Message)
       
         
        try{
            // Set Request parameter
                    //searchtext = searchview.getQuery().toString();
            data +="?" + URLEncoder.encode("groupid", "UTF-8") + "=" + groupId + "&" + URLEncoder.encode("userid","UTF-8") + "=" + currentUserId;
                 
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         
    }        
    protected JSONObject doInBackground(String... urls) {
        JSONObject result = null;
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(urls[0]+data);
        Log.d("JSON Thing","lets get started");
        try {
            HttpResponse execute_response = client.execute(httpGet);
                    StatusLine statusLine = execute_response.getStatusLine();
                    int statusCode = statusLine.getStatusCode();
                    if(statusCode == 200){
                InputStream content = execute_response.getEntity().getContent();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String response = "",s = "";

                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
                Log.d("MY_APP",response);
                result = new JSONObject(response);
                    } else {
                            
                    }
        } catch (Exception e) {
           
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
        }
        return result;
    }

    protected void onPostExecute(JSONObject result) {
    	try{
	        JSONArray reviews = result.getJSONArray("groupMembers");
		       
	        //Log.d("ONPOSTEXECUTE", "starting post execute");
	        for(int i = 0; i < reviews.length(); i++)
	        {
	        	JSONObject obj = reviews.getJSONObject(i);
	        	String reviewString = obj.getString("name");
	        	String id = obj.getString("id");
	        	Log.d("Review String " + i + " ", reviewString);
	        	memberList.add(reviewString);
	        	memberIDList.add(id);
	        }
    	}
    	catch(Exception e){
    		
    	}
        adapter = new ArrayAdapter<String>(getActivity(),R.layout.group_view, R.id.gsearchtitle, memberList);
        setListAdapter(adapter);
    }
    }
}
