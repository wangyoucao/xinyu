package together.activity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import together.connectivity.JsonHandler;
import together.connectivity.ServerResponse;
import together.models.EventMsg;
import together.models.UserMsg;
import together.utils.Followers;
import together.utils.MyConstants;
import together.utils.Overlays;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.platform.comapi.basestruct.GeoPoint;

	private Button btn2;
	private static int flag=0;
	private Followers foverlays;
	// 存储从服务器获得的所有follower信息
	private ArrayList<HashMap<String, Object>> mFollower;
	/**
	 * 初始化activity
	 * @param instance Bundle
	 * */
	/**
	 * 当activity被唤醒时，重新start locationClient
	 * */
		//向服务器更新自己的位置信息
	/**
	 * 当activity被暂停时，关闭locationClient
	 * */
	/**
	 * 初始化UI，主要是从layout中获得布局components
	 * 
	 * */
						+ type + " ,  地点: " + place + "。 " 
						+ "活动描述： " + description;
		if(showText.length() > 63)
			showText = showText.substring(0, 63) + "...";
		
		btn2=(Button)findViewById(R.id.button2);

		btn2.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				//刷新地图
				mapView.getOverlays().clear();
				if(flag==1){
					try {
						showallfollowers();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if(mFollower==null || mFollower.size()==0) {
						Toast.makeText(getApplicationContext(), "这个活动还没有人参与，赶快参加吧！", Toast.LENGTH_SHORT).show();
						return;
					}
					mapView.getOverlays().add(foverlays);
					mapView.refresh();
                    GeoPoint p=new GeoPoint((int)(Double.parseDouble(latitude)*1e6), (int)(Double.parseDouble(longitude)*1e6));
					mapView.getController().animateTo(p);
					flag=0;
					btn2.setText("周围活动");
				}
				else {
					try {
						showallevents();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if(mAllEvents==null || mAllEvents.size()==0) {
						Toast.makeText(getApplicationContext(), "没有其他活动了", Toast.LENGTH_SHORT).show();
						return;
					}
					mapView.getOverlays().add(overlays);
					mapView.refresh();
					GeoPoint p=new GeoPoint((int)(Double.parseDouble(latitude)*1e6), (int)(Double.parseDouble(longitude)*1e6));
					mapView.getController().animateTo(p);
					flag=1;
					btn2.setText("参与用户");

				}

				
			}
		});
	
	/**
	 * 异步task，用于返回跟踪者
	 * */
	private class RequestFollowers extends AsyncTask<Void, Void, ArrayList<HashMap<String, Object>>> {
		protected ArrayList<HashMap<String, Object>> doInBackground(Void... params) {
			ArrayList<HashMap<String, Object>> result = null;
			try {
				result = requestFollwers();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}

		protected void onPostExecute(ArrayList<HashMap<String, Object>> result) {
			mFollower = result;

			
	     }
	}
	
	/**
	 * 异步task，用于返回所有事件
	 * */
			flag=1;
	/***
	 * 初始化地图，获取经纬度信息，并显示地图
	 * */
	/**
	 * 从服务器获取followers的信息，并返回arraylist
	 * @exception JSONException,ClientProtocolException,IOException
	 * @return list ArrayList<String,Object>
	 * */
	private ArrayList<HashMap<String, Object>> requestFollwers() throws JSONException, ClientProtocolException, IOException {
		ArrayList<HashMap<String, Object>> followersArray = new ArrayList<HashMap<String, Object>>();
		/*从服务器获取follower信息*/
		JSONObject jo = new JSONObject();
		//TODO 添加真实的用户信息
		jo.put("eid", eid);
		String url = MyConstants.SITE + getString(R.string.ListFollowUser);
		String result = ServerResponse.getResponse(url, jo);
		Log.i("together", "result:" + result);
		JsonHandler jsonHandler = new JsonHandler();
		List<UserMsg> msgs = jsonHandler.getUserMessages(result, "user");
		HashMap<String, Object> map;
		for (UserMsg p : msgs) {
			map = getUserMap(p);
			followersArray.add(map);
		}
		return followersArray;
	}
	
	/***
	 * @return 从服务器获取所有的事件列表
	 * @exception JSONException,ClientProtocolException,IOException
	 * */
	
	/**
	 * 从UserMsg对象中获取hash map
	 * @param p userMsg
	 * @return map<String,Object>
	 * */
	private HashMap<String, Object> getUserMap(UserMsg p) {
		HashMap<String, Object> map;
		map = new HashMap<String, Object>();
		map.put("uid", p.getUid());
		map.put("longitude", p.getLongitude());
		map.put("latitude", p.getLatitude());
		return map;
	}
	
	/**
	 * @param p EventMsg
	 * @return map HashMap<String , Object>
	 * 从EventMsg对象中返回hash map
	 * */
		map.put("type", p.getType());
		map.put("description", p.getDescription());
	/**
	 * 获得所有的坐标点
	 * @return points ArrayList<GeoPoint>
	 * */
	 * 刷新地图
	 * @throws IOException
	 * */
	
	
	/**
	 * 在地图中显示所有的参加者
	 * @throws IOException
	 * */
	public void showallfollowers() throws IOException{
		ArrayList<GeoPoint> followers=new ArrayList<GeoPoint>();
		for (int i=0;i<mFollower.size();i++){
			HashMap<String, Object> follower=mFollower.get(i);
			int latitude=(int)(Double.parseDouble((String)follower.get("latitude"))*1e6);
			int longitude=(int)(Double.parseDouble((String)follower.get("longitude"))*1e6);
			GeoPoint point=new GeoPoint(latitude, longitude);
			followers.add(point);
		}
		
		followers.add(new GeoPoint((int)(Double.parseDouble(latitude)*1e6), (int)(Double.parseDouble(longitude)*1e6)));
		
    	Drawable d=getResources().getDrawable(R.drawable.follower);
		foverlays=new Followers(context, d, followers, mapView, mFollower);


	}
	
	/**
	 * 获得当前的位置信息option
	 * @return location LocationClientOption
	 * */
	/**
	 * 参与某个活动
	 * @param eid event_id
	 * @param sid startUID
	 * @param fid followUID
	 * 
	 * */