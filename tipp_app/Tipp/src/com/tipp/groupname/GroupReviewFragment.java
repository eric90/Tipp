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
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tipp.R;
import com.tipp.adapters.ReviewsAdapter;



public class GroupReviewFragment extends ListFragment{
	private int groupId;
	private String currentUserId;
	private ArrayAdapter adapter;
	private ArrayList<String> reviewList = new ArrayList<String>();
	private ArrayList<Reviews> reviewsList;
	private ReviewsAdapter reviewAdapter;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_group_review, container, false);
		groupId = getArguments().getInt("groupid");
		Log.d("groupID: ",""+groupId);
		currentUserId = getArguments().getString("user_ID");
		//currentUserId = getArguments().getInt("userId");
		Log.d("userID: ",""+currentUserId);
   	 	new obtainReviews().execute(new String[] {"http://ec2-54-191-237-123.us-west-2.compute.amazonaws.com/obtainGroupInfo.php"});
        
   	 	View header = GroupNameFragment.change_header_View;
	 	TextView t = (TextView) header.findViewById(R.id.textView1);
		String currentGroupName = getArguments().getString("group_name");
		t.setText(currentGroupName);
		
   	 	return view;
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
                Log.d("REVIEW_RESPONSE",response);
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
	        JSONArray reviews = result.getJSONArray("yourReviews");
		    reviewsList = new ArrayList<Reviews>();   
	        //Log.d("ONPOSTEXECUTE", "starting post execute");
	        for(int i = 0; i < reviews.length(); i++)
	        {
	        	JSONObject reviewString = reviews.getJSONObject(i);
	        	
	        	String timestamp = reviewString.getString("ts");
	        	String content = reviewString.getString("content");
	        	int rating = reviewString.getInt("review_type");
	        	Reviews rev = new Reviews(timestamp,content,rating);
	        	reviewsList.add(rev);
	        	Log.d("Review String " + i + " ", content);
	        	reviewList.add(content);
	        }
    	}
    	catch(Exception e){
    		
    	}
    	reviewAdapter = new ReviewsAdapter(getActivity(),reviewsList);
    	//TODO NEED TO FINISH REVIEWS ADAPTER BY SETTING THE IMAGE
        adapter = new ArrayAdapter<String>(getActivity(),R.layout.review, R.id.gsearchtitle, reviewList);
        //setListAdapter(adapter); //PASS IN REVIEWSADAPTER
        setListAdapter(reviewAdapter);
    }
}
}
