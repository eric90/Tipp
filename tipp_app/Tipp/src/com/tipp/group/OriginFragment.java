package com.tipp.group;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.tipp.R;
import com.tipp.group.adt.GroupManager;
import com.tipp.user.adt.UserProfileFragment;

public class OriginFragment extends Fragment{
    private GroupManager groupManager;
    private String user_ID;
    private int requestNum = 0;
    

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		user_ID = getArguments().getString("SESSION_ID");
		Log.d("user_id",user_ID);
		View view = inflater.inflate(R.layout.fragment_origin, container, false);
		new DownloadJSONTask().execute(new String[] {"http://ec2-54-191-237-123.us-west-2.compute.amazonaws.com/test.php"}); 
		final ImageButton btnGroup = (ImageButton) view.findViewById(R.id.btnGroup);
		final ImageButton btnSearch = (ImageButton) view.findViewById(R.id.btnSearch);
		final ImageButton btnProfile = (ImageButton) view.findViewById(R.id.btnProfile);
		
		btnGroup.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				requestNum = 0;
				final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
				btnGroup.setImageResource(R.drawable.addgroupcurrent);
				btnSearch.setImageResource(R.drawable.ssearch);
				btnProfile.setImageResource(R.drawable.profile);
				
				new DownloadJSONTask().execute(new String[] {"http://ec2-54-191-237-123.us-west-2.compute.amazonaws.com/test.php"});
			}
		});
		btnSearch.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				requestNum=1;
				btnSearch.setImageResource(R.drawable.ssearchcurrent);
				btnGroup.setImageResource(R.drawable.addgroup);
				btnProfile.setImageResource(R.drawable.profile);
				new DownloadJSONTask().execute(new String[] {"http://ec2-54-191-237-123.us-west-2.compute.amazonaws.com/test.php"});

				//startOnSearchFragment(groupManager.getGroupNotJoinedBundle());
			}
		});
		btnProfile.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				  final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			      imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

			      FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			      UserProfileFragment userProfileFragment = new UserProfileFragment();
			      Bundle bundle = groupManager.getGroupJoinedBundle();
			      bundle.putString("user_Name", getArguments().getString("USER_NAME"));
			      userProfileFragment.setArguments(bundle);
			      fragmentTransaction.replace(R.id.origin_container, userProfileFragment);
			      fragmentTransaction.commit();
			      btnProfile.setImageResource(R.drawable.profilecurrent);
				  btnGroup.setImageResource(R.drawable.addgroup);
				  btnSearch.setImageResource(R.drawable.ssearch);
			}
		});
		
		return view;
	}
	
    private class DownloadJSONTask extends AsyncTask<String,Integer,JSONObject> { 
	    String data = "";
    	protected void onPreExecute() {
	        // NOTE: You can call UI Element here.
	         
	        //Start Progress Dialog (Message)
	       
	         
	        try{
	            // Set Request parameter
	                    //searchtext = searchview.getQuery().toString();
	            data +="?" + URLEncoder.encode("userid","UTF-8") + "=" + user_ID ;
	                 
	        } catch (UnsupportedEncodingException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	         
	    } 	
      protected JSONObject doInBackground(String... urls) {
          JSONObject result = null;
          DefaultHttpClient client = new DefaultHttpClient();
          HttpGet httpGet = new HttpGet(urls[0] + data);
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
          try {
          	  groupManager = new GroupManager(result);
          	  groupManager.setUserID(user_ID);
          	  if(requestNum == 0)
          	  {
          		  startGroupFragment(groupManager.getGroupJoinedBundle());
          	  } else if(requestNum == 1)
          	  {
          		 startOnSearchFragment(groupManager.getGroupNotJoinedBundle());
          	  }
          } catch (Exception e) {
              Log.d("EXCEPTION", e.getMessage());
          } 
      }
  }
  public void startGroupFragment(Bundle bundle){
      FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
      GroupFragment fragment = new GroupFragment();
      fragment.setArguments(bundle);
      fragmentTransaction.replace(R.id.origin_container, fragment);
      fragmentTransaction.commit();
  }
  
  public void startOnSearchFragment(Bundle bundle){
      FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
      OnSearchFragment searchFragment = new OnSearchFragment();
      searchFragment.setArguments(bundle);
      fragmentTransaction.replace(R.id.origin_container, searchFragment);
      fragmentTransaction.commit();
  }   
}
