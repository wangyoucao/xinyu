package com.krislq.sliding.fragment;



import com.yangyu.mycustomtab02.FragmentPage1;
import com.yangyu.mycustomtab02.FragmentPage2;
import com.yangyu.mycustomtab02.FragmentPage3;
import com.yangyu.mycustomtab02.FragmentPage4;
import com.yangyu.mycustomtab02.FragmentPage5;

import together.activity.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class MainFragment extends Fragment {
	
	private int mColorRes = -1;
	//����FragmentTabHost����
		private FragmentTabHost mTabHost;
		
		//����һ������
		private LayoutInflater layoutInflater;
			
		//�������������Fragment����
		private Class fragmentArray[] = {FragmentPage1.class,FragmentPage2.class,FragmentPage3.class,FragmentPage4.class,FragmentPage5.class};
		
		//������������Ű�ťͼƬ
		private int mImageViewArray[] = {R.drawable.tab_home_btn,R.drawable.tab_message_btn,R.drawable.tab_selfinfo_btn,
										 R.drawable.tab_square_btn,R.drawable.tab_more_btn};
		
		//Tabѡ�������
		private String mTextviewArray[] = {"��ҳ", "��Ϣ", "����", "�㳡", "���"};
	public MainFragment() { 
		this(R.color.white);
	}
	
	public MainFragment(int colorRes) {
		mColorRes = colorRes;
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (savedInstanceState != null)
			mColorRes = savedInstanceState.getInt("mColorRes");
		int color = getResources().getColor(mColorRes);
//		// construct the RelativeLayout
		RelativeLayout v = new RelativeLayout(getActivity());
		v.setBackgroundColor(color);		
		layoutInflater = inflater;
		View view = inflater.inflate(R.layout.main_tab_layout, container, false);	
		v.addView(view);
		
		//ʵ��TabHost���󣬵õ�TabHost
			mTabHost = (FragmentTabHost)view.findViewById(android.R.id.tabhost);
			mTabHost.setup(this.getActivity().getApplicationContext(), this.getActivity().getSupportFragmentManager(), R.id.realtabcontent);	
			
			//�õ�fragment�ĸ���
			int count = fragmentArray.length;	
					
			for(int i = 0; i < count; i++){	
				//Ϊÿһ��Tab��ť����ͼ�ꡢ���ֺ�����
				TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
				//��Tab��ť��ӽ�Tabѡ���
				mTabHost.addTab(tabSpec, fragmentArray[i], null);
				//����Tab��ť�ı���
				mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
			}
	return v;
		
	}
	
	/**
	 * ��ʼ�����
	 */
	private void initView(){
		//ʵ��ֶ���
		//layoutInflater = LayoutInflater.from(this);
		
	}
				
	/**
	 * ��Tab��ť����ͼ�������
	 */
	private View getTabItemView(int index){
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);
	
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(mImageViewArray[index]);
		
		TextView textView = (TextView) view.findViewById(R.id.textview);		
		textView.setText(mTextviewArray[index]);
	
		return view;
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("mColorRes", mColorRes);
	}
	
}
