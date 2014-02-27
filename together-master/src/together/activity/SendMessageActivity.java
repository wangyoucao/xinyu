
package together.activity;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Toast;
	
	/**
	 * 初始化activity
	 * @param intance Bundle
	 * 
	 * */
	/**
	 * 初始化UI
	 * */
	/**
	 * 发送按钮的点击事件
	 * */
			String type = eventType;
			Animation shakeAnim = AnimationUtils.loadAnimation(context,
					R.anim.shake_x);
			if (place == null || place.trim().equals("")) {
				placeEditText.startAnimation(shakeAnim);
				Toast.makeText(SendMessageActivity.this, R.string.fill_event_place,
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (description == null || description.trim().equals("")) {
				descriptionEditText.startAnimation(shakeAnim);
				Toast.makeText(SendMessageActivity.this, R.string.fill_event_description,
						Toast.LENGTH_SHORT).show();
				return;
			}
				Toast.makeText(getApplicationContext(), "发布成功", Toast.LENGTH_SHORT).show();
				finish();
			}
	/***
	 * spinner的选择事件
	 * */