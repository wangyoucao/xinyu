/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package together.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import together.models.Picture;
import together.utils.GridViewAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.krislq.sliding.fragment.WhisperMainFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class ImagePagerActivity extends AbstractAsyncActivity {
	GridView gridview;
	boolean shouldPage;
	GridViewAdapter aa;
	AQuery listAq = null;
	private static final String STATE_POSITION = "STATE_POSITION";

	DisplayImageOptions options;

	ViewPager pager,pager2;
	RelativeLayout llTop, llBotMini;
	LinearLayout llBot, noReplies;
	FrameLayout llActions;
	GestureDetector gestureDetector;
	OnTouchListener touchListener;
	boolean animating = true, issearching = true;
	boolean shoutVisible = true;
	ArrayList<Picture> list = new ArrayList<Picture>();
	ImagePagerAdapter imagepageadapter;
	String picId = null;
	protected ImageLoader imageLoader = ImageLoader.getInstance();

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_clear_memory_cache:
			imageLoader.clearMemoryCache();
			return true;
		case R.id.item_clear_disc_cache:
			imageLoader.clearDiscCache();
			return true;
		default:
			return false;
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Runtime.getRuntime().gc();
		setContentView(R.layout.activity_wwhisper);
		setBehindContentView(R.layout.menu_frame);
		imageLoader.clearMemoryCache();

		Bundle bundle = getIntent().getExtras();

		int pagerPosition = bundle.getInt("position", 0);

		final String type = bundle.getString("type");
		list = (ArrayList<Picture>) bundle.getSerializable("list");
		picId = list.get(pagerPosition).getIdpic();
		if (savedInstanceState != null) {
			pagerPosition = savedInstanceState.getInt(STATE_POSITION);
		}

		this.gestureDetector = new GestureDetector(this,
				new GestureDetector.SimpleOnGestureListener() {
					public boolean onDoubleTap(
							MotionEvent paramAnonymousMotionEvent) {
						// ImagePagerActivity.this.heart(null);
						return true;
					}

					public boolean onDown(MotionEvent paramAnonymousMotionEvent) {
						return true;
					}

					public boolean onSingleTapConfirmed(
							MotionEvent paramAnonymousMotionEvent) {
						if (ImagePagerActivity.this.llTop.isShown()) {

							ImagePagerActivity.this.hideChrome();
						}else{
							ImagePagerActivity.this.toggleChrome();	
						}
						return true;
					}
				});
		this.touchListener = new View.OnTouchListener() {
			public boolean onTouch(View paramAnonymousView,
					MotionEvent paramAnonymousMotionEvent) {
				try {
					boolean bool = ImagePagerActivity.this.gestureDetector
							.onTouchEvent(paramAnonymousMotionEvent);

					return bool;
				} catch (Exception localException) {
					localException.printStackTrace();
				}
				return false;
			}
		};

		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error)
				.resetViewBeforeLoading(true).cacheOnDisc(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(300)).build();

		this.llTop = ((RelativeLayout) findViewById(R.id.w_top));
		this.llBot = ((LinearLayout) findViewById(R.id.w_bot));
		this.llBotMini = ((RelativeLayout) findViewById(R.id.w_bot_mini));
		// is.svReplies = ((HorizontalListView)findViewById(R.id.sv_replies));
		this.llActions = ((FrameLayout) findViewById(R.id.w_actions));
		this.noReplies = ((LinearLayout) findViewById(R.id.ww_empty_view));
		pager = (ViewPager) findViewById(R.id.w_pager);
		imagepageadapter = new ImagePagerAdapter(list, this.touchListener);
		pager.setAdapter(imagepageadapter);
		pager.setCurrentItem(pagerPosition);
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int scrollState) {
				

				if (pager.getCurrentItem() + 2 > list.size() && !issearching) {
					new GzipRequestTask().execute(list.size() + "", type);
				}

				// mStaticScreemAdapter.notifyDataSetChanged();

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageSelected(int arg0) {

				Picture picture = (Picture) list.get(pager.getCurrentItem());
				picId = picture.getIdpic();
				new GzipRequestTask().execute(list.size() + "", type);
				}

		});
		
		
		gridview = (GridView) findViewById(R.id.tablegrid);
		//final String type = "popular";
		
		gridview.setSelection(0);
		this.gridview
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long id) {
						Intent localIntent = new Intent(
								ImagePagerActivity.this,
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

					ImagePagerActivity.this.shouldPage = true;

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

					if (ImagePagerActivity.this.shouldPage && !issearching) {
						new GzipRequestTask().execute(list.size() + "", type);
					}

					ImagePagerActivity.this.shouldPage = false; // mStaticScreemAdapter.notifyDataSetChanged();

				}
			}
		});
		listAq = new AQuery(ImagePagerActivity.this);
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error)
				.resetViewBeforeLoading(true).cacheOnDisc(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(300)).build();
		aa = new GridViewAdapter(ImagePagerActivity.this, list,
				listAq, options);

		gridview.setAdapter(aa);
		// pager.setOffscreenPageLimit(2);
		// pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
		//
		// @Override
		// public void onPageScrollStateChanged(int arg0) {
		// while (!ImagePagerActivity.this.llTop.isShown());
		// ImagePagerActivity.this.hideChrome();
		//
		// }
		//
		// @Override
		// public void onPageScrolled(int arg0, float arg1, int arg2) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void onPageSelected(int arg0) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// });
		//
		// Gallery gallery = (Gallery) findViewById(R.id.gallery);
		// gallery.setAdapter(new ImageGalleryAdapter(imageUrls));
		// gallery.setOnItemClickListener(new OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view, int
		// position, long id) {
		// Intent localIntent = new Intent(ImagePagerActivity.this,
		// WhisperMainActivity.class);
		// //localIntent.put("lstImages", list);
		// //localIntent.putExtra("wt", this.wt.ordinal());
		// localIntent.putExtra("position", position);
		// startActivity(localIntent);
		// }
		// });

	}

	public void toggleChrome() {

		System.out.println("animating=" + animating);
		// System.out.println("shoutVisible="+shoutVisible);
		// if (this.animating);

		// this.animating = true;
		// if (this.llTop.isShown())
		// hideChrome();
		// while (this.shoutVisible)
		// {
		// findViewById(2131165250).setVisibility(8);
		// this.shoutVisible = false;

		showChrome();
		// }

	}

	public void showChrome() {
		this.llTop.setVisibility(View.VISIBLE);
		this.llBot.setVisibility(View.VISIBLE);
		this.llBotMini.setVisibility(View.INVISIBLE);
		this.llActions.setVisibility(View.VISIBLE);
		TranslateAnimation localTranslateAnimation1 = new TranslateAnimation(0,
				0.0F, 0, 0.0F, 1, -1.0F, 1, 0.0F);
		localTranslateAnimation1.setDuration(300L);

		this.llBot.postDelayed(new Runnable() {
			public void run() {
				ImagePagerActivity.this.animating = false;
			}
		}, 300L);
		this.llTop.startAnimation(localTranslateAnimation1);

		// this.svReplies.setVisibility(0);
		this.noReplies.setVisibility(View.VISIBLE);

	}

	public void hideChrome() {
		TranslateAnimation localTranslateAnimation1 = new TranslateAnimation(0,
				0.0F, 0, 0.0F, 1, 0.0F, 1, -1.0F);
		localTranslateAnimation1.setDuration(300L);

		this.llBot.postDelayed(new Runnable() {
			public void run() {
				ImagePagerActivity.this.llTop.setVisibility(8);
				ImagePagerActivity.this.noReplies.setVisibility(8);
				ImagePagerActivity.this.llBot.setVisibility(8);
				ImagePagerActivity.this.llBotMini.setVisibility(0);
				ImagePagerActivity.this.llActions.setVisibility(View.INVISIBLE);
				ImagePagerActivity.this.animating = false;
			}
		}, 290L);
		this.llTop.startAnimation(localTranslateAnimation1);

	}

	public void reply(View paramView) {
		CharSequence[] arrayOfCharSequence = { "拍照", "选择照片" };

		new AlertDialog.Builder(this)
				.setTitle("发布心语")
				.setItems(arrayOfCharSequence,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface paramAnonymousDialogInterface,
									int paramAnonymousInt) {
								try {
									paramAnonymousDialogInterface.dismiss();
									if (paramAnonymousInt == 0) {
										imageLoader.clearMemoryCache();
										Intent intent = new Intent(
												Intent.ACTION_PICK, null);

										/**
										 * 下面这句话，与其它方式写是一样的效果，如果：
										 * intent.setData(MediaStore.Images
										 * .Media.EXTERNAL_CONTENT_URI);
										 * intent.setType(""image/*");设置数据类型
										 * 如果朋友们要限制上传到服务器的图片类型时可以直接写如
										 * ："image/jpeg 、 image/png等的类型"
										 */
										intent.setDataAndType(
												MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
												"image/*");
										startActivityForResult(intent, 1);
										return;
									} else {

										Intent intent = new Intent(
												MediaStore.ACTION_IMAGE_CAPTURE);
										// 下面这句指定调用相机拍照后的照片存储的路径
										intent.putExtra(
												MediaStore.EXTRA_OUTPUT,
												Uri.fromFile(new File(
														Environment
																.getExternalStorageDirectory(),
														"superspace.jpg")));
										startActivityForResult(intent, 2);

										return;
									}
								} catch (Exception localException) {
									localException.printStackTrace();
								}
							}
						}).create().show();
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
		// 下面这句指定调用相机拍照后的照片存储的路径
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
				Environment.getExternalStorageDirectory(), "superspace.jpg")));
		startActivityForResult(intent, 2);
	}

	/**
	 * call back method
	 */

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		Intent intent = new Intent(ImagePagerActivity.this,
				WcreatewhActivity.class);
		intent.putExtra("picId", picId);
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

			intent.putExtra("bitmap", Uri.fromFile(temp)); // 这里可以放一个bitmap
			this.startActivity(intent);
			// startPhotoZoom(Uri.fromFile(temp));
			break;
		// 取得裁剪后的图片

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, pager.getCurrentItem());
	}

	private class ImagePagerAdapter extends PagerAdapter {

		private ArrayList<Picture> list;
		private LayoutInflater inflater;
		View.OnTouchListener touchlistener;

		ImagePagerAdapter(ArrayList<Picture> list,
				View.OnTouchListener ontouchListener) {
			this.list = list;
			this.touchlistener = ontouchListener;
			inflater = getLayoutInflater();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public void finishUpdate(View container) {
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			View imageLayout = inflater.inflate(R.layout.item_pager_image,
					view, false);
			ImageView imageView = (ImageView) imageLayout
					.findViewById(R.id.image);
			imageView.setOnTouchListener(touchlistener);
			final ProgressBar spinner = (ProgressBar) imageLayout
					.findViewById(R.id.loading);

			Picture pic = list.get(position);
			String image_path = pic.getPicPath();

			imageLoader.displayImage(image_path, imageView, options,
					new SimpleImageLoadingListener() {
						@Override
						public void onLoadingStarted(String imageUri, View view) {
							spinner.setVisibility(View.VISIBLE);
						}

						@Override
						public void onLoadingFailed(String imageUri, View view,
								FailReason failReason) {
							String message = null;
							switch (failReason.getType()) {
							case IO_ERROR:
								message = "Input/Output error";
								break;
							case DECODING_ERROR:
								message = "Image can't be decoded";
								break;
							case NETWORK_DENIED:
								message = "Downloads are denied";
								break;
							case OUT_OF_MEMORY:
								message = "Out Of Memory error";
								break;
							case UNKNOWN:
								message = "Unknown error";
								break;
							}
							Toast.makeText(ImagePagerActivity.this, message,
									Toast.LENGTH_SHORT).show();

							spinner.setVisibility(View.GONE);
						}

						@Override
						public void onLoadingComplete(String imageUri,
								View view, Bitmap loadedImage) {
							spinner.setVisibility(View.GONE);
						}
					});

			((ViewPager) view).addView(imageLayout, 0);
			return imageLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View container) {
		}
	}

	private class GzipRequestTask extends
			AsyncTask<String, Void, List<Picture>> {

		@Override
		protected void onPreExecute() {
			issearching = true;
			showLoadingProgressDialog();
		}

		@Override
		protected List<Picture> doInBackground(String... params) {
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

				// Add the gzip Accept-Encoding header to the request
				System.out.println("url =" + url);
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);

				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(
						new MappingJackson2HttpMessageConverter());

				// Perform the HTTP GET request
				ResponseEntity<Picture[]> response = restTemplate.exchange(url,
						HttpMethod.GET, new HttpEntity<Object>(requestHeaders),
						Picture[].class, "SpringSource");

				return Arrays.asList(response.getBody());
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(TAG, e.getMessage(), e);
			}

			return null;
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
				imagepageadapter.notifyDataSetChanged();
				// gridview.invalidate();

			} else {
				// searchrestult = false;
				Toast.makeText(ImagePagerActivity.this, "没有找到新数据",
						Toast.LENGTH_SHORT).show();
			}

		}

	}
}