package com.tipp.group;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tipp.R;



public class OnSearchFragment extends Fragment{
	OnSearchListFragment searchFragment;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_on_search, container, false);
	      
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        searchFragment = new OnSearchListFragment();
        searchFragment.setArguments(this.getArguments());
        fragmentTransaction.add(R.id.search_list_container, searchFragment);
        fragmentTransaction.commit();
        return view;
    }
}
