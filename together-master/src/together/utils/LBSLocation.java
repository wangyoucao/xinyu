package together.utils;

import together.activity.TogetherApp;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * �ٶȶ�λAPIʹ���࣬������λ�������ض�λ�����ֹͣ��λ
 * 
 * @author Lu.Jian
 * 
 */
public class LBSLocation {

	private static LBSLocation location = null;
	private static TogetherApp app = null;

	private MyLocationListenner myListener = new MyLocationListenner();
	public LocationClient mLocationClient = null;

	public static LBSLocation getInstance(TogetherApp application) {
		app = application;
		if (location == null) {
			location = new LBSLocation(app);
		}

		return location;
	}

	private LBSLocation(TogetherApp app) {
		mLocationClient = new LocationClient(app);
		mLocationClient.registerLocationListener(myListener);
		mLocationClient.start();
	}

	/**
	 * ��ʼ��λ���󣬽���ڻص���
	 */
	public void startLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// ���صĶ�λ�����ַ��Ϣ
		option.setCoorType("bd09ll");// ���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
		option.disableCache(true);// ��ֹ���û��涨λ
		mLocationClient.setLocOption(option);
		mLocationClient.requestLocation();
	}

	/**
	 * ����������λ�õ�ʱ�򣬸�ʽ�����ַ��������Ļ��
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
		    app.currlocation = location;
			mLocationClient.stop();
//			
//			//��ݵ�ǰλ�ã������б���ÿһ��ľ���
//			for (ContentModel content : app.getList()) {
//
//				float results[] = new float[1];
//				if (location != null) {
//					Location.distanceBetween(location.getLatitude(),
//							location.getLongitude(), content.getLatitude(),
//							content.getLongitude(), results);
//				}
//				int distance = (int) results[0];
//				content.setDistance(distance == 0 ? "" : (int) results[0] + "��");
//			}
			//ˢ���б�
		//	app.getAdapter().notifyDataSetChanged();
			
		}

		public void onReceivePoi(BDLocation poiLocation) {

		}
	}
}
