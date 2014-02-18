/**
 * manage cache file
 * @author zhangjie
 * @version 1.0
 * @since 2012-2-13
 * Created on 2012-2-13
 *
 * TODO To manage cache
 * Window - Preferences - Java - Code Style - Code Templates
 */
package together.utils;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;

public class MemoryCache {
	private Map<String, SoftReference<Bitmap>> cache;// =
														// Collections.synchronizedMap(new
														// HashMap<String,
														// SoftReference<Bitmap>>());

	public MemoryCache() {
		// Log.e("" , "init memory cache");
		cache = Collections
				.synchronizedMap(new HashMap<String, SoftReference<Bitmap>>());
	}

	// public void print(){
	// // for(int i = 0; i < cache.size();i++)
	// //
	// //Log.e("size = " , cache.size() +" ");
	// //Log.e("call put" , "call put");
	// }
	/**
	 * get bitmap from id
	 * 
	 * @param id
	 *            bitmap id
	 * @return bitmap
	 * */
	public Bitmap get(String id) {

		// Log.e("" , "call get");
		// print();
		if (!cache.containsKey(id)) {

			return null;
		}
		SoftReference<Bitmap> ref = cache.get(id);
		return ref.get();
	}

	/**
	 * put an id and a bitmap together
	 * 
	 * @param id
	 *            bitmap id
	 * @param b
	 *            bitmap
	 * 
	 * */
	public void put(String id, Bitmap b) {
		// System.out.println("call put");
		// Log.e("call put" , "call put");
		// print();
		cache.put(id, new SoftReference<Bitmap>(b));
		// print();
	}

	// public void clear() {
	// cache.clear();
	// }
}