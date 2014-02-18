/**
 * hash a string
 * @author zhangjie
 * @version 1.0
 * @since 2012-2-13
 * Created on 2012-2-13
 *
 * TODO To hash a string
 * Window - Preferences - Java - Code Style - Code Templates
 */
package together.utils;

public class MD5 {

	/**
	 * get a hash string
	 * 
	 * @param str
	 * @return hash_str
	 * 
	 * */
	public static String getMD5(String instr) {
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			md.update(instr.getBytes());
			byte tmp[] = md.digest();

			char str[] = new char[16 * 2];

			int k = 0;
			for (int i = 0; i < 16; i++) {

				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];

				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str).toUpperCase();

		} catch (Exception e) {

		}
		return s;
	}

}
