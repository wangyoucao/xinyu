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
import android.content.ContentValues;
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

public class WhisperMainFragment extends Fragment {
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
	String TAG = WhisperMainFragment.this.toString();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_whome, null);
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
								WhisperMainFragment.this.getActivity(),
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

					WhisperMainFragment.this.shouldPage = true;

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

					if (WhisperMainFragment.this.shouldPage && !issearching) {
						new GzipRequestTask().execute(list.size() + "", type);
					}

					WhisperMainFragment.this.shouldPage = false; // mStaticScreemAdapter.notifyDataSetChanged();

				}
			}
		});

		final Button btnLatest = (Button) view.findViewById(R.id.latest);
		final Button btnNearby = (Button) view.findViewById(R.id.nearby);
		final Button btnPopular = (Button) view.findViewById(R.id.popular);
		final ImageButton btnCreate = (ImageButton) view
				.findViewById(R.id.w_create_button);
		final Button btnSearch = (Button) view
				.findViewById(R.id.w_search_button);
		final Button btnCamera = (Button) view
				.findViewById(R.id.w_camera_button);

		listAq = new AQuery(WhisperMainFragment.this.getActivity());
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error)
				.resetViewBeforeLoading(true).cacheOnDisc(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(300)).build();
		aa = new GridViewAdapter(WhisperMainFragment.this.getActivity(), list,
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
				case R.id.w_create_button:
					create(v);
					return;
				case R.id.w_search_button:
					search(v);
					return;
				case R.id.w_camera_button:
					camera(v);
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

		btnCreate.setOnClickListener(buttonlistener);
		btnSearch.setOnClickListener(buttonlistener);
		btnCamera.setOnClickListener(buttonlistener);

	}

	/**
	 * 
	 * onClik method for selecting photo
	 * 
	 */
	public void search(View paramView) {
		Intent intent = new Intent(Intent.ACTION_PICK, null);

		/**
		 * 下面这句话，与其它方式写是一样的效果，如果： intent.setData(MediaStore.Images
		 * .Media.EXTERNAL_CONTENT_URI); intent.setType(""image/*");设置数据类型
		 * 如果朋友们要限制上传到服务器的图片类型时可以直接写如 ："image/jpeg 、 image/png等的类型"
		 */
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, 1);

	}

	/**
	 * on Click method for camera
	 * 
	 * @param paramView
	 */
	public void camera(View paramView) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		ContentValues values = new ContentValues();
		// 下面这句指定调用相机拍照后的照片存储的路径
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
				Environment.getExternalStorageDirectory(), "superspace.jpg")));
		
		startActivityForResult(intent, 2);
	}

	/**
	 * call back method
	 */

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		Intent intent = new Intent(WhisperMainFragment.this.getActivity(),
				WcreatewhActivity.class);
		switch (requestCode) {
		// 如果是直接从相册获取

		case 1:
			// startPhotoZoom(data.getData());
			if (data != null) {
				intent.putExtra("bitmap", data.getData()); // 这里可以放一个bitmap
				this.startActivity(intent);
			}

			break;
		// 如果是调用相机拍照时
		case 2:
			 File temp = new File(Environment.getExternalStorageDirectory()
			 + "/superspace.jpg");
			//Bitmap bitmap = getSmallBitmap(Environment
			//		.getExternalStorageDirectory() + "/superspace.jpg");
			 
			intent.putExtra("bitmap", Uri.fromFile(temp)); // 这里可以放一个bitmap
			this.startActivity(intent);
			// startPhotoZoom(Uri.fromFile(temp));
			break;
		// 取得裁剪后的图片

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * onClick method to control bottom button
	 * 
	 * @param paramView
	 */
	public void create(View paramView) {
		if (this.showingCreate) {
			((ImageButton) paramView)
					.setImageResource(R.drawable.whisper_bottombar_close);
			View localView3 = view.findViewById(R.id.w_create_bar);
			View localView4 = ((LinearLayout) localView3).getChildAt(1);
			localView3.setVisibility(0);
			paramView.setVisibility(0);
			localView4.setVisibility(0);
			this.createHeight = localView4.getHeight();
			TranslateAnimation localTranslateAnimation2 = new TranslateAnimation(
					0, 0.0F, 0, 0.0F, 0, this.createHeight, 0, 0.0F);
			localTranslateAnimation2.setDuration(300L);
			localView3.startAnimation(localTranslateAnimation2);
			this.showingCreate = false;
		} else {
			this.showingCreate = true;

			((ImageButton) paramView).setImageResource(R.drawable.w_bottom_add);
			View localView1 = view.findViewById(R.id.w_create_bar);
			final View localView2 = ((LinearLayout) localView1).getChildAt(1);

			this.createHeight = localView2.getHeight();
			TranslateAnimation localTranslateAnimation1 = new TranslateAnimation(
					0, 0.0F, 0, 0.0F, 0, 0.0F, 0, this.createHeight);
			localTranslateAnimation1.setDuration(300L);
			localView1.startAnimation(localTranslateAnimation1);
			localView1.postDelayed(new Runnable() {
				public void run() {
					localView2.setVisibility(8);
				}
			}, 300L);

		}
	}

	
	// ***************************************
	private class GzipRequestTask extends
			AsyncTask<String, Void, List<Picture>> {

		@Override
		protected void onPreExecute() {
			issearching = true;
			//showLoadingProgressDialog();
		}

		@Override
		protected List<Picture> doInBackground(String... params) {
			List<Picture> lst = null;
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
				// System.out.println("url =" + url);
				// HttpHeaders requestHeaders = new HttpHeaders();
				// requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);
				// requestHeaders.setContentType(MediaType.APPLICATION_JSON);
				// // Create a new RestTemplate instance
				// RestTemplate restTemplate = new RestTemplate();
				// restTemplate.getMessageConverters().add(
				// new MappingJackson2HttpMessageConverter());
				//
				// // Perform the HTTP GET request
				// ResponseEntity<Picture[]> response =
				// restTemplate.exchange(url,
				// HttpMethod.GET, new HttpEntity<Object>(requestHeaders),
				// Picture[].class, "SpringSource");

				System.out.println("url =" + url);
				HttpGet request = new HttpGet(url);

				// 先封装一个 JSON 对象

				// 发送请求
				HttpResponse httpResponse = new DefaultHttpClient()
						.execute(request);
				// 得到应答的字符串，这也是一个 JSON 格式保存的数据
				String retSrc = EntityUtils.toString(httpResponse.getEntity());
				// 生成 JSON 对象
				lst = JSON.parseArray(retSrc, Picture.class);

			} catch (Exception e) {
				e.printStackTrace();
				Log.e(TAG, e.getMessage(), e);
			} finally {

			}

			return lst;
		}

		@Override
		protected void onPostExecute(List<Picture> result) {
			//dismissProgressDialog();
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
				Toast.makeText(WhisperMainFragment.this.getActivity(),
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
