package com.krislq.sliding.fragment;

import java.util.ArrayList;

import together.activity.FragmentChangeActivity;
import together.activity.R;
import together.activity.util.DemoFragmentAdapter;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
/**
 * menu fragment ，主要是用于显示menu菜单
 * @author <a href="mailto:kris@krislq.com">Kris.lee</a>
 * @since Mar 12, 2013
 * @version 1.0.0
 */
public class MenuFragmentt extends Fragment{
    private int index = -1;
    private ViewPager mViewPager = null;
    private FrameLayout mFrameLayout = null;
    FragmentChangeActivity  mActivity =null;
    
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
    	View view = inflater.inflate(R.layout.frame_content, null);
    	
    	 mActivity = (FragmentChangeActivity)this.getActivity();
    	 together.activity.util.DemoFragmentAdapter demoFragmentAdapter = new DemoFragmentAdapter(mActivity.getFragmentManager());
    	 mViewPager = (ViewPager)view.findViewById(R.id.viewpager);
	      //  mFrameLayout = (FrameLayout)mActivity.findViewById(R.id.content);
	        mViewPager.setAdapter(demoFragmentAdapter);

	        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
				@Override
				public void onPageScrollStateChanged(int arg0) { }

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) { }

				@Override
				public void onPageSelected(int position) {
					switch (position) {
					case 0:
						mActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
						break;
					default:
						mActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
						break;
					}
				}

			});
	        ActionBar actionBar = mActivity.getActionBar();
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            actionBar.removeAllTabs();
            actionBar.addTab(actionBar.newTab()
                    .setText("First Tab")
                    .setTabListener(tabListener));

            actionBar.addTab(actionBar.newTab()
                    .setText("Second Tab")
                    .setTabListener(tabListener));
            actionBar.addTab(actionBar.newTab()
                    .setText("Third Tab")
                    .setTabListener(tabListener));

    	return  view ;
	}
   
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
    
    ActionBar.TabListener tabListener = new ActionBar.TabListener() {
        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            if (mViewPager.getCurrentItem() != tab.getPosition())
                mViewPager.setCurrentItem(tab.getPosition());
             
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }
    };

	public class ColorPagerAdapter extends FragmentPagerAdapter {
		
		private ArrayList<Fragment> mFragments;

		private final int[] COLORS = new int[] {
			R.color.red,
			R.color.green,
			R.color.blue,
			R.color.white,
			R.color.black
		};
		
		public ColorPagerAdapter(android.support.v4.app.Fragment fragmentManager) {
			super(fragmentManager.getChildFragmentManager());
			mFragments = new ArrayList<Fragment>();
			for (int color : COLORS)
				mFragments.add(new ColorFragment(color));
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

	}
}
