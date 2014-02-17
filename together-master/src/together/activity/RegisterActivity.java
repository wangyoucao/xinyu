/**
 * register page
 * @author zhangjie
 * @version 1.0
 * @since 2012-2-13
 * */
package together.activity;
 

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import together.models.User;
import together.utils.MyConstants;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;



public class RegisterActivity extends Activity {
	private Context context;
	private ImageButton back;
	private TextView register;
	private EditText name;
	private EditText pwd;
	private EditText confirm;
	private ProgressDialog progressDialogFirstTime;
	/***
	 * called when the activity is created
	 * 
	 * @param bundle
	 *            saved instance state
	 */
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MyConstants.MSG_SUCCESS1: 
				break;
			case MyConstants.MSG_FAILURE:
				String str = (String) msg.obj;
				Toast.makeText(RegisterActivity.this,
						getString(R.string.register_fail) + " " + str,
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	/**
	 * 初始化activity
	 * @param instance Bundle
	 * 
	 * */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
	
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		context = this;
 		register = (TextView) findViewById(R.id.register_btn);
 		
		name = (EditText) findViewById(R.id.register_name_text);
		pwd = (EditText) findViewById(R.id.register_password_text);
		confirm = (EditText) findViewById(R.id.register_confirm_text);
		final Animation shakeAnim = AnimationUtils.loadAnimation(context,
				R.anim.shake_x);
		progressDialogFirstTime = new ProgressDialog(context);
		progressDialogFirstTime.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		//progressDialogFirstTime.setIcon(R.drawable.loading);
		progressDialogFirstTime.setMessage(getString(R.string._loading));

//		back.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				finish();
//			}
//
//		});
		User user = new User();
		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name_str = name.getText().toString();
				String pwd_str = pwd.getText().toString();
				String confirm_str = confirm.getText().toString();
				if (name_str == null || name_str.trim().equals("")) {
					name.startAnimation(shakeAnim);
					Toast.makeText(RegisterActivity.this,
							R.string.login_miss_username, Toast.LENGTH_SHORT)
							.show();
					return;
				}

				if (pwd_str == null || pwd_str.trim().equals("")) {
					pwd.startAnimation(shakeAnim);
					Toast.makeText(RegisterActivity.this,
							R.string.login_miss_password, Toast.LENGTH_SHORT)
							.show();
					return;
				}

				if (confirm_str == null || confirm_str.trim().equals("")) {
					confirm.startAnimation(shakeAnim);
					Toast.makeText(RegisterActivity.this,
							R.string.login_miss_confirm, Toast.LENGTH_SHORT)
							.show();
					return;
				}

				if (pwd_str.trim().equals(confirm_str.trim()) == false) {
					Toast.makeText(RegisterActivity.this,
							R.string.login_confirm_fail, Toast.LENGTH_SHORT)
							.show();
					return;
				}
				
				
				
				new PostMessageTask().execute(MediaType.APPLICATION_JSON);
				//progressDialogFirstTime.show();
				//register(name1, pwd1);
			}
		});
	}

	
	
	// ***************************************
		// Private classes
		// ***************************************
		private class PostMessageTask extends AsyncTask<MediaType, Void, String> {

			private User user = new User();;

			@Override
			protected void onPreExecute() {
				String name_str = name.getText().toString();
				String pwd_str = pwd.getText().toString();
				
				
				user.setUsername(name_str.trim());
				user.setPasword(pwd_str.trim());
				
				//showLoadingProgressDialog();

			}

			@Override
			protected String doInBackground(MediaType... params) {
				//String url = MyConstants.SITE + getString(R.string.NewEvent);
				
				//向服务器发送活动信息
				String result = "";
				
				try {
					result=	inertOrUpdateUser(user.getUsername(),user.getPasword());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	            if(result.equals("success")){
	            	Intent intent = new Intent(RegisterActivity.this,
	    					FragmentChangeActivity.class);
	            	startActivity(intent);
	            	finish();
	            }


				return result;
			}


			@Override
			protected void onPostExecute(String result) {
				//dismissProgressDialog();
				showResult(result);
			}

		}
	
		String inertOrUpdateUser(String uid, String username)  throws IOException {
			final String url = getString(R.string.base_uri) + "addOrUpdateUser";
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = null;
		

			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("uid", uid));
			params.add(new BasicNameValuePair("username", username));

			try {

				// String url="http://localhost:8080/rwar/addOrUpdateUser";
				HttpPost httppost = new HttpPost(url);
				httppost.setHeader("Content-Type",
						"application/x-www-form-urlencoded;charset=utf-8");
				httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				// Execute HTTP request

				try {
					response = httpclient.execute(httppost);
					if(response.getStatusLine().getStatusCode()==200){
						return "success";
					}else{
						return "false";
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

//				if (response.getStatusLine().getStatusCode() == 200) {
//					InputStream in = response.getEntity().getContent();// 接收服务器的数据，并在Toast上显示
//					String str = readString(in);
//					Toast.makeText(LoginActivity.this, str, Toast.LENGTH_SHORT)
//							.show();
	//
//				}
				// Log.d("dsfdsfsf", sResponse);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }
			return "";
		}
	
		// ***************************************
		// Private methods
		// ***************************************
		private void showResult(String result) {
			if (result != null) {
				// display a notification to the user with the response message
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, "I got null, something happened!", Toast.LENGTH_LONG).show();
			}
		}
	
	
	
	
	
	
	
	
	
	
	
	private void register(final String name1, final String pwd1) {
		new Thread(){  
			  
            @Override  
            public void run() {  
                //需要花时间计算的方法  
            	try {
					post();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                  
                //向handler发消息  
                handler.sendEmptyMessage(0);  
            }}.start();  
//		 
	}
	private void post() throws ParseException, JSONException, IOException {
		
		
		//String url = MyConstants.SITE + getString(R.string.NewEvent);
		User user = new User();
		user.setUsername("febng");
		user.setPasword("dssdg");
		//向服务器发送活动信息
		String result = "";
		try {
			
			
			// The URL for making the POST request
			final String url = "http://192.168.1.155:8080/xinwei-demo-web/register/";

			HttpHeaders requestHeaders = new HttpHeaders();

			// Sending a JSON or XML object i.e. "application/json" or "application/xml"
			requestHeaders.setContentType(MediaType.APPLICATION_JSON);

			// Populate the Message object to serialize and headers in an
			// HttpEntity object to use for the request
			HttpEntity<User> requestEntity = new HttpEntity<User>(user, requestHeaders);

			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
			
		    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			

			// Make the network request, posting the message, expecting a String in response from the server
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
					String.class);

			// Return the response body to display to the user
			result = response.getBody();
		} catch (Exception e) {
			Log.e("registerActivity", e.getMessage(), e);
			result="false";
		}


		if(result.contains("success"))
		{
			Toast.makeText(getApplicationContext(), "发布成功", Toast.LENGTH_SHORT).show();
			finish();
		}
		else
			Toast.makeText(getApplicationContext(), "发布失败", Toast.LENGTH_SHORT).show();
	}

	
}
