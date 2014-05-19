package com.fragments;


import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.adapters.AllPrizesAdapter;
import com.classes.Prize;
import com.myfamily.R;

public class FragmentPrize extends Fragment  {

	private ListView prizesListView;
	private ArrayList<Prize> prizesList;
	private Context context;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_prize, container, false);
       /* context = getActivity().getApplicationContext();
        Button addPrizeButton = (Button) rootView.findViewById(R.id.addPrizeButton);
        addPrizeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addPrizePopUp();
			}
		});*/
        
        prizesListView = (ListView) rootView.findViewById(R.id.oldTasksListView); 
        
        Bundle bundle = getArguments();
        prizesList = (ArrayList<Prize>) bundle.get("prizesList");
        
        if(prizesList != null) {
        	initializedList();
        }
        
        return rootView;
    }
	private void initializedList() {
		AllPrizesAdapter adapter;
		if(!prizesList.isEmpty()) {
			adapter = new AllPrizesAdapter(this.getActivity(), prizesList);
		} else {
			ArrayList<Prize> tempList = new ArrayList<Prize>();
			tempList.add(new Prize("Brak nagrod","",""));
			adapter = new AllPrizesAdapter(this.getActivity(), tempList);
		}
		prizesListView.setAdapter(adapter);
	}
	public void updateList(ArrayList<Prize> allPrizes) {
		this.prizesList = allPrizes;
		initializedList();
	}
}

