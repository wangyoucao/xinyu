/**
 * handle gif image
 * @author zhangjie
 * @version 1.0
 * @since 2012-2-13
 * */
package together.utils;


import java.io.InputStream;

import together.utils.GifHelper.GifFrame;

public class GifUtil {
	/**
	 * 
	 * get gif from input stream
	 * 
	 * @param is
	 *            input stream
	 * @return gif frame array
	 */
	public static GifFrame[] getGif(InputStream is) {
		GifHelper gifHelper = new GifHelper();
		if (GifHelper.STATUS_OK == gifHelper.read(is)) {
			return gifHelper.getFrames();
		}
		return null;
	}

	/**
	 * tell whether the input stream is a gif image
	 * 
	 * @param is
	 *            input stream
	 * @return whether it is a gif image
	 */
	public static boolean isGif(InputStream is) {
		GifHelper gifHelper = new GifHelper();
		return gifHelper.isGif(is);
	}
}
