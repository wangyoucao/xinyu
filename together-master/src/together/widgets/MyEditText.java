/*****************************************************************************
 *
 * Copyright 2012-2012 nbtstatx Corporation
 *
 *****************************************************************************
 *
 *   File Name   : MyEditText.java
 *   Created     : 2011-5-3 By nbtstatx
 *   Modified    : 2011-5-3 By nbtstatx
 *   Description : MyEditText.
 *
 ****************************************************************************/
package together.widgets;

import android.content.Context;
import android.graphics.Color;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author nbtstatx
 * 
 */
public class MyEditText extends LinearLayout {

	private TextView titleText = null;
	private EditText editText = null;
	private TextView downLine = null;

	private static final LayoutParams LINEARLAYOUT_LAYOUTPARAMS = new LinearLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

	/**
	 * @param context
	 */
	public MyEditText(Context context, String title, String value) {
		super(context);
		this.setOrientation(VERTICAL);

		titleText = new TextView(context);
		titleText.setText(title);
		addView(titleText, LINEARLAYOUT_LAYOUTPARAMS);

		editText = new EditText(context);
		editText.setText(value);
		addView(editText, LINEARLAYOUT_LAYOUTPARAMS);

		downLine = new TextView(context);
		downLine.setTextColor(Color.WHITE);
		addView(downLine, LINEARLAYOUT_LAYOUTPARAMS);
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 */
	public void setTile(String title) {
		titleText.setText(title);
	}

	/**
	 * 设置内容
	 * 
	 * @param value
	 */
	public void setValue(String value) {
		editText.setText(value);
	}

	public void setText(String value) {
		// TODO Auto-generated method stub
		editText.setText(value);
	}
}
