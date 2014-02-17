/**
 * load image
 * @author zhangjie
 * @version 1.0
 * @since 2012-2-13
 * Created on 2012-2-13
 *
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
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.widget.ImageView;

public class ImageLoader {

	MemoryCache memoryCache;
	FileCache fileCache;
	private Map<ImageView, String> imageViews = Collections
			.synchronizedMap(new WeakHashMap<ImageView, String>());
	ExecutorService executorService;

	/**
	 * @param context
	 *            constructor
	 * */
	public ImageLoader(Context context) {
		memoryCache = new MemoryCache();
		fileCache = new FileCache(context);
		executorService = Executors.newFixedThreadPool(5);
	}

	final int stub_id = R.drawable.placeholder;

	/**
	 * get a reflected image
	 * 
	 * @param image
	 *            origin image
	 * @return new_image
	 * */
	public Bitmap handleBitmap(Bitmap originalImage) {
		final int reflectionGap = 4;
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
				height / 2, width, height / 2, matrix, false);
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(originalImage, 0, 0, null);
		Paint deafaultPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);

		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0,
				originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
						+ reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);

		paint.setShader(shader);

		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);
		return bitmapWithReflection;
	}

	/**
	 * display a image from url
	 * 
	 * @param url
	 *            image url
	 * @param imageview
	 *            imageview to show the image
	 * */
	public void DisplayImage(String url, ImageView imageView, int maxSize) {
		imageViews.put(imageView, url);
		Bitmap bitmap = null;
		File f = fileCache.getFile(url);
		if (f != null)
			bitmap = BitmapUtil.decodeFile(f, maxSize);
		if (bitmap != null) {
			imageView.setImageBitmap((bitmap));
		} else {
			// Log.e("no cache in memory" , "downloading!");
			queuePhoto(url, imageView, false, maxSize);
			imageView.setImageResource(stub_id);
		}
	}

	/**
	 * get a reflected image
	 * 
	 * @param url
	 *            image url
	 * @param imageview
	 *            imageview to show the image
	 * @param flag
	 *            image flag
	 * @return new_image
	 * 
	 * */
	public void DisplayImage(String url, ImageView imageView, boolean flag,
			int maxSize) {
		imageViews.put(imageView, url);
		Bitmap bitmap = memoryCache.get(url);
		Bitmap shadow;
		if (bitmap != null) {
			bitmap.getHeight();
			bitmap.getWidth();
			shadow = BitmapUtil.drawShadow(bitmap, 5);
			imageView.setImageBitmap(shadow);
		} else {
			queuePhoto(url, imageView, false, maxSize);
			imageView.setImageResource(stub_id);
		}
	}

	private void queuePhoto(String url, ImageView imageView, boolean f,
			int maxSize) {
		PhotoToLoad p = new PhotoToLoad(url, imageView, f, maxSize);
		executorService.submit(new PhotosLoader(p));
	}

	/**
	 * get bitmap from url
	 * 
	 * @param url
	 *            image url
	 * @return bitmap
	 * */
	public Bitmap getBitmap(String url, int maxSize) {
		File f = fileCache.getFile(url);
		// from SD cache
		Bitmap b = BitmapUtil.decodeFile(f, maxSize);
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
			bitmap = BitmapUtil.decodeFile(f, maxSize);
			return bitmap;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	// Task for the queue
	private class PhotoToLoad {
		public String url;
		public ImageView imageView;
		public boolean flag = false;

		// public int maxSize = 480;
		public PhotoToLoad(String u, ImageView i, boolean f, int m) {
			url = u;
			imageView = i;
			flag = f;
			// maxSize = m;
		}
	}

	/**
	 * class for loading photos
	 * */
	class PhotosLoader implements Runnable {
		PhotoToLoad photoToLoad;
		boolean flag = false;
		private int maxSize = 480;

		PhotosLoader(PhotoToLoad photoToLoad) {
			this.photoToLoad = photoToLoad;
			flag = photoToLoad.flag;
		}

		@Override
		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			Bitmap bmp = getBitmap(photoToLoad.url, maxSize);
			memoryCache.put(photoToLoad.url, bmp);
			if (imageViewReused(photoToLoad))
				return;
			BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
			Activity a = (Activity) photoToLoad.imageView.getContext();
			a.runOnUiThread(bd);
		}
	}

	/**
	 * @param photoToLoad
	 * @return flag return whether the imageview is reused
	 * */
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
			if (bitmap != null)
				photoToLoad.imageView.setImageBitmap(bitmap);
			else
				photoToLoad.imageView.setImageResource(stub_id);
		}
	}

}
