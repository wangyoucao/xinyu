/** load image
 * Created on 2012-2-13
 *
 * @author zhangjie
 * @version 1.0
 * @since 2012-2-13
 * TODO To load image
 * Window - Preferences - Java - Code Style - Code Templates
 */
package together.utils;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import together.activity.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class ReflectionImageLoader {

	MemoryCache memoryCache;
	FileCache fileCache;
	private Map<ImageView, String> imageViews;
	ExecutorService executorService;
	private static int imageWidth = 0;
	private static int imageHeight = 0;
	private Context context;

	public int getImageWidth() {
		return imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	/**
	 * constructor
	 * 
	 * @param context
	 *            image context
	 * */
	public ReflectionImageLoader(Context context) {
		this.context = context;
		fileCache = new FileCache(context);
		executorService = Executors.newFixedThreadPool(5);
		memoryCache = new MemoryCache();
		imageViews = Collections
				.synchronizedMap(new WeakHashMap<ImageView, String>());
	}

	final int stub_id = R.drawable.placeholder;

	/****
	 * display a image from the network
	 * 
	 * @param url
	 *            image url
	 * @param view
	 *            imageView
	 * @return null
	 */
	public void DisplayImage(String url, ImageView view) {
		imageViews.put(view, url);
		Bitmap bitmap = null;// memoryCache.get(url);
		File f = fileCache.getFile(url);
		if (f != null)
			bitmap = BitmapUtil.decodeFile(f, 480);
		if (bitmap != null) {
			view.setImageBitmap(BitmapUtil.handleReflection(bitmap));
		} else {
			// Log.e("no cache in memory" , "downloading!");
			queuePhoto(url, view);
			Bitmap bitmap1 = BitmapFactory.decodeResource(
					context.getResources(), R.drawable.placeholder);
			view.setImageBitmap(BitmapUtil.handleReflection(bitmap1));
		}
	}

	private void queuePhoto(String url, ImageView imageView) {
		PhotoToLoad p = new PhotoToLoad(url, imageView);
		executorService.submit(new PhotosLoader(p));
	}

	/**
	 * return a bitmap from a url
	 * 
	 * @param url
	 *            image url
	 * @return bitmap
	 * */
	public Bitmap getBitmap(String url) {
		File f = fileCache.getFile(url);
		// from SD cache
		Bitmap b = BitmapUtil.decodeFile(f, 480);
		if (b != null)
			return b;
		// from web
		try {
			Bitmap bitmap = null;
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(f);
			BitmapUtil.CopyStream(is, os);
			os.close();
			bitmap = BitmapUtil.decodeFile(f, 480);
			return bitmap;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	// /****
	// * decode stream from an input stream
	// * @param f input stream
	// * @return bitmap
	// */
	// public static Bitmap decodeStream(InputStream f){
	// //decode image size
	// BitmapFactory.Options o = new BitmapFactory.Options();
	// o.inJustDecodeBounds = true;
	// BitmapFactory.decodeStream(f,null,o);
	// //Find the correct scale value. It should be the power of 2.
	// final int REQUIRED_SIZE=480;
	// int width_tmp=o.outWidth, height_tmp=o.outHeight;
	// int scale=1;
	// while(true){
	// if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
	// break;
	// width_tmp/=2;
	// height_tmp/=2;
	// scale*=2;
	// }
	//
	// //decode with inSampleSize
	// BitmapFactory.Options o2 = new BitmapFactory.Options();
	// o2.inSampleSize=scale;
	// return BitmapFactory.decodeStream(f, null, o2);
	// }

	// Task for the queue
	private class PhotoToLoad {
		public String url;
		public ImageView imageView;

		public PhotoToLoad(String u, ImageView i) {
			url = u;
			imageView = i;
		}
	}

	/**
	 * class for loading photos
	 * */
	class PhotosLoader implements Runnable {
		PhotoToLoad photoToLoad;

		PhotosLoader(PhotoToLoad photoToLoad) {
			this.photoToLoad = photoToLoad;
		}

		@Override
		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			Bitmap bmp = getBitmap(photoToLoad.url);// download
			bmp = BitmapUtil.handleReflection(bmp);
			memoryCache.put(photoToLoad.url, bmp);
			if (imageViewReused(photoToLoad))
				return;
			BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
			Activity a = (Activity) photoToLoad.imageView.getContext();
			a.runOnUiThread(bd);
		}
	}

	boolean imageViewReused(PhotoToLoad photoToLoad) {
		String tag = imageViews.get(photoToLoad.imageView);
		if (tag == null || !tag.equals(photoToLoad.url))
			return true;
		return false;
	}

	// Used to display bitmap in the UI thread
	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		PhotoToLoad photoToLoad;

		public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
			bitmap = b;
			photoToLoad = p;
		}

		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			if (bitmap != null) {
				photoToLoad.imageView.setImageBitmap((bitmap));
				if (photoToLoad.imageView.getWidth() > 0)
					imageWidth = photoToLoad.imageView.getWidth();
				if (photoToLoad.imageView.getHeight() > 0)
					imageHeight = photoToLoad.imageView.getHeight();
			} else
				photoToLoad.imageView.setImageResource(stub_id);
		}
	}

}
