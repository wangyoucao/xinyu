/**
 * count the cache time
 * @author zhangjie
 * @version 1.0
 * @since 2012-2-13
 * Created on 2012-2-13
 *
 * TODO To count the cache time
 * Window - Preferences - Java - Code Style - Code Templates
 */
package together.utils;

public class CacheTime {
	public static final int CACHE_DURATION_INFINITE = Integer.MAX_VALUE;
	public static final int CACHE_DURATION_ONE_Minute = 1000 * 60;
	public static final int CACHE_DURATION_ONE_DAY = 1000 * 60 * 60 * 24;
	public static final int CACHE_DURATION_TWO_DAYS = CACHE_DURATION_ONE_DAY * 2;
	public static final int CACHE_DURATION_THREE_DAYS = CACHE_DURATION_ONE_DAY * 3;
	public static final int CACHE_DURATION_FOUR_DAYS = CACHE_DURATION_ONE_DAY * 4;
	public static final int CACHE_DURATION_FIVE_DAYS = CACHE_DURATION_ONE_DAY * 5;
	public static final int CACHE_DURATION_SIX_DAYS = CACHE_DURATION_ONE_DAY * 6;
	public static final int CACHE_DURATION_ONE_WEEK = CACHE_DURATION_ONE_DAY * 7;
	public static final long expirationInMinutes = 60 * 24;
	public static final long imageExpirationInMinutes = 60 * 24 * 30;
}
