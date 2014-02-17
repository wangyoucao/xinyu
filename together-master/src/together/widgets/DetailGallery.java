/**
 * show gallery , with out fling function
 * @author zhangjie
 * @version 1.0
 * @since 2012-2-13
 * Created on 2012-2-13
 *
 * TODO To show gallery , with out fling function
 * Window - Preferences - Java - Code Style - Code Templates
 */
package together.widgets;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;

public class DetailGallery extends Gallery {
	private Camera mCamera = new Camera();
	private int mMaxRotationAngle = 75;
	private int mMaxZoom = -165;
	private int mCoveflowCenter;
	private boolean enable_fling = false;

	public boolean isEnable_fling() {
		return enable_fling;
	}

	public void setEnable_fling(boolean enable_fling) {
		this.enable_fling = enable_fling;
	}

	/**
	 * constructor
	 * 
	 * @param context
	 *            gallery context
	 * */
	public DetailGallery(Context context) {
		super(context);
		this.setStaticTransformationsEnabled(true);
	}

	/**
	 * constructor
	 * 
	 * @param context
	 *            gallery context
	 * @param attrs
	 *            attributeset
	 * */
	public DetailGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setStaticTransformationsEnabled(true);
	}

	/**
	 * constructor
	 * 
	 * @param context
	 *            gallery context
	 * @param attrs
	 *            attribute set
	 * @param style
	 *            defstyle
	 * */
	public DetailGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setStaticTransformationsEnabled(true);
	}

	// private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
	// if(e1 != null && e2!=null)
	// return e2.getX() > e1.getX();
	// else
	// return false;
	// }

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// e1是按下的事件，e2是抬起的事件
		if (enable_fling == false)
			return false;
		// System.out.println("call onfling");
		int keyCode;
		// if(isScrollingLeft(e1,e2)){//
		if (velocityX > 0) {
			keyCode = KeyEvent.KEYCODE_DPAD_LEFT;
		} else {
			keyCode = KeyEvent.KEYCODE_DPAD_RIGHT;
		}
		onKeyDown(keyCode, null);
		return true;
	}

	public int getMaxRotationAngle() {
		return mMaxRotationAngle;
	}

	public void setMaxRotationAngle(int maxRotationAngle) {
		mMaxRotationAngle = maxRotationAngle;
	}

	public int getMaxZoom() {
		return mMaxZoom;
	}

	public void setMaxZoom(int maxZoom) {
		mMaxZoom = maxZoom;
	}

	private int getCenterOfCoverflow() {
		return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2
				+ getPaddingLeft();
	}

	private static int getCenterOfView(View view) {
		return view.getLeft() + view.getWidth() / 2;
	}

	protected boolean getChildStaticTransformation(View child, Transformation t) {
		final int childCenter = getCenterOfView(child);
		final int childWidth = child.getWidth();
		int rotationAngle = 0;

		t.clear();
		t.setTransformationType(Transformation.TYPE_MATRIX);

		if (childCenter == mCoveflowCenter) {
			transformImageBitmap(child, t, 0);
		} else {
			rotationAngle = (int) (((float) (mCoveflowCenter - childCenter) / childWidth) * mMaxRotationAngle);
			if (Math.abs(rotationAngle) > mMaxRotationAngle) {
				rotationAngle = (rotationAngle < 0) ? -mMaxRotationAngle
						: mMaxRotationAngle;
			}
			transformImageBitmap(child, t, rotationAngle);
		}
		return true;
	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mCoveflowCenter = getCenterOfCoverflow();
		super.onSizeChanged(w, h, oldw, oldh);
	}

	private boolean isTouchable = true;

	/**
	 * set touchable
	 * 
	 * @param touchable
	 * 
	 * */
	public void setTouchable(boolean touchable) {
		isTouchable = touchable;
	}

	/**
	 * despatch touch event
	 * 
	 * @param ev
	 *            motion event
	 * @return false if istouchable = false otherwise return
	 *         super.dispatchTouchEvent
	 * */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (isTouchable) {
			return super.dispatchTouchEvent(ev);
		} else {
			return false;
		}
	}

	private void transformImageBitmap(View child, Transformation t,
			int rotationAngle) {
		mCamera.save();
		final Matrix imageMatrix = t.getMatrix();
		final int imageHeight = child.getLayoutParams().height;
		final int imageWidth = child.getLayoutParams().width;
		final int rotation = Math.abs(rotationAngle);
		mCamera.translate(0.0f, 10.0f, 100.0f);
		// As the angle of the view gets less, zoom in
		if (rotation < mMaxRotationAngle) {
			float zoomAmount = (float) (mMaxZoom + (rotation * 1.5));// *2
			mCamera.translate(0.0f, 0.0f, zoomAmount);
		}
		mCamera.rotateY(rotationAngle);
		mCamera.getMatrix(imageMatrix);
		imageMatrix.preTranslate(-(imageWidth / 2), -(imageHeight / 2));
		imageMatrix.postTranslate((imageWidth / 2), (imageHeight / 2));
		mCamera.restore();
	}
}