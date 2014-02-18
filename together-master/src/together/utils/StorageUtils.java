/**
 * storage utils
 * @author zhangjie
 * @version 1.0
 * @since 2012-2-13
 * 
 * */
package together.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class StorageUtils {

	private static final String SDCARD_ROOT = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/";
	public static final String FILE_ROOT = SDCARD_ROOT + "test/";

	private static final long LOW_STORAGE_THRESHOLD = 1024 * 1024 * 10;

	public static boolean isSdCardWrittenable() {

		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	/**
	 * return available storages
	 * */
	public static long getAvailableStorage() {

		String storageDirectory = null;
		storageDirectory = Environment.getExternalStorageDirectory().toString();

		try {
			StatFs stat = new StatFs(storageDirectory);
			long avaliableSize = ((long) stat.getAvailableBlocks() * (long) stat
					.getBlockSize());
			return avaliableSize;
		} catch (RuntimeException ex) {
			return 0;
		}
	}

	/**
	 * check if storage is enough
	 * 
	 * @return true if storage is enough otherwise return false
	 * */
	public static boolean checkAvailableStorage() {

		if (getAvailableStorage() < LOW_STORAGE_THRESHOLD) {
			return false;
		}

		return true;
	}

	/**
	 * check if sdcard exists
	 * 
	 * @return true if sdcard exists otherwise return false
	 * */
	public static boolean isSDCardPresent() {

		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	/**
	 * make root dir
	 * */
	public static void mkdir() throws IOException {

		File file = new File(FILE_ROOT);
		if (!file.exists() || !file.isDirectory())
			file.mkdir();
	}

	/**
	 * get local bitmap
	 * 
	 * @param url
	 *            bitmap url return bitmap
	 * */
	public static Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String size(long size) {
		if (size / (1024 * 1024) > 0) {
			float tmpSize = (float) (size) / (float) (1024 * 1024);
			DecimalFormat df = new DecimalFormat("#.##");
			return "" + df.format(tmpSize) + "MB";
		} else if (size / 1024 > 0) {
			return "" + (size / (1024)) + "KB";
		} else
			return "" + size + "B";
	}

	/**
	 * delete file
	 * 
	 * @param path
	 *            file
	 * @return true if deleted otherwise return false
	 * */
	public static boolean delete(File path) {
		boolean result = true;
		if (path.exists()) {
			if (path.isDirectory()) {
				for (File child : path.listFiles()) {
					result &= delete(child);
				}
				result &= path.delete(); // Delete empty directory.
			}
			if (path.isFile()) {
				result &= path.delete();
			}
			if (!result) {
				Log.e(null, "Delete failed;");
			}
			return result;
		} else {
			Log.e(null, "File does not exist.");
			return false;
		}
	}
}
