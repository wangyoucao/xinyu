/**
 * @author zhangjie
 * @version 1.0
 * @since 2012-2-13
 * Created on 2012-2-13
 *
 * TODO To create cache for files
 * Window - Preferences - Java - Code Style - Code Templates
 */
package together.utils;

import java.io.File;

import android.content.Context;

public class FileCache {

	private File cacheDir;

	/**
	 * constructor
	 * 
	 * @param context
	 *            context of file cache
	 * 
	 * */
	public FileCache(Context context) {
		// Find the dir to save cached images
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					"LazyList");
		else
			cacheDir = context.getCacheDir();
		if (!cacheDir.exists())
			cacheDir.mkdirs();
	}

	/**
	 * create a file
	 * 
	 * @param url
	 *            get file from the url
	 * @return file got
	 * */
	public File getFile(String url) {
		// I identify images by hashcode. Not a perfect solution, good for the
		// demo.
		// Log.e("","call getFile");
		String filename = String.valueOf(url.hashCode());
		File f = new File(cacheDir, filename);
		return f;
	}

	/**
	 * clear caches
	 * */
	public void clear() {
		File[] files = cacheDir.listFiles();
		if (files == null)
			return;
		for (File f : files)
			f.delete();
	}

}