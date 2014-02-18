package together.activity;

import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.baidu.mapapi.map.MapController;
import com.my.views.MyMapView;

/**
 * 功能描述：实现日期时间选择器
 * 
 * @author android_ls
 */
public class SendWhisper extends AbstractAsyncActivity  {

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
				
				Intent intent =new Intent(SendWhisper.this,PiccutActivity.class);
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