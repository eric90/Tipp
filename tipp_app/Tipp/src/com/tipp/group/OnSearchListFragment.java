package com.tipp.group;
     
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.tipp.R;
import com.tipp.adapters.GroupNotJoinedAdapter;
import com.tipp.group.adt.GroupNotJoined;
     
     
public class OnSearchListFragment extends ListFragment{
    
	private String searchStr = "";
	ArrayAdapter<String> adapter;
    ArrayList <String> groupNames;
    ArrayList <Integer> groupIds;
	private int grp;
	private String currentUserId = "";
	private ArrayList<GroupNotJoined> groupNotJoinedList;
	private GroupNotJoinedAdapter groupAdapter;
	
	
	public void searchFilterText(String str){
		searchStr = str;
		if(groupAdapter != null)
		{
			groupAdapter.getFilter().filter(searchStr);

		} 
	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_on_search_list,container,false);
		final SearchView sv = (SearchView) view.findViewById(R.id.searchView1);
		sv.setQueryHint("Search or Add groups");
		
		//automatically expand search view and place cursor inside
		//must be set to true in xml file to resize back down
		sv.setIconified(false);
		
		
	      sv.setOnQueryTextListener(new OnQueryTextListener(){
	          @Override
	          public boolean onQueryTextSubmit(String query) {
	                	  searchFilterText(query);
	                  return false;
	          }
	          
	          @Override
	          public boolean onQueryTextChange(String newText) {
	        	  searchFilterText(newText);
	                //  return true;
	              return false;
	          }

	          
	      });
	      sv.setOnCloseListener(new OnCloseListener()
	      {

			@Override
			public boolean onClose() {
				// TODO Auto-generated method stub
				View view = getView();
				searchStr = "";
				//createButton.setVisibility(View.INVISIBLE);
				//Log.d("ONCLOSE", createButton.getVisibility() + "");
				return false;
			}
			
	    	  
	      });
	      
			Button createButton = (Button) view.findViewById(R.id.createGroupButton);
			createButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(searchStr.equals(""))
					{
						Toast.makeText(getActivity(), "Need to type in a string",
	                           Toast.LENGTH_LONG).show();
					} else if(groupAdapter.getCount() == 0 && !searchStr.equals(""))
					{
						View view = getView();
						Button createButton = (Button) view.findViewById(R.id.createGroupButton);
						//createButton.setVisibility(View.VISIBLE);
				    	 Log.d("searchtext1" , searchStr);
						createButton.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Toast.makeText(getActivity(), "Group Added",
				                           Toast.LENGTH_LONG).show();
								
								new AddToGroup().execute(new String[] {"http://ec2-54-191-237-123.us-west-2.compute.amazonaws.com/add.php"});
								sv.setQuery("", false);
							}
							
						});
					} 
					
				}
				
			});
			
	      return view;
    }
     public void onActivityCreated(Bundle savedInstanceState) {
  
       //get bundle and get all groups array
       currentUserId = getArguments().getString("user_ID");
       //Log.d("onsearchlistfragment userid = ",currentUserId);
	   groupIds = getArguments().getIntegerArrayList("groupIds");
       groupNames = getArguments().getStringArrayList("groupStringArray");
       groupNotJoinedList = getArguments().getParcelableArrayList("groupNotJoined");
       super.onActivityCreated(savedInstanceState);
       adapter = new ArrayAdapter<String>(getActivity(),R.layout.light_blue, R.id.gsearchtitle, groupNames);
       groupAdapter = new GroupNotJoinedAdapter(getActivity(),groupNotJoinedList);
       setListAdapter(groupAdapter);

     }

     @Override
     public void onListItemClick(ListView l, View v, int position, long id) {
    	 super.onListItemClick(l,  v,  position, id);
    	 Log.d("string!!!", "the pos:"+position);
    	 grp = (Integer) groupIds.toArray()[position];
    	 
    	 //get string at position
    	 //use that string at remove
    	 GroupNotJoined remove_group = groupAdapter.getItem(position);
    	 grp = remove_group.getGroupId();
    	 groupAdapter.remove(remove_group);
    	 Toast.makeText(getActivity(), "Group Joined",
                 Toast.LENGTH_LONG).show();
    	 
    	 new JoinGroup().execute(new String[] {"http://ec2-54-191-237-123.us-west-2.compute.amazonaws.com/joinGroups.php"});
     }
     
     
     
            private class JoinGroup extends AsyncTask<String,Integer,String> {
            String data = "";
            String Content = "";
            String searchtext = "";
           
            protected void onPreExecute() {
                // NOTE: You can call UI Element here.
                 
                //Start Progress Dialog (Message)
               
                 
                try{
                    // Set Request parameter
                            //searchtext = searchview.getQuery().toString();
                    data +="?" + URLEncoder.encode("groupid", "UTF-8") + "=" + grp + "&" + URLEncoder.encode("userid","UTF-8") + "=" + currentUserId;
                         
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                 
            }        
            protected String doInBackground(String... urls) {
                JSONObject result = null;
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(urls[0]); // Don't do this
                Log.d("JSON Thing","lets get started");
                //test.setText("before try");
                try {
                   
                    // Defined URL  where to send data
                    URL url = new URL(urls[0] + data);
                       
                   // Send POST data request
         
                   URLConnection conn = url.openConnection();
                   conn.setDoOutput(true);
                   OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                   wr.write( data );
                   wr.flush();
               
                   // Get the server response
                     
                   BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                   StringBuilder sb = new StringBuilder();
                   String line = null;
                 
                     // Read Server Response
                     while((line = reader.readLine()) != null)
                         {
                                // Append server response in string
                                sb.append(line + "");
                         }
                     
                     // Append Server Response To Content String
                    Content = sb.toString();
                } catch (Exception e) {
                   
                    StringWriter sw = new StringWriter();
                    e.printStackTrace(new PrintWriter(sw));
     
                } // Don't do this
                //return result;
                return Content;
            }
     
            protected void onPostExecute(String result) {
            }
       }          
            private class AddToGroup extends AsyncTask<String,Integer,String> {
                String data = "";
                String Content = "";
     
                protected void onPreExecute() {
                    // NOTE: You can call UI Element here.
                      
                    //Start Progress Dialog (Message)
                    
                      
                    try{
                        // Set Request parameter
                                //searchtext = searchview.getQuery().toString();
                        data +="?" + URLEncoder.encode("groupname", "UTF-8") + "=" + URLEncoder.encode(searchStr, "UTF-8") + "&" + URLEncoder.encode("userid","UTF-8") + "=" + currentUserId;
                        Log.d("DATA2", searchStr);      
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                      
                }        
                protected String doInBackground(String... urls) {
                    JSONObject result = null;
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(urls[0] + data); // Don't do this
                    Log.d("JSON Thing",data);
                    //test.setText("before try");
                    try {
                        
                        // Defined URL  where to send data
                        URL url = new URL(urls[0] + data);
                            
                       // Send POST data request
              
                       URLConnection conn = url.openConnection();
                       conn.setDoOutput(true);
                       OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                       wr.write( data );
                       wr.flush();
                    
                       // Get the server response
                          
                       BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                       StringBuilder sb = new StringBuilder();
                       String line = null;
                      
                         // Read Server Response
                         while((line = reader.readLine()) != null)
                             {
                                    // Append server response in string
                                    sb.append(line + "");
                             }
                          
                         // Append Server Response To Content String
                        Content = sb.toString();
                    } catch (Exception e) {
                        
                        StringWriter sw = new StringWriter();
                        e.printStackTrace(new PrintWriter(sw));
          
                    } // Don't do this
                    //return result;
                    return Content;
                }
          
                protected void onPostExecute(String result) {
                }
                
          
          
            } 
     
    }

