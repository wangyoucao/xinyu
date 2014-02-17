/**
 * the login page
 * @author zhangjie
 * @version 1.0
 * @since 2012-2-13
 * 
 * */
package together.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import together.utils.MyConstants;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.framework.WeiboActionListener;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.weibo.TencentWeibo;

public class LoginActivity extends Activity implements WeiboActionListener,
		OnClickListener {
	private Context context;
	private TextView login;
	private TextView register;
	private EditText uid;
	private EditText pwd;
	private TextView tecentBtn;
	private ProgressDialog progressDialog;
	private TextView sinaweibo;

	// private SharedPreferences preferences;
	// private SharedPreferences.Editor editor;

	/**
	 * called when the activity is created
	 * 
	 * @param bundle
	 *            saved instance state
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AbstractWeibo.initSDK(this);

		setContentView(R.layout.login);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		context = this;

		progressDialog = new ProgressDialog(context);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setIcon(R.drawable.loading);
		progressDialog.setMessage(getString(R.string._loading));
		/* 如果已经登录，无需再输入登录信息 */
//		 String uid = getSharedPreferences("user", Context.MODE_PRIVATE)
//		 .getString("uid", "NOT_LOGGED");
//		
//		 Log.i("together", uid);
//		 if (!uid.equals("NOT_LOGGED")) {
//		 // loginVarify(uid, null);
//		 Intent intent = new Intent(LoginActivity.this,
//		 FragmentChangeActivity.class);
//		 startActivity(intent);
//		 this.finish();
//		 }

		// 只要是已经登录验证过的不需要验证，直接登录
		// AbstractWeibo[] weiboList
		// = AbstractWeibo.getWeiboList(this);
		//
		// for(AbstractWeibo weibo:weiboList){
		// if(weibo.isValid()){
		//
		// String accessToken = weibo.getDb().getToken(); // 获取授权token
		// String openId = weibo.getDb().getWeiboId(); // 获取用户在此平台的 ID
		// String nickname = weibo.getDb().get("nickname"); // 获取用户昵称
		// weibo.setWeiboActionListener(this);
		// weibo.showUser(openId);
		// this.finish();
		//
		// return;
		// }
		// }

		initUI();

		setClickListener();
	}

	// @Override
	// public void onResume() {
	//
	// }

	/**
	 * 初始化UI界面
	 * */
	private void initUI() {
		login = (TextView) findViewById(R.id.login_login);
		register = (TextView) findViewById(R.id.login_register);
		uid = (EditText) findViewById(R.id.login_account);
		pwd = (EditText) findViewById(R.id.login_password);
		sinaweibo = (TextView) findViewById(R.id.sinaweibo);
		tecentBtn = (TextView) findViewById(R.id.btnQz);

	}

	/**
	 * 设置点击事件
	 * */
	private void setClickListener() {

		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
				finish();
			}
		});

		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickLogin();
			}

		});
		sinaweibo.setOnClickListener(this);
		tecentBtn.setOnClickListener(this);
	}

	public void onClick(View v) {

		AbstractWeibo weibo = getWeibo(v.getId());
		// weibo.removeAccount();
		// weibo.setWeiboActionListener(this);
		// weibo.authorize();

		// if (weibo.isValid()) {
		// String userId = weibo.getDb().getWeiboId();
		// if (userId != null) {
		// // UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
		// loginVarify(userId, null);
		// return;
		// }
		// }

		weibo.setWeiboActionListener(this);
		weibo.SSOSetting(true);
		weibo.showUser(null);

	}

	/**
	 * 检查login是否合法
	 * */
	private void clickLogin() {
		Animation shakeAnim = AnimationUtils.loadAnimation(context,
				R.anim.shake_x);
		String name_str = uid.getText().toString();
		String pwd_str = pwd.getText().toString();
		if (name_str == null || name_str.trim().equals("")) {
			uid.startAnimation(shakeAnim);
			progressDialog.cancel();
			Toast.makeText(LoginActivity.this, R.string.login_miss_username,
					Toast.LENGTH_SHORT).show();
			return;
		}
		// if (!(name_str.equals("朱荣飞") || name_str.equals("余东亮")
		// || name_str.equals("张婕") || name_str.equals("曹老师")
		// || name_str.equals("余老师") || name_str.equals("马老师")
		// || name_str.equals("胡老师") || name_str.equals("tester")
		// || name_str.equals("某同学"))) {
		// uid.startAnimation(shakeAnim);
		// progressDialog.cancel();
		// Toast.makeText(LoginActivity.this, "请输入正确的测试账号",
		// Toast.LENGTH_SHORT).show();
		// return;
		// }
		if (pwd_str == null || pwd_str.trim().equals("")) {
			pwd.startAnimation(shakeAnim);
			progressDialog.cancel();
			Toast.makeText(LoginActivity.this, R.string.login_miss_password,
					Toast.LENGTH_SHORT).show();
			return;
		}
		final String name1 = name_str.trim();
		final String pwd1 = pwd_str.trim();
		progressDialog.show();

		// Editor sharedata = getSharedPreferences("user",
		// Context.MODE_PRIVATE).edit().putString("uid",name1).commit();
		// sharedata.putString("uid",name1);
		// sharedata.commit();
		loginVarify(name1, pwd1);
	}

	/**
	 * 验证登录
	 * 
	 * @param uid
	 *            userid
	 * @param pwd
	 *            user_pwd
	 * */
	private void loginVarify(final String uid, final String pwd1) {

		handler.obtainMessage(MyConstants.MSG_SUCCESS1).sendToTarget();
		getSharedPreferences("user", Context.MODE_PRIVATE).edit()
				.putString("uid", uid).commit();

	}

	/**
	 * 显示登录信息
	 * */
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// menu.triggerItem(MainAdapter.GROUP_DEMO, MainAdapter.ITEM_DEMO);
			Intent intent = new Intent(LoginActivity.this,
					FragmentChangeActivity.class);

			switch (msg.what) {
			case MyConstants.MSG_SUCCESS1:
				progressDialog.cancel();

				startActivity(intent);
				finish();
				Toast.makeText(LoginActivity.this, "login sucess",
						Toast.LENGTH_SHORT).show();
				AbstractWeibo weibo = (AbstractWeibo) msg.obj;

				final String openId = weibo.getDb().getWeiboId(); // 获取用户在此平台的
																	// ID
				final String nickname = weibo.getDb().get("nickname");
				Toast.makeText(context, openId + "/" + nickname,
						Toast.LENGTH_SHORT).show();
				//weibo.removeAccount();
				SharedPreferences user = context.getSharedPreferences("user",
						MODE_PRIVATE);
				// 存入数据
				Editor editor = user.edit();
				editor.putString("uid", openId);
				editor.commit();
//				try {
//					inertOrUpdateUser(openId, nickname);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				 new Thread() {
				 public void run() {
				 try {
				
				 inertOrUpdateUser(openId, nickname);
				 } catch (Exception e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
				 }
				
				 };
				 }.start();

				break;
			case MyConstants.MSG_FAILURE:
				progressDialog.cancel();
				String str = (String) msg.obj;
				Toast.makeText(LoginActivity.this,
						getString(R.string.login_fail) + " " + str,
						Toast.LENGTH_SHORT).show();
				break;

			case 1: {

				Map<String, String> mapData = (HashMap<String, String>) msg.obj;

				// startActivity(intent);
				// finish();
				Toast.makeText(LoginActivity.this, "autho login sucess",
						Toast.LENGTH_SHORT).show();

				SharedPreferences user2 = context.getSharedPreferences("user",
						MODE_PRIVATE);
				// 存入数据
				Editor editor2 = user2.edit();

				Iterator iter = mapData.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry<String, String> entry = (Map.Entry<String, String>) iter
							.next();
					Object key = entry.getKey();
					Object val = entry.getValue();
					if (key != null) {
						editor2.putString(key.toString(), val.toString());
					}
				}

				editor2.putBoolean("BOOLEAN_KEY", true);
				editor2.commit();

				Log.d("user",
						"saved the user info to user.xml sharedpreference file");

			}
			}
		}
	};

	protected void onDestroy() {
		AbstractWeibo.stopSDK(this);
		super.onDestroy();
	}

	public void onComplete(AbstractWeibo weibo, int action,
			HashMap<String, Object> res) {
		Message msg = new Message();
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = weibo;
		handler.sendMessage(msg);

		// Message msg2 = new Message();
		// msg2.what = 1;
		// // JsonUtils ju = new JsonUtils();
		// // /String json = ju.fromHashMap(res);
		// msg2.obj = res;// ju.format(json);
		// handler.sendMessage(msg2);

	}

	public void onError(AbstractWeibo weibo, int action, Throwable t) {
		t.printStackTrace();

		Message msg = new Message();
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = weibo;
		handler.sendMessage(msg);
	}

	public void onCancel(AbstractWeibo weibo, int action) {
		Message msg = new Message();
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = weibo;
		handler.sendMessage(msg);
	}

	/**
	 * 处理操作结果
	 * <p>
	 * 如果获取到用户的名称，则显示名称；否则如果已经授权，则显示 平台名称
	 */

	public static String actionToString(int action) {
		switch (action) {
		case AbstractWeibo.ACTION_AUTHORIZING:
			return "ACTION_AUTHORIZING";
		case AbstractWeibo.ACTION_GETTING_FRIEND_LIST:
			return "ACTION_GETTING_FRIEND_LIST";
		case AbstractWeibo.ACTION_FOLLOWING_USER:
			return "ACTION_FOLLOWING_USER";
		case AbstractWeibo.ACTION_SENDING_DIRECT_MESSAGE:
			return "ACTION_SENDING_DIRECT_MESSAGE";
		case AbstractWeibo.ACTION_TIMELINE:
			return "ACTION_TIMELINE";
		case AbstractWeibo.ACTION_USER_INFOR:
			return "ACTION_USER_INFOR";
		case AbstractWeibo.ACTION_SHARE:
			return "ACTION_SHARE";
		default: {
			return "UNKNOWN";
		}
		}
	}

	private AbstractWeibo getWeibo(int vid) {
		String name = null;
		switch (vid) {
		case R.id.sinaweibo:
			name = SinaWeibo.NAME;
			break;

		case R.id.btnQz:
			name = TencentWeibo.NAME;
			break;
		}

		if (name != null) {
			return AbstractWeibo.getWeibo(this.context, name);
		}
		return null;
	}

	void inertOrUpdateUser(String uid, String username)  throws IOException {
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

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//			if (response.getStatusLine().getStatusCode() == 200) {
//				InputStream in = response.getEntity().getContent();// 接收服务器的数据，并在Toast上显示
//				String str = readString(in);
//				Toast.makeText(LoginActivity.this, str, Toast.LENGTH_SHORT)
//						.show();
//
//			}
			// Log.d("dsfdsfsf", sResponse);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	}

	protected String readString(InputStream in) throws Exception {
		byte[] data = new byte[1024];
		int length = 0;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		while ((length = in.read(data)) != -1) {
			bout.write(data, 0, length);
		}
		return new String(bout.toByteArray(), "UTF-8");

	}
}
