package together.activity;

import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.my.views.MyMapView;

/**
 * 功能描述：实现日期时间选择器
 * 
 * @author android_ls
 */
public class SendActivity extends AbstractAsyncActivity implements View.OnTouchListener {

	private EditText etStartTime;

	private EditText etEndTime;
	private EditText activityAddress;
	private MyMapView mapView;
	private MapController mMapController;
	private Spinner spinner;
	private Button rel_btn;
	private Button btnUpload;
	private static byte UPLOAD_CODE = 11;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.release_cup);

		etStartTime = (EditText) this.findViewById(R.id.startTime);
		etEndTime = (EditText) this.findViewById(R.id.endTime);
 
		
		
		activityAddress = (EditText) this.findViewById(R.id.activityAddress);

		activityAddress.setOnTouchListener(this);

		etStartTime.setOnTouchListener(this);
		etEndTime.setOnTouchListener(this);
		
		rel_btn = (Button)this.findViewById(R.id.rel_btn);
		rel_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				new PostMessageTask().execute();
				
			}
			
		});
		btnUpload = (Button)this.findViewById(R.id.upload);
		btnUpload.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Intent intent =new Intent(SendActivity.this,PiccutActivity.class);
				startActivityForResult(intent, UPLOAD_CODE);	
			}
			
		});

	}

	// @Override
	// public void onClick(View v) {
	//
	//
	// if (v.getId() == R.id.activityAddress) {
	//
	//
	//
	//
	// AlertDialog.Builder builder = new AlertDialog.Builder(this);
	// final View view = View.inflate(this, R.layout.mapview, null);
	// builder.setView(view) ;
	//
	// mapView =(MyMapView) view.findViewById(R.id.bmapsView) ;
	// mMapController = mapView.getController();
	// mMapController.enableClick(true);
	// mMapController.setZoom(12);
	// mapView.displayZoomControls(true);
	// // mMapView.setTraffic(true);
	// // mMapView.setSatellite(true);
	// mapView.setDoubleClickZooming(true);
	//
	// builder.setTitle("选取地图坐标");
	// builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	//
	//
	// mapView.getXx();
	// activityAddress.setText( mapView.getXx()+"");
	//
	// etEndTime.requestFocus();
	//
	// dialog.cancel();
	// }
	// });
	// }
	//
	// }
	
	@Override
	protected void onActivityResult(int requestCode,int resultCode,Intent data) { 
	  super.onActivityResult(requestCode, resultCode, data); 
	  if(requestCode==12){ 
	    if(resultCode==RESULT_CANCELED){ 
	      setTitle("Cancel****"); 
	    }else if(resultCode==RESULT_OK){ 
	       Bundle  showBundle=data.getExtras();//从返回的Intent中获得Bundle 
	       String Name=showBundle.getString("maplocationValue");//从bundle中获得相应数据 
	       String longtitude =showBundle.getString("Longitude");
	       String latitude = showBundle.getString("Latitude");
	       activityAddress.setText(Name+":"+longtitude+":"+latitude); 
	     } 
	    } 
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			if (v.getId() == R.id.activityAddress) {

				Intent intent =new Intent(this,BaiduMapOverlayActivity.class);
				startActivityForResult(intent, 12);
//				
				//startActivity(intent);
				
//				final View view = View.inflate(this, R.layout.mapview, null);
//				builder.setView(view);
//
//				mapView = (MyMapView) view.findViewById(R.id.bmapsView);
//				mMapController = mapView.getController();
//				mMapController.enableClick(true);
//				mMapController.setZoom(12);
//				mapView.displayZoomControls(true);
//				// mMapView.setTraffic(true);
//				// mMapView.setSatellite(true);
//				mapView.setDoubleClickZooming(true);
//
//				// Drawable d=getResources().getDrawable(R.drawable.follower);
//				// foverlays=new Followers(context, d, followers, mapView,
//				// mFollower);
//
//				// 获取用于在地图上标注一个地理坐标点的图标
//				Drawable drawable = this.getResources().getDrawable(
//						R.drawable.follower);
//
//				// 创建覆盖物（MyOverlayItem）对象并添加到覆盖物列表中
//				mapView.getOverlays().add(new MyOverlayItem(drawable));
//
//				// 刷新地图
//				mapView.refresh();
//
//				builder.setTitle("选取地图坐标");
//				builder.setPositiveButton("确  定",
//						new DialogInterface.OnClickListener() {
//
//							@Override
//							public void onClick(DialogInterface dialog,
//									int which) {
//
//								mapView.getXx();
//								activityAddress.setText(mapView.getXx() + "");
//
//								etEndTime.requestFocus();
//
//								dialog.cancel();
//							}
//						});
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				View view = View.inflate(this, R.layout.date_time_dialog, null);
				final DatePicker datePicker = (DatePicker) view
						.findViewById(R.id.date_picker);
				final TimePicker timePicker = (android.widget.TimePicker) view
						.findViewById(R.id.time_picker);
				builder.setView(view);

				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(System.currentTimeMillis());
				datePicker.init(cal.get(Calendar.YEAR),
						cal.get(Calendar.MONTH),
						cal.get(Calendar.DAY_OF_MONTH), null);

				timePicker.setIs24HourView(true);
				timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
				timePicker.setCurrentMinute(Calendar.MINUTE);

				if (v.getId() == R.id.startTime) {
					final int inType = etStartTime.getInputType();
					etStartTime.setInputType(InputType.TYPE_NULL);
					etStartTime.onTouchEvent(event);
					etStartTime.setInputType(inType);
					etStartTime.setSelection(etStartTime.getText().length());

					builder.setTitle("选取起始时间");
					builder.setPositiveButton("确  定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									StringBuffer sb = new StringBuffer();
									sb.append(String.format("%d-%02d-%02d",
											datePicker.getYear(),
											datePicker.getMonth() + 1,
											datePicker.getDayOfMonth()));
									sb.append("  ");
									sb.append(timePicker.getCurrentHour())
											.append(":")
											.append(timePicker
													.getCurrentMinute());

									etStartTime.setText(sb);
									etEndTime.requestFocus();

									dialog.cancel();
								}
							});

				} else if (v.getId() == R.id.endTime) {
					int inType = etEndTime.getInputType();
					etEndTime.setInputType(InputType.TYPE_NULL);
					etEndTime.onTouchEvent(event);
					etEndTime.setInputType(inType);
					etEndTime.setSelection(etEndTime.getText().length());

					builder.setTitle("选取结束时间");
					builder.setPositiveButton("确  定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									StringBuffer sb = new StringBuffer();
									sb.append(String.format("%d-%02d-%02d",
											datePicker.getYear(),
											datePicker.getMonth() + 1,
											datePicker.getDayOfMonth()));
									sb.append("  ");
									sb.append(timePicker.getCurrentHour())
											.append(":")
											.append(timePicker
													.getCurrentMinute());
									etEndTime.setText(sb);

									dialog.cancel();
								}
							});
				}
			

			Dialog dialog = builder.create();
			dialog.show();
			}
		}

		return true;
	}

	/**
	 * 包含了一个覆盖物列表的覆盖物类
	 * 
	 * @author android_ls
	 */
	final class MyOverlayItem extends ItemizedOverlay<OverlayItem> {

		/** 覆盖物列表集合 */
		private ArrayList<OverlayItem> mOverlayList = new ArrayList<OverlayItem>();

		// 声明double类型的变量存储北京天安门的纬度、经度值
		private double mLat1 = 39.915; // point1纬度

		private double mLon1 = 116.404; // point1经度

		// 传进来的Drawable对象用于在地图上标注一个地理坐标点
		public MyOverlayItem(Drawable drawable) {
			super(drawable);

			// 将GPS纬度经度值转换成以微度的整数形式存储的地理坐标点

			/*
			 * 注：GeoPoint对象构造方法的参数列表：第一个是参数表示纬度，
			 * 第二个是经度（我们平时都是经纬度这么叫的，想着应该是经度在前的，呵呵。）
			 * 在网上查了下，GPS的值官方给的就是纬度经度，也就是说纬度是在前的，以前一直没太注意。
			 */
			GeoPoint geoPoint1 = new GeoPoint((int) (mLat1 * 1E6),
					(int) (mLon1 * 1E6));

			// 构造OverlayItem对象并添加到mOverlayList集合里
			mOverlayList.add(new OverlayItem(geoPoint1, "point1", "point1"));

			/*
			 * 官方的解释：在一个新ItemizedOverlay上执行所有操作的工具方法。
			 * 没搞明白啥意思，但是必须的调用这个方法，否则程序运行报错
			 */
			populate();
		}

		/*
		 * 返回的是从指定List集合中，取出的一个OverlayItem对象。 mOverlayList集合里一旦有了数据，在调用其之前，
		 * 一定的在MyOverlayItem的构造函数里调用这个方法populate();
		 */
		@Override
		protected OverlayItem createItem(int index) {
			return mOverlayList.get(index);
		}

		@Override
		public int size() {
			return mOverlayList.size();
		}

	}
	
	private class PostMessageTask extends AsyncTask<Void, Void, String> {

		private MultiValueMap<String, Object > message;

		@Override
		protected void onPreExecute() {
			showLoadingProgressDialog();

			// assemble the map
			message = new LinkedMultiValueMap<String, Object>();

			EditText editText = (EditText) findViewById(R.id.huodongName);
			message.add("huodongName", editText.getText().toString());

			spinner = (Spinner) findViewById(R.id.huodongType);
			message.add("huodongType", spinner.getSelectedItem().toString());

			//editText = (EditText) findViewById(R.id.activityAddress);
			message.add("huodongAddress", activityAddress.getText().toString());
			editText = (EditText) findViewById(R.id.startTime);
			message.add("startTime", editText.getText().toString());
			editText = (EditText) findViewById(R.id.endTime);
			message.add("endTime", editText.getText().toString());
		 
			editText = (EditText) findViewById(R.id.huodongDetail);
			message.add("huodongDetail", editText.getText().toString());
			
			editText = (EditText) findViewById(R.id.HuodongNote);
			message.add("huodongNote", editText.getText().toString());
		
			//Resource resource = new ClassPathResource("res/drawable/spring09_logo.png");

			
		//	message.add("file", resource);
		
		
		}
		

		@Override
		protected String doInBackground(Void... params) {
			try {
				// The URL for making the POST request
				final String url = getString(R.string.base_uri) + "/sendmessagemap";

				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate(true);
				
				// Make the network request, posting the message, expecting a String in response from the server
				ResponseEntity<String> response = restTemplate.postForEntity(url, message, String.class);

				// Return the response body to display to the user
				return response.getBody();
			} catch (Exception e) {
				Log.d(TAG, e.getMessage(), e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			dismissProgressDialog();
			showResult(result);
		}

	}
	// ***************************************
		// Private methods
		// ***************************************
		private void showResult(String result) {
			if (result != null) {
				// display a notification to the user with the response information
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, "I got null, something happened!", Toast.LENGTH_LONG).show();
			}
		}
}