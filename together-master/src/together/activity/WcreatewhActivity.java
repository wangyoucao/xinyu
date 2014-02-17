package together.activity;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImage.OnPictureSavedListener;
import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageToneCurveFilter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.util.MultiValueMap;

import together.activity.util.SystemUiHider;
import together.utils.WPrefs;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.my.views.TextImageView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class WcreatewhActivity extends AbstractAsyncActivity implements
		OnPictureSavedListener {
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

	private ImageView ivFilter;
	private byte[] mContent;
	Bitmap myBitmap;
	Bitmap bmp=null;
	
	GPUImageFilter filter;
	GPUImage gpuImage;
	// private GPUImageView mGPUImageView;
	private static final int REQUEST_PICK_IMAGE = 1;
	private TextView wcnext, wc_back;
	private ViewFlipper vf;
	private WCreateStep step;
	private ImageView progessview;
	private String whispertext;
	private String location;
	private Uri uri;
	float renderFontSize;
	float renderStrokeWidth;
	int fontColor;
	Paint tp = new Paint();
	int strokeColor;
	float strokeWidth;
	float textSize;
	Typeface tf;
	EditText whtextEditText3;
	int textX;
	int textY;
	TextImageView textimageview;
	String picId = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.gpuImage = new GPUImage(this);
		//ContentResolver resolver = getContentResolver();
		setContentView(R.layout.activity_wcreate);
		setBehindContentView(R.layout.menu_frame);
		Bundle bundle = getIntent().getExtras();
		picId = bundle.getString("picId", "");
		uri = (Uri) (getIntent().getParcelableExtra("bitmap"));
		//String path = getPath(uri);

		System.gc();
		// int degree = readPictureDegree(path);
		// BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
		// Bitmap cameraBitmap = BitmapFactory.decodeFile(path, bitmapOptions);

		/**
		 * 把图片旋转为正的方向
		 */
		// Bitmap bitmap = rotaingImageView(degree, cameraBitmap);
		// cameraBitmap=null;
		gpuImage.setGLSurfaceView((GLSurfaceView) findViewById(R.id.surfaceView1));
	    //bmp =getSmallBitmap(uri.getPath());
		gpuImage.setImage(uri);
		
		// gpuImage.setImage(yasuo(uri));

		textimageview = ((TextImageView) findViewById(R.id.textimageview));
		int picWidth = this.getWindowManager().getDefaultDisplay().getHeight();
		textimageview.setAdjustViewBounds(true);
		textimageview.setMaxHeight(picWidth - 75);

		// try {
		// myBitmap = MediaStore.Images.Media.getBitmap(
		// this.getContentResolver(), uri);
		// } catch (FileNotFoundException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		// LayoutParams params = textimageview.getLayoutParams();
		// params.height=bitmap.getHeight();
		// params.width =bitmap.getWidth();
		// textimageview.setLayoutParams(params);
		// textimageview.setImageURI(uri);

		// textimageview.setScaleType(FIT_CENTER);
		// mGPUImageView = (GPUImageView) findViewById(R.id.gpuimage);
		// mGPUImageView.setDrawingCacheEnabled(true);
		progessview = (ImageView) findViewById(R.id.wc_progress);
		vf = (ViewFlipper) findViewById(R.id.wc_vf);
		step = WCreateStep.WStepFilter;

		View.OnClickListener local1 = new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				int i = v.getId();
				switch (i) {

				case R.id.wc_normalfilter:
					// mGPUImageView.d
				case R.id.wc_carbon:
					filter = new GPUImageGrayscaleFilter();

					break;
				case R.id.wc_downtown:

					filter = new GPUImageContrastFilter(1.75F);
					GPUImageToneCurveFilter localGPUImageToneCurveFilter5 = new GPUImageToneCurveFilter();
					try {
						localGPUImageToneCurveFilter5
								.setFromCurveFileInputStream(WcreatewhActivity.this
										.getResources().getAssets()
										.open("aqua.acv"));
						filter = localGPUImageToneCurveFilter5;
					} catch (IOException localIOException5) {

						Log.e("W", localIOException5.getLocalizedMessage());
					}

					break;
				case R.id.wc_escape:

					GPUImageToneCurveFilter filterEscape = new GPUImageToneCurveFilter();
					try {
						filterEscape
								.setFromCurveFileInputStream(WcreatewhActivity.this
										.getResources().getAssets()
										.open("yellow-red.acv"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					filter = filterEscape;
					break;
				case R.id.wc_paper:
					GPUImageToneCurveFilter localfilter = new GPUImageToneCurveFilter();
					try {
						localfilter
								.setFromCurveFileInputStream(WcreatewhActivity.this
										.getResources().getAssets()
										.open("aqua.acv"));
						filter = localfilter;
					} catch (IOException localIOException5) {

						Log.e("W", localIOException5.getLocalizedMessage());
					}

					break;
				case R.id.wc_vintage:
					GPUImageToneCurveFilter filterPaper = new GPUImageToneCurveFilter();
					try {
						filterPaper
								.setFromCurveFileInputStream(WcreatewhActivity.this
										.getResources().getAssets()
										.open("crossprocess.acv"));
						filter = filterPaper;
					} catch (IOException localIOException3) {

						Log.e("W", localIOException3.getLocalizedMessage());
					}
					break;
				case R.id.wc_blues:
					GPUImageToneCurveFilter localGPUImageToneCurveFilter2 = new GPUImageToneCurveFilter();
					try {
						localGPUImageToneCurveFilter2
								.setFromCurveFileInputStream(WcreatewhActivity.this
										.getResources().getAssets()
										.open("06.acv"));
						filter = localGPUImageToneCurveFilter2;
					} catch (IOException localIOException2) {

						Log.e("W", localIOException2.getLocalizedMessage());
					}
					break;
				case R.id.wc_arizona:
					GPUImageToneCurveFilter filterBlue = new GPUImageToneCurveFilter();
					try {
						filterBlue
								.setFromCurveFileInputStream(WcreatewhActivity.this
										.getResources().getAssets()
										.open("02.acv"));
						filter = filterBlue;
					} catch (IOException localIOException1) {

						Log.e("W", localIOException1.getLocalizedMessage());
					}
					break;
				case R.id.wc_wired:
					GPUImageToneCurveFilter filterWired = new GPUImageToneCurveFilter();
					try {
						filterWired
								.setFromCurveFileInputStream(WcreatewhActivity.this
										.getResources().getAssets()
										.open("purple-green.acv"));
						filter = filterWired;
					} catch (IOException localIOException1) {

						Log.e("W", localIOException1.getLocalizedMessage());
					}
					break;
				}

				if (filter != null) {

					gpuImage.setFilter(filter);
					gpuImage.requestRender();

				}

			}

		};

		LinearLayout localLinearLayout = (LinearLayout) ((HorizontalScrollView) findViewById(R.id.wc_svfilters))
				.getChildAt(0);
		int count = localLinearLayout.getChildCount();

		for (int i = 0; i < count; i++) {
			localLinearLayout.getChildAt(i).setOnClickListener(local1);
		}
		// image button event

		View.OnClickListener imageButtonlistener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				int i = v.getId();
				switch (i) {
				case R.id.wc_font1:

					textimageview.setFont(WFont.WJustRealize);
					return;
				case R.id.wc_font2:

					textimageview.setFont(WFont.WUpright);
					return;
				case R.id.wc_font3:

					textimageview.setFont(WFont.WCentury);
					return;
				case R.id.wc_font4:

					textimageview.setFont(WFont.WCassenet);
					return;

				}
			}
		};
		LinearLayout linlayoutImage = (LinearLayout) findViewById(R.id.wc_fonts);

		int cnt = linlayoutImage.getChildCount();

		for (int i = 0; i < cnt; i++) {
			linlayoutImage.getChildAt(i)
					.setOnClickListener(imageButtonlistener);
		}

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.

		// Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		// photoPickerIntent.setType("image/*");
		// startActivityForResult(photoPickerIntent, REQUEST_PICK_IMAGE);
		wcnext = (TextView) findViewById(R.id.wc_next);
		wcnext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("sdfgdsfsg");
				// progessview.setImageResource(R.drawable.w_progress_half);
				next(v);

			}
		});

		wc_back = (TextView) findViewById(R.id.wc_back);
		wc_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				back(v);

			}
		});
	}

	private Bitmap yasuo(Uri uri) {
		Bitmap bitmap = null;
		try {
			System.gc();

			// String path = getPath(uri);
			// FileOutputStream localFileOutputStream = new
			// FileOutputStream(path);
			// bitmap.compress(Bitmap.CompressFormat.JPEG, 30,
			// localFileOutputStream);
			// localFileOutputStream.flush();
			// localFileOutputStream.close();
			// bitmap.recycle();

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = false;
			Bitmap resizedBitmap = BitmapFactory.decodeStream(this
					.getContentResolver().openInputStream(uri), null, options);

			// int degree = readPictureDegree(path);
			// Matrix matrix = new Matrix();;
			// matrix.postRotate(degree);
			// System.out.println("angle2=" + degree);
			// // 创建新的图片
			// Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
			// bitmap.getWidth(), bitmap.getHeight(), matrix, true);
			// bitmap=null;
			return resizedBitmap;
			// bitmap = rotaingImageView(degree, bitmap);
			// int picWidth = bitmap.getWidth();
			// int picHeight = bitmap.getHeight();
			// WindowManager windowManager = getWindowManager();
			// Display display = windowManager.getDefaultDisplay();
			// int screenWidth = display.getWidth();
			// int screenHeight = display.getHeight();
			// options.inSampleSize = 1;
			// if (picWidth > picHeight) {
			// if (picWidth > screenWidth)
			// options.inSampleSize = picWidth / screenWidth;
			// } else {
			// if (picHeight > screenHeight)
			// options.inSampleSize = picHeight / screenHeight;
			// }
			// options.inJustDecodeBounds = false;
			// bitmap = BitmapFactory.decodeStream(this.getContentResolver()
			// .openInputStream(uri), null, options);
			// img_touxiang.setImageBitmap(bitmap);
			/*
			 * if (bitmap.isRecycled() == false) { bitmap.recycle(); }
			 */

		} catch (Exception e1) {
		}
		return bitmap;
	}

	public String getPath(Uri uri)

	{
		ContentResolver cr = this.getContentResolver();
	    Cursor cursor = cr.query(uri, null, null, null, null);
	 
	     int index = cursor.getColumnIndex("_data");
	    cursor.moveToFirst();
	 
	     String url = cursor.getString(index);

	    return url;
	}


	public static Bitmap getSmallBitmap(String filePath) {  
        
        final BitmapFactory.Options options = new BitmapFactory.Options();  
        options.inJustDecodeBounds = true;  
        BitmapFactory.decodeFile(filePath, options);  
  
        // Calculate inSampleSize  
        options.inSampleSize = calculateInSampleSize(options, 480, 800);  
  
        // Decode bitmap with inSampleSize set  
        options.inJustDecodeBounds = false;  
          
        Bitmap bm = BitmapFactory.decodeFile(filePath, options);  
        if(bm == null){  
            return  null;  
        }  
        int degree = readPictureDegree(filePath);  
        bm = rotateBitmap(bm,degree) ;  
        ByteArrayOutputStream baos = null ;  
        try{  
            baos = new ByteArrayOutputStream();  
            bm.compress(Bitmap.CompressFormat.JPEG, 30, baos);  
              
        }finally{  
            try {  
                if(baos != null)  
                    baos.close() ;  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return bm ;  
  
    } 

	private static Bitmap rotateBitmap(Bitmap bitmap, int rotate) {
		if (bitmap == null)
			return null;

		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		// Setting post rotate to 90
		Matrix mtx = new Matrix();
		mtx.postRotate(rotate);
		return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
		
	}

	private static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	private Bitmap getBitMap(Uri mImageCaptureUri) {
		Bitmap bitmap = null;
		// 不管是拍照还是选择图片每张图片都有在数据中存储也存储有对应旋转角度orientation值
		// 所以我们在取出图片是把角度值取出以便能正确的显示图片,没有旋转时的效果观看

		ContentResolver cr = this.getContentResolver();
		Cursor cursor = cr.query(mImageCaptureUri, null, null, null, null);// 根据Uri从数据库中找
		if (cursor != null) {
			cursor.moveToFirst();// 把游标移动到首位，因为这里的Uri是包含ID的所以是唯一的不需要循环找指向第一个就是了
			String filePath = cursor.getString(cursor.getColumnIndex("_data"));// 获取图片路
			String orientation = cursor.getString(cursor
					.getColumnIndex("orientation"));// 获取旋转的角度
			cursor.close();
			if (filePath != null) {
				bitmap = BitmapFactory.decodeFile(filePath);// 根据Path读取资源图片
				int angle = 0;
				if (orientation != null && !"".equals(orientation)) {
					angle = Integer.parseInt(orientation);
				}
				if (angle != 0) {
					// 下面的方法主要作用是把图片转一个角度，也可以放大缩小等
					Matrix m = new Matrix();
					int width = bitmap.getWidth();
					int height = bitmap.getHeight();
					m.setRotate(angle); // 旋转angle度
					bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
							m, true);// 从新生成图片

				}
				// photo.setImageBitmap(bitmap);
			}
		}
		return bitmap;
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// 源图片的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			// 计算出实际宽高和目标宽高的比率
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
			// 一定都会大于等于目标的宽和高。
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	@Override
	public void onPictureSaved(Uri uri) {
		this.uri = uri;
		Toast.makeText(WcreatewhActivity.this, "Saved: " + uri.toString(),
				Toast.LENGTH_SHORT).show();
		System.out.println("Saved:::::::::::::::" + uri.toString());
		// 先回收
		BitmapDrawable bd = (BitmapDrawable) textimageview.getDrawable();
		if (bd != null) {
			Bitmap bp = bd.getBitmap();
			if (bp != null)
				bp.recycle();
			bp = null;
		}

		try {
			bmp = MediaStore.Images.Media.getBitmap(
					WcreatewhActivity.this.getContentResolver(), uri);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// bmp = createAppIconText(bmp,textimageview.text,25);
		textimageview.setImageBitmap(bmp);
		dismissProgressDialog();
		// textimageview.setImageURI(uri);
	}

	public void back(View paramView) {
		this.vf.setInAnimation(this, R.anim.in_from_right);
		this.vf.setOutAnimation(this, R.anim.out_to_left);
		this.vf.setClipChildren(true);
		switch (this.step.ordinal()) {
		default:
			return;
		case 0:

			this.textimageview.setImageBitmap(null);
			this.gpuImage.deleteImage();
//			if (this.bmp != null)
//				this.bmp.recycle();

			finish();
			return;
		case 1:
			this.step = WCreateStep.WStepFilter;
			progessview.setImageResource(R.drawable.w_progress_half);
			EditText localEditText2 = (EditText) findViewById(R.id.wc_inputtext);
			((InputMethodManager) getSystemService("input_method"))
					.hideSoftInputFromInputMethod(
							localEditText2.getWindowToken(), 0);
			this.vf.showPrevious();
			return;
		case 2:
			this.vf.showPrevious();
			this.step = WCreateStep.WStepInput;
			wcnext.setText("Next");
			progessview.setImageResource(R.drawable.w_progress_threequarter);
			EditText localEditText1 = (EditText) findViewById(R.id.wc_inputtext);
			((InputMethodManager) getSystemService("input_method"))
					.showSoftInput(localEditText1, 0);
			localEditText1.requestFocus();
			return;
		case 3:

			this.vf.showPrevious();
			this.step = WCreateStep.WStepPlace;
			wcnext.setText("Next");
			return;
		}
	}

	public void next(View paramView) {
		this.vf.setInAnimation(this, R.anim.in_from_right);
		this.vf.setOutAnimation(this, R.anim.out_to_left);
		this.vf.setClipChildren(true);
		switch (this.step.ordinal()) {
		default:

		case 0:

			this.step = WCreateStep.WStepInput;
			progessview.setImageResource(R.drawable.w_progress_half);
			EditText editwc = (EditText) findViewById(R.id.wc_inputtext);
			editwc.requestFocus();
			((InputMethodManager) getSystemService("input_method"))
					.showSoftInput(editwc, 0);
			// GPUImage mGPUImage = new GPUImage(this);
			// mGPUImage.set
			// mGPUImageView.get.saveToPictures("GPUImage",
			// "ImageWithFilter.jpg", this) ;
			// showLoadingProgressDialog();

			this.vf.showNext();
			return;
		case 1:
			whtextEditText3 = (EditText) findViewById(R.id.wc_inputtext);
			this.whispertext = whtextEditText3.getText().toString();
			textimageview.text = whispertext;
//			bmp.recycle();
//			bmp=null;
			textimageview.setImageBitmap(gpuImage.getBitmapWithFilterApplied());
			// Bitmap bitmap = getViewBitmap(mGPUImageView);
			// BitmapDrawable drawable = R.drawable.w_back_icon;
			// Bitmap bitmap = drawable.getBitmap();

			// textimageview.setImageResource(R.drawable.w_back_icon);
			// mGPUImageView.setImage(bitmap);
			this.location = (String) ((Spinner) findViewById(R.id.wc_locspinner))
					.getSelectedItem();

			// this.mGPUImageView.setText(this.whispertext);
			this.step = WCreateStep.WStepPlace;
			progessview.setImageResource(R.drawable.w_progress_threequarter);
			((InputMethodManager) getSystemService("input_method"))
					.hideSoftInputFromWindow(whtextEditText3.getWindowToken(),
							0);
			if (WPrefs.isRegistered(this))
				wcnext.setText("Finish");
			// write the text ont the pic
			// setTextOnPic(tp);
			this.vf.showNext();
			return;
		case 2:
			progessview.setImageResource(R.drawable.w_progress_full);
			// if (!WPrefs.isRegistered(this))
			// {
			// wcnext.setText("Finish");
			// // String str3 = WPrefs.nickname(this);
			// // if (str3 != null)
			// // ((EditText)findViewById(2131165219)).setText(str3);
			// // this.vf.showNext();
			// // this.step = WCreateStep.WStepRegister;
			// // return;
			// // }
			this.step = WCreateStep.WStepFinal;

			createWhisper();
			return;
		case 3:

			EditText wc_pin = (EditText) findViewById(R.id.wc_pin);
			String str1 = wc_pin.getText().toString();
			EditText wc_pin_confirm = (EditText) findViewById(R.id.wc_pin_confirm);
			String str2 = wc_pin_confirm.getText().toString();
			if (str1.length() != 4) {
				wc_pin.setText("");
				wc_pin.setError("This must be 4 numbers");
				return;
			}
			if (str2.length() != 4) {
				wc_pin_confirm.setText("");
				wc_pin.setError("Your PINs don't match");
				return;
			}
			if (!str1.equals(str2)) {
				wc_pin_confirm.setText("");
				wc_pin_confirm.setError("Your PINs don't match");
				return;
			}
			// register();
			this.step = WCreateStep.WStepFinal;
			return;
		}

	}

	// public void setFont(WFont paramWFont) {
	// switch (paramWFont.ordinal()) {
	// default:
	// case 0:
	// this.tf = Typeface.createFromAsset(this.getAssets(),
	// "justrealizebold.ttf");
	// this.renderFontSize = 64.0F;
	// this.fontColor = -1;
	// this.renderStrokeWidth = 10.67F;
	// this.strokeColor = -16777216;
	// break;
	// case 1:
	// this.tf = Typeface.createFromAsset(this.getAssets(), "upright.otf");
	// this.renderFontSize = 87.110001F;
	// this.fontColor = -1;
	// this.strokeColor = -16777216;
	// this.renderStrokeWidth = 8.88F;
	// break;
	// case 2:
	// this.tf = Typeface.createFromAsset(this.getAssets(), "century.ttf");
	// this.renderFontSize = 67.559998F;
	// this.fontColor = -1;
	// this.strokeColor = -16777216;
	// this.renderStrokeWidth = 3.47F;
	// break;
	// case 3:
	// this.tf = Typeface.createFromAsset(this.getAssets(),
	// "cassannet.otf");
	// this.renderFontSize = 74.660004F;
	// this.renderStrokeWidth = 5.33F;
	// this.fontColor = -16777216;
	// this.strokeColor = -1;
	// break;
	// }
	//
	//
	// this.tp.setColor(this.fontColor);
	// this.tp.setStrokeWidth(this.strokeWidth);
	// this.tp.setTextSize(this.textSize);
	// this.tp.setDither(true);
	// this.tp.setAntiAlias(true);
	// this.tp.setTypeface(this.tf);
	// this.tp.setTextAlign(Paint.Align.CENTER);
	// this.tp.setStyle(Paint.Style.FILL);
	// this.tp.setStrokeCap(Paint.Cap.SQUARE);
	// this.tp.setStrokeJoin(Paint.Join.BEVEL);
	// // if (this.text != null)
	// // {
	// // fittedString();
	// // Log.v("W", "Saved Offset Bounds: " +
	// // this.textBounds.flattenToString());
	// // invalidate();
	// // }
	// setTextOnPic(tp);
	// }

	// public void setTextOnPic(Paint paint) {
	//
	// Bitmap bitmap = null;
	// try {
	// bitmap = MediaStore.Images.Media.getBitmap(
	// this.getContentResolver(), uri);
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// int width = bitmap.getWidth(), hight = bitmap.getHeight();
	// System.out.println("宽" + width + "高" + hight);
	//
	// Bitmap icon = Bitmap
	// .createBitmap(width, hight, Bitmap.Config.ARGB_8888); // 建立一个空的BItMap
	// Canvas canvas = new Canvas(icon);// 初始化画布绘制的图像到icon上
	//
	// // 建立画笔
	// // photoPaint.setDither(true); //获取跟清晰的图像采样
	// // photoPaint.setFilterBitmap(true);//过滤一些
	//
	// Rect src = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
	// Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
	// canvas.drawBitmap(bitmap, src, dst, paint);// 将photo 缩放或则扩大到
	// // dst使用的填充区photoPaint
	//
	// // Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG |
	// // Paint.DEV_KERN_TEXT_FLAG);//设置画笔
	// paint.setTextSize(20.0f);// 字体大小
	// paint.setTypeface(Typeface.DEFAULT_BOLD);// 采用默认的宽度
	// paint.setColor(Color.RED);// 采用的颜色
	//
	//
	//
	// Rect bounds = new Rect();
	//
	// paint.getTextBounds(whispertext, 0, whispertext.length(), bounds);
	// int x = (bitmap.getWidth() - bounds.width())/2;
	// int y = (bitmap.getHeight() + bounds.height())/2;
	//
	// if(textX !=0||textY !=0){
	// x = textX;
	// y = textY;
	// }
	// canvas.drawText(whispertext,x, y , paint);
	// // textPaint.setShadowLayer(3f, 1,
	// //
	// 1,this.getResources().getColor(android.R.color.background_dark));//影音的设置
	// canvas.drawText("dsfdsfsfd", 200, 200, paint);// 绘制上去字，开始未知x,y采用那只笔绘制
	// // canvas.save(Canvas.ALL_SAVE_FLAG);
	// // canvas.restore();
	//
	// textimageview.setImageBitmap(icon);
	//
	// //mGPUImageView.saveToPictures("test", "test", null);
	// // mGPUImageView.saveToPictures(folderName, fileName, listener);
	// //saveMyBitmap(icon, "test");
	// }

	public void saveMyBitmap(Bitmap bitmap, String bitName) {
		File f = new File(Environment.getExternalStorageDirectory().getPath()
				+ bitName + ".png");
		System.out.println(Environment.getExternalStorageDirectory().getPath()
				+ bitName + ".png");
		FileOutputStream fOut = null;
		try {
			f.createNewFile();
			fOut = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			fOut.flush();
			fOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {
		switch (requestCode) {
		case REQUEST_PICK_IMAGE:
			if (resultCode == RESULT_OK) {
				handleImage(data.getData());
			} else {
				finish();
			}
			break;

		default:
			super.onActivityResult(requestCode, resultCode, data);
			break;
		}
	}

	@Override
	public void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

	}

	public static Bitmap getPicFromBytes(byte[] bytes,
			BitmapFactory.Options opts) {

		if (bytes != null)
			if (opts != null)
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
						opts);
			else
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return null;
	}

	public static byte[] readStream(InputStream inStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;

	}

	private void handleImage(final Uri selectedImage) {
		gpuImage.setImage(selectedImage);
	}

	/**
	 * 把一个View的对象转换成bitmap
	 */
	// static Bitmap getViewBitmap(View v) {
	//
	// v.clearFocus();
	// v.setPressed(false);
	//
	// // 能画缓存就返回false
	// boolean willNotCache = v.willNotCacheDrawing();
	// v.setWillNotCacheDrawing(false);
	// int color = v.getDrawingCacheBackgroundColor();
	// v.setDrawingCacheBackgroundColor(0);
	// if (color != 0) {
	// v.destroyDrawingCache();
	// }
	// v.buildDrawingCache();
	// Bitmap cacheBitmap = v.getDrawingCache();
	// if (cacheBitmap == null) {
	// Log.e("getViewBitmap", "failed getViewBitmap(" + v + ")",
	// new RuntimeException());
	// return null;
	// }
	// Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
	// // Restore the view
	// v.destroyDrawingCache();
	// v.setWillNotCacheDrawing(willNotCache);
	// v.setDrawingCacheBackgroundColor(color);
	// return bitmap;
	// }

	public static Bitmap convertViewToBitmap(View view) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		return bitmap;
	}

	static enum WCreateStep {
		WStepFilter, WStepInput, WStepPlace, WStepRegister, WStepFinal;

	}

	public static enum WFont {
		WJustRealize, WUpright, WCentury, WCassenet;

	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	void createWhisper() {

		new PostMessageTask().execute();

	}

	Bitmap createAppIconText(Bitmap icon, String txt, float txtSize) {
		Bitmap canvasBitmap = Bitmap.createBitmap(icon.getWidth(),
				icon.getHeight(), Config.ARGB_8888);
		int width = canvasBitmap.getWidth();
		Canvas canvas = new Canvas(canvasBitmap);
		canvas.drawBitmap(icon, 0, 0, null);
		TextPaint paint = new TextPaint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(txtSize);
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		StaticLayout layout = new StaticLayout(txt, 0, txt.length(), paint,
				width, Alignment.ALIGN_CENTER, 1.0F, 0.0F, true,
				TruncateAt.END, width);
		// canvas.translate(0, 62);
		layout.draw(canvas);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		return canvasBitmap;
	}

	@Override
	public void onStop() {
		// if( myBitmap != null){
		// myBitmap.recycle();
		// myBitmap = null;
		// }
		// mGPUImageView =null;
		BitmapDrawable bd = (BitmapDrawable) textimageview.getDrawable();
		if (bd != null) {
			Bitmap bp = bd.getBitmap();
			if (bp != null)
				bp.recycle();
			bp = null;
		}
		gpuImage.deleteImage();
		// textimageview =null;
		super.onStop();

	}

	@Override
	public void onStart() {

		super.onStart();

	}

	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		;
		matrix.postRotate(angle);
		System.out.println("angle2=" + angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		bitmap = null;
		return resizedBitmap;
	}

	private class PostMessageTask extends AsyncTask<Void, Void, String> {

		private MultiValueMap<String, Object> formData;
		private ProgressDialog progressDialog;

		private boolean destroyed = false;
		File fileNam;

		@Override
		protected void onPreExecute() {
			showLoadingProgressDialog();

			String path = Environment.getExternalStorageDirectory().getPath()
					+ File.separator + "whisper.png";
			fileNam = new File(path);
			if (!fileNam.exists()) {

				// 方式一：
				try {
					fileNam.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("fileNam " + fileNam.toString());
			}
			//
			//
			// formData = new LinkedMultiValueMap<String, Object>();
			//
			//
			// // ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// // //int options = 80;// 个人喜欢从80开始,
			// // //finabmp.compress(Bitmap.CompressFormat.PNG, options, baos);
			// // //while (baos.toByteArray().length / 1024 > 100) {
			// // //baos.reset();
			// // finabmp.compress(Bitmap.CompressFormat.PNG, 30, baos);
			// // //}
			// //
			// // FileOutputStream fos = new FileOutputStream(fileNam);
			// // fos.write(baos.toByteArray());
			// // fos.flush();
			// // fos.close();
			// //
			// // baos.close();
			// // finabmp.recycle();
			//
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// Resource resource = new FileSystemResource(fileNam.toString());
			// // System.out.println("fileNam " + fileNam.toString());
			// // PictureUtil.bitmapToString(filePath)
			// // populate the data to post
			// formData.add("file", resource);
			// formData.add("description", "Spring logo");
			//
			// formData.add("latitude", TogetherApp.getMyLocation()
			// .getLatitudeE6() + "");
			// formData.add("longtitude", TogetherApp.getMyLocation()
			// .getLongitudeE6() + "");
			// if (!picId.equals("")) {
			// formData.add("parentID", picId);
			// }

		}

		@Override
		protected String doInBackground(Void... params) {
			String path = Environment.getExternalStorageDirectory().getPath()
					+ File.separator + "whisper.png";

			// ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final String url = getString(R.string.base_uri) + "postformdata";
			//
			try {
				// Bitmap finabmp = textimageview.createAppIconText(gpuImage
				// .getBitmapWithFilterApplied());
				//
				// finabmp.compress(CompressFormat.PNG, 30, baos);
				return sendData(url);
				// HttpClientUtil client = new HttpClientUtil(url);
				// client.connectForMultipart();
				// client.addFormPart("latitude", TogetherApp.getMyLocation()
				// .getLatitudeE6() + "");
				// client.addFormPart("longtitude", TogetherApp.getMyLocation()
				// .getLongitudeE6() + "");
				// client.addFilePart("file", fileNam.toString(),
				// baos.toByteArray());
				// if (!picId.equals("")) {
				// client.addFormPart("parentID", picId);
				//
				// }
				// client.finishMultipart();
				// String data = client.getResponse();
				// return data;
			} catch (Throwable t) {
				t.printStackTrace();
			}
			// System.out.println("url " + url.toString());
			// System.out.println("parnentId:" + picId);
			// HttpHeaders requestHeaders = new HttpHeaders();
			//
			// // Sending multipart/form-data
			// requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
			// requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);
			//
			// // Populate the MultiValueMap being serialized and headers in an
			// // HttpEntity object to use for the request
			// HttpEntity<MultiValueMap<String, Object>> requestEntity = new
			// HttpEntity<MultiValueMap<String, Object>>(
			// formData, requestHeaders);
			//
			// // Create a new RestTemplate instance
			// RestTemplate restTemplate = new RestTemplate(true);
			// restTemplate.getMessageConverters().add(
			// new MappingJackson2HttpMessageConverter());
			// // Make the network request, posting the message, expecting a
			// // String in response from the server
			// ResponseEntity<String> response = restTemplate.exchange(url,
			// HttpMethod.POST, requestEntity, String.class);
			//
			// // Return the response body to display to the user
			// return response.getBody();
			// finally {
			// try {
			// baos.close();
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			dismissProgressDialog();
			showResult(result);
		}

	}

	public String sendData(String url) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileOutputStream localFileOutputStream = null;
		try {

			String path = Environment.getExternalStorageDirectory().getPath()
					+ File.separator + "whisper.jpg";
			File fileNam = new File(path);
			if (!fileNam.exists()) {

				// 方式一：
				try {
					fileNam.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("fileNam " + fileNam.toString());
			}
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpPost httpPost = new HttpPost(url);
			MultipartEntity entity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			Bitmap finabmp = textimageview.createAppIconText(gpuImage
					.getBitmapWithFilterApplied());

			localFileOutputStream = new FileOutputStream(fileNam);
			finabmp.compress(Bitmap.CompressFormat.JPEG, 30,
					localFileOutputStream);
			localFileOutputStream.flush();
			localFileOutputStream.close();
			finabmp.recycle();
			// finabmp.compress(CompressFormat.PNG, 0, baos);
			// byte[] data = baos.toByteArray();
			// baos.flush();
			// baos.close();
			// sending a String param;
			entity.addPart("longtitude", new StringBody(TogetherApp
					.getMyLocation().getLongitudeE6() + ""));
			entity.addPart("latitude", new StringBody(TogetherApp
					.getMyLocation().getLatitudeE6() + ""));
			entity.addPart("create_time",  new StringBody(new Date().toString()));
			if (!picId.equals("")) {
				entity.addPart("parentID", new StringBody(picId + ""));
			} else {
				entity.addPart("parentID", new StringBody(0 + ""));
			}
			entity.addPart("file", new FileBody(fileNam));
			// entity.addPart("file", new InputStreamKnownSizeBody(
			// new ByteArrayInputStream(data), data.length, "image/png",
			// "test.png"));

			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost, localContext);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "UTF-8"));
			String sResponse = reader.readLine();
			Log.d("dsfdsfsf", sResponse);
			return sResponse;

		} catch (Exception e) {
			e.printStackTrace();
			Log.v("myApp", "Some error came up");

		} finally {
			
		}
		return null;
	}

	private void showResult(String result) {
		if (result != null) {
			// display a notification to the user with the response information
			Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "I got null, something happened!",
					Toast.LENGTH_LONG).show();
		}
	}

	class InputStreamKnownSizeBody extends InputStreamBody {
		private int lenght;

		public InputStreamKnownSizeBody(final InputStream in, final int lenght,
				final String mimeType, final String filename) {
			super(in, mimeType, filename);
			this.lenght = lenght;
		}

		@Override
		public long getContentLength() {
			return this.lenght;
		}
	}
}
