
package together.activity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;


public class SettingsActivity extends PreferenceActivity implements	 OnPreferenceChangeListener { 
	        /** Called when the activity is first created. */ 
	        @Override 
	        public void onCreate(Bundle savedInstanceState) { 
	            super.onCreate(savedInstanceState); 
	            // 设置PreferenceActivity保存数据使用的XML文件的名称 
	            getPreferenceManager().setSharedPreferencesName("mySetting"); 
	            // 加载XML文件：此处不能使用Activity的setContentView() 
	         addPreferencesFromResource(R.xml.settings); 
	          // 获取【姓名】设置项对应的Preference对象 
	           Preference individualNamePreference = findPreference("my_individual_name"); 
	           // 获得指向mySetting.xml文件的SharedPreference对象 
	           SharedPreferences sharedPreferences = individualNamePreference 
	                   .getSharedPreferences(); 
	           // 设置【姓名】设置项的summary 
	           individualNamePreference.setSummary(sharedPreferences.getString( 
	                   "my_individual_name", "")); 
	           // 判断【是否保存个人信息】设置项是否被选中 
	          if (sharedPreferences 
	                   .getBoolean("my_yesno_save_individual_info", false)) { 
	                individualNamePreference.setEnabled(true); 
	            } else { 
	                individualNamePreference.setEnabled(false); 
	            } 
	            individualNamePreference.setOnPreferenceChangeListener(this); 
	            // 获得【公司地址】设置项对应的Preference对象 
	            Preference mobileNamePreference = findPreference("my_comp"); 
	            SharedPreferences mobileSharedPreferences = mobileNamePreference 
	                  .getSharedPreferences(); 
	           // 设置【公司地址】的summary 
	            mobileNamePreference.setSummary(mobileSharedPreferences.getString( 
	                    "my_comp", "")); 
	            mobileNamePreference.setOnPreferenceChangeListener(this); 
	        } 
	      
	        @Override 
	        public boolean onPreferenceChange(Preference preference, Object newValue) { 
	            preference.setSummary(String.valueOf(newValue)); 
	          return true; 
	       } 
	      
	        /** 
	         * 处理【是否保存个人信息】的改变事件 
	         */ 
	        @Override 
	        public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, 
	                Preference preference) { 
	            if ("my_yesno_save_individual_info".equals(preference.getKey())) { 
	              findPreference("my_individual_name").setEnabled( 
	                       !findPreference("my_individual_name").isEnabled()); 
	            } 
	            return super.onPreferenceTreeClick(preferenceScreen, preference); 
	        } }
