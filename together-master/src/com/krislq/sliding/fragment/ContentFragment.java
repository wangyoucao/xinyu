package com.krislq.sliding.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import together.activity.R;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


/**
 * 
 * @author <a href="mailto:kris@krislq.com">Kris.lee</a>
 * @since Mar 12, 2013
 * @version 1.0.0
 */
public class ContentFragment extends Fragment {
    String text = null;
    GridView gridview;
    Wtype wt = Wtype.Popular;
    public ContentFragment() {
    }

    public ContentFragment(String text) {
        Log.e("Krislq", text);
        this.text = text;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.e("Krislq", "onCreate:"+text);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
    	
        
    	
    	List<HashMap<String, String>> list = getimageList();
    	
    //	AQuery listAq = new AQuery(this.getActivity());
//    	
//    	GridViewAdapter aa = new GridViewAdapter(this.getActivity(), list,listAq);
//    	
//    	listAq.id(R.id.grid).adapter(aa);
    }
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	View view = inflater.inflate(R.layout.fragment_whome, container, false);	
    	gridview = (GridView) view.findViewById(R.id.grid);
		
    	
    	
		return view;
    	
    	
    	}

    	// 从json中获取，图片地址、专辑名存放在list中

    	public List<HashMap<String, String>> getimageList() {
    	List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    	String body;
    	try {
    	//Constants.URL json地址,getContent用来获取数据流
//    	body = JsonUtil.getContent(Constants.URL);
//    	JSONArray array = new JSONArray(body);
//    	for (int i = 0; i < array.length(); i++) {
//    	JSONObject jobj = array.getJSONObject(i);
    	HashMap<String, String> map = new HashMap<String, String>();
    	//“你的json地址”+json中取图片的相对地址得到绝对地址
//    	map.put("itemImage",
//    	Constants.ABSOLUTE_URL + jobj.getString("music_pic"));
//    	map.put("itemText", jobj.getString("music_name"));
    	map.put("itemImage","http://192.168.1.155:8080/xinwei-demo-web/userpic/test.png");
    	map.put("itemText", "这是一个测试");
    	list.add(map);
    	
    	
    	
    	//}
    	} catch (Exception e) {
    	// TODO Auto-generated catch block
    	   e.printStackTrace();
    	}
    	return list;

    	
	
	}
    
    public String getText(){
        return text;
    }
    
    
    void feed(View paramView)
	  {
	    switch (paramView.getId())
	    {
	      
	    case 2131165339:
	      this.gridview.setEmptyView(null);
	     
	      if (this.wt != Wtype.Popular)
	      {
	        this.gridview.setSelection(0);
	        Log.v("WMainFragment", "Set Feed");
	        paramView.setSelected(true);
	        getView().findViewById(2131165340).setSelected(false);
	        getView().findViewById(2131165341).setSelected(false);
	       // setFeed(WType.WPopular);
	        return;
	      }
	      this.gridview.setSelection(0);
	      Log.v("WMainFragment", "Refresh Feed");
	      if (this.gridview.getCount() > 40){
	        this.gridview.setSelection(0);
	     
	        this.gridview.smoothScrollToPosition(0);
	      }
	    break;
	    case 2131165340:
	      
	    	if (this.wt != Wtype.Latest)
	      {
	        this.gridview.setSelection(0);
	        paramView.setSelected(true);
	        getView().findViewById(2131165339).setSelected(false);
	        getView().findViewById(2131165341).setSelected(false);
	       // setFeed(Wtype.Latest);
	        return;
	      }
	      if (this.gridview.getCount() > 40){
	        this.gridview.setSelection(0);
	     
	        // this.getView().refresh(false);
	       
	        this.gridview.smoothScrollToPosition(0);
	      }
         break;
	    case 2131165341:
	   
	    if (this.wt != Wtype.Near)
	    {
	      this.gridview.setSelection(0);
	      paramView.setSelected(true);
	      getView().findViewById(2131165340).setSelected(false);
	      getView().findViewById(2131165339).setSelected(false);
	      //setFeed(Wtype.Nearby);
	      return;
	    }
	    if (this.gridview.getCount() > 40){
	      this.gridview.setSelection(0);
	    //  refresh(false);
	     
	      this.gridview.smoothScrollToPosition(0);
	    
	  }
     }
	  }

    public static enum Wtype {
    		Popular,Latest,Near;
    
    }
    @Override
    public void onDestroy() {
        Log.e("Krislq", "onDestroy:"+ text);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.e("Krislq", "onDetach:"+ text);
        super.onDetach();
    }

    @Override
    public void onPause() {
        Log.e("Krislq", "onPause:"+ text);
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.e("Krislq", "onResume:"+ text);
        super.onResume();
    }

    @Override
    public void onStart() {
        Log.e("Krislq", "onStart:"+ text);
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.e("Krislq", "onStop:"+ text);
        super.onStop();
    }
    
}
