package together.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import together.activity.util.SystemUiHider;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

import com.androidquery.AQuery;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class WhDetailActivity extends Activity implements OnItemSelectedListener,  
ViewFactory {
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;
    private ImageSwitcher mSwitcher;
    ViewPager pager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ac_image_pager);

       
		AQuery listAq = new AQuery(this);
 
       // Gallery g = (Gallery) findViewById(R.id.gallery);  
          
         
        Intent intent = getIntent();  
        int  position = intent.getIntExtra("position", 0);
        List<HashMap<String, String>> list = getimageList();
		//GridViewAdapter aa = new GridViewAdapter(this, list,listAq);
		// 获取GridViewActivity传来的图片位置position   
        ImagePagerAdapter pageadapter = new ImagePagerAdapter(this,list,listAq);
;		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(pageadapter);
		pager.setCurrentItem(position);
       // g.setAdapter(aa);        // 设置图片ImageAdapter  
       // g.setSelection(position);  
        //g.setOnItemClickListener(this);// 设置当前显示图片  
              
//        Animation an= AnimationUtils.loadAnimation(this,R.anim.cycle );     // Gallery动画  
//        g.setAnimation(an);   
		
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		
	}

	@Override
	public View makeView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// mSwitcher.setImageResource(arg1);
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	public List<HashMap<String, String>> getimageList() {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String body;
		try {
		//Constants.URL json地址,getContent用来获取数据流
//		body = JsonUtil.getContent(Constants.URL);
//		JSONArray array = new JSONArray(body);
//		for (int i = 0; i < array.length(); i++) {
//		JSONObject jobj = array.getJSONObject(i);
		HashMap<String, String> map = new HashMap<String, String>();
		//“你的json地址”+json中取图片的相对地址得到绝对地址
//		map.put("itemImage",
//		Constants.ABSOLUTE_URL + jobj.getString("music_pic"));
//		map.put("itemText", jobj.getString("music_name"));
		map.put("itemImage","http://192.168.1.155:8080/xinwei-demo-web/userpic/test.png");
		map.put("message", "这是一个测试");
		map.put("timeago","4");
		map.put("count", "4");
		list.add(map);
		map = new HashMap<String, String>();
		map.put("itemImage","http://192.168.1.155:8080/xinwei-demo-web/userpic/bg_hot.png");
		map.put("message", "这是一个测试");
		map.put("timeago","4");
		map.put("count", "4");
		list.add(map);
		
		map = new HashMap<String, String>();
		map.put("itemImage","http://192.168.1.155:8080/xinwei-demo-web/userpic/test.png");
		map.put("message", "这是一个测试");
		map.put("timeago","4");
		map.put("count", "4");
		list.add(map);
		
		map = new HashMap<String, String>();
		map.put("itemImage","http://192.168.1.155:8080/xinwei-demo-web/userpic/bg_hot.png");
		map.put("message", "这是一个测试");
		map.put("timeago","4");
		map.put("count", "4");
		list.add(map);
		map = new HashMap<String, String>();
		map.put("itemImage","http://192.168.1.155:8080/xinwei-demo-web/userpic/test.png");
		map.put("message", "这是一个测试");
		map.put("timeago","4");
		map.put("count", "4");
		list.add(map);
		
		map = new HashMap<String, String>();
		map.put("itemImage","http://192.168.1.155:8080/xinwei-demo-web/userpic/bg_hot.png");
		map.put("message", "这是一个测试");
		map.put("timeago","4");
		map.put("count", "4");
		list.add(map);
		
		
		//}
		} catch (Exception e) {
		// TODO Auto-generated catch block
		   e.printStackTrace();
		}
		return list;

		}
	
	private class ImagePagerAdapter extends PagerAdapter {

		private List<HashMap<String, String>> list;
		private LayoutInflater inflater;
		AQuery listaq;
		Context context ;
		ImagePagerAdapter(Context context, List<HashMap<String, String>> list,AQuery aq) {
			this.list = list;
			this.listaq = aq ;
			this.context = context;
			inflater = getLayoutInflater();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public void finishUpdate(View container) {
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			//View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
			View imageLayout = LayoutInflater.from(context).inflate(
					R.layout.item_pager_image, null);
			ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
			
			
			
			String image_path = list.get(position).get("itemImage");
			String text = list.get(position).get("message");
			String time = list.get(position).get("timeago");
			String count = list.get(position).get("count");
			//Bitmap bm = getBitMap(context, image_path);
		
			AQuery aq = listaq.recycle(imageLayout);
			
			String tbUrl = image_path;
			
			Bitmap placeholder = aq.getCachedImage(R.drawable.bg);
			
			//if(aq.shouldDelay(position, imageLayout, (View)view.getParent(), tbUrl)){
//				aq.id(R.id.activity_message).text(text);
//				aq.id(R.id.activity_timeago).text(time);
//				aq.id(R.id.activity_count).text(count);
			
//				aq.id(R.id.activity_message).text(text);
//				aq.id(R.id.activity_timeago).text(time);
//				aq.id(R.id.activity_count).text(count);
				aq.id(R.id.image).image(tbUrl, false, false, 0, 0, placeholder, 0, 0);
		//	}	
			
//			MyASyncTask yncTask=new MyASyncTask(convertView);
//			yncTask.execute(image_path,text);
//			
			
		
			((ViewPager) view).addView(imageLayout, 0);
			return imageLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View container) {
		}
	}
}
