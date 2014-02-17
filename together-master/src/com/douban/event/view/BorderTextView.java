package com.douban.event.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.TextView;

public class BorderTextView extends TextView
{
  protected boolean borderableBottom;
  protected boolean borderableLeft;
  protected boolean borderableRight;
  protected Paint mBorderPaint;

  public BorderTextView(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public BorderTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  public BorderTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }

  public void init()
  {
    this.mBorderPaint = new Paint();
    this.mBorderPaint.setColor(-2763307);
    this.mBorderPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    this.mBorderPaint.setStrokeWidth(4.0F);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.borderableLeft)
      paramCanvas.drawLine(0.0F, getTop(), 0.0F, getBottom(), this.mBorderPaint);
    if (this.borderableRight)
      paramCanvas.drawLine(getMeasuredWidth(), getTop(), getMeasuredWidth(), getBottom(), this.mBorderPaint);
    if (this.borderableBottom)
      paramCanvas.drawLine(0.0F, getBottom(), getMeasuredWidth(), getBottom(), this.mBorderPaint);
  }

  public void setBorder(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this.borderableLeft = paramBoolean1;
    this.borderableRight = paramBoolean2;
    this.borderableBottom = paramBoolean3;
    invalidate();
  }
}

/* Location:           E:\androdproject\dex2jar-0.0.9.13\classes_dex2jar.jar
 * Qualified Name:     com.douban.event.view.BorderTextView
 * JD-Core Version:    0.6.2
 */