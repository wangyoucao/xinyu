package together.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.baidu.platform.comapi.map.Projection;
import com.my.views.MyMapView;

public class BaiduMapOverlayActivity extends Activity   {

	/**地图引擎管理类*/ 
   
 
    /**显示地图的View*/ 
    private MyMapView mapView = null;  
 
    private TextView jingdu;
    private TextView  diming;
    private MKSearch  mMKSearch;
    private Button  btnOk;
    private Bundle bundle = new Bundle();
    private GestureDetector mGestureDetector;
    /**  
     * 经研究发现在申请KEY时：应用名称一定要写成my_app_应用名（也就是说"my_app_"是必须要有的）。  
     * 百度地图SDK提供的服务是免费的，接口无使用次数限制。您需先申请密钥（key)，才可使用该套SDK。  
     * */ 
    public static final String BAIDU_MAP_KEY = "07418AEC69BAAB7104C6230A6120C580DFFAEEBB";  
 
    @Override 
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        mGestureDetector = new GestureDetector(this, new MyGestureListener());
        // 注意：请在调用setContentView前初始化BMapManager对象，否则会报错  
//        mBMapManager = new BMapManager(this.getApplicationContext());  
//        mBMapManager.init(BAIDU_MAP_KEY, new MKGeneralListener() {  
// 
//            @Override 
//            public void onGetNetworkState(int iError) {  
//                if (iError == MKEvent.ERROR_NETWORK_CONNECT) {  
//                    Toast.makeText(BaiduMapOverlayActivity.this.getApplicationContext(),   
//                            "您的网络出错啦！",  
//                            Toast.LENGTH_LONG).show();  
//                }  
//            }  
// 
//            @Override 
//            public void onGetPermissionState(int iError) {  
//                if (iError == MKEvent.ERROR_PERMISSION_DENIED) {  
//                    // 授权Key错误：  
//                    Toast.makeText(BaiduMapOverlayActivity.this.getApplicationContext(),  
//                            "请在 DemoApplication.java文件输入正确的授权Key！",  
//                            Toast.LENGTH_LONG).show();  
//                }  
//            }  
//        });  
 
        setContentView(R.layout.mapview);  
 
         mapView = (MyMapView) this.findViewById(R.id.bmapsView); 
         //jingdu = (TextView)this.findViewById(R.id.jingduValue);
         diming = (TextView)this.findViewById(R.id.dimingValue);
         btnOk = (Button)this.findViewById(R.id.btnOk);
         btnOk.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Intent intent = BaiduMapOverlayActivity.this.getIntent();
	            intent.putExtras(bundle);
	            BaiduMapOverlayActivity.this.setResult(RESULT_OK, intent);
	            BaiduMapOverlayActivity.this.finish();
			}
        	 
         });
         
        // 设置启用内置的缩放控件  
        mapView.setBuiltInZoomControls(true);  
 
        // 获取地图控制器，可以用它控制平移和缩放  
        MapController mMapController = mapView.getController();  
        // 设置地图的缩放级别。 这个值的取值范围是[3,18]。   
        mMapController.setZoom(13);  
        GeoPoint getMyLocation =((TogetherApp) TogetherApp.getInstance()).getMyLocation();
        mMapController.setCenter(getMyLocation);//设置地图中心
 
        // 获取用于在地图上标注一个地理坐标点的图标  
        Drawable drawable = this.getResources().getDrawable(R.drawable.ic_map_marker); 
        // 创建覆盖物（MyOverlayItem）对象并添加到覆盖物列表中  
        mapView.getOverlays().add(new MyOverlayItem(drawable,getMyLocation));  
        mapView.setOnTouchListener(new OnTouchListener(){

			
		    @Override  
		    public boolean onTouch(View v,MotionEvent ev) {
		    	 
		    	 
		    	
		    	return  mGestureDetector.onTouchEvent(ev);
		    }
         
          
        });
        /** 初始化MKSearch */
        BMapManager bmap=((TogetherApp) TogetherApp.getInstance()).getMapManager();
        mMKSearch = new MKSearch();
        mMKSearch.init(bmap, new MySearchListener());
    }  
 
    
    
//    @Override  
//    public boolean onTouchEvent(View v,MotionEvent ev) {
//    	 
//    	int actionType = ev.getAction();
//
//    	mapView.getOverlays().clear();
//    	
//
//    	if (ev.getAction() == MotionEvent.ACTION_DOWN){
//
//    	//	if (v.getId() == R.id.activityAddress)
//
//    	Projection proj = mapView.getProjection();
//
//    	GeoPoint loc = proj.fromPixels((int)ev.getX(), (int)ev.getY());
//
//    	String sirina=Double.toString(loc.getLongitudeE6()/1000000);
//
//    	String dolzina=Double.toString(loc.getLatitudeE6()/1000000);
//    	jingdu.setText(sirina+":"+dolzina);
//    	Log.d("sirina", loc.getLatitudeE6()+""); 
//    	Log.d("dolzina", loc.getLongitudeE6()+""); 
////    	Toast toast = Toast.m;akeText(getApplicationContext(), "Širina: "+sirina+" Dolzina: "+dolzina, Toast.LENGTH_LONG);
////    	toast.show();
//    	 
//    	Drawable drawable = this.getResources().getDrawable(R.drawable.ic_map_marker); 
//        // 创建覆盖物（MyOverlayItem）对象并添加到覆盖物列表中  
//        mapView.getOverlays().add(new MyOverlayItem(drawable,loc));  
// 
//        
//        // 刷新地图  
//        mapView.refresh();
//        
//
//        //mapView.getController().animateTo(p);
//        mMKSearch.reverseGeocode(loc);
//      } 
//    	
//    	return  true;
//    }
    
    /**  
     * 包含了一个覆盖物列表的覆盖物类  
     * @author android_ls  
     */ 
    final class MyOverlayItem extends ItemizedOverlay<OverlayItem> {  
 
        /**覆盖物列表集合*/ 
        private ArrayList<OverlayItem> mOverlayList = new ArrayList<OverlayItem>();  
 
        // 声明double类型的变量存储北京天安门的纬度、经度值  
        private double mLat1 = 39.915; // point1纬度  
 
        private double mLon1 = 116.404; // point1经度  
 
        // 传进来的Drawable对象用于在地图上标注一个地理坐标点  
        public MyOverlayItem(Drawable drawable,GeoPoint geo) {  
            super(drawable);  
 
            // 将GPS纬度经度值转换成以微度的整数形式存储的地理坐标点  
 
            /* 注：GeoPoint对象构造方法的参数列表：第一个是参数表示纬度，  
             * 第二个是经度（我们平时都是经纬度这么叫的，想着应该是经度在前的，呵呵。）  
             * 在网上查了下，GPS的值官方给的就是纬度经度，也就是说纬度是在前的，以前一直没太注意。*/ 
//            GeoPoint geoPoint1 = new GeoPoint(  
//                    (int) (mLat1 * 1E6),   
//                    (int) (mLon1 * 1E6));  
 
            // 构造OverlayItem对象并添加到mOverlayList集合里  
            mOverlayList.add(new OverlayItem(geo, "point1", "point1"));  
 
            /*   
             * 官方的解释：在一个新ItemizedOverlay上执行所有操作的工具方法。  
             * 没搞明白啥意思，但是必须的调用这个方法，否则程序运行报错*/ 
            populate();  
        }  
 
        /*  
         * 返回的是从指定List集合中，取出的一个OverlayItem对象。  
         * mOverlayList集合里一旦有了数据，在调用其之前，  
         * 一定的在MyOverlayItem的构造函数里调用这个方法populate();  
         */ 
        @Override 
        protected OverlayItem createItem(int index) {  
            return mOverlayList.get(index);  
        }  
 
        @Override 
        public int size() {  
            return mOverlayList.size();  
        }  
 
    }  
 
    // 重写以下方法，管理API  
    @Override 
    protected void onResume() {  
        mapView.onResume();  
        
        super.onResume();  
    }  
 
    @Override 
    protected void onPause() {  
        mapView.onPause();  
         
        super.onPause();  
    }  
 
    @Override 
    protected void onDestroy() {  
        mapView.destroy();  
          
        super.onDestroy();  
    }  
    
    /** 内部类实现MKSearchListener接口,用于实现异步搜索服务 */
    private class MySearchListener implements MKSearchListener {
        @Override
        public void onGetAddrResult(MKAddrInfo result, int iError) {
            if( iError != 0 || result == null){
                Toast.makeText(BaiduMapOverlayActivity.this, "获取地理信息失败", Toast.LENGTH_LONG).show();
            }else {
              String cityName =result.strAddr;
            
               diming.setText(cityName);
                
               bundle.putString("maplocationValue",cityName);
               Intent intent = BaiduMapOverlayActivity.this.getIntent();
               intent.putExtras(bundle);
               BaiduMapOverlayActivity.this.setResult(RESULT_OK, intent);
//                Intent intent = new Intent(Weather_WelcomeActivity.this,Weather_MainActivity.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
//                Weather_WelcomeActivity.this.finish();
            }
        }

        @Override
        public void onGetDrivingRouteResult(MKDrivingRouteResult result,
                int iError) {
        }

        @Override
        public void onGetPoiResult(MKPoiResult result, int type, int iError) {
        }

        @Override
        public void onGetTransitRouteResult(MKTransitRouteResult result,
                int iError) {
        }

        @Override
        public void onGetWalkingRouteResult(MKWalkingRouteResult result,
                int iError) {
        }

		@Override
		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
    	 
    	@Override
    	public void onShowPress(MotionEvent ev){
    		 mapView.getOverlays().clear();
 	    	
     		
 	    	//if (ev.getAction() == MotionEvent.ACTION_DOWN){
 	
 	    	//	if (v.getId() == R.id.activityAddress)
 	
 	    	Projection proj = mapView.getProjection();
 	
 	    	GeoPoint loc = proj.fromPixels((int)ev.getX(), (int)ev.getY());
 	
 	    	String sirina=Double.toString(loc.getLongitudeE6()/1000000);
 	
 	    	String dolzina=Double.toString(loc.getLatitudeE6()/1000000);
 	    	//jingdu.setText(sirina+":"+dolzina);
 	    	Log.d("sirina", loc.getLatitudeE6()+""); 
 	    	Log.d("dolzina", loc.getLongitudeE6()+""); 
// 	    	Toast toast = Toast.m;akeText(getApplicationContext(), "Širina: "+sirina+" Dolzina: "+dolzina, Toast.LENGTH_LONG);
// 	    	toast.show();
 	    	 
 	    	Drawable drawable = BaiduMapOverlayActivity.this.getResources().getDrawable(R.drawable.ic_map_marker); 
 	        // 创建覆盖物（MyOverlayItem）对象并添加到覆盖物列表中  
 	        mapView.getOverlays().add(new MyOverlayItem(drawable,loc));  
 	 
 	        
 	        // 刷新地图  
 	        mapView.refresh();
 	        
 	
 	        //mapView.getController().animateTo(p);
 	        mMKSearch.reverseGeocode(loc);
 	     // } 
     	// return true;
     	  
    	  }
    	 
    	  @Override
    	 
    	  public void onLongPress(MotionEvent ev) {
    	 
//    	    Log.d("onLongPress",ev.toString());
//    	    mapView.getOverlays().clear();
//	    	
//    		
//	    	if (ev.getAction() == MotionEvent.ACTION_DOWN){
//	
//	    	//	if (v.getId() == R.id.activityAddress)
//	
//	    	Projection proj = mapView.getProjection();
//	
//	    	GeoPoint loc = proj.fromPixels((int)ev.getX(), (int)ev.getY());
//	
//	    	String sirina=Double.toString(loc.getLongitudeE6()/1000000);
//	
//	    	String dolzina=Double.toString(loc.getLatitudeE6()/1000000);
//	    	//jingdu.setText(sirina+":"+dolzina);
//	    	Log.d("sirina", loc.getLatitudeE6()+""); 
//	    	Log.d("dolzina", loc.getLongitudeE6()+""); 
////	    	Toast toast = Toast.m;akeText(getApplicationContext(), "Širina: "+sirina+" Dolzina: "+dolzina, Toast.LENGTH_LONG);
////	    	toast.show();
//	    	 
//	    	Drawable drawable = BaiduMapOverlayActivity.this.getResources().getDrawable(R.drawable.ic_map_marker); 
//	        // 创建覆盖物（MyOverlayItem）对象并添加到覆盖物列表中  
//	        mapView.getOverlays().add(new MyOverlayItem(drawable,loc));  
//	 
//	        
//	        // 刷新地图  
//	        mapView.refresh();
//	        
//	
//	        //mapView.getController().animateTo(p);
//	        mMKSearch.reverseGeocode(loc);
//	      } 
//    	 
  	  }
    	 
    	
    	}
}  
