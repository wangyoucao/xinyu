package com.my.views;

import java.util.Vector;

import together.activity.R;
import together.activity.WcreatewhActivity.WFont;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TextImageView extends ImageView{
	
	int textX;
	int textY;
	public String text = "";
	float renderFontSize;
	float renderStrokeWidth;
	int fontColor;
	TextPaint tp = new TextPaint();
	int strokeColor;
	float strokeWidth;
	float textSize;
	Typeface tf;
    int textShowWidth;
    public  static  int m_iTextHeight; //文本的高度
    public  static  int m_iTextWidth;//文本的宽度
    private float LineSpace = 0;//行间距
    public Bitmap icon ;
	public TextImageView(Context context) {
		super(context);
		
	}
	public TextImageView(Context context, AttributeSet set)
	  {
	   
	    super(context,set); 
        TypedArray typedArray = context.obtainStyledAttributes(set, R.styleable.CYTextView);
        int width = typedArray.getInt(R.styleable. CYTextView_textwidth, 320);
        float textsize = typedArray.getDimension(R.styleable. CYTextView_textSize, 24);
        int textcolor = typedArray.getColor(R.styleable. CYTextView_textColor, -1442840576);
        float linespace = typedArray.getDimension(R.styleable. CYTextView_lineSpacingExtra, 15);
        int typeface = typedArray.getColor(R.styleable. CYTextView_typeface, 0);
       
        typedArray.recycle();
	  }

	  public TextImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
	  {
	    super(paramContext, paramAttributeSet, paramInt);
	  }
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		textX = (int) event.getX();
		textY = (int) event.getY();
		this.invalidate();
		return true;
	}
    @Override
	public void onDraw(Canvas canvas){
    	 super.onDraw(canvas);
    	

    	tp.setTextSize(45.0f);// 字体大小
		//tp.setTypeface(Typeface.DEFAULT_BOLD);// 采用默认的宽度
		//tp.setColor(Color.BLACK);// 采用的颜
		
		Rect bounds = new Rect();
		int x,y;
		tp.getTextBounds(text, 0, text.length(), bounds);  
		x = (this.getWidth() - bounds.width())/2;
		y = (this.getHeight() + bounds.height())/2;
		if(textX ==0||textY ==0) {
			textX =x;
			textY = y;
		}
		canvas.drawText("大家好", textX, textY, tp);
		//canvas.save(Canvas.ALL_SAVE_FLAG);
	   // canvas.restore();
	    
		int width = this.getWidth();
		StaticLayout layout = new StaticLayout(text, tp, width, Alignment.ALIGN_NORMAL, 1.5F, 0.0F, false);
		//canvas.drawText(layout.getText().toString(), textX-1, textY-1, tp);
		//
		canvas.translate(textX, textY);
		System.out.println("X ="+textX);
		System.out.println("Y ="+textY);
	    layout.draw(canvas);
	    //canvas. 
	   //canvas.save(Canvas.ALL_SAVE_FLAG);
	  //  canvas.restore();
	   
    }
    
    public void setFont(WFont paramWFont) {
		switch (paramWFont.ordinal()) {
		default:
		case 0:
			this.tf = Typeface.createFromAsset(getContext().getAssets(),
					"fzyingbixingshu.ttf");
			this.renderFontSize = 64.0F;
			this.fontColor = Color.BLACK;
			this.renderStrokeWidth = 10.67F;
			this.strokeColor = -16777216;
			break;
		case 1:
			this.tf = Typeface.createFromAsset(getContext().getAssets(), "fzjingleijianti.ttf");
			this.renderFontSize = 87.110001F;
			this.fontColor = Color.WHITE;
			this.tp.setFakeBoldText(true); 
			
			this.strokeColor = -16777216;
			this.renderStrokeWidth = 8.88F;
			break;
		case 2:
			this.tf = Typeface.createFromAsset(getContext().getAssets(), "hkshaonvziti.ttf");
			this.renderFontSize = 67.559998F;
			this.fontColor = Color.BLACK;
			this.strokeColor = -16777216;
			this.renderStrokeWidth = 3.47F;
			break;
		case 3:
			this.tf = Typeface.createFromAsset(getContext().getAssets(),
					"hkwawaziti.ttf");
			this.renderFontSize = 74.660004F;
			this.renderStrokeWidth = 5.33F;
			this.fontColor = Color.WHITE;
			this.strokeColor = -1;
			break;
		}

		
		this.tp.setColor(this.fontColor);
		this.tp.setStrokeWidth(this.strokeWidth);
		this.tp.setTextSize(this.textSize);
		this.tp.setDither(true);
		this.tp.setAntiAlias(true);
		this.tp.setTypeface(this.tf);
		this.tp.setTextAlign(Paint.Align.CENTER);
		this.tp.setStyle(Paint.Style.FILL);
		this.tp.setStrokeCap(Paint.Cap.SQUARE);
		this.tp.setStrokeJoin(Paint.Join.BEVEL);
		Rect bounds = new Rect();
		 
		tp.getTextBounds(text, 0, text.length(), bounds);  
		textX = (this.getWidth() - bounds.width())/2;
		textY = (this.getHeight() + bounds.height())/2;
		
		 if (this.text != null)
		 {
		// fittedString();
		// Log.v("W", "Saved Offset Bounds: " +
		// this.textBounds.flattenToString();
		 invalidate();
		 }
		//setTextOnPic(tp);
	}
    
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
//    {         
//        int measuredHeight = measureHeight(heightMeasureSpec);         
//        int measuredWidth = measureWidth(widthMeasureSpec);          
//        this.setMeasuredDimension(measuredWidth, measuredHeight);
//        this.setLayoutParams(new LinearLayout.LayoutParams(measuredWidth,measuredHeight));
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    } 
//                
//    private int measureHeight(int measureSpec)
//    { 
//        int specMode = MeasureSpec.getMode(measureSpec);         
//        int specSize = MeasureSpec.getSize(measureSpec);                  
//        // Default size if no limits are specified. 
//        initHeight();
//        int result = m_iTextHeight;         
//        if (specMode == MeasureSpec.AT_MOST){        
//            // Calculate the ideal size of your         
//            // control within this maximum size.         
//            // If your control fills the available          
//            // space return the outer bound.         
//            result = specSize;          
//        }else if (specMode == MeasureSpec.EXACTLY){          
//            // If your control can fit within these bounds return that value.           
//            result = specSize;          
//        }          
//        return result;           
//    } 
//   
//    private void initHeight()
//    {
//        //设置 CY TextView的初始高度为0
//        m_iTextHeight=0;
//       
//        //大概计算 CY TextView所需高度
//        FontMetrics fm = tp.getFontMetrics();       
//        int m_iFontHeight = (int) Math.ceil(fm.descent - fm.top) + (int)LineSpace;
//        int line=0;
//        int istart=0;
//       
//        int w=0;
//        for (int i = 0; i < text.length(); i++)
//        {
//            char ch = text.charAt(i);
//            float[] widths = new float[1];
//            String srt = String.valueOf(ch);
//            tp.getTextWidths(srt, widths);
//            if (ch == '\n'){
//                line++;
//                istart = i + 1;
//                w = 0;
//            }else{
//                w += (int) (Math.ceil(widths[0]));
//                if (w > m_iTextWidth){
//                    line++;
//                    istart = i;
//                    i--;
//                    w = 0;
//                }else{
//                    if (i == (text.length() - 1)){
//                        line++;
//                    }
//                }
//            }
//        }
//        m_iTextHeight=(line)*m_iFontHeight+2;
//    }
//                
//    private int measureWidth(int measureSpec)
//    { 
//        int specMode = MeasureSpec.getMode(measureSpec);          
//        int specSize = MeasureSpec.getSize(measureSpec);            
//         
//        // Default size if no limits are specified.         
//        int result = 500;         
//        if (specMode == MeasureSpec.AT_MOST){         
//            // Calculate the ideal size of your control          
//            // within this maximum size.        
//            // If your control fills the available space        
//            // return the outer bound.        
//            result = specSize;         
//        }else if (specMode == MeasureSpec.EXACTLY){          
//            // If your control can fit within these bounds return that value.          
//            result = specSize;           
//        }          
//        return result;         
//    }
    
    protected void onMeasure(int paramInt1, int paramInt2)
    {
      int i = View.MeasureSpec.getSize(paramInt1);
      int j = View.MeasureSpec.getSize(paramInt2);
      
      Log.v("W", "OnMeasure: " + i + "w " + j + "h");
    
      if ((j > 920) && (i > 640))
      {
        float f1 = j / 920.0F;
        float f2 = i / 640.0F;
        float f3 = f2;
        if (f1 < f2)
          f3 = f1;
        j = (int)(920.0F * f3);
        i = (int)(640.0F * f3);
      }
      if ((i == 0) && (j == 0))
      {
        i = 920;
        j = 640;
        setMeasuredDimension(920, 640);
      }
     
        Log.v("TextImage", "Set w" + i + " h" + j);
       
        if (i == 0)
        {
          i = (int)(0.6956522F * j);
          setMeasuredDimension(i, j);
        }
        else if (j == 0)
        {
          j = (int)(i / 0.6956522F);
          setMeasuredDimension(i, j);
        }
        else if ((i >= 640) && (j >= 920))
        {
          setMeasuredDimension(i, j);
        }
        else if (i / 640.0F > j / 920.0F)
        {
          i = (int)(0.6956522F * j);
          setMeasuredDimension(i, j);
        }
        else
        {
          j = (int)(i / 0.6956522F);
          setMeasuredDimension(i, j);
        }
//        
        
    }
    
   public  Bitmap createAppIconText(Bitmap icon)
	{
	    
    	Bitmap canvasBitmap = Bitmap.createBitmap(icon.getWidth(), icon.getHeight(), Config.ARGB_8888);
	    int width = canvasBitmap.getWidth();
	    Canvas canvas = new Canvas(canvasBitmap);
	    canvas.drawBitmap(icon, 0, 0, null);
	    Rect bounds = new Rect();
		int x,y;
		tp.getTextBounds(text, 0, text.length(), bounds);  
		x = (this.getWidth() - bounds.width())/2;
		y = (this.getHeight() + bounds.height())/2;
		if(textX ==0||textY ==0) {
			textX =x;
			textY = y;
		}
		
		StaticLayout layout = new StaticLayout(text, tp, width, Alignment.ALIGN_CENTER, 1.0F, 0.0F, false);
		
		canvas.translate(textX-x, textY);
	    layout.draw(canvas);
	    canvas.save(Canvas.ALL_SAVE_FLAG);
	    canvas.restore();
	    return canvasBitmap;
	}
	 
}
