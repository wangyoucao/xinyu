package com.krislq.sliding.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import together.activity.ImagePagerActivity;
import together.activity.R;
import together.activity.TogetherApp;
import together.activity.WcreatewhActivity;
import together.models.Picture;
import together.utils.GridViewAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.androidquery.AQuery;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class WhisperMyActivityFragment extends Fragment {
	GridView gridview;
	ArrayList<Picture> list = new ArrayList<Picture>();
	AQuery listAq = null;
	boolean shouldPage = true, isLoadThumbNail, issearching = false,
			searchrestult = true;
	private Button btnPopular, btnLatest, btnNearby;
	GridViewAdapter aa;
	DisplayImageOptions options;
	boolean showingCreate = true;
	private LocationClient locationClient;
	int createHeight;
	int ddd = 0;
	String type = null;
	View view;
	private ProgressDialog progressDialog;
	private boolean destroyed = false;
	String TAG = WhisperMyActivityFragment.this.toString();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_my_activity, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// set the Behind View
		Runtime.getRuntime().gc();
		gridview = (GridView) view.findViewById(R.id.grid);
		type = "popular";
		new GzipRequestTask().execute(list.size() + "", type);
		gridview.setSelection(0);
		this.gridview
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long id) {
						Intent localIntent = new Intent(
								WhisperMyActivityFragment.this.getActivity(),
								ImagePagerActivity.class);
						// localIntent.put("lstImages", list);
						// localIntent.putExtra("wt", this.wt.ordinal());

						localIntent.putExtra("type", type);
						localIntent.putExtra("list", list);
						localIntent.putExtra("position", position);
						startActivity(localIntent);
					}
				});

		this.gridview.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScroll(AbsListView paramAnonymousAbsListView,
					int paramAnonymousInt1, int paramAnonymousInt2,
					int paramAnonymousInt3) {

				if (paramAnonymousInt3 == 0)
					return;

				if ((paramAnonymousInt3 - paramAnonymousInt2
						- paramAnonymousInt1 <= 4)) {

					WhisperMyActivityFragment.this.shouldPage = true;

					// if(( isLoadThumbNail==true) &&searchrestult == true){
					// System.out.println("翻页"+(ddd++));
					// new GzipRequestTask().execute(list.size()+"","popular");

				} else {
					// WhisperMainActivity.this.shouldPage = false;
					// WhisperMainActivity.this.page();
				}

			}

			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_FLING) {
					// Log.d(TAG,"luzechun scroll fling");
					// isLoadThumbNail=false;

				} else if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {

					if (WhisperMyActivityFragment.this.shouldPage && !issearching) {
						new GzipRequestTask().execute(list.size() + "", type);
					}

					WhisperMyActivityFragment.this.shouldPage = false; // mStaticScreemAdapter.notifyDataSetChanged();

				}
			}
		});

		final Button btnLatest = (Button) view.findViewById(R.id.latest);
		final Button btnNearby = (Button) view.findViewById(R.id.nearby);
		final Button btnPopular = (Button) view.findViewById(R.id.popular);
		
		listAq = new AQuery(WhisperMyActivityFragment.this.getActivity());
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error)
				.resetViewBeforeLoading(true).cacheOnDisc(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(300)).build();
		aa = new GridViewAdapter(WhisperMyActivityFragment.this.getActivity(), list,
				listAq, options);

		gridview.setAdapter(aa);
		View.OnClickListener buttonlistener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				int i = v.getId();
				switch (i) {
				case R.id.popular:
					// Button btn = (Button)v;
					btnPopular.setTextColor(Color.WHITE);

					btnNearby.setTextColor(Color.BLACK);
					btnLatest.setTextColor(Color.BLACK);
					v.setSelected(true);
					btnNearby.setSelected(false);
					btnLatest.setSelected(false);
					// btn.setBackground(background);
					list.clear();
					type = "popular";
					new GzipRequestTask().execute(list.size() + "", type);
					// aa.notifyDataSetChanged();
					// list = getimageList();
					// GridViewAdapter aa = new
					// GridViewAdapter(WhisperMainActivity.this, list,listAq);
					gridview.setSelection(0);

					return;
				case R.id.latest:
					v.setSelected(true);
					btnPopular.setTextColor(Color.BLACK);
					btnNearby.setTextColor(Color.BLACK);
					btnLatest.setTextColor(Color.WHITE);
					btnNearby.setSelected(false);
					btnPopular.setSelected(false);
					list.clear();
					type = "latest";
					new GzipRequestTask().execute(list.size() + "", type);
					// textimageview.setFont(WFont.WUpright);
					return;
				case R.id.nearby:
					v.setSelected(true);
					btnPopular.setTextColor(Color.BLACK);
					btnNearby.setTextColor(Color.WHITE);
					btnLatest.setTextColor(Color.BLACK);
					btnPopular.setSelected(false);
					btnLatest.setSelected(false);
					list.clear();
					type = "nearby";
					new GzipRequestTask().execute(list.size() + "", type);

					return;
				
				}
			}
		};

		LinearLayout linlayoutImage = (LinearLayout) view
				.findViewById(R.id.whisper_feeds);

		int cnt = linlayoutImage.getChildCount();

		for (int i = 0; i < cnt; i++) {
			linlayoutImage.getChildAt(i).setOnClickListener(buttonlistener);
		}

		

	}

	
	

	

	// ***************************************
	private class GzipRequestTask extends
			AsyncTask<String, Void, List<Picture>> {

		@Override
		protected void onPreExecute() {
			issearching = true;
			showLoadingProgressDialog();
		}

		@Override
		protected List<Picture> doInBackground(String... params) {
			List<Picture> lst =null;
			try {
				String size = params[0];
				String type = params[1];
				String url = null;
				if (type.equals("nearby")) {
					GeoPoint ge = TogetherApp.mylocation;
					String latitude = ge.getLatitudeE6() + "";
					String longtitude = ge.getLongitudeE6() + "";

					// The URL for making the GET request
					// final String url =
					// "http://search.twitter.com/search.json?q={query}&rpp=100";
					url = getString(R.string.base_uri) + "getpiclist?size="
							+ size + "&type=" + type + "&latitude=" + latitude
							+ "&longtitude=" + longtitude;
				} else {
					url = getString(R.string.base_uri) + "getpiclist?size="
							+ size + "&type=" + type;

				}

				// The URL for making the GET request
				// final String url =
				// "http://search.twitter.com/search.json?q={query}&rpp=100";

				// Add the gzip Accept-Encoding header to the request
//				System.out.println("url =" + url);
//				HttpHeaders requestHeaders = new HttpHeaders();
//				requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);
//				requestHeaders.setContentType(MediaType.APPLICATION_JSON);
//				// Create a new RestTemplate instance
//				RestTemplate restTemplate = new RestTemplate();
//				restTemplate.getMessageConverters().add(
//						new MappingJackson2HttpMessageConverter());
//
//				// Perform the HTTP GET request
//				ResponseEntity<Picture[]> response = restTemplate.exchange(url,
//						HttpMethod.GET, new HttpEntity<Object>(requestHeaders),
//						Picture[].class, "SpringSource");

				System.out.println("url =" + url);	
				HttpGet request = new HttpGet(url);  
				// 先封装一个 JSON 对象  
				
				// 发送请求  
				HttpResponse httpResponse = new DefaultHttpClient().execute(request);  
				// 得到应答的字符串，这也是一个 JSON 格式保存的数据  
				String retSrc = EntityUtils.toString(httpResponse.getEntity());  
				// 生成 JSON 对象  
				lst = JSON.parseArray(retSrc, Picture.class);
			
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(TAG, e.getMessage(), e);
			}finally{
				
			}

			return lst;
		}

		@Override
		protected void onPostExecute(List<Picture> result) {
			dismissProgressDialog();
			issearching = false;
			if (result != null && result.size() > 0) {
				list.addAll(result);
				
				if (result.size() < 5) {
					HashSet h = new HashSet(list);
					list.clear();
					list.addAll(h);
					h = null;
				}
				aa.notifyDataSetChanged();
				// gridview.invalidate();

			} else {
				// searchrestult = false;
				Toast.makeText(WhisperMyActivityFragment.this.getActivity(),
						"没有找到新数据", Toast.LENGTH_SHORT).show();
			}

		}

	}

	public LocationClientOption getlocOption() {
		LocationClientOption option = new LocationClientOption();
		// option.setOpenGps(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		// option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);// 禁止启用缓存定位
		// option.setPoiNumber(5); //最多返回POI个数
		// option.setPoiDistance(1000); //poi查询距离
		// option.setPoiExtraInfo(true); //是否需要POI的电话和地址等详细信息
		return option;
	}

	public void onResume() {
		super.onResume();

	}

	/**
	 * 当activity被暂停时，关闭locationClient
	 * */
	public void onPause() {
		super.onPause();

	}

	public void showLoadingProgressDialog() {
		this.showProgressDialog("Loading. Please wait...");
	}

	public void showProgressDialog(CharSequence message) {
		if (this.progressDialog == null) {
			this.progressDialog = new ProgressDialog(this.getActivity());
			this.progressDialog.setIndeterminate(true);
		}

		this.progressDialog.setMessage(message);
		this.progressDialog.show();
	}

	public void dismissProgressDialog() {
		if (this.progressDialog != null && !this.destroyed) {
			this.progressDialog.dismiss();
		}
	}

}
