/*
 * Created on 2012-2-13
 *
 * TODO To show the constants  
 * Window - Preferences - Java - Code Style - Code Templates
 */
package together.utils;

import android.os.Environment;

public class MyConstants {
	public static final int TEST = 1000 * 60;// one minute
	public static final int TWO = TEST * 10;
	public static final String ROOT = Environment.getExternalStorageDirectory()
			+ "/";
	public static final String ROOTDIR = Environment
			.getExternalStorageDirectory() + "/cache/";
	public static final String M3U8DIR = ROOTDIR + "m3u8/";
	public static final String SITE = "http://itogether.sinaapp.com";
	public static final String SITE_PORT = "http://music.client.cnlive.com:8888";
	public static final int MSG_SUCCESS1 = 0;
	public static final int MSG_SUCCESS2 = 1;
	public static final int MSG_FAILURE = 2;
	public static final int MSG_FAILURE2 = 3;
	
	public static final int MainFocusImageCount = 6;
	public static final int MainImageCount = 8;
	public static final int MainVideoCount = 8;
	public static final int MainVideoCommentCount = 8;
	public static final int DJ = 8;
	public static final int STAR = 8;
	public static final int SearchImageCount = 10;
	public static final int ClassifyImageCount = 14;

	public static final int IO_BUFFER_SIZE = 4096;

	public static final int CHANGED = 0x0010;

	public static final String DB_NAME = "download.db";
	public static final String DOWN_TB_NAME = "downs";
	public static final String FAV_TB_NAME = "favs";

	public static final String ID = "_id";
	public static final String TITLE = "title";
	public static final String PATH = "path";
	public static final String MUSIC_ID = "music_id";
	public static final String URL = "url";
	public static final String CURRENT = "current";
	public static final String TOTAL = "total";
	public static final String STATE = "state";
	public static final String M3U8 = "m3u8";

	// public static final String createTB = "CREATE TABLE IF NOT EXISTS "
	// + MyConstants.DOWN_TB_NAME + " ("
	// + MyConstants.ID + " INTEGER PRIMARY KEY,"
	// + MyConstants.TITLE + " VARCHAR,"
	// + MyConstants.URL + " VARCHAR,"
	// + MyConstants.PATH + " VARCHAR,"
	// + MyConstants.MUSIC_ID +" VARCHAR,"
	// + MyConstants.M3U8 +" VARCHAR,"
	// + MyConstants.STATE+ " INTEGER,"
	// + MyConstants.TOTAL+" INTEGER,"
	// + MyConstants.CURRENT+ " INTEGER)";

	public static final String FAV_ID = "_id";
	public static final String FAV_TITLE = "title";
	public static final String FAV_M3U8 = "m3u8";
	public static final String FAV_MUSIC_ID = "music_id";
	public static final String FAV_IMAGE_URL = "image_url";

	public final static String SER_KEY = "info.nemoworks.inmusic.ser";

}