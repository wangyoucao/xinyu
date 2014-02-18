package together.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

public class AssetsUtil {
	/**
	 * get file from assets folders
	 * @param context
	 * @param filename
	 * @return fileString
	 * */
	public static String getFromAssets(Context con, String fileName) {
		String Result = "";
		try {
			InputStreamReader inputReader = new InputStreamReader(con
					.getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			while ((line = bufReader.readLine()) != null)
				Result += line;
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result;
	}
	
	/**
	 * check network info
	 * @param context
	 * @return true if connected
	 * */
	public static boolean checkNetworkInfo(Context context)
    {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //mobile 3G Data Network
        State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
         //wifi
        State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
         
        //如果3G网络和wifi网络都未连接，且不是处于正在连接状态 则进入Network Setting界面 由用户配置网络连接
        if(mobile==State.CONNECTED || mobile==State.CONNECTING)
            return true;
        if(wifi==State.CONNECTED || wifi==State.CONNECTING)
            return true;
		return false;        
        
//        //进入无线网络配置界面
        //startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)); //进入手机中的wifi网络设置界面        
    }
}
