package com.krislq.sliding.fragment;

import together.activity.FragmentChangeActivity;
import together.activity.R;
import together.activity.SettingsActivity;
import together.activity.WhisperMainActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class ColorMenuFragment extends ListFragment {

	
	Fragment newContent = null;
	Bundle bundle = null;
	Fragment[] fr ={new HomeFragment(),new MenuFragmentt(),new ColorFragment(android.R.color.white),new ColorFragment(android.R.color.black)};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] colors = getResources().getStringArray(R.array.color_names);
		ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_list_item_1, android.R.id.text1, colors);
		setListAdapter(colorAdapter);
		bundle = savedInstanceState;
		 
		
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Intent intent;
		switch (position) {
		case 0:
//			if (bundle != null){
//				newContent = this.getActivity().getSupportFragmentManager().getFragment(bundle, "mContent");
//			}else{
			newContent =new  WhisperMainFragment();
//			}
//			 ComponentName componetName = new ComponentName(  
//	                    //这个是另外一个应用程序的包名  
//	                    "com.yangyu.mycustomtab02",  
//	                    //这个参数是要启动的Activity  
//	                    "com.yangyu.mycustomtab02.MainTabActivity");  
//			 
	                
	                    //Intent intent = new Intent(this.getActivity(),HomeActivity.class);  
//	                  ////  intent.setComponent(componetName);  
	                    //startActivity(intent);  
//	                
			break;
		case 1:
			newContent =new  WhisperMyActivityFragment();
			
			break;
		case 2:
			 intent = new Intent(this.getActivity(),SettingsActivity.class );
				startActivity(intent);
			
			return;
		case 3:
		    intent = new Intent(this.getActivity(),SettingsActivity.class );
			startActivity(intent);
			return;
			
		}
		if (newContent != null)
			switchFragment(newContent);
	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		
		if (getActivity() instanceof FragmentChangeActivity) {
			FragmentChangeActivity fca = (FragmentChangeActivity) getActivity();
			fca.switchContent(fragment);
		
	}


	}
}
