package together.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import together.activity.R;
import together.models.Picture;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class GridViewAdapter extends BaseAdapter {
	private List<Picture> list;
	private Context context;
	MyView tag;
	AQuery listaq;
	DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	public GridViewAdapter(Context context, List<Picture> lst,AQuery aq,DisplayImageOptions options) {
		this.list = lst;
		this.context = context;
		this.listaq = aq;
		this.options = options;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		String imagpath  = list.get(position).getPicPath();
		int interestcount = list.get(position).getInterestCount();
		String time = list.get(position).getCreateDate();
		int count = list.get(position).getAnswerCount();
		View v = LayoutInflater.from(context).inflate(
				R.layout.cell_activity, null);
		if (convertView == null) {
			
			tag = new MyView();
			tag.imageView = (ImageView) v.findViewById(R.id.activity_thumb);
			tag.message = (TextView) v.findViewById(R.id.wh_interesting);
			tag.timeago = (TextView) v.findViewById(R.id.wh_timeago);
			tag.count = (TextView) v.findViewById(R.id.wh_reply);
			v.setTag(tag);
			convertView = v;
		} else {
			tag = (MyView) convertView.getTag();
			tag.message.setText(interestcount+"");
			tag.timeago.setText(time);
			tag.count.setText(count+"");
		}
		
		
		//Bitmap bm = getBitMap(context, image_path);
	
		//AQuery aq = listaq;
		
		String tbUrl = imagpath;
		
		//Bitmap placeholder = aq.getCachedImage(R.drawable.bg);
		
		imageLoader.displayImage(tbUrl, tag.imageView, options);
		
//		if(aq.shouldDelay(position, convertView, parent, tbUrl)){
//			aq.id(R.id.activity_message).text(interestcount);
//			aq.id(R.id.activity_timeago).text(time);
//			aq.id(R.id.activity_count).text(count);
//			aq.id(R.id.activity_thumb).image(placeholder);
//		}else{
//			aq.id(R.id.activity_message).text(interestcount);
//			aq.id(R.id.activity_timeago).text(time);
//			aq.id(R.id.activity_count).text(count);
//			aq.id(R.id.activity_thumb).image(tbUrl, false, false, 0, 0, placeholder, AQuery.FADE_IN_NETWORK, 0);
//		}	
//		
//		MyASyncTask yncTask=new MyASyncTask(convertView);
//		yncTask.execute(imagpath,"xinyu");
//		
		
		return convertView;
	}

	public synchronized Bitmap getBitMap(Context c, String url) {
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			bitmap = BitmapFactory.decodeResource(c.getResources(),
					R.drawable.cancel); // 当网络连接异常后,给个默认图片
			return bitmap;
		}
		try {
			// 打开网络连接
			System.out.println("myFileUrl="+myFileUrl);
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream(); // 把得到的内容转换成流
			int length = (int) conn.getContentLength(); // 获取文件的长度
			if (length != -1) {
				byte[] imgData = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, imgData, destPos, readLen);
					destPos += readLen;
				}

				bitmap = BitmapFactory.decodeByteArray(imgData, 0,
						imgData.length);
			}

		} catch (Exception e) {
			e.printStackTrace();
			bitmap = BitmapFactory.decodeResource(c.getResources(),
					R.drawable.cancel);
			return bitmap;
		}

		return bitmap;
	}
	
	public class MyASyncTask extends AsyncTask<String, Integer, Bitmap> {
        String text =null;
		
        View view;
        public MyASyncTask(View v){
        	view =v;
        	
        }
        @Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bitmap=null;
	        try {
	        	text = params[1];
	            URL url = new URL(params[0]);
	            System.out.println("URL ="+url);
	            HttpURLConnection con=(HttpURLConnection) url.openConnection();
	            con.setDoInput(true);
	            con.connect();
	            InputStream inputStream=con.getInputStream();
	            
	             bitmap=BitmapFactory.decodeStream(inputStream); 
	            inputStream.close();
	        } 
	         catch (Exception e) {
	           
	            e.printStackTrace();
	        } 
	        return bitmap;
	    
		}

		
		//执行获得图片数据后，更新UI:显示图片，隐藏进度条
	    @Override
	    protected void onPostExecute(Bitmap Result){
	    	System.out.println("---------------"+Result.toString());
	    	
	    	MyView ve = (MyView)view.getTag();
	    	ve.imageView.setImageBitmap(Result);
	    	ve.message.setText("sdfgsdgsdg");
	       
	    }
		
	}
}

class MyView {
	ImageView imageView;
	TextView message;
	TextView count;
	TextView timeago;

}
